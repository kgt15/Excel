package mec.mec.excel16;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class exhibitions_view extends AppCompatActivity {

    ImageView imageView;
    TextView description1,name1;
    SharedPreferences preferences;
    event event_instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibitions_view);
        description1=(TextView) findViewById(R.id.description);
        name1=(TextView)findViewById(R.id.header);
        imageView=(ImageView)findViewById(R.id.description_image);
        event_instance=new event();
        preferences = getSharedPreferences("CSEvents", Context.MODE_PRIVATE);
        String jsonFavorites = preferences.getString("Events", null);
        Gson gson = new Gson();
        event_instance = gson.fromJson(jsonFavorites,event_instance.getClass());
        Log.d("name",event_instance.getName());
        name1.setText(event_instance.getName());
        description1.setText(event_instance.getDescription());
        imageView.setImageResource(getResources().getIdentifier(event_instance.getImage(),"drawable",getPackageName()));
    }
}
