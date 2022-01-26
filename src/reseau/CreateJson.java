package reseau;

import java.util.ArrayList;

import model.BomberManGame;
import model.utils.InfoAgent;

public class CreateJson {

	public static String JsonTchat(String nameUser, String message) {
		String res = "{\"tchat\":{\"name\":\"" + nameUser + "\",\"message\":\"" + message + "\"}}";
		return res;
	}

	public static String JsonSelect(String nameUser, String partie) {
		String res = "{\"select\":{\"name\":\"" + nameUser + "\",\"partie\":\"" + partie + "\"}}";
		return res;
	}

	public static String JsonGameBegin(BomberManGame game) {
		String res = "{\"Game\":{";
		boolean[][] walls = game.getWalls();
		res += "\"x\":" + walls.length + ",";
		res += "\"y\":" + walls[0].length + ",";
		// walls

		res += "\"walls\":[";
		for (int i = 0; i < walls.length; i++) {
			for (int j = 0; j < walls[0].length; j++) {
				if (walls[i][j] == true) {
					res += "{" + "\"x\":" + i + "," + "\"y\":" + j + "},";
				}
			}
		}
		res = res.substring(0, res.lastIndexOf(","));
		res += "],";
		// breakables
		boolean[][] breakable = game.getBreakable_walls();
		res += "\"breakable\":[";
		for (int i = 0; i < breakable.length; i++) {
			for (int j = 0; j < breakable[0].length; j++) {
				if (breakable[i][j] == true) {
					res += "{" + "\"x\":" + i + "," + "\"y\":" + j + "},";
				}
			}
		}
		res = res.substring(0, res.lastIndexOf(","));
		res += "],";
		// agent
		ArrayList<InfoAgent> listInfoAgents = game.getAgents();
		res += "\"agents\":[";
		for (InfoAgent agent : listInfoAgents) {
			res += "{" + "\"type\":\"" + agent.getType() + "\"," + "\"x\":" + agent.getX() + "," + "\"y\":"
					+ agent.getY() + "},";
		}
		res = res.substring(0, res.lastIndexOf(","));
		res += "]";
		res += "}}";
		System.out.println(res);
		return res;
	}
}
