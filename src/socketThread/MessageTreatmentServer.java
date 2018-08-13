package socketThread;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class MessageTreatmentServer implements Runnable {
	private Socket client;
    private InputStream inputStream;
    private Server servidor;

    public MessageTreatmentServer(Socket client, Server server) throws IOException {
        this.client = client;
        this.inputStream = client.getInputStream();
        this.servidor = server;
    }

    public void run() {
        // quando chegar uma msg, distribui pra todos    	
        Scanner s = new Scanner(this.inputStream);
        while (s.hasNextLine()) {
            servidor.shareMessage(client.getInetAddress() + " - " + s.nextLine());
        }
        s.close();
    }
}