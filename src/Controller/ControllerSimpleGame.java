package Controller;

import View.ViewCommand;
import View.ViewSimpleGame;
import model.Game;

public class ControllerSimpleGame extends AbstractController {
	ViewSimpleGame viewSimple;
	ViewCommand viewCommand;
	public ControllerSimpleGame(Game game) {
		this.game=game;
		viewSimple=new ViewSimpleGame();
		viewCommand=new ViewCommand(this,0,0);
		game.addObserver(viewCommand);
		game.addObserver(viewSimple);
		
	}
	@Override
	public void changeLvl() {
		// TODO Auto-generated method stub
		
	}


}
