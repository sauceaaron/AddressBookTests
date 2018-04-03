import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeviceCapabilitiesDataProvider
{
	@DataProvider(name="staticDeviceCapabilities", parallel=true)
	public Iterator<Object> getStaticDeviceCapabilities()
	{
		List<DesiredCapabilities> multiCapabilities = new ArrayList<>();

		multiCapabilities.add( new DesiredCapabilities(){{
			setCapability("platformName", "iOS");
			setCapability("platformVersion", "10.2");
			setCapability("deviceName", "iPhone SE");
			setCapability("testobject_api_key", "48B2B5909EE14A1EB6F40A0A748A1FF0");
			setCapability("app", "AddressBookIOS.zip");
		}});

		multiCapabilities.add(new DesiredCapabilities(){{
			setCapability("platformName", "Android");
			setCapability("platformVersion", "6.0.3");
			setCapability("deviceName", "Samsung Galaxy S6");
			setCapability("testobject_api_key", "86006954598A4FE3B11725E193F45DA0");
			setCapability("app", "AddressBookAndroid.apk");
		}});

		return new ArrayList<Object>(multiCapabilities).iterator();
	}

	@DataProvider(name="deviceCapabilitiesFromFile", parallel=true)
	public Iterator<Object> getDeviceCapabilitiesFromFile() throws IOException
	{
		String json = ResourceLoader.ReadFile("devices.json");
		List<DesiredCapabilities> multiCapabilities = DeviceCapabilities.multiCapabilities(json);

		return new ArrayList<Object>(multiCapabilities).iterator();
	}

}
