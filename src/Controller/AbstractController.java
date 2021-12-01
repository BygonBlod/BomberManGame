package Controller;

import model.Game;

public abstract class AbstractController {
	Game game;
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public void restart() {
		game.pause();
		game.gameOver();
		game.init();
		game.launch();
		
	}
	public void step() {
		game.step();
		
	}
	public void play() {
		game.launch();
		
	}
	public void pause() {
		game.pause();
		
	}
	public  void setSpeed(double speed) {
		game.setSleep((long)speed*1000);
	}

}
