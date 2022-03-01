package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import json.CreateJson;
import json.DeserializationJson;
import model.utils.AgentAction;

public class ThreadedClient extends Thread {
	private Server server;
	private Socket socket;
	private BufferedReader entree;
	private PrintWriter sortie;
	private String nameClient;
	private ArrayList<String> received;
	private AgentAction action;
	private Partie party;

	public ThreadedClient(Socket socket, Server s) {
		this.socket = socket;
		this.server = s;

		try {
			entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sortie = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		sortie.println(message);
		sortie.flush();
	}

	public void run() {

		System.out.println("New client on " + socket.getLocalPort());

		if (socket.isConnected()) {
			try {
				while (socket.isConnected()) {
					String data = entree.readLine();
					String message = "";
					JSONParser jsonParser = new JSONParser();
					if (data.length() > 0) {
						// System.out.println(data);

						JSONObject json = (JSONObject) jsonParser.parse(data);
						String type = (String) json.get("type");
						System.out.println("type :" + type);
						switch (type) {
						case "name":
							received = DeserializationJson.JsonName(json);
							nameClient = received.get(0);
							if (received.size() == 2) {
								try {
									HttpClient client = HttpClient.newHttpClient();

									HttpRequest request = HttpRequest.newBuilder()
											.uri(URI.create("http://127.0.0.1:8080/Oui/ConnexionApi?name="
													+ received.get(0) + "&pwd=" + received.get(1)))
											.GET().header("Accept", "application.json").build();

									HttpResponse<String> response = client.send(request,
											HttpResponse.BodyHandlers.ofString());

									System.out.println(response.body());
									if (response.body().contains("success")) {
										sendMessage(CreateJson.JsonConnect(true));
										System.out.println("succés");
									}
								} catch (IOException | InterruptedException e) {
									e.printStackTrace();
								}
							}
							break;
						case "tchat":
							received = DeserializationJson.JsonTchat(json);
							Tchat();
							break;
						case "select":
							received = DeserializationJson.JsonSelect(json);
							Select();
							break;
						case "action":
							action = DeserializationJson.JsonAction(json);
							System.out.println("action " + action);
							party.action(action, this);
						}
					}
				}
			} catch (SocketException e) {
				deconnect();
			} catch (Exception e) {
				e.printStackTrace();
				deconnect();
			}
		}

	}

	public void deconnect() {
		System.out.println("connexion fermer avec " + nameClient);
		server.removeClient(this);
		if (party != null) {
			party.remove(this);
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public String getNameClient() {
		return nameClient;
	}

	public void Tchat() {
		try {
			if (nameClient.equals(received.get(0))) {
				String message = received.get(1);
				if (message.contains("/close")) {
					socket.close();
				} else {
					System.out.println("[TCHAT]" + nameClient + "> " + message);
					server.broadcast(CreateJson.JsonTchat(nameClient, message), socket);
				}
			}
		} catch (SocketException e) {
			deconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Select() {
		System.out.println(nameClient + " " + received.get(0));
		if (nameClient.equals(received.get(0))) {
			String message = received.get(1);
			String[] partieUrl = message.split("/");
			String partie = partieUrl[partieUrl.length - 1];
			server.addGame(message, this);
			System.out.println("[GAME]:choix niveau " + nameClient + "> " + partie);
		}

	}

	public void setParty(Partie p) {
		this.party = p;
	}

	public void setNameClient(String n) {
		this.nameClient = n;
	}

}