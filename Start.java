import java.io.IOException;

public class Start 
{
	public static void main(String[] args) throws IOException
	{
		Process process = Runtime.getRuntime().exec("java -Xms150m -Xmx400m -jar files.jar",null);
	}
}