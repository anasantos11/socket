

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceiver extends Thread {
	private Socket socketCli;
	private BufferedReader input;
	private String text;

	public ClientReceiver(Socket socketCli) throws IOException {
		this.socketCli = socketCli;
	}

	@Override
	public void run() {
		try {
			this.input = new BufferedReader(new InputStreamReader(this.socketCli.getInputStream()));
			while (true)
            {
				this.text  = input.readLine();
				if (this.text == null) {
					System.exit(0);
				}
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
