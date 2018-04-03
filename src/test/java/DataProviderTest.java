import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class DataProviderTest
{
	@Test(dataProviderClass = DeviceCapabilitiesDataProvider.class, dataProvider = "deviceCapabilitiesFromFile")
	public void getMultiCapabilitiesFromFile(DesiredCapabilities desiredCapabilities)
	{
		System.out.println("desiredCapabilities: " + desiredCapabilities);
	}
}
