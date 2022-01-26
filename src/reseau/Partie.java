package reseau;

import java.util.ArrayList;

import model.BomberManGame;

public class Partie {
	String name;
	private BomberManGame game;
	private ArrayList<ThreadedClient> gamers = new ArrayList<>();
	private int nbGamers;

	public Partie(String input) {
		name = input;
		game = new BomberManGame(input);
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
			if (isFool()) {
				System.out.println("lancement de la partie " + name);
				for (ThreadedClient gamer : gamers) {
					gamer.sendMessage(CreateJson.JsonGameBegin(game));
				}
				game.launch();

			}
		}

	}

}
