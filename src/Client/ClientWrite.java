package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import View.ViewConnect;
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

	/**
	 * envoie du message vers le serveur
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		sortie.println(message);
		sortie.flush();
	}

	/**
	 * regarde ce qui est entrer dans le terminal et agis en fonction
	 */
	public void run() {
		try {
			sortie = new PrintWriter(socket.getOutputStream(), true);
			ViewConnect view = new ViewConnect(this);
			while (socket.isConnected()) {
				String jsonStr = "";
				Scanner scanner = new Scanner(System.in);
				String text = scanner.nextLine();
				switch (text) {
				/*
				 * case "/connect":
				 * 
				 * break;
				 */
				case "/close":
					socket.close();
					break;
				default:
					if (client.isConnected()) {
						jsonStr = CreateJson.JsonTchat(client.Id, text);
						sendMessage(jsonStr);
					}
					break;
				}

			}

		} catch (SocketException e) {
			System.out.println("Vous êtes déconnecter");
			client.deleteClient();
		} catch (NullPointerException e) {
			System.out.println("Vous êtes déconnecter");
			client.deleteClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * envoie le json de connexion au serveur
	 * 
	 * @param pseudo
	 * @param pwd
	 */
	public void connect(String pseudo, String pwd) {
		String jsonStr = "";
		client.Id = pseudo;
		jsonStr = CreateJson.JsonConnectAnswer(pseudo, pwd);
		sendMessage(jsonStr);
	}
}
