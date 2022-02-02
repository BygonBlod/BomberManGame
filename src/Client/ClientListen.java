package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import json.DeserializationJson;

public class ClientListen extends Thread {
	private Socket socket;
	private BufferedReader entree;
	private Client client;

	public ClientListen(Socket s, Client c) {
		this.socket = s;
		this.client = c;
	}

	public void run() {
		try {
			entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (socket.isConnected()) {
				Gson gson = new GsonBuilder().create();
				String data = entree.readLine();
				String message = "";
				if (data.length() > 0) {
					JsonElement element = gson.fromJson(data, JsonElement.class);
					JsonObject jObj = element.getAsJsonObject();
					JsonObject j2 = gson.fromJson(jObj.getAsJsonObject("Game"), JsonObject.class);
					System.out.println(data);
					if (j2 != null) {
						client.changeGame(DeserializationJson.JsonGameBegin(j2));
					}
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
