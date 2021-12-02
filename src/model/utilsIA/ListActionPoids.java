package model.utilsIA;

import java.util.ArrayList;

import model.Agent.Agent;
import model.BomberManGame;
import model.utils.AgentAction;

public class ListActionPoids extends ArrayList<ActionPoids> {
	private AgentAction up=AgentAction.MOVE_UP;
	private AgentAction down=AgentAction.MOVE_DOWN;
	private AgentAction left=AgentAction.MOVE_LEFT;
	private AgentAction right=AgentAction.MOVE_RIGHT;
	private AgentAction putBomb=AgentAction.PUT_BOMB;
	
	
	
	public boolean contains(AgentAction a) {
		for(ActionPoids action : this) {
			if(action.getAction()==a)return true;
		}
		return false;
	}
	
	public void change(AgentAction a,int poids) {
		int i=0;
		for(ActionPoids action: this) {
			if(action.getAction()==a) {
				this.set(i,new ActionPoids(a,poids));
			}
			i++;
		}
	}
	public int getPoids(AgentAction a) {
		for(ActionPoids action: this) {
			if(action.getAction()==a) {
				return action.getPoids();
			}
		}
		return -1;
	}
	public ListActionPoids getBestActions(){
		ListActionPoids res=new ListActionPoids();
		int best=0;
		for(ActionPoids action:this) {
			if(action.getPoids()>best) {
				res.clear();
				best=action.getPoids();
			}
			if(action.getPoids()==best) {
				res.add(action);
			}
			
		}
		return res;
	}
	
	public void cheminPossible(Agent a, BomberManGame game){
		if(game.IsLegalMove(a, AgentAction.MOVE_DOWN))this.add(new ActionPoids(AgentAction.MOVE_DOWN,0));
		if(game.IsLegalMove(a, AgentAction.MOVE_UP))this.add(new ActionPoids(AgentAction.MOVE_UP,0));
		if(game.IsLegalMove(a, AgentAction.MOVE_RIGHT))this.add(new ActionPoids(AgentAction.MOVE_RIGHT,0));
		if(game.IsLegalMove(a, AgentAction.MOVE_LEFT))this.add(new ActionPoids(AgentAction.MOVE_LEFT,0));
		if(game.IsLegalMove(a, AgentAction.PUT_BOMB))this.add(new ActionPoids(AgentAction.PUT_BOMB,0));
		
	}
	
	public void eviterObjects(Agent a, BomberManGame game, ArrayList<Position> objectToAvoid){
		for(Position pos:objectToAvoid) {

				if (a.getX()==pos.getX() && a.getY()==pos.getY()) {
					if(this.contains(AgentAction.PUT_BOMB))this.change(AgentAction.PUT_BOMB,-1);
				}else {
					int distX=a.getX()-pos.getX();
					int distY=a.getY()-pos.getY();
					if(distX==0) {
						moveLeft(a,game);
						moveRight(a,game);
					}
					if(distY==0) {
						moveUp(a,game);
						moveDown(a,game);
					}
					if(distX<0) {
						moveLeft(a,game);
					}
					else {
						moveRight(a,game);
					}
					if(distY<0) {
						moveUp(a,game);
					}else {
						moveDown(a,game);
					}
				}
		}
	}
	
	public void moveto(Agent a,BomberManGame game,int x,int y) {
		int distX=a.getX()-x;
		int distY=a.getY()-y;
		System.out.println("test "+distX+" "+distY);
		if(distX<=1 && distY<=1 && distX>=-1 && distY>=-1 && game.IsLegalMove(a, putBomb))change(putBomb,this.getPoids(putBomb)+2);
		else {
				if(distX==0) {
					moveUp(a,game);
					moveDown(a,game);
				}
				if(distY==0) {
					moveLeft(a,game);
					moveRight(a,game);
				}
				if(distX<0) {
					moveRight(a,game);				
				}
				else {
					moveLeft(a,game);
				}
				if(distY<0) {
					moveDown(a,game);
				}
				else {
					moveUp(a,game);
				}
			}
	}
	
	//Méthode de déplacement 
	private void moveLeft(Agent a, BomberManGame g) {
		if(g.IsLegalMove(a, left)) {
			change(left,this.getPoids(left)+2);
		}
	}
	private void moveRight(Agent a, BomberManGame g) {
		if(g.IsLegalMove(a,right)) {
			change(right,this.getPoids(right)+2);
		}
	}
	private void moveDown(Agent a, BomberManGame g) {
		if(g.IsLegalMove(a,down)) {
			change(down,this.getPoids(down)+2);
		}

	}
	private void moveUp(Agent a, BomberManGame g) {
		if(g.IsLegalMove(a,up)) {
			change(up,this.getPoids(up)+2);
		}
	}
	


}
