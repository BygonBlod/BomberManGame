package json;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import Server.GameChange;
import model.BomberManGame;
import model.utils.AgentAction;
import model.utils.Wall;

public class CreateJson {

	public static String JsonTchat(String nameUser, String message) {
		JSONObject json = new JSONObject();
		json.put("type", "tchat");
		json.put("name", nameUser);
		json.put("message", message);
		return json.toJSONString();
	}

	public static String JsonName(String nameUser) {
		JSONObject json = new JSONObject();
		json.put("type", "name");
		json.put("name", nameUser);
		return json.toJSONString();
	}

	public static String JsonSelect(String nameUser, String partie) {
		JSONObject json = new JSONObject();
		json.put("type", "select");
		json.put("name", nameUser);
		json.put("partie", partie);
		return json.toJSONString();
	}

	public static String JsonAction(AgentAction action) {
		JSONObject json = new JSONObject();
		json.put("type", "action");
		json.put("action", action.toString());
		return json.toJSONString();
	}

	public static String JsonGameBegin(BomberManGame game) {

		ArrayList<Wall> listWall = new ArrayList<Wall>();
		boolean[][] walls = game.getWalls();
		for (int i = 0; i < walls.length; i++) {
			for (int j = 0; j < walls[0].length; j++) {
				if (walls[i][j] == true) {
					listWall.add(new Wall(i, j));
				}
			}
		}
		ArrayList<Wall> listbreakable = new ArrayList<Wall>();
		boolean[][] breakables = game.getBreakable_walls();
		for (int i = 0; i < breakables.length; i++) {
			for (int j = 0; j < breakables[0].length; j++) {
				if (breakables[i][j] == true) {
					listbreakable.add(new Wall(i, j));
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
