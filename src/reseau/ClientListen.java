package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ClientListen extends Thread{
	private Socket socket;
	private BufferedReader entree;
	private Client client;
	
	public ClientListen(Socket s,Client c) {
		this.socket=s;
		this.client=c;
	}
	public void run(){
		try {
			entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    while (socket.isConnected()) {   
		    	String data;
				data = entree.readLine();
				if(data==null)socket.close();
				else
	            	System.out.println(data);
		    }
		} catch (SocketException e) {
			System.out.println("Vous êtes déconnecter");
			client.deleteClient();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
