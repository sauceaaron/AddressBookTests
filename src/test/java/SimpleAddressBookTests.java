import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.junit.After;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class SimpleAddressBookTests
{
	String testName;
	AppiumDriver<MobileElement> driver;
	String sessionId;

	static final String TESTOBJECT_APPIUM_SERVER_US = "https://us1.appium.testobject.com/wd/hub";
	static final String TESTOBJECT_APPIUM_SERVER_EU = "https://eu1.appium.testobject.com/wd/hub";

	@BeforeMethod
	public void setup(Method method)
	{
		testName = method.getName();
	}

	@After
	public void teardown(ITestResult result)
	{
		System.out.println(result.getTestName() + " " + result.getStatus());

		if (driver != null)
		{
			driver.quit();
		}
	}

	@Test(dataProviderClass = DeviceCapabilitiesDataProvider.class, dataProvider = "deviceCapabilitiesFromFile")
	public void getMultiCapabilitiesFromFile(DesiredCapabilities desiredCapabilities) throws MalformedURLException
	{
		System.out.println("desiredCapabilities: " + desiredCapabilities);

		URL appiumURL = new URL(TESTOBJECT_APPIUM_SERVER_US);
		desiredCapabilities.setCapability("testobject_suite_name", this.getClass().getSimpleName());
		desiredCapabilities.setCapability("testobject_test_name", this.testName);

		driver = new AppiumDriver<>(appiumURL, desiredCapabilities);
	}
}
