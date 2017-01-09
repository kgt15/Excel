package mec.mec.excel16;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;

/**
 * Created by gopikm on 20/6/16.
 */
public class Details extends AppCompatActivity {

    private BottomBar bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Gson gson = new Gson();
        event event_instance=new event();
        Intent intent=getIntent();
        event_instance=gson.fromJson(intent.getStringExtra("event"),event_instance.getClass());
        bottomBar=BottomBar.attach(this,savedInstanceState);
        bottomBar.noTopOffset();
        bottomBar.noNavBarGoodness();
        bottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer, new BottomBarFragment(fragment_description.newInstance("Details",event_instance), R.drawable.deatilsblue1, "Details"),
                new BottomBarFragment(fragment_format.newInstance("Format"), R.drawable.formatblue1, "Format"),
                new BottomBarFragment(fragment_rules.newInstance("Rules"), R.drawable.rulesblue1, "Rules"),
                new BottomBarFragment(fragment_contacts.newInstance("Contacts"), R.drawable.contactblue1, "Contacts"));



    }
}
