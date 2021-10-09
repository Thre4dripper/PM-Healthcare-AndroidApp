package com.example.pmhealthcare.Utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    static String STATES_DISTRICTS_JSON=null;

    public static String[] getStatesFromJSON(Context context){
        loadJSONFromAsset(context);
        List<String> list=new ArrayList<>();

        try {
            JSONObject rootObject=new JSONObject(STATES_DISTRICTS_JSON);

            for(int i=0;i<36;i++){
                list.add(rootObject.getJSONArray("states_and_districts").getJSONObject(i).getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] states=new String[list.size()];
        states=list.toArray(states);
        return states;
    }

    public static String[] getDistrictsFromJSON(Context context, String currentState){
        loadJSONFromAsset(context);

        List<String> list=new ArrayList<>();

        try {
            JSONObject rootObject=new JSONObject(STATES_DISTRICTS_JSON);

            int i=0;

            JSONObject state=rootObject.getJSONArray("states_and_districts").getJSONObject(i);
            while(!state.getString("name").equals(currentState)){
                i++;
                state=rootObject.getJSONArray("states_and_districts").getJSONObject(i);
            }

            JSONArray districts=state.getJSONArray("districts");
            for(int j=0;j<districts.length();j++){
                list.add(districts.getString(j));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] districtsList=new String[list.size()];
        districtsList=list.toArray(districtsList);
        return districtsList;
    }

    /**=========================== LOCAL JSON FILE READER =========================================**/
    public static void loadJSONFromAsset(Context context) {
        InputStream inputStream;
        try {
            inputStream=context.getAssets().open("states_and_districts.json");
            int size=inputStream.available();
            byte[] buffer=new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            STATES_DISTRICTS_JSON=new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
