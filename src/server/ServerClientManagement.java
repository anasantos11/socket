package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerClientManagement extends Thread {
	private Socket client;
	private Server server;
	private String nameClient;

	private BufferedReader input;
	private PrintStream output;
	private String text;

	public ServerClientManagement(Socket client, Server server) {
		this.client = client;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			this.input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			this.output = new PrintStream(this.client.getOutputStream());

			this.nameClient = input.readLine().toUpperCase();
			if (this.server.clientIsConnect(this.nameClient)) {
				this.output.println(Response.ERROR_NAME_IN_USE.getValue());
				CloseConnection();
				return;
			}
			this.server.addNewConnectedClient(this);
			this.output.println(Response.CONNECTED_SUCCESS.getValue());

			while (true) {
				this.text = input.readLine();
				this.server.checkCommand(this, this.text);
			}

		} catch (Exception e) {
			System.out.println("S - O seguinte problema ocorreu : \n" + e.toString());
		}
	}

	public void CloseConnection() {
		try {
			this.client.close();
			this.output.println("S - Conexão encerrada");
		} catch (Exception e) {
			System.out.println("S - O seguinte problema ocorreu : \n" + e.toString());
		}

	}

	public String getNameClient() {
		return nameClient;
	}

	public PrintStream getOutput() {
		return output;
	}

}
