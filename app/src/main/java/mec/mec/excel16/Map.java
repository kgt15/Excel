package mec.mec.excel16;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.special.ResideMenu.ResideMenu;



public class Map extends AppCompatActivity {
    ResideMenu residemenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


//        ResideMenu1 resideMenu1=new ResideMenu1(Map.this,this);
//        residemenu=resideMenu1.main();

         changeFragment(new mapfragment());

    }

    public void changeFragment(Fragment fragment){


        FragmentManager manager5=getSupportFragmentManager();


        FragmentTransaction fragmentTransaction5 = manager5.beginTransaction();

        fragmentTransaction5.replace(R.id.container, fragment);

        fragmentTransaction5.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Map.this,Home.class);
        startActivity(intent);
finish();
    }



//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        return residemenu.dispatchTouchEvent(ev);
//    }
}

