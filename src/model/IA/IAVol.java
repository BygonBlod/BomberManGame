package model.IA;

import model.Agent.Agent;
import model.Agent.AgentBird;
import model.BomberManGame;
import model.IA.IARandom;
import model.IA.IAStrategi;

public class IAVol implements IAStrategi {

	@Override
	public void Action(Agent a, BomberManGame game) {
		AgentBird bird=(AgentBird)a;
		IsAnBomberMan(2,a,game);
		if(bird.isContact()) {
			new IARandom().Action(a, game);
		}
	}
	public void IsAnBomberMan(int rayon,Agent a,BomberManGame game) {
		AgentBird bird=(AgentBird)a;
		for(Agent b:game.getListBomberMan()) {
			 if(a.getX()>=b.getX()-rayon && a.getX()<=b.getX()+rayon) {
				 if(a.getY()>=b.getY()-rayon && a.getY()<=b.getY()+rayon)
					 bird.setContact(true);
			 }
		}
	}

}
