package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.net.ServerSocketFactory;

public class Server {
	private int port;
	private ServerSocket serverSocket;
	private static Set<ServerClientManagement> NAME_CONNECTED_CLIENTS = new LinkedHashSet<ServerClientManagement>();

	public Server(int port) {
		this.port = port;
		try {
			this.serverSocket = ServerSocketFactory.getDefault().createServerSocket(this.port);
			System.out.println("S - Aguardando conexao (P:" + this.port + ")...");
		} catch (Exception e) {
			System.out.println("S - O seguinte problema ocorreu : " + e.toString());
		}

	}

	public void connect() {
		while (true) {
			acceptsNewClientConnection();
		}
	}

	public void acceptsNewClientConnection() {
		try {
			Socket clientConection = this.serverSocket.accept();
			ServerClientManagement serverClient = new ServerClientManagement(clientConection, this);
			serverClient.start();
		} catch (Exception e) {
			System.out.println("S - O seguinte problema ocorreu : " + e.toString());
		}
	}

	public void checkCommand(ServerClientManagement serverClient, String text) {
		String[] split = text.split(":", 2);
		String requestedCommand = split[0];
		String message = split[1];

		Command command = Command.getCommand(requestedCommand);
		switch (command) {
			case CONNECT: {
				System.out.println("conectado " + message);
				break;
			}
			case DIRECT_MESSAGE: {
				split = message.split(":", 2);
				String clientToSend = split[0];
				message = split[1];
				sendDirect(serverClient, clientToSend, message);
				break;
			}
			case MESSAGE_FOR_ALL: {
				sendToAll(serverClient, message);
				break;
			}
			case DISCONNECT: {
				serverClient.CloseConnection();
				break;
			}
			case LIST_ALL_CONNECTED: {
				showConnectedClients(serverClient);
				break;
			}
			default:
				serverClient.getOutput().println(Response.UNKNOWN_COMMAND.getValue());
		}

	}

	public boolean clientIsConnect(String name) {
		if (NAME_CONNECTED_CLIENTS.stream().filter(x -> name.equals(x.getNameClient())).count() > 0) {
			return true;
		}
		return false;
	}

	public void addNewConnectedClient(ServerClientManagement client) {
		NAME_CONNECTED_CLIENTS.add(client);
		System.out.println("S - Novo cliente conectado: " + client.getNameClient().toString());
	}

	public void removeConnectedClient(ServerClientManagement client) {
		NAME_CONNECTED_CLIENTS.remove(client);
		System.out.println("S - Cliente desconectado ->" + client.getNameClient().toString());
	}

	public void sendDirect(ServerClientManagement serverClient, String name, String message) {
		Optional<ServerClientManagement> clientToSend = NAME_CONNECTED_CLIENTS.stream()
				.filter(x -> x.getNameClient().equals(name.toUpperCase())).findAny();

		if (clientToSend.isPresent())
			clientToSend.get().getOutput().print(serverClient.getNameClient() + " escreveu: " + message + "/n");
		else
			serverClient.getOutput().println(Response.NOT_FOUND_CONNECTED_USER.getValue());
	}

	public void sendToAll(ServerClientManagement serverClient, String message) {
		NAME_CONNECTED_CLIENTS.forEach(x -> {
			if (!x.getNameClient().equals(serverClient.getNameClient()))
				x.getOutput().println(serverClient.getNameClient() + " escreveu: " + message + "/n");
		});

	}

	public void showConnectedClients(ServerClientManagement serverClient) {
		serverClient.getOutput().println("S - Pessoas conectadas: " + getConnectedClients().toString());
	}

	public Set<ServerClientManagement> getConnectedClients() {
		return NAME_CONNECTED_CLIENTS;
	}

	public void closeConnection() {
		try {
			getConnectedClients().forEach(x -> {
				x.CloseConnection();
			});
			this.serverSocket.close();
			System.out.println("S - Conexao finalizada...");
		} catch (Exception e) {
			System.out.println("S - O seguinte problema ocorreu : " + e.toString());
		}
	}

}
