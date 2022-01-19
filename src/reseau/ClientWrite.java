package reseau;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientWrite extends Thread{
	private PrintWriter sortie;
	private Socket socket;
	private String Id;
	private Client client;
	
	public ClientWrite(Socket s,String id,Client c) {
		this.socket=s;
		this.Id=id;
		this.client=c;
	}
	public void run(){
		try {
			sortie=new PrintWriter(socket.getOutputStream(),true);
			while (socket.isConnected()) {    
                String jsonStr="";
                Scanner scanner = new Scanner(System.in);
                String text = scanner.nextLine();
                if(text.contains("/choose")) {
                	String[] textSplit=text.split("/choose");
                	if(textSplit.length==2) {
	                	String lvl=textSplit[1];
	                	jsonStr=CreateJson.JsonSelect(Id, lvl);
	                }
                	else System.out.println("Vous devez utiliser le commande /choose \"votre niveau\"");;
                }
                else {
					jsonStr=CreateJson.JsonTchat(Id, text);
                }
				if(text.length()!=0){
					sortie.println(jsonStr);
					sortie.flush();
					if(text.contains("/close")) {
						socket.close();
					}
				}
            }
			
		} catch (SocketException e) {
			System.out.println("Vous êtes déconnecter");
			client.deleteClient();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
