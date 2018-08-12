import client.Client;

public class AppClient {
	public static void main (String[] args){
		Client c = new Client("192.168.0.7", 7001);
		c.connect();
		c.sendMessage("Oii");
		c.receptMessage();
		c.closeConnection();
	}
}
