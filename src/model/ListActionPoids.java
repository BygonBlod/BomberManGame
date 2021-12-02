package model;

import java.util.ArrayList;

import utils.AgentAction;
import utils.InfoBomb;

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
					if(this.contains(AgentAction.PUT_BOMB))this.change(AgentAction.PUT_BOMB,-1);
				}else {
					int distX=a.getX()-bomb.getX();
					int distY=a.getY()-bomb.getY();
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
	
	/**
	 * retourne l'ennemi le plus proche 
	 * @param a
	 * @param game
	 * @return
	 */
	public Agent searchEmmeni(Agent a, BomberManGame game) {
		Agent res=null;
		for(Agent ennemi:game.getListEnnemi()) {
			if(res==null)res=ennemi;
			else {
				double distEnnemi=Math.sqrt(Math.pow((a.getX()-ennemi.getX()),2)+Math.pow((a.getY()-ennemi.getY()),2));
				double distRes=Math.sqrt(Math.pow((a.getX()-res.getX()),2)+Math.pow((a.getY()-res.getY()),2));
				if(distEnnemi<distRes)res=ennemi;
			}
		}
		return res;
	}

}
