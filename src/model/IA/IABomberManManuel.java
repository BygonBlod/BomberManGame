package model.IA;


import model.Agent.Agent;
import model.BomberManGame;
import model.utils.AgentAction;

public class IABomberManManuel implements IAStrategi {
	private boolean joue=false;
	private AgentAction action=null;

	@Override
	public void Action(Agent a, BomberManGame game) {
		//game.pause();
		/*while(joue==false) {
			if(action!=null) {
				if(game.IsLegalMove(a, action)) {
					joue=true;
				}
			}
		}*/
		if(action==null)action=AgentAction.STOP;
		if(action!=AgentAction.PUT_BOMB)game.moveAgent(a, action);
		else game.putBomb(a);
		joue=false;
		
	}
	public void setAction(AgentAction a) {
		action=a;
	}
	

}
