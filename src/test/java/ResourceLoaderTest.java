import org.testng.annotations.Test;

import java.io.IOException;

public class ResourceLoaderTest
{
	@Test
	public void loadJsonResource() throws IOException
	{
		String json = ResourceLoader.ReadFile("TestObjectDevices.json");
		System.out.println(json);
	}
}
