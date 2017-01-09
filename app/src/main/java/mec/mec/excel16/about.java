package mec.mec.excel16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.special.ResideMenu.ResideMenu;

public class about extends AppCompatActivity {
    ResideMenu residemenu;
    LinearLayout layout;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout  tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    SharedPreferences about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        about = getSharedPreferences("firsttime", 0);

//        ResideMenu1 resideMenu1=new ResideMenu1(about.this,this);
//        residemenu=resideMenu1.main();
        setContentView(R.layout.activity_about);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Sponsors(),"Sponsors");
        viewPagerAdapter.addFragment(new ExcelTeam(),"ExcelTeam");
        viewPagerAdapter.addFragment(new AppTeam(),"AppTeam" );
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //residemenu.addIgnoredView(viewPager);
    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        return residemenu.dispatchTouchEvent(ev);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(about.this,Home.class);
        startActivity(intent);
        finish();

    }
}
