package socketThread;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private String name;
	private String host;
	private int port;
	private Socket client;

	public Client(String name, String host, int porta) {
		this.name = name;
		this.host = host;
		this.port = porta;
	}

	public void run() throws UnknownHostException, IOException {
		this.client = new Socket(this.host, this.port);
		System.out.println("-C- Conectado ao servidor, pronto para enviar a mensagem");

		//CRIA UM PACOTE DE ENTRADA PARA RECEBER MENSAGENS DO SERVIDOR
		Receiver r = new Receiver(this.client.getInputStream());
		new Thread(r).start();

		readKeyboardAndSendMessage();

		this.client.close();
	}

	public void readKeyboardAndSendMessage() throws IOException {
		// lê msgs do teclado e manda pro servidor
		Scanner keyboard = new Scanner(System.in);
		PrintStream outputStream = new PrintStream(this.client.getOutputStream());
		while (keyboard.hasNextLine()) {
			outputStream.println(keyboard.nextLine());
		}
		outputStream.close();
		keyboard.close();
	}

	public String getName() {
		return name;
	}

	public String getHost() {
		return host;
	}

	public int getPorta() {
		return port;
	}

}
