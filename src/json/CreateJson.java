package json;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import Server.GameChange;
import model.BomberManGame;
import model.IAutils.Position;
import model.utils.AgentAction;

public class CreateJson {

	/**
	 * créer le json pour le tchat
	 * 
	 * @param nameUser
	 * @param message
	 * @return
	 */
	public static String JsonTchat(String nameUser, String message) {
		JSONObject json = new JSONObject();
		json.put("type", "tchat");
		json.put("name", nameUser);
		json.put("message", message);
		return json.toJSONString();
	}

	/**
	 * créer le json pour la connexion
	 * 
	 * @param nameUser
	 * @param pwd
	 * @return
	 */
	public static String JsonConnectAnswer(String nameUser, String pwd) {
		JSONObject json = new JSONObject();
		json.put("type", "connectAnswer");
		json.put("pwd", pwd);
		json.put("name", nameUser);
		return json.toJSONString();
	}

	/**
	 * créer le json pour la selection de la partie
	 * 
	 * @param nameUser
	 * @param partie
	 * @return
	 */
	public static String JsonSelect(String nameUser, String partie) {
		JSONObject json = new JSONObject();
		json.put("type", "select");
		json.put("name", nameUser);
		json.put("partie", partie);
		return json.toJSONString();
	}

	/**
	 * créer le json pour le retour si la connexion s'est bien passé
	 * 
	 * @param co
	 * @return
	 */
	public static String JsonConnectResponse(boolean co) {
		JSONObject json = new JSONObject();
		json.put("type", "connectResponse");
		json.put("valid", co);
		return json.toJSONString();
	}

	/**
	 * créer le json pour envoyer l'action choisi par le joueur
	 * 
	 * @param action
	 * @return
	 */
	public static String JsonAction(AgentAction action) {
		JSONObject json = new JSONObject();
		json.put("type", "action");
		json.put("action", action.toString());
		return json.toJSONString();
	}

	/**
	 * créer le json pour le début de partie
	 * 
	 * @param game
	 * @return
	 */
	public static String JsonGameBegin(BomberManGame game) {

		ArrayList<Position> listWall = new ArrayList<Position>();
		boolean[][] walls = game.getWalls();
		for (int i = 0; i < walls.length; i++) {
			for (int j = 0; j < walls[0].length; j++) {
				if (walls[i][j] == true) {
					listWall.add(new Position(i, j));
				}
			}
		}
		ArrayList<Position> listbreakable = new ArrayList<Position>();
		boolean[][] breakables = game.getBreakable_walls();
		for (int i = 0; i < breakables.length; i++) {
			for (int j = 0; j < breakables[0].length; j++) {
				if (breakables[i][j] == true) {
					listbreakable.add(new Position(i, j));
				}
			}
		}

		JSONObject json = new JSONObject();
		json.put("type", "GameBegin");
		json.put("x", walls.length);
		json.put("y", walls[0].length);
		json.put("walls", listWall);
		json.put("breakables", listbreakable);
		json.put("listBomberman", game.getListBomberMan());
		json.put("listEnnemi", game.getListEnnemi());
		return json.toJSONString();
	}

	/**
	 * créer le json pendant la partie
	 * 
	 * @param game
	 * @return
	 */
	public static String JsonGamePartie(GameChange game) {
		JSONObject json = new JSONObject();
		json.put("type", "GameParty");
		json.put("walls", game.getListBreakable());
		json.put("listBomberman", game.getListBomberMan());
		json.put("listEnnemi", game.getListEnnemi());
		json.put("listBomb", game.getListBomb());
		json.put("listItem", game.getListItem());
		return json.toJSONString();

	}

}
