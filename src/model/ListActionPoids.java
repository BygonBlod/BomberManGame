package model;

import java.util.ArrayList;

import utils.AgentAction;
import utils.InfoBomb;

public class ListActionPoids extends ArrayList<ActionPoids> {
	
	
	
	public boolean contains(AgentAction a) {
		for(ActionPoids action : this) {
			if(action.getAction()==a)return true;
		}
		return false;
	}
	
	public void remove(AgentAction a) {
		for(ActionPoids action: this) {
			if(action.getAction()==a)this.remove(action);
		}
	}
	
	public void cheminPossible(Agent a,BomberManGame game){
		if(game.IsLegalMove(a, AgentAction.MOVE_DOWN))this.add(new ActionPoids(AgentAction.MOVE_DOWN,0));
		if(game.IsLegalMove(a, AgentAction.MOVE_UP))this.add(new ActionPoids(AgentAction.MOVE_UP,0));
		if(game.IsLegalMove(a, AgentAction.MOVE_RIGHT))this.add(new ActionPoids(AgentAction.MOVE_RIGHT,0));
		if(game.IsLegalMove(a, AgentAction.MOVE_LEFT))this.add(new ActionPoids(AgentAction.MOVE_LEFT,0));
		if(game.IsLegalMove(a, AgentAction.PUT_BOMB))this.add(new ActionPoids(AgentAction.PUT_BOMB,0));
		
	}
	
	public void eviterBomb(Agent a,BomberManGame game){
		for(InfoBomb bomb:game.getListBomb()) {
			if(a.getX()<bomb.getX()+5 && a.getX()>bomb.getX()-5 && a.getY()<bomb.getY()+5 && a.getY()>bomb.getY()-5) {
				if (a.getX()==bomb.getX() && a.getY()==bomb.getY()) {
					if(this.contains(AgentAction.PUT_BOMB))this.remove(AgentAction.PUT_BOMB);
				}else {
					int distX=a.getX()-bomb.getX();
					int distY=a.getY()-bomb.getY();
					if(distX<0) {
						
					}
					/*if(a.getX()-bomb.getX()<0 && a.getY()-bomb.getY()<0) {
						if(actions.contains(AgentAction.MOVE_RIGHT))actions.remove(AgentAction.MOVE_RIGHT);
						if(actions.contains(AgentAction.MOVE_DOWN))actions.remove(AgentAction.MOVE_DOWN);
					}
					else {
						if(a.getX()-bomb.getX()>=0 && a.getY()-bomb.getY()<0) {
							if(actions.contains(AgentAction.MOVE_RIGHT))actions.remove(AgentAction.MOVE_RIGHT);
							if(actions.contains(AgentAction.MOVE_UP))actions.remove(AgentAction.MOVE_UP);
						}
						else {
							if(a.getX()-bomb.getX()<0 && a.getY()-bomb.getY()>=0) {
								if(actions.contains(AgentAction.MOVE_LEFT))actions.remove(AgentAction.MOVE_LEFT);
								if(actions.contains(AgentAction.MOVE_DOWN))actions.remove(AgentAction.MOVE_DOWN);
							}
							else {
								if(a.getX()-bomb.getX()>=0 && a.getY()-bomb.getY()>=0) {
									if(actions.contains(AgentAction.MOVE_LEFT))actions.remove(AgentAction.MOVE_LEFT);
									if(actions.contains(AgentAction.MOVE_UP))actions.remove(AgentAction.MOVE_UP);
								}
							}
						}
					}*/
				}
			}
		}
		
	}

}
