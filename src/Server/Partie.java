package Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import json.CreateJson;
import model.Agent.Agent;
import model.utils.AgentAction;

public class Partie {
	String name;
	private BomberManGameServ game;
	private ArrayList<ThreadedClient> gamers = new ArrayList<>();
	private int nbGamers;
	private Server serveur;

	public Partie(String input, Server serv) {
		System.out.println("création de la partie " + input);
		name = input;
		serveur = serv;
		game = new BomberManGameServ(input, this);
		nbGamers = game.getListBomberMan().size();

	}

	public boolean isFool() {
		if (gamers.size() == nbGamers) {
			return true;
		} else {
			return false;
		}
	}

	public void addGamer(ThreadedClient client) {
		if (!isFool()) {
			gamers.add(client);
			client.setParty(this);
			System.out.println("nb Joueurs :" + gamers.size());
			if (isFool()) {
				System.out.println("lancement de la partie " + name);
				setIdClient();
				broadcast(CreateJson.JsonGameBegin(game));
				game.launch();
			}
		}
	}

	private void setIdClient() {
		List<Agent> bomberman = this.game.getListBomberMan();
		int i = 0;
		for (ThreadedClient t : gamers) {
			t.setId(bomberman.get(i).getId());
		}
	}

	public void endTurn(GameChange game) {
		broadcast(CreateJson.JsonGamePartie(game));
	}

	public void broadcast(String message) {
		for (ThreadedClient gamer : gamers) {
			gamer.sendMessage(message);
		}
	}

	public void action(AgentAction action, ThreadedClient client) {
		int i = 0;
		for (ThreadedClient t : gamers) {
			if (t == client) {
				game.stratBomberman.setAction(action, i + "");
				System.out.println("action partie " + i);
			}
			++i;
		}
	}

	public void remove(ThreadedClient threadedClient) {
		if (gamers.contains(threadedClient)) {
			gamers.remove(threadedClient);
			if (gamers.size() == 0) {
				game.setRunning(false);
				serveur.removeParty(this);
			}
		}
	}

	public void gamOver() {
		switch (game.getEnd()) {
		case "YOU DIED":
			messageOver(0);
			break;
		case "YOU WIN":
			messageOver(1);
			break;
		default:
			messageOver(0);
			break;
		}

	}

	public void messageOver(int i) {
		try (InputStream input = new FileInputStream(System.getProperty("user.dir") + "/config.properties")) {
			for (ThreadedClient t : gamers) {
				String dataSet = "name=" + t.getNameClient();
				Properties prop = new Properties();
				prop.load(input);
				String web_name = prop.getProperty("web.name");
				String token = prop.getProperty("web.change.token");

				System.out.println("envoie end " + t.getIdClient() + " " + i);
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest
						.newBuilder().uri(URI.create("http://localhost:8080/" + web_name + "/EndPartyApi?name="
								+ t.getNameClient() + "&win=" + i))
						.POST(BodyPublishers.ofString("")).header("Accept", token).build();

				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

				System.out.println(response.body());
				if (response.body().contains("success")) {
					System.out.println("succés fin de partie pour " + t.getNameClient());
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
