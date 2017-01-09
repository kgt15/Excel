package mec.mec.excel16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class exhibition extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventList=new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray=jsonObj.getJSONArray("eventlist");
            Log.d("length","here");
            Log.d("length",""+jsonArray.length());
            for(int i = 0; i<jsonArray.length(); i++) {
                event event_instance = new event();
                JSONObject json = null;
                json = jsonArray.getJSONObject(i);
                event_instance.setImage(json.getString(config.TAG_IMAGE));
                event_instance.setDescription(json.getString(config.TAG_DESCRIPTION));
                event_instance.setName(json.getString(config.TAG_NAME));
                eventList.add(event_instance);
            };
        }catch (JSONException j){
            j.printStackTrace();
        }
        setContentView(R.layout.activity_exhibition);
        recyclerView1=(RecyclerView)findViewById(R.id.my_recycler_view);
        adapter = new cardAdapter_initiatives(eventList, getApplicationContext());
        recyclerView1.setAdapter(adapter);
        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("exhibitions.json");
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(exhibition.this,Home.class);
        startActivity(intent);
        finish();
    }
}
