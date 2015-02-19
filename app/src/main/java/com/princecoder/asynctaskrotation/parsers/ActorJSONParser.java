package com.princecoder.asynctaskrotation.parsers;

import com.princecoder.asynctaskrotation.model.Actors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActorJSONParser {
	
	public static List<Actors> parseFeed(String content) {

        List<Actors> actorsList = new ArrayList<>();
		try {
            // Initialize my JSON Object
            JSONObject  ob= new JSONObject(content);

            // Get the list of actors
            JSONArray ar = ob.getJSONArray("actors");

			for (int i = 0; i < ar.length(); i++) {
				
				JSONObject obj = ar.getJSONObject(i);

                Actors actor = new Actors();
                actor.setName(obj.getString("name"));
                actor.setDescription(obj.getString("description"));
                actor.setDob(obj.getString("dob"));
                actor.setCountry(obj.getString("country"));
                actor.setHeight(obj.getString("height"));
                actor.setSpouse(obj.getString("spouse"));
                actor.setChildren(obj.getString("children"));
                actor.setImage(obj.getString("image"));
                actorsList.add(actor);
			}
			
			return actorsList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
