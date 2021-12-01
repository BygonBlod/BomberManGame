package Controller;

import View.ViewBomberManGame;
import View.ViewCommand;
import model.BomberManGame;
import model.Game;
import model.IABomberManManuel;
import utils.AgentAction;

public class ControllerBomberManGame extends AbstractController {
	ViewBomberManGame view;
	ViewCommand view2;
	private String plateau;
	
	public ControllerBomberManGame(String p) {
		this.plateau=p;
		this.game=new BomberManGame(500,p);;
		try {
			view=new ViewBomberManGame(plateau,this);
			view2=new ViewCommand(this,view.getTaillex(),view.getTailley());
			game.addObserver(view);
			game.addObserver(view2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		game.launch();
	}
	public void moveBomberman(AgentAction action) {
		((BomberManGame) game).stratBomberman.setAction(action);;
		
	}

}
