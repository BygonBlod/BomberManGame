package model;

import utils.AgentAction;

public class IARajion implements IAStrategi {

	@Override
	public void Action(Agent a, BomberManGame bomberManGame) {
		bomberManGame.moveAgent(a,AgentAction.MOVE_DOWN );
		System.out.println(a.getType()+" x"+a.getX()+" y"+a.getY()+"  :"+AgentAction.MOVE_DOWN);
		
	}

}
