package socketThread;
import java.io.IOException;

public class AppClient {
	public static void main(String[] args) {
		try {
			new Client("Ana Paula", "127.0.0.1", 12345).run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}