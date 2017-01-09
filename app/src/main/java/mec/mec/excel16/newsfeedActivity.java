package mec.mec.excel16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class newsfeedActivity extends AppCompatActivity {

    ArrayList<newsInShort> newsList = new ArrayList<newsInShort>();
    SharedPreferences preference;
    private static String preferences = "Password";
    private static String retreiveMain = "NewsFeed";
//    private FlipViewController flipView;
    Intent intent;
    int positions;

    private static final float MIN_SCALE = 0.75f;
    private List<Fragment> lists = new ArrayList<>();
    private VerticalViewPager verticalViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        positions = intent.getIntExtra("TAB_NUM", 0);
        preference = getSharedPreferences(preferences, 1);
        String json = preference.getString(retreiveMain, "");
        JSONArray newsArray = null;
        try {
            newsArray = new JSONArray(json);
        } catch (JSONException e) {
            Log.v("Here4Here", "Array not formed");
            e.printStackTrace();
        }

        verticalViewPager = (VerticalViewPager) findViewById(R.id.verticalviewpager);

        parse(newsArray);

        verticalViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 0) { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    view.setAlpha(1);
                    view.setTranslationX(0);
                    view.setScaleX(1);
                    view.setScaleY(1);
                } else if (position <= 1) {
                    // Fade the page out.
                    view.setAlpha(1 - position);

                    // Counteract the default slide transition
                    view.setTranslationY(pageHeight * -position);

                    // Scale the page down (between MIN_SCALE and 1)
                    float scaleFactor = MIN_SCALE
                            + (1 - MIN_SCALE) * (1 - Math.abs(position));
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                } else {
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }
        });
    }

    public void parse(JSONArray news)
    {
        int size = news.length();
        for(int i = size - 1; i >= 0; i--)
        {
            try
            {
                JSONObject singleNews = news.getJSONObject(i);
                String title = "" + singleNews.getString("title");
                String image_url = "" + singleNews.getString("imageurl");
                String descrip = "" + singleNews.getString("description");
                String date = "" + singleNews.get("timestamp");
                date = date.substring(8, 10) + "-" + date.substring(5, 7) + "-" + date.substring(2, 4);
                lists.add(newsLikeInShort.newInstance(i, title, image_url, date, descrip));
            }
            catch (JSONException err)
            {
             err.printStackTrace();
            }
        }

        verticalViewPager.setAdapter(new newsFeedInDetailsAdapter(getSupportFragmentManager(), lists));
        verticalViewPager.setCurrentItem(positions);

    }

    public class newsFeedInDetailsAdapter extends FragmentPagerAdapter {

        private List<Fragment> list;

        public newsFeedInDetailsAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }


        @Override
        public Fragment getItem(int position) {
            return this.list.get(position);
        }

        @Override
        public int getCount() {
            // Show total size pages.
            return this.list.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "PAGE 1";
                case 1:
                    return "PAGE 2";
                case 2:
                    return "PAGE 3";
                default:
                    return "PAGE " + position + 1 + "";
            }
        }

    }

}
