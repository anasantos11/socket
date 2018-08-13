package socketThread;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Receiver implements Runnable {
	private InputStream inputStream;

	public Receiver(InputStream inputStream) throws IOException {
		this.inputStream = inputStream;
	}

	public void run() {
		//RECEBE MENSAGEM DO SERVIDOR E IMPRIME NA TELA
		Scanner s = new Scanner(this.inputStream);
		while (s.hasNextLine()) {
			System.out.println(s.nextLine());
		}
		s.close();
	}
}