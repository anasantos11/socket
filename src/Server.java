
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Server extends Thread {
	private Socket connectionClient;
	private String nameClient;
	private BufferedReader input;
	private PrintStream output;
	private String text;

	private static Map<String, PrintStream> CONNECTED_CLIENTS = new HashMap<String, PrintStream>();
	private static Set<String> NAME_CONNECTED_CLIENTS = new LinkedHashSet<String>();

	public Server(Socket socket) {
		this.connectionClient = socket;

	}

	@Override
	public void run() {
		try {
			this.input = new BufferedReader(new InputStreamReader(this.connectionClient.getInputStream()));
			this.output = new PrintStream(this.connectionClient.getOutputStream());

			String text = input.readLine().toUpperCase();

			String[] split = text.split(":", 2);

			if (split.length == 0 || split.length == 1
					|| Command.getCommand(split[0]) != Command.CONNECT) {
				this.output.println(Response.ERROR_CONNECTION.getValue());
				this.connectionClient.close();
				return;
			}
			this.nameClient = split[1];

			if (clientIsConnect(this.nameClient)) {
				this.output.println(Response.ERROR_NAME_IN_USE.getValue());
				closeClientConnection();
			}
			// Add new connected client
			NAME_CONNECTED_CLIENTS.add(this.nameClient);
			CONNECTED_CLIENTS.put(this.nameClient, this.output);
			this.output.println(Response.CONNECTED_SUCCESS.getValue());

			while (true) {
				this.text = input.readLine();
				checkCommand();
			}

		} catch (Exception e) {
			closeClientConnection();
			
		}
	}

	public void checkCommand() {
		String[] split = text.split(":", 2);
		String message = "";

		if (split.length == 0) {
			this.output.println(Response.UNKNOWN_COMMAND.getValue());
			return;
		}

		String requestedCommand = split[0];
		if (split.length >= 2) {
			message = split[1];
		}

		Command command = Command.getCommand(requestedCommand);
		if (command == null) {
			this.output.println(Response.UNKNOWN_COMMAND.getValue());
			return;
		}
		switch (command) {
		case CONNECT: {
			System.out.println("conectado " + message);
			break;
		}
		case MESSAGE_DIRECT: {
			split = message.split(":", 2);
			if (split.length == 0) {
				this.output.println(Response.NOT_INFORMED_USER_TO_SEND_MESSAGE.getValue());
				return;
			} else if (split.length == 1) {
				this.output.println(Response.NOT_INFORMED_MESSAGE.getValue());
				return;
			}
			String clientToSend = split[0].toUpperCase();
			message = split[1];
			if (!clientIsConnect(clientToSend)) {
				this.output.println(Response.NOT_FOUND_CONNECTED_USER.getValue());
				return;
			}
			sendDirect(clientToSend, message);
			break;
		}
		case MESSAGE_FOR_ALL: {
			if (message.equals("") || split.length == 1) {
				this.output.println(Response.NOT_INFORMED_MESSAGE.getValue());
				return;
			}
			sendToAll(message);
			break;
		}
		case DISCONNECT: {
			removeConnectedClient();
			closeClientConnection();
			break;
		}
		case LIST_ALL_CONNECTED: {
			showConnectedClients();
			break;
		}
		default:
			this.output.println(Response.UNKNOWN_COMMAND.getValue());
			break;
		}

	}

	public boolean clientIsConnect(String name) {
		if (NAME_CONNECTED_CLIENTS.stream().filter(x -> x.equals(name)).count() > 0) {
			return true;
		}
		return false;
	}

	public void sendDirect(String name, String message) {
		CONNECTED_CLIENTS.get(name).println(this.nameClient + " escreveu: " + message);
	}

	public void sendToAll(String message) {
		CONNECTED_CLIENTS.entrySet().forEach(x -> {
			if (!x.getKey().equals(this.nameClient))
				x.getValue().println(this.nameClient + " escreveu: " + message);
		});

	}

	public void showConnectedClients() {
		this.output.println("S - Pessoas conectadas: " + NAME_CONNECTED_CLIENTS.toString());
	}

	public void removeConnectedClient() {
		NAME_CONNECTED_CLIENTS.remove(this.nameClient);
		CONNECTED_CLIENTS.remove(this.nameClient);
		System.out.println("S - Cliente desconectado: " + this.nameClient.toString());
	}

	public void closeClientConnection() {
		try {
			this.connectionClient.close();
			this.output.println("S - Conexão encerrada");
		} catch (IOException e) {
			System.out.println("S - O seguinte problema ocorreu : \n" + e.toString());
		}

	}

}
