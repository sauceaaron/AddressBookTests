import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceLoader
{
	static ClassLoader classLoader = ResourceLoader.class.getClassLoader();

	public static String ReadFile(String resourceFileName) throws IOException
	{
		String path = ClassLoader.getSystemResource(resourceFileName).getFile();
		return new String(Files.readAllBytes(Paths.get(path)), Charset.defaultCharset());
	}

	public static String getAbsolutePath(String resourceFileName) throws IOException, URISyntaxException
	{
		return Paths.get(classLoader.getResource(resourceFileName).toURI()).toFile().getAbsolutePath();
	}
}
