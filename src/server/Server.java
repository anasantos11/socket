package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private int port;
	private List<Socket> clients;
	private ServerSocket serverSocket;
	private Socket clientSocket;

	public Server(int port) {
		this.port = port;
		this.clients = new ArrayList<>();
	}

	public void connect() {
		try {
			System.out.println(" -S- Aguardando conexao (P:" + this.port + ")...");
			this.serverSocket = new ServerSocket(this.port);
			clientSocket = this.serverSocket.accept();
			this.clients.add(clientSocket);
			System.out.println(" -S- Conectado ao cliente ->" + clientSocket.toString());
		} catch (Exception e) {
			System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
		}
	}

	public void receptMenssageClient() {
		try {
			ObjectInputStream sServIn = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println(" -S- Recebendo mensagem...");
			Object msgIn = sServIn.readObject();
			System.out.println(" -S- Recebido: " + msgIn.toString());
			sendReturnClient(msgIn);
		} catch (Exception e) {
			System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
		}
	}

	public void sendReturnClient(Object msgIn) {
		try {
			ObjectOutputStream sSerOut = new ObjectOutputStream(clientSocket.getOutputStream());
			sSerOut.writeObject("RETORNO " + msgIn.toString() + " - TCP"); // ESCREVE NO PACOTE
			System.out.println(" -S- Enviando mensagem resposta...");
			sSerOut.flush(); // ENVIA O PACOTE
		} catch (Exception e) {
			System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
		}
	}
	
	public void closeConnection() {
		try {
			this.serverSocket.close();
			clientSocket.close();
			System.out.println(" -S- Conexao finalizada...");
		} catch (Exception e) {
			System.out.println(" -C- O seguinte problema ocorreu : \n" + e.toString());
		}
	}

	public void getClientsConnect() {
		System.out.println(" -S- Clientes conectados: " + this.clients.toString());
	}

}
