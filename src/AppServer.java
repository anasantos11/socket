import server.Server;

public class AppServer {
	public static void main (String[] args){
		Server s = new Server(7001);
		s.connect();
	}
}
