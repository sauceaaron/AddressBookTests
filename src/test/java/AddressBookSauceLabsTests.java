import com.saucelabs.saucerest.SauceREST;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddressBookSauceLabsTests
{
	String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
	String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	String SAUCE_URL = "https://SAUCE_USERNAME:SAUCE_ACCESS_KEY@ondemand.saucelabs.com:443/wd/hub"
			.replace("SAUCE_USERNAME", SAUCE_USERNAME)
			.replace("SAUCE_ACCESS_KEY", SAUCE_ACCESS_KEY);

	ThreadLocal<AppiumDriver> threadlocal = new ThreadLocal<>();
	SauceREST api;

	@Test(dataProvider = "sauceCapabilitiesFromFile")
	public void OpenAddressBook(DeviceCapabilities device, Method method) throws IOException, URISyntaxException
	{
		api = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY);
		String filename = ResourceLoader.getAbsolutePath(device.app);
		api.uploadFile(new File(filename), device.app, false);

		URL appiumURL = getAppiumURL();

		DesiredCapabilities capabilities = getDesiredCapabilities(device);
		capabilities.setCapability("name", method.getName());

		System.out.println(capabilities);

		AppiumDriver appium = new AppiumDriver(appiumURL, capabilities);
		threadlocal.set(appium);
	}

	@DataProvider(name = "sauceCapabilitiesFromFile", parallel = true)
	public Iterator<Object> getDeviceCapabilitiesFromFile() throws IOException
	{
		String json = ResourceLoader.ReadFile("SauceLabsDevices.json");
		List<DeviceCapabilities> multiCapabilities = DeviceCapabilities.parseMultiCapabilities(json);

		return new ArrayList<Object>(multiCapabilities).iterator();
	}

	@AfterMethod
	public void after(ITestResult result)
	{
		if (threadlocal.get() == null)
		{
			return; // no driver to report result
		}

		String sessionId = threadlocal.get().getSessionId().toString();

		if (result.isSuccess())
		{
			api.jobPassed(sessionId);
		}
		else
		{
			api.jobFailed(sessionId);
		}

		threadlocal.get().quit();
		threadlocal.remove();
	}


	public URL getAppiumURL()
	{
		try
		{
			return new URL(SAUCE_URL);
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
	}

	private DesiredCapabilities getDesiredCapabilities(DeviceCapabilities deviceCapabilities)
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platformName", deviceCapabilities.platformName);
		capabilities.setCapability("platformVersion", deviceCapabilities.platformVersion);
		capabilities.setCapability("deviceName", deviceCapabilities.deviceName);
		capabilities.setCapability("deviceOrientation", deviceCapabilities.deviceOrientation);
		capabilities.setCapability("app", "sauce-storage:" + deviceCapabilities.app);

		return capabilities;
	}
}