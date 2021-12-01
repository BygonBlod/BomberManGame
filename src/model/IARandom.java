package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import utils.AgentAction;
import utils.InfoBomb;

public class IARandom implements IAStrategi {

	@Override
	public void Action(Agent a,BomberManGame g) {
		int i=0;
		AgentAction res=null;
		ListActionPoids listAction=new ListActionPoids();
		listAction.cheminPossible(a,g);
		while(res==null) {			
			listAction.eviterBomb(a,g);
			if(listAction.size()==0)res=AgentAction.STOP;
			else {
				i=new Random().nextInt(listAction.size());
				res=listAction.get(i).getAction();
			}
			
			
		}
		System.out.println("list actions "+listAction);
		listAction.clear();
		System.out.println(a.getType()+" x"+a.getX()+" y"+a.getY()+"  :"+res);
		if(res!=AgentAction.PUT_BOMB)g.moveAgent(a, res);
		else g.putBomb(a);
	}
	

}
