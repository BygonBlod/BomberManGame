package model;

import java.util.Observable;
import java.util.Observer;

public class SimpleGame extends Game  {

	public SimpleGame(int maxTurn) {
		super(maxTurn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initializeGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		System.out.println("vous avez fini le jeu");
		
	}

	@Override
	protected boolean gameContinue() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void takeTurn() {
		// TODO Auto-generated method stub
		System.out.println("Tour "+turn+" du jeu en cours");
		
	}

	@Override
	public String getEnd() {
		// TODO Auto-generated method stub
		return null;
	}


}
