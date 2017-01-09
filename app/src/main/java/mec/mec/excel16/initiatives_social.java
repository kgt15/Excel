package mec.mec.excel16;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * Created by gopikm on 2/8/16.
 */
public class initiatives_social extends AppCompatActivity {
    TextView header,description;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiatives);
        header=(TextView)findViewById(R.id.header);
        description=(TextView)findViewById(R.id.description);
        Gson gson = new Gson();
        event event_instance=new event();
        Intent intent=getIntent();
        event_instance=gson.fromJson(intent.getStringExtra("event"),event_instance.getClass());
        header.setText(event_instance.getName());
        description.setText(event_instance.getDescription());
        imageView = (ImageView) findViewById(R.id.description_image);
        imageView.setImageResource(getResources().getIdentifier(event_instance.getEventid(),"drawable",getPackageName()));


    }
}
