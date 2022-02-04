package json;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Server.GameChange;
import model.BomberManGame;
import model.Agent.Agent;
import model.Agent.AgentBird;
import model.Agent.AgentBomberMan;
import model.Agent.AgentEnnemi;
import model.Agent.AgentRajion;
import model.utils.AgentAction;
import model.utils.ColorAgent;
import model.utils.InfoBomb;
import model.utils.InfoItem;
import model.utils.ItemType;
import model.utils.StateBomb;
import model.utils.Wall;

public class DeserializationJson {

	public static ArrayList<String> JsonTchat(JSONObject json) {
		Gson gson = new GsonBuilder().create();
		ArrayList<String> res = new ArrayList<>();
		res.add((String) json.get("name"));
		res.add((String) json.get("message"));
		return res;
	}

	public static ArrayList<String> JsonSelect(JSONObject json) {

		ArrayList<String> res = new ArrayList<>();
		res.add((String) json.get("name"));
		res.add((String) json.get("partie"));
		return res;
	}

	public static BomberManGame JsonGameBegin(JSONObject json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		BomberManGame res = new BomberManGame();
		int x = (int) ((long) json.get("x"));
		int y = (int) ((long) json.get("y"));
		boolean[][] walls = new boolean[x][y];
		boolean[][] breakables = new boolean[x][y];
		ArrayList<Agent> listBomber = new ArrayList<Agent>();
		ArrayList<Agent> listEnnemi = new ArrayList<Agent>();
		JSONArray list = (JSONArray) json.get("walls");
		for (Object w : list) {
			JSONObject obj = (JSONObject) jsonParser.parse(w.toString());
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			walls[xw][yw] = true;
		}
		list = (JSONArray) json.get("breakables");
		System.out.println(list);
		for (Object w2 : list) {
			JSONObject obj = (JSONObject) jsonParser.parse(w2.toString());
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			breakables[xw][yw] = true;
		}
		list = (JSONArray) json.get("listBomberman");
		ColorAgent[] color = ColorAgent.values();
		int cpt_col = 0;
		for (Object w3 : list) {
			ColorAgent col;
			if (cpt_col < color.length)
				col = color[cpt_col];
			else
				col = ColorAgent.DEFAULT;
			JSONObject obj = (JSONObject) jsonParser.parse(w3.toString());
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			int lvlB = (int) ((long) obj.get("lvlB"));
			int ti = (int) ((long) obj.get("ti"));
			int ts = (int) ((long) obj.get("ts"));
			String action = (String) obj.get("action");
			AgentAction actionA = getAction(action);
			listBomber.add(new AgentBomberMan(xw, yw, actionA, col, false, false));

		}
		list = (JSONArray) json.get("listEnnemi");
		for (Object w3 : list) {
			JSONObject obj = (JSONObject) jsonParser.parse(w3.toString());
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			int lvlB = (int) ((long) obj.get("lvlB"));
			String type = (String) obj.get("type");
			int ti = (int) ((long) obj.get("ti"));
			int ts = (int) ((long) obj.get("ts"));
			String action = (String) obj.get("action");
			AgentAction actionA = getAction(action);
			listEnnemi.add(FabriqueAgent(xw, yw, actionA, type.charAt(0), ColorAgent.DEFAULT, false, false));

		}
		res.setListBomberMan(listBomber);
		res.setListEnnemi(listEnnemi);
		res.setBreakable_walls(breakables);
		res.setWalls(walls);
		return res;

	}

