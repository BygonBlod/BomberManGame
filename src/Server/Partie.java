package Server;

import java.util.ArrayList;

import json.CreateJson;
import model.utils.AgentAction;

public class Partie {
	String name;
	private BomberManGameServ game;
	private ArrayList<ThreadedClient> gamers = new ArrayList<>();
	private int nbGamers;

	public Partie(String input) {
		System.out.println("création de la partie " + input);
		name = input;
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
				broadcast(CreateJson.JsonGameBegin(game));
				game.launch();
			}
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
				game.stratBomberman.setAction(action);
			}
			++i;
		}
	}

}
