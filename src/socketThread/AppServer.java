package socketThread;
import java.io.IOException;

public class AppServer {

    public static void main(String[] args) throws IOException {
        new Server(12345).run();
    }

    
}