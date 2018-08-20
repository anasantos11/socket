package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientManagement extends Thread {
	private Socket socketCli;
	private PrintStream output;
	private BufferedReader input;
	private BufferedReader keyboard;
	private String text;

	public ClientManagement(Socket socketCli) throws IOException {
		this.socketCli = socketCli;
	}

	@Override
	public void run() {
		try {

			this.keyboard = new BufferedReader(new InputStreamReader(System.in));
			this.input = new BufferedReader(new InputStreamReader(this.socketCli.getInputStream()));
			this.output = new PrintStream(this.socketCli.getOutputStream());

			while (true)
            {
				this.text  = input.readLine();
                System.out.println(this.text);
            }

		} catch (IOException e) {
			System.out.println(" -C- Ocorreu uma Falha... .. ." + " IOException: " + e);
		}
	}

	public void closeConnection() {
		try {
			this.socketCli.close();
			System.out.println(" -C- Conexao finalizada...");
		} catch (Exception e) {
			System.out.println(" -C- Ocorreu uma Falha... .. ." + " IOException: " + e);
		}
	}

}
