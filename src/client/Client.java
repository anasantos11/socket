package client;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private String ipServer;
	private int port;
	private Socket socketCli;

	public Client(String ip, int port) {
		this.ipServer = ip;
		this.port = port;
	}

	public void connect() {
		try {
			System.out.println(" -C- Conectando ao servidor ->" + ipServer + ":" + port);
			this.socketCli = new Socket(ipServer, port);
			System.out.println(" -C- Detalhes conexao :" + this.socketCli.toString());
		} catch (Exception e) {
			System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
		}
	}

	public void sendMessage(String text) {
		try {
			ObjectOutputStream sCliOut = new ObjectOutputStream(this.socketCli.getOutputStream());
			sCliOut.writeObject(text);
			System.out.println(" -C- Enviando mensagem...");
			sCliOut.flush();
		} catch (Exception e) {
			System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
		}

	}

	public void receptMessage() {
		try {
			ObjectInputStream sCliIn = new ObjectInputStream(this.socketCli.getInputStream());
			System.out.println(" -C- Recebendo mensagem...");
			System.out.println(sCliIn.readObject().toString());
		} catch (Exception e) {
			System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
		}
	}

	public void closeConnection() {
		try {
			this.socketCli.close();
			System.out.println(" -C- Conexao finalizada...");
		} catch (Exception e) {
			System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
		}
	}
}
