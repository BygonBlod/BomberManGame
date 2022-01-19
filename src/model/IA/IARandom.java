package model.IA;

import static model.IAutils.Research.*;

import java.util.ArrayList;
import java.util.Random;
import model.IAutils.*;
import model.Agent.Agent;
import model.BomberManGame;

import model.utils.AgentAction;

public class IARandom implements IAStrategi {

	@Override
	public void Action(Agent a, BomberManGame g) {
		int i=0;
		AgentAction res=null;
		ListActionPoids listAction=new ListActionPoids();
		ListActionPoids bestActions=new ListActionPoids();
		ArrayList<Position> positionObject=new ArrayList<>();
		positionObject=getBomb(a,g);
		listAction.cheminPossible(a,g);		
		listAction.eviterObjects(a,g,positionObject,10);
		listAction.moveto(a, g, searchGoodItem(a,g), 4);
		if(listAction.size()==0)res=AgentAction.STOP;
		else {
			bestActions=listAction.getBestActions();
			if(bestActions.size()==1)res=bestActions.get(0).getAction();
			else {
				int nbAlea=new Random().nextInt(bestActions.size());
				//System.out.println("size "+bestActions.size()+" : choix "+nbAlea);
				res=bestActions.get(nbAlea).getAction();
			}
		}
		//System.out.println(" list : "+bestActions);
		listAction.clear();
		//System.out.println(a.getType()+" x"+a.getX()+" y"+a.getY()+"  :"+res);
		if(res!=AgentAction.PUT_BOMB)g.moveAgent(a, res);
		else g.putBomb(a);
	}
}
