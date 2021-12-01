package model;

import java.util.ArrayList;
import java.util.Random;

import utils.AgentAction;

public class IABomberManAggro implements IAStrategi {

	@Override
	public void Action(Agent a, BomberManGame game) {
		if(a.getAgentG().isInvincible()) {
			AgentAction action=movetoEnnemi(a,game);
			if(action==AgentAction.PUT_BOMB)game.putBomb(a);
			else game.moveAgent(a, action);
		}
		else {
			int i=0;
			AgentAction res=null;
			while(res==null) {
				i=new Random().nextInt(5);
				if(i==0 ) {
					if(game.IsLegalMove(a, AgentAction.MOVE_UP))res=AgentAction.MOVE_UP;
				}
				if(i==1 ) {
					if(game.IsLegalMove(a, AgentAction.MOVE_DOWN))res=AgentAction.MOVE_DOWN;
				}
				if(i==2) {
					if(game.IsLegalMove(a, AgentAction.MOVE_LEFT))res=AgentAction.MOVE_LEFT;
				}
				if(i==3) {
					if(game.IsLegalMove(a, AgentAction.MOVE_RIGHT))res=AgentAction.MOVE_RIGHT;
				}
				if(i==4 && game.IsLegalPutBomb(a))res=AgentAction.PUT_BOMB;
			}
			System.out.println(a.getType()+" x"+a.getX()+" y"+a.getY()+"  :"+res);
			if(i<4)game.moveAgent(a, res);
			//else if(res==AgentAction.PUT_BOMB)game.putBomb(a);
		}
		
	}

	private AgentAction movetoEnnemi(Agent a, BomberManGame game) {
		Agent ennemi=searchEmmeni(a,game);
		AgentAction res=AgentAction.PUT_BOMB;
		if(ennemi!=null) {
			System.out.println("test ennemi:"+ennemi.getX()+" "+ennemi.getY());
			int distX=a.getX()-ennemi.getX();
			int distY=a.getY()-ennemi.getY();
			System.out.println("test "+distX+" "+distY);
			if(distX<=1 && distY<=1 && distX>=-1 && distY>=-1 && game.IsLegalMove(a, res))return res;
			else {
				while(res==AgentAction.PUT_BOMB) {
						int nbAlea=new Random().nextInt(2);
						if(distX==0)nbAlea=0;
						if(distY==0)nbAlea=1;
						System.out.println("test nbalea "+nbAlea);
						if(nbAlea==1) {	
							if(distX<0) {
								System.out.println("test distY<0  ");
								AgentAction right=moveRight(a,game);
								if(right!=null)res=right;			
							}
							else {
								System.out.println("test distY>=0 ");
								AgentAction left=moveLeft(a,game);
								if(left!=null)res=left;
							}
						}
						else {
							if(distY<0) {
								System.out.println("test distX<0  ");
								AgentAction down=moveDown(a,game);
								if(down!=null)res=down;
								
								
							}
							else {
								System.out.println("test distX>=0 ");
								AgentAction up=moveUp(a,game);
								if(up!=null)res=up;
							}
						}
				}
			}
				
			System.out.println(a.getType()+" x"+a.getX()+" y"+a.getY()+"  :"+res);
			return res;
		}
		return res;
	}
	
	private AgentAction moveLeft(Agent a, BomberManGame g) {
		AgentAction res=null;
		System.out.println("test left");
		if(g.IsLegalMove(a, AgentAction.MOVE_LEFT)) {
			res=AgentAction.MOVE_LEFT;
		}
		else {
			if(g.IsLegalMove(a, AgentAction.MOVE_UP)) {
				res=AgentAction.MOVE_UP;
			}
			else {
				if(g.IsLegalMove(a, AgentAction.MOVE_DOWN)) {
					res=AgentAction.MOVE_DOWN;
				}
			}	
		}
		return res;
	}
	private AgentAction moveRight(Agent a, BomberManGame g) {
		AgentAction res=null;
		System.out.println("test right");
		if(g.IsLegalMove(a,AgentAction.MOVE_RIGHT)) {
			res=AgentAction.MOVE_RIGHT;
		}
		else {
			if(g.IsLegalMove(a, AgentAction.MOVE_UP)) {
				res=AgentAction.MOVE_UP;
				
			}
			else {
				if(g.IsLegalMove(a, AgentAction.MOVE_DOWN)) {
					res=AgentAction.MOVE_DOWN;
				}
			}
		}
		return res;
	}
	private AgentAction moveDown(Agent a, BomberManGame g) {
		AgentAction res=null;
		System.out.println("test down");
		if(g.IsLegalMove(a,AgentAction.MOVE_DOWN)) {
			res=AgentAction.MOVE_DOWN;
		}
		else {
			if(g.IsLegalMove(a, AgentAction.MOVE_LEFT)) {
				res=AgentAction.MOVE_LEFT;
				
			}
			else {
				if(g.IsLegalMove(a, AgentAction.MOVE_RIGHT)) {
					res=AgentAction.MOVE_RIGHT;
				}
			}
		}
		return res;
	}
	private AgentAction moveUp(Agent a, BomberManGame g) {
		AgentAction res=null;
		System.out.println("test up");
		if(g.IsLegalMove(a,AgentAction.MOVE_UP)) {
			res=AgentAction.MOVE_UP;
		}
		else {
			if(g.IsLegalMove(a, AgentAction.MOVE_LEFT)) {
				res=AgentAction.MOVE_LEFT;
				
			}
			else {
				if(g.IsLegalMove(a, AgentAction.MOVE_RIGHT)) {
					res=AgentAction.MOVE_RIGHT;
				}
			}
		}
		return res;
	}

	private Agent searchEmmeni(Agent a, BomberManGame game) {
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
