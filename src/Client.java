

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

public class Client {
	private Socket socketCli;
	private BufferedReader input;
	private PrintStream output;
	private BufferedReader keyboard;
	private ClientReceiver thread;
	private String text;

	public Client(String ipServer, int port) throws UnknownHostException, IOException {
		this.socketCli = SocketFactory.getDefault().createSocket(ipServer, port);

	}

	public void connect() {
		try {
			this.keyboard = new BufferedReader(new InputStreamReader(System.in));
			this.output = new PrintStream(this.socketCli.getOutputStream());
			this.input = new BufferedReader(new InputStreamReader(this.socketCli.getInputStream()));

			System.out.println("Conecte-se ao servidor");
			this.text = this.keyboard.readLine();
			this.output.println(this.text.toUpperCase());

			Response response = Response.findFromString(input.readLine());
			if (response != Response.CONNECTED_SUCCESS) {
				System.out.println(response.getValue());
				this.socketCli.close();
				System.exit(0);
			} else {
				System.out.println(response.getValue());
				this.thread = new ClientReceiver(this.socketCli);
				this.thread.start();

				while (true) {
					this.text = this.keyboard.readLine();
					if (this.text == null) {
						System.exit(0);
					}
					this.output.println(this.text);
				}
			}
		} catch (Exception e) {
			System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
		}
	}

}
