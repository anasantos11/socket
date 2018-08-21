import java.io.IOException;
import java.net.UnknownHostException;

public class AppClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client c = new Client("192.168.0.6", 7001);
		c.connect();
	}
}
