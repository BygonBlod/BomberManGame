package reseau;

import java.util.ArrayList;

import model.utils.InfoAgent;

public class CreateJson {
	
	public static String JsonTchat(String nameUser, String message) {
		String res="{\"tchat\":{\"name\":\""+nameUser+"\",\"message\":\""+message+"\"}}";
		return res;
	}
	
	public static String JsonSelect(String nameUser,String partie) {
		String res="{\"select\":{\"name\":\""+nameUser+"\",\"partie\":\""+partie+"\"}}";
		return res;
	}
	public static String JsonGameBegin( boolean[][] walls, boolean[][] breakable_walls, ArrayList<InfoAgent> listInfoAgents) {
		String res="{\"Game\":{";
		//walls
		res+="\"walls\":[";
		for(int i=0;i<walls.length;i++) {
			for(int j=0;j<walls[0].length;j++) {
				if(walls[i][j]==true) {
					res+="{\"wall\":{"
							+"\"x\":\""+i+"\","
							+"\"y\":\""+j+"\"}},";
				}
			}
		}
		res+= "],";
		//breakables
		res+="\"breakable\":[";
		for(int i=0;i<breakable_walls.length;i++) {
			for(int j=0;j<breakable_walls[0].length;j++) {
				if(walls[i][j]==true) {
					res+="{\"wall\":{"
							+"\"x\":\""+i+"\","
							+"\"y\":\""+j+"\"}},";
				}
			}
		}
		res+="],";
		//agent
		res+="\"agents\":[";
		for(InfoAgent agent:listInfoAgents) {
			res+="{\"agent\":{"	
						+"\"type\":\""+agent.getType()+"\","
						+"\"x\":\""+agent.getX()+"\","
						+"\"y\":\""+agent.getY()+"\""
				+"}},";
		}
		res+="]";
		res+="}}";
		System.out.println(res);
		return res;
	}
}
