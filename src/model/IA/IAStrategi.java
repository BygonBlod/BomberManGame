package model.IA;

import model.BomberManGame;
import model.Agent.Agent;

/**
 * 
 * @author Antonin interface utiliser pour générer les actions pour chaque type
 *         d'agent
 */
public interface IAStrategi {
	public void Action(Agent a, BomberManGame bomberManGame);

}
