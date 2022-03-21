package Server;

import java.util.List;

import model.BomberManGame;
import model.utils.Wall;

public class BomberManGameServ extends BomberManGame {
	private GameChange change;
	private Partie partie;

	public BomberManGameServ(String input, Partie p) {
		super(input);
		this.partie = p;
		change = new GameChange(this.getWalls().length, this.getWalls()[0].length);
	}

	@Override
	public void gameOver() {
		super.gameOver();
		partie.gamOver();
		if (thread != null) {
			this.thread.stop();
		}
	}

	@Override
	protected void takeTurn() {
		super.takeTurn();
		// envoie a tout les joueur le jeu;
		change.set(this);
		partie.endTurn(change);
		change.reset();// à la fin réinitialise les changement
	}

	@Override
	public void noBreakable(int x, int y) {
		List<Wall> res = change.getListBreakable();
		res.add(new Wall(x, y));
		change.setListBreakable(res);

	}

}
