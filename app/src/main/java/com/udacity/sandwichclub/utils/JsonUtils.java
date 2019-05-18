package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        JSONArray ingredients = null ;
        String image = null;
        String description = null;
        String placeOfOrigin = null;
        String mainName = null;
        JSONArray alsoKnownAs=null;
        Sandwich sandwich1 = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
            mainName = name.getString("mainName");
            alsoKnownAs = name.getJSONArray("alsoKnownAs");
            ArrayList<String> arrayList = new ArrayList<String>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                arrayList.add(alsoKnownAs.getString(i));
            }
            placeOfOrigin = jsonObject.getString("placeOfOrigin");
            description = jsonObject.getString("description");
            image = jsonObject.getString("image");
            ingredients = jsonObject.getJSONArray("ingredients");
            ArrayList<String> inList = new ArrayList<String>();
            for (int i = 0; i < ingredients.length(); i++) {
                inList.add(ingredients.getString(i));

                sandwich1 = new Sandwich(mainName, arrayList, placeOfOrigin, description, image, inList);

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich1;
    }
}
