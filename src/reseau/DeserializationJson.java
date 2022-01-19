package reseau;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class DeserializationJson {
	
	public static ArrayList<String> JsonTchat(JsonObject json) {
		Gson gson=new GsonBuilder().create();
		ArrayList <String> res=new ArrayList<>();
		res.add(gson.fromJson(json.getAsJsonPrimitive("name"),String.class));
        res.add(gson.fromJson(json.getAsJsonPrimitive("message"), String.class));
        return res;
	}
	
	public static ArrayList<String> JsonSelect(JsonObject json) {
		Gson gson=new GsonBuilder().create();
		ArrayList <String> res=new ArrayList<>();
		res.add(gson.fromJson(json.getAsJsonPrimitive("name"),String.class));
        res.add(gson.fromJson(json.getAsJsonPrimitive("partie"), String.class));
        return res;
	}

}
