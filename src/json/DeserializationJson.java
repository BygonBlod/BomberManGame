package json;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import Server.GameChange;
import model.BomberManGame;
import model.Agent.Agent;

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
		JSONArray listWall = (JSONArray) json.get("walls");
		for (Object w : listWall) {
			JSONObject obj = (JSONObject) jsonParser.parse(w.toString());
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			walls[xw][yw] = true;
		}
		JSONArray listWall2 = (JSONArray) json.get("breakables");
		System.out.println(listWall2);
		for (Object w2 : listWall2) {
			JSONObject obj = (JSONObject) jsonParser.parse(w2.toString());
			System.out.println(w2);
			int xw = (int) ((long) obj.get("x"));
			int yw = (int) ((long) obj.get("y"));
			breakables[xw][yw] = true;
		}
		// listBomber = (ArrayList<Agent>) json.get("listBomberman");
		// listEnnemi = (ArrayList<Agent>) json.get("listEnnemi");
		res.setListBomberMan(listBomber);
		res.setListEnnemi(listEnnemi);
		res.setBreakable_walls(breakables);
		res.setWalls(walls);
		return res;

	}

	public static GameChange JsonGamePartie(JsonObject json) {
		GameChange res = new GameChange();
		/*
		 * BomberManGame res = new BomberManGame(); Gson gson = new
		 * GsonBuilder().create(); int xGame =
		 * gson.fromJson(json.getAsJsonPrimitive("x"), Integer.class); int yGame =
		 * gson.fromJson(json.getAsJsonPrimitive("y"), Integer.class); boolean[][]
		 * breakableGame = new boolean[xGame][yGame]; JsonArray breakables =
		 * gson.fromJson(json.getAsJsonArray("breakable"), JsonArray.class); JsonArray
		 * agents = gson.fromJson(json.getAsJsonArray("agents"), JsonArray.class);
		 * JsonArray items = gson.fromJson(json.getAsJsonArray("item"),
		 * JsonArray.class);
		 * 
		 * ArrayList<InfoAgent> start_agent = new ArrayList<InfoAgent>(); for
		 * (JsonElement agent : agents) { JsonObject agentE =
		 * gson.fromJson(agent.getAsJsonObject(), JsonObject.class); String type =
		 * gson.fromJson(agentE.getAsJsonPrimitive("type"), String.class); int x =
		 * gson.fromJson(agentE.getAsJsonPrimitive("x"), Integer.class); int y =
		 * gson.fromJson(agentE.getAsJsonPrimitive("y"), Integer.class);
		 * start_agent.add(new InfoAgent(x, y, AgentAction.STOP, type.charAt(0),
		 * ColorAgent.DEFAULT, false, false));
		 * 
		 * } ArrayList<Agent> listBomberMan = new ArrayList<Agent>(); ArrayList<Agent>
		 * listEnnemi = new ArrayList<Agent>(); for (InfoAgent a : start_agent) { switch
		 * (a.getType()) { case 'B': listBomberMan.add(new AgentBomberMan(a.getX(),
		 * a.getY(), a.getAgentAction(), a.getColor(), a.isInvincible(), a.isSick()));
		 * break; case 'R': listEnnemi.add(new AgentRajion(a.getX(), a.getY(),
		 * a.getAgentAction(), a.getColor(), a.isInvincible(), a.isSick())); break; case
		 * 'V': listEnnemi.add(new AgentBird(a.getX(), a.getY(), a.getAgentAction(),
		 * a.getColor(), a.isInvincible(), a.isSick())); break; default:
		 * listEnnemi.add(new AgentEnnemi(a.getX(), a.getY(), a.getAgentAction(),
		 * a.getType(), a.getColor(), a.isInvincible(), a.isSick())); break; } }
		 * 
		 * for (JsonElement wall : breakables) { JsonObject wallE =
		 * gson.fromJson(wall.getAsJsonObject(), JsonObject.class); int x =
		 * gson.fromJson(wallE.getAsJsonPrimitive("x"), Integer.class); int y =
		 * gson.fromJson(wallE.getAsJsonPrimitive("y"), Integer.class);
		 * breakableGame[x][y] = true; } res.setListBomberMan(listBomberMan);
		 * res.setListEnnemi(listEnnemi); res.setBreakable_walls(breakableGame);
		 */
		return res;
	}

}