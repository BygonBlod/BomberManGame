package Server;

import model.BomberManGame;

public class BomberManGameServ extends BomberManGame {
	private GameChange change;
	private Partie partie;

	public BomberManGameServ(String input, Partie p) {
		super(input);
		this.partie = p;
	}

	@Override
	protected void takeTurn() {
		super.takeTurn();
		// envoie a tout les joueur le jeu;
		change.set(this);
		partie.endTurn(change);
		change.reset();// à la fin
	}

}
