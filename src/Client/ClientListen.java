package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import json.DeserializationJson;

public class ClientListen extends Thread {
	private Socket socket;
	private BufferedReader entree;
	private Client client;

	public ClientListen(Socket s, Client c) {
		this.socket = s;
		this.client = c;
	}

	/**
	 * lorsqu'il reçoit un message du serveur il regarde son type et agis en
	 * fonction d'elle
	 */
	public void run() {
		try {
			entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (socket.isConnected()) {

				JSONParser jsonParser = new JSONParser();
				String data = entree.readLine();
				String message = "";
				if (data.length() > 0) {
					JSONObject json = (JSONObject) jsonParser.parse(data);
					String type = (String) json.get("type");
					switch (type) {
					case "connectResponse":// connexion
						client.setConnected(DeserializationJson.JsonConnectResponse(json));
						break;
					case "tchat":// tchat
						Tchat(DeserializationJson.JsonTchat(json));
						break;
					case "GameBegin":// début de partie
						client.changeGame(DeserializationJson.JsonGameBegin(json));
						break;
					case "GameParty":// pendant la partie
						client.changeGame(DeserializationJson.JsonGamePartie(json));
						break;
					}
				}
			}
		} catch (SocketException e) {
			System.out.println("Vous êtes déconnecter");
			client.deleteClient();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void Tchat(ArrayList<String> jsonTchat) {
		System.out.println("[TCHAT] " + jsonTchat.get(0) + ">" + jsonTchat.get(1));

	}
}