	public static GameChange JsonGamePartie(JSONObject json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		GameChange res = new GameChange();
		ArrayList<Agent> listBomber = new ArrayList<Agent>();
		ArrayList<Agent> listEnnemi = new ArrayList<Agent>();
		ArrayList<InfoBomb> listBomb = new ArrayList<InfoBomb>();
		ArrayList<InfoItem> listItem = new ArrayList<InfoItem>();
		ArrayList<Wall> listWall = new ArrayList<Wall>();

		JSONArray list = (JSONArray) json.get("listBomberman");
		ColorAgent[] color = ColorAgent.values();
		int cpt_col = 0;
		for (Object w3 : list) {
			ColorAgent col;
			if (cpt_col < color.length)
				col = color[cpt_col];
			else
				col = ColorAgent.DEFAULT;
			JSONObject obj = (JSONObject) jsonParser.parse(w3.toString());
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			int lvlB = (int) ((long) obj.get("lvlB"));
			int ti = (int) ((long) obj.get("ti"));
			int ts = (int) ((long) obj.get("ts"));
			String action = (String) obj.get("action");
			AgentAction actionA = getAction(action);
			listBomber.add(new AgentBomberMan(xw, yw, actionA, col, false, false));

		}
		list = (JSONArray) json.get("listEnnemi");
		for (Object w3 : list) {
			JSONObject obj = (JSONObject) jsonParser.parse(w3.toString());
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			int lvlB = (int) ((long) obj.get("lvlB"));
			String type = (String) obj.get("type");
			int ti = (int) ((long) obj.get("ti"));
			int ts = (int) ((long) obj.get("ts"));
			String action = (String) obj.get("action");
			AgentAction actionA = getAction(action);
			listEnnemi.add(FabriqueAgent(xw, yw, actionA, type.charAt(0), ColorAgent.DEFAULT, false, false));

		}
		list = (JSONArray) json.get("listBomb");
		for (Object w3 : list) {
			JSONObject obj = (JSONObject) jsonParser.parse(w3.toString());
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			int lvlB = (int) ((long) obj.get("range"));
			String step = (String) obj.get("state");
			listBomb.add(new InfoBomb(xw, yw, lvlB, GetStep(step)));

		}
		list = (JSONArray) json.get("walls");
		for (Object w3 : list) {
			JSONObject obj = (JSONObject) jsonParser.parse(w3.toString());
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			listWall.add(new Wall(xw, yw));

		}
		list = (JSONArray) json.get("listItem");
		for (Object w3 : list) {
			JSONObject obj = (JSONObject) jsonParser.parse(w3.toString());
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			String type = (String) obj.get("type");
			ItemType typeI = GetItem(type);
			listItem.add(new InfoItem(xw, yw, typeI));

		}

		res.setListBomb(listBomb);
		res.setListBomberMan(listBomber);
		res.setListEnnemi(listEnnemi);
		res.setListItem(listItem);
		res.setListBreakable(listWall);
		return res;

	}

	private static ItemType GetItem(String type) {
		switch (type) {
		case "FIRE_UP":
			return ItemType.FIRE_UP;
		case "FIRE_DOWN":
			return ItemType.FIRE_DOWN;
		case "FIRE_SUIT":
			return ItemType.FIRE_SUIT;
		case "SKULL":
			return ItemType.SKULL;
		}
		return null;
	}

	private static StateBomb GetStep(String step) {
		switch (step) {
		case "Step0":
			return StateBomb.Step0;
		case "Step1":
			return StateBomb.Step1;
		case "Step2":
			return StateBomb.Step2;
		case "Step3":
			return StateBomb.Step3;
		case "Boom":
			return StateBomb.Boom;

		}
		return null;
	}

	private static Agent FabriqueAgent(int xw, int yw, AgentAction actionA, char cha, ColorAgent color, boolean b,
			boolean c) {
		switch (cha) {
		case 'R':
			return new AgentRajion(xw, yw, actionA, color, b, c);
		case 'V':
			return new AgentBird(xw, yw, actionA, color, b, c);
		default:
			return new AgentEnnemi(xw, yw, actionA, cha, color, b, c);
		}
	}

	private static AgentAction getAction(String action) {
		switch (action) {
		case "MOVE_UP":
			return AgentAction.MOVE_UP;
		case "MOVE_DOWN":
			return AgentAction.MOVE_DOWN;
		case "MOVE_LEFT":
			return AgentAction.MOVE_LEFT;
		case "MOVE_RIGHT":
			return AgentAction.MOVE_RIGHT;
		case "STOP":
			return AgentAction.STOP;
		case "PUT_BOMB":
			return AgentAction.PUT_BOMB;

		}
		return null;
	}

}
