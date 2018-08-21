import java.net.ServerSocket;
import java.net.Socket;

public class AppServer {
	private static ServerSocket serverSocket;

	public static void main(String[] args) {
		connect();
	}

	public static void connect() {
		try {
			serverSocket = new ServerSocket(7001);
			System.out.println("S - Aguardando conexao (P:" + 7001 + ")...");
			while (true) {
				Socket connection = serverSocket.accept();
				Thread t = new Server(connection);
				t.start();
			}
		} catch (Exception e) {
			System.out.println("S - O seguinte problema ocorreu : " + e.toString());
		}
	}
}
