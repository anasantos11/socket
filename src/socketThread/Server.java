package socketThread;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private int port;
	private ServerSocket server;
	private List<PrintStream> clientsMessages;

	public Server(int port) {
		this.port = port;
		this.clientsMessages = new ArrayList<PrintStream>();
	}

	public void run() throws IOException {
		this.server = new ServerSocket(this.port);
		System.out.println("-S- Servidor conectado na porta: " + this.port);

		while (true) {
			acceptsNewClientConnection();
		}

	}

	public void acceptsNewClientConnection() throws IOException {
		//RECEBE CONEXÃO E CRIA UM NOVO CANAL NO SENTIDO CONTRÁRIO (SERVIDOR -> CLIENTE)
		Socket client = server.accept();
		System.out.println("-S- Novo cliente conectado: " + client.getInetAddress().getHostAddress());

		//CRIA UM PACOTE DE SAÍDA PARA ENVIAR MENSAGENS, ASSOCIANDO-O À CONEXÃO E ADICIONA A LISTA
		PrintStream ps = new PrintStream(client.getOutputStream());
		this.clientsMessages.add(ps);

		//CRIA UM PACOTE DE ENTRADA PARA RECEBER MENSAGENS, ASSOCIADO À CONEXÃO, COM NOVO TRATADOR DO CLIENTE EM UMA NOVA THREAD
		MessageTreatmentServer tc = new MessageTreatmentServer(client, this);
		new Thread(tc).start();
	}

	public void shareMessage(String message) {
		// envia msg para todos conectados
		this.clientsMessages.forEach(client -> {
			client.println(message);
		});
	}
}
