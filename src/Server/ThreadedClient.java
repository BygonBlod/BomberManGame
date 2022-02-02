package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import json.DeserializationJson;

public class ThreadedClient extends Thread {
	private Server server;
	private Socket socket;
	private BufferedReader entree;
	private PrintWriter sortie;
	private String name;
	private ArrayList<String> received;

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
					Gson gson = new GsonBuilder().create();
					String data = entree.readLine();
					String message = "";
					if (data.length() > 0) {
						JsonElement element = gson.fromJson(data, JsonElement.class);
						JsonObject jObj = element.getAsJsonObject();
						JsonObject j2 = gson.fromJson(jObj.getAsJsonObject("tchat"), JsonObject.class);
						if (j2 != null) {
							received = DeserializationJson.JsonTchat(j2);
							Tchat();
						} else {
							j2 = gson.fromJson(jObj.getAsJsonObject("select"), JsonObject.class);
							if (j2 != null) {
								received = DeserializationJson.JsonSelect(j2);
								Select();
							}
						}
					}
				}
			} catch (SocketException e) {
				System.out.println("connexion fermer avec " + name);
				server.removeClient(this);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("null connexion fermer avec " + name);
				server.removeClient(this);
			}
		}

	}

	public Socket getSocket() {
		return socket;
	}

	public String getNameClient() {
		return name;
	}

	public void Tchat() {
		try {
			name = received.get(0);
			String message = received.get(1);
			if (message.contains("/close")) {
				socket.close();
			} else {
				System.out.println("[TCHAT]" + name + "> " + message);
				server.broadcast("[TCHAT]" + name + "> " + message, socket);
			}
		} catch (SocketException e) {
			System.out.println("connexion fermer avec " + name);
			server.removeClient(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Select() {
		name = received.get(0);
		String message = received.get(1);
		String[] partieUrl = message.split("/");
		String partie = partieUrl[partieUrl.length - 1];
		server.addGame(message, this);
		System.out.println("[GAME]:choix niveau " + name + "> " + partie);
		// this.sendMessage("[GAME] vous avez choisi le niveau " + partie);

	}

}