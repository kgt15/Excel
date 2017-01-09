package mec.mec.excel16;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gopikm on 11/7/16.
 */
public class fragment_bm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<event> eventList;
    public static final String CSEvents = "CSEvents" ;
    SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*sp=getActivity().getSharedPreferences(CSEvents, Context.MODE_PRIVATE);
        String jsonFavorites = sp.getString("Events", null);
        Gson gson = new Gson();
        event[] favoriteItems = gson.fromJson(jsonFavorites,event[].class);
        eventList = Arrays.asList(favoriteItems);
        eventList = new ArrayList(eventList);*/
        eventList=new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray=jsonObj.getJSONArray("eventlist");
            for(int i = 0; i<jsonArray.length(); i++) {
                event event_instance = new event();
                JSONObject json = null;
                json = jsonArray.getJSONObject(i);
                event_instance.setImage(json.getString(config.TAG_IMAGE));
                event_instance.setDescription(json.getString(config.TAG_DESCRIPTION));
                event_instance.setName(json.getString(config.TAG_NAME));
                event_instance.setEventid(json.getString(config.TAG_EVENT_ID));
                //event_instance.setWebsitelink(json.getString(config.TAG_WEBSITELINK));
                event_instance.setEventformat(json.getString(config.TAG_EVENT_FORMAT));
                //event_instance.setDetails(json.getString(config.TAG_DETAILS));
                //event_instance.setYoutubelink(json.getString(config.TAG_YOUTUBE_LINK));
                Log.d("ID ",event_instance.getEventid());
                ArrayList<String> rules = new ArrayList<String>();
                JSONArray jArray_rules = json.getJSONArray(config.TAG_RULES);
                for(int j = 0; j<jArray_rules.length(); j++){
                    rules.add((String)jArray_rules.get(j)+"\n"+"\n"+"\n");
                }
                HashMap<String,String> contacts = new HashMap<>();
                JSONArray jArray_contacts = json.getJSONArray(config.TAG_CONTACTS);
                for(int j = 0; j<jArray_contacts.length(); j++){
                    // contacts.add(((String) jArray_contacts.get(j))+"\n");
                    contacts.put("name"+j,jArray_contacts.getJSONObject(j).getString("name"));
                    contacts.put("phone"+j,jArray_contacts.getJSONObject(j).getString("phone"));
                }
                event_instance.setRules(rules);
                event_instance.setContacts(contacts);
                eventList.add(event_instance);
            };
        }catch (JSONException j){
            j.printStackTrace();
        }
        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,container,false);
        adapter = new CardAdapter(eventList, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("tech_g.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
