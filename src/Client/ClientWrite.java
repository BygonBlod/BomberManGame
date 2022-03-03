package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import json.CreateJson;

public class ClientWrite extends Thread {
	private PrintWriter sortie;
	private Socket socket;
	private String Id;
	private Client client;

	public ClientWrite(Socket s, String id, Client c) {
		this.socket = s;
		this.Id = id;
		this.client = c;
	}

	public void sendMessage(String message) {
		sortie.println(message);
		sortie.flush();
	}

	public void run() {
		try {
			sortie = new PrintWriter(socket.getOutputStream(), true);
			while (socket.isConnected()) {
				String jsonStr = "";
				Scanner scanner = new Scanner(System.in);
				String text = scanner.nextLine();
				switch (text) {
				case "/connect":
					System.out.println("entrer votre pseudo :");
					scanner = new Scanner(System.in);
					String pseudo = scanner.nextLine();
					client.Id = pseudo;
					System.out.println("entrer votre mot de passe :");
					scanner = new Scanner(System.in);
					String pwd = scanner.nextLine();
					jsonStr = CreateJson.JsonName(pseudo, pwd);
					sendMessage(jsonStr);

					break;
				case "/close":
					socket.close();
					break;
				case "/play":
					if (client.isConnected()) {
						System.out.println("essai play");
						client.connect();
					}
					break;
				default:
					if (client.isConnected()) {
						jsonStr = CreateJson.JsonTchat(Id, text);
						sendMessage(jsonStr);
					}
					break;
				}

			}

		} catch (SocketException e) {
			System.out.println("Vous êtes déconnecter");
			client.deleteClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
