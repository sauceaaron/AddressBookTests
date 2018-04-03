import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddressBookTests
{
	@Test(dataProvider = "deviceCapabilities")
	public void openAddressBook(DesiredCapabilities capabilities)
	{
		System.out.println("capabilities: " + capabilities);
	}

	@DataProvider(name="deviceCapabilities", parallel=true)
	public Iterator<Object> getDeviceCapabilities()
	{
		List<DesiredCapabilities> multiCapabilities = new ArrayList<>();
		multiCapabilities.add( new DesiredCapabilities(){{
			setCapability("platformName", "iOS");
			setCapability("platformVersion", "10.2");
			setCapability("deviceName", "iPhone SE");
			setCapability("testobject_api_key", "48B2B5909EE14A1EB6F40A0A748A1FF0");
		}});

		multiCapabilities.add(new DesiredCapabilities(){{
			setCapability("platformName", "Android");
			setCapability("platformVersion", "6.0.3");
			setCapability("deviceName", "Samsung Galaxy S6");
			setCapability("testobject_api_key", "86006954598A4FE3B11725E193F45DA0");
		}});

		return new ArrayList<Object>(multiCapabilities).iterator();
	}
}
