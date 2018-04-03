import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class DeviceCapabilities
{
	public String platformName;
	public String platformVersion;
	public String deviceName;
	public String deviceOrientation;
	public String testobject_api_key;
	public String app;

	private static Gson parser = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

	private static Type DeviceCapabilitiesType = new TypeToken<DeviceCapabilities>(){}.getType();
	private static Type DeviceCapabilitiesListType = new TypeToken<List<DeviceCapabilities>>(){}.getType();

	public static List<DeviceCapabilities> parseMultiCapabilities(String json)
	{
		return parser.fromJson(json, DeviceCapabilitiesListType);
	}

	public static List<DesiredCapabilities> multiCapabilities(String json)
	{
		return parseMultiCapabilities(json)
				.stream()
				.map(device -> device.toDesiredCapabilities())
				.collect(Collectors.toList());
	}

	public DeviceCapabilities fromJson(String json)
	{
		return parser.fromJson(json, DeviceCapabilitiesType);
	}

	public String toJson()
	{
		return parser.toJson(this);
	}

	public String toString()
	{
		return toJson();
	}

	public DesiredCapabilities toDesiredCapabilities()
	{
		return new DesiredCapabilities()
		{{
			setCapability("platformName", platformName);
			setCapability("platformVersion", platformVersion);
			setCapability("deviceName", deviceName);
			setCapability("deviceOrientation", deviceOrientation);
			setCapability("app", app);
			setCapability("testobject_api_key", testobject_api_key);

		}};
	}
}