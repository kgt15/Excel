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
import java.util.List;

/**
 * Created by gopikm on 30/8/16.
 */
public class fragment_talks extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<event> eventList;
    public static final String CSEvents = "CSEvents" ;
    SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                Log.d("Name: ",event_instance.getName());
                eventList.add(event_instance);
            };
        }catch (JSONException j){
            j.printStackTrace();
        }
        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,container,false);
        adapter = new cardAdapter_initiatives(eventList, getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("talks.json");
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
