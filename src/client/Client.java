package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

import server.Response;

public class Client {
	private Socket socketCli;
	private BufferedReader input;
	private PrintStream output;
	private BufferedReader keyboard;
	private ClientManagement thread;
	private String text;

	public Client(String ipServer, int port) throws UnknownHostException, IOException {
		this.socketCli = SocketFactory.getDefault().createSocket(ipServer, port);
		this.keyboard = new BufferedReader(new InputStreamReader(System.in));
	}

	public void connect() {
		try {
			this.output = new PrintStream(this.socketCli.getOutputStream());
			this.input = new BufferedReader(new InputStreamReader(this.socketCli.getInputStream()));

			System.out.println("Digite seu nome: ");
			this.text = this.keyboard.readLine();
			this.output.println(this.text.toUpperCase());

			Response response = Response.findFromString(input.readLine());
			if (response != Response.CONNECTED_SUCCESS) {
				System.out.println(response.getValue());
				this.socketCli.close();
				System.exit(0);
			} else {
				System.out.println(response.getValue());
				this.thread = new ClientManagement(this.socketCli);
				this.thread.start();
				
				while (true) {
					this.text = this.keyboard.readLine();
					this.output.println(this.text);
				}
			}
		} catch (Exception e) {
			System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
		}
	}

}
