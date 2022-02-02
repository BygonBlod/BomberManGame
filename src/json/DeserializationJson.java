package json;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import model.BomberManGame;
import model.Agent.Agent;
import model.Agent.AgentBird;
import model.Agent.AgentBomberMan;
import model.Agent.AgentEnnemi;
import model.Agent.AgentRajion;
import model.utils.AgentAction;
import model.utils.ColorAgent;
import model.utils.InfoAgent;

public class DeserializationJson {

	public static ArrayList<String> JsonTchat(JsonObject json) {
		Gson gson = new GsonBuilder().create();
		ArrayList<String> res = new ArrayList<>();
		res.add(gson.fromJson(json.getAsJsonPrimitive("name"), String.class));
		res.add(gson.fromJson(json.getAsJsonPrimitive("message"), String.class));
		return res;
	}

	public static ArrayList<String> JsonSelect(JsonObject json) {
		Gson gson = new GsonBuilder().create();
		ArrayList<String> res = new ArrayList<>();
		res.add(gson.fromJson(json.getAsJsonPrimitive("name"), String.class));
		res.add(gson.fromJson(json.getAsJsonPrimitive("partie"), String.class));
		return res;
	}

	public static BomberManGame JsonGameBegin(JsonObject json) {
		BomberManGame res = new BomberManGame();
		Gson gson = new GsonBuilder().create();
		ColorAgent[] color = ColorAgent.values();
		int cpt_col = 0;
		int xGame = gson.fromJson(json.getAsJsonPrimitive("x"), Integer.class);
		int yGame = gson.fromJson(json.getAsJsonPrimitive("y"), Integer.class);
		boolean[][] wallsGame = new boolean[xGame][yGame];
		boolean[][] breakableGame = new boolean[xGame][yGame];
		ArrayList<InfoAgent> start_agent = new ArrayList<InfoAgent>();

		JsonArray walls = gson.fromJson(json.getAsJsonArray("walls"), JsonArray.class);
		JsonArray breakables = gson.fromJson(json.getAsJsonArray("breakable"), JsonArray.class);
		JsonArray agents = gson.fromJson(json.getAsJsonArray("agents"), JsonArray.class);

		for (JsonElement wall : walls) {
			JsonObject wallE = gson.fromJson(wall.getAsJsonObject(), JsonObject.class);
			int x = gson.fromJson(wallE.getAsJsonPrimitive("x"), Integer.class);
			int y = gson.fromJson(wallE.getAsJsonPrimitive("y"), Integer.class);
			wallsGame[x][y] = true;
		}
		for (JsonElement wall : breakables) {
			JsonObject wallE = gson.fromJson(wall.getAsJsonObject(), JsonObject.class);
			int x = gson.fromJson(wallE.getAsJsonPrimitive("x"), Integer.class);
			int y = gson.fromJson(wallE.getAsJsonPrimitive("y"), Integer.class);
			breakableGame[x][y] = true;
		}
		for (JsonElement agent : agents) {
			JsonObject agentE = gson.fromJson(agent.getAsJsonObject(), JsonObject.class);
			String type = gson.fromJson(agentE.getAsJsonPrimitive("type"), String.class);
			int x = gson.fromJson(agentE.getAsJsonPrimitive("x"), Integer.class);
			int y = gson.fromJson(agentE.getAsJsonPrimitive("y"), Integer.class);
			if (type.equals("E") || type.equals("V") || type.equals("R"))
				start_agent
						.add(new InfoAgent(x, y, AgentAction.STOP, type.charAt(0), ColorAgent.DEFAULT, false, false));
			else {
				ColorAgent col;
				if (cpt_col < color.length)
					col = color[cpt_col];
				else
					col = ColorAgent.DEFAULT;
				start_agent.add(new InfoAgent(x, y, AgentAction.STOP, type.charAt(0), col, false, false));
				cpt_col++;
			}
		}
		ArrayList<Agent> listBomberMan = new ArrayList<Agent>();
		ArrayList<Agent> listEnnemi = new ArrayList<Agent>();
		for (InfoAgent a : start_agent) {
			switch (a.getType()) {
			case 'B':
				listBomberMan.add(new AgentBomberMan(a.getX(), a.getY(), a.getAgentAction(), a.getColor(),
						a.isInvincible(), a.isSick()));
				break;
			case 'R':
				listEnnemi.add(new AgentRajion(a.getX(), a.getY(), a.getAgentAction(), a.getColor(), a.isInvincible(),
						a.isSick()));
				break;
			case 'V':
				listEnnemi.add(new AgentBird(a.getX(), a.getY(), a.getAgentAction(), a.getColor(), a.isInvincible(),
						a.isSick()));
				break;
			default:
				listEnnemi.add(new AgentEnnemi(a.getX(), a.getY(), a.getAgentAction(), a.getType(), a.getColor(),
						a.isInvincible(), a.isSick()));
				break;
			}
		}
		res.setListBomberMan(listBomberMan);
		res.setListEnnemi(listEnnemi);
		res.setWalls(wallsGame);
		res.setBreakable_walls(breakableGame);
		return res;
	}

	public static BomberManGame JsonGamePartie(JsonObject json) {
		BomberManGame res = new BomberManGame();
		Gson gson = new GsonBuilder().create();

		return res;
	}

}
