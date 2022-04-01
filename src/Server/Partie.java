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

/**
 * 
 * @author Antonin
 *
 */
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
		game = new BomberManGameServ("/home/etud/eclipse-workspace/BomberMan/layouts/" + input, this);
		nbGamers = game.getListBomberMan().size();

	}

	/**
	 * vérifie si il y a assez de joueurs dans la partie pour jouer tous les
	 * bombermans
	 * 
	 * @return
	 */
	public boolean isFool() {
		if (gamers.size() == nbGamers) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ajout d'un joueur à cette partie
	 * 
	 * @param client
	 */
	public void addGamer(ThreadedClient client) {
		if (!isFool()) {
			gamers.add(client);
			client.setParty(this);
			System.out.println("nb Joueurs :" + gamers.size());
			if (isFool()) {// si la partie est pleine ont lance le jeu
				System.out.println("lancement de la partie " + name);
				setIdClient();
				broadcast(CreateJson.JsonGameBegin(game));
				game.launch();
			}
		}
	}

	/**
	 * ajoute à chaque client un id pour qu'il puisse affecter ses actions au bon
	 * bomberman
	 */
	private void setIdClient() {
		List<Agent> bomberman = this.game.getListBomberMan();
		int i = 0;
		for (ThreadedClient t : gamers) {
			t.setId(bomberman.get(i).getId());
		}
	}

	/**
	 * fin de tour ont envoie la partie à tous les clients
	 * 
	 * @param game
	 */
	public void endTurn(GameChange game) {
		broadcast(CreateJson.JsonGamePartie(game));
	}

	/**
	 * envoie d'un message à tous les clients
	 * 
	 * @param message
	 */
	public void broadcast(String message) {
		for (ThreadedClient gamer : gamers) {
			gamer.sendMessage(message);
		}
	}

	/*
	 * set l'action au client qui a fait l'action
	 */
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

	/**
	 * supprime le client qui s'est déconnecter et si la partie n'as plus de joueur
	 * elle s'arrête et se supprime de la liste des parties
	 * 
	 * @param threadedClient
	 */
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

	/**
	 * envoie un message à l'api pour mettre à jour le nombre de parti de chaque
	 * joueur
	 * 
	 * @param i
	 */
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
