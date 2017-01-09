package mec.mec.excel16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.special.ResideMenu.ResideMenu;

public class LeaderBoard extends AppCompatActivity {
    ResideMenu residemenu;
    LinearLayout layout;
    SharedPreferences leaderboard;

    private TabLayout tabLayout;
    private ViewPager viewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
//        ResideMenu1 resideMenu1=new ResideMenu1(LeaderBoard.this,this);
//        residemenu=resideMenu1.main();

        leaderboard= getSharedPreferences("firsttime", 0);
//        layout =(LinearLayout) findViewById(R.id.linear1);
//        if (leaderboard.getBoolean("leaderboard", true)) {
//            layout.setVisibility(View.VISIBLE);
//
//            //this line need to be removed
//            leaderboard.edit().putBoolean("leaderboard", false).apply();
//        }


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
     //   residemenu.addIgnoredView(viewPager);



    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentManager manager=null;
        manager=getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(manager);
        adapter.addFragment(singleLeaderBoards.newInstance(0), "Headshot");
        adapter.addFragment(singleLeaderBoards.newInstance(1), "Blackhole");
        adapter.addFragment(singleLeaderBoards.newInstance(2), "Dalal Bull");
        adapter.addFragment(singleLeaderBoards.newInstance(3), "#include");
        adapter.addFragment(singleLeaderBoards.newInstance(4), "Circuimstances");
        adapter.addFragment(singleLeaderBoards.newInstance(5), "Kryptos");
        adapter.addFragment(singleLeaderBoards.newInstance(6), "Tech  Geek");
        viewPager.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LeaderBoard.this,Home.class);
        startActivity(intent);
finish();
    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        return residemenu.dispatchTouchEvent(ev);
//    }
}
