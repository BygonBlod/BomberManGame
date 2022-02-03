package json;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import Server.GameChange;
import model.BomberManGame;
import model.utils.Wall;

public class CreateJson {

	public static String JsonTchat(String nameUser, String message) {
		JSONObject json = new JSONObject();
		json.put("type", "tchat");
		json.put("name", nameUser);
		json.put("message", message);
		return json.toJSONString();
	}

	public static String JsonSelect(String nameUser, String partie) {
		JSONObject json = new JSONObject();
		json.put("type", "select");
		json.put("name", nameUser);
		json.put("partie", partie);
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
		/*
		 * ArrayList<InfoAgent> listAgents = game.getListAgents(); ArrayList<InfoBomb>
		 * listBomb = game.getListBomb(); ArrayList<InfoItem> listItem =
		 * game.getListItem(); boolean breakable[][] = game.getBreakable_walls(); String
		 * res = "{\"GameParty\":{"; res += "\"x\":" + breakable.length + ","; res +=
		 * "\"y\":" + breakable[0].length + ","; if (!listAgents.isEmpty()) { res +=
		 * "\"agents\":["; for (InfoAgent agent : listAgents) { res += "{" +
		 * "\"type\":\"" + agent.getType() + "\"," + "\"x\":" + agent.getX() + "," +
		 * "\"y\":" + agent.getY() + "},"; } res = res.substring(0,
		 * res.lastIndexOf(",")); res += "],"; } if (!listBomb.isEmpty()) { res +=
		 * "\"bomb\":["; for (InfoBomb bomb : listBomb) { res += "{\"lvl\":" +
		 * bomb.getRange() + "," + "\"state\":\"" + bomb.getStateBomb() + "\"," +
		 * "\"x\":" + bomb.getX() + "," + "\"y\":" + bomb.getY() + "},"; } res =
		 * res.substring(0, res.lastIndexOf(",")); res += "],"; } if
		 * (!listItem.isEmpty()) { res += "\"item\":["; for (InfoItem item : listItem) {
		 * res += "{\"type\":" + item.getType() + "," + "\"x\":" + item.getX() + "," +
		 * "\"y\":" + item.getY() + "},"; } res = res.substring(0,
		 * res.lastIndexOf(",")); res += "],";
		 * 
		 * } res += "\"breakable\":["; for (int i = 0; i < breakable.length; i++) { for
		 * (int j = 0; j < breakable[0].length; j++) { if (breakable[i][j] == true) {
		 * res += "{" + "\"x\":" + i + "," + "\"y\":" + j + "},"; } } } res =
		 * res.substring(0, res.lastIndexOf(",")); res += "]"; res += "}}"; return res;
		 */
		JSONObject json = new JSONObject();
		json.put("type", "GameParty");
		json.put("walls", game.getBreakable_walls());
		json.put("listBomberman", game.getListBomberMan());
		json.put("listEnnemi", game.getListEnnemi());
		return json.toJSONString();

	}

}