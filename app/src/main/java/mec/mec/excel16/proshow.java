package mec.mec.excel16;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.special.ResideMenu.ResideMenu;

public class proshow extends AppCompatActivity {
    ResideMenu residemenu;
    LinearLayout layout;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proshow);
//        ResideMenu1 resideMenu1=new ResideMenu1(proshow.this,this);
//        residemenu=resideMenu1.main();


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(proshow.this,Home.class);
        startActivity(intent);
finish();
    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        return residemenu.dispatchTouchEvent(ev);
//    }

}
