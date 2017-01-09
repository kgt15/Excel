package mec.mec.excel16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.special.ResideMenu.ResideMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class News extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    ResideMenu residemenu;


    public newsInShortAdapter myAdapter;
    public ArrayList<newsInShort> details = new ArrayList<>();
    public Intent intent;
    private ListView listView;

    SharedPreferences preference;
    private static String preferences = "Password";
    private static String retreiveMain = "NewsFeed";
    private static String timeCode = "time";
    private SwipeRefreshLayout swipe;
    appcontroller ap = new appcontroller();

    LinearLayout layout;

    SharedPreferences news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
//        ResideMenu1 resideMenu1=new ResideMenu1(News.this,this);
//        residemenu=resideMenu1.main();

        news = getSharedPreferences("firsttime", 0);
       // layout =(LinearLayout) findViewById(R.id.linear1);
//        if (news.getBoolean("news", true)) {
//            layout.setVisibility(View.VISIBLE);
//
//            //this line need to be removed
//
//
//            news.edit().putBoolean("news", false).apply();
//        }
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        listView = (ListView) findViewById(R.id.shortList);
        preference = getSharedPreferences(preferences, 1);

        swipe.setOnRefreshListener(News.this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                getNews("http://excelapp-ondjango.rhcloud.com/feeds");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(News.this, newsfeedActivity.class);
                intent.putExtra("TAB_NUM", position);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onRefresh() {
        getNews("http://excelapp-ondjango.rhcloud.com/feeds");
    }


    public void getNews(String url) {
        swipe.setRefreshing(true);
        String timestamp = preference.getString(timeCode, "");

        if (timestamp == "") {
            //First time
            Log.v("Here14Here", "Here");
            url = url + "/?format=json";
        } else {
            Log.v("Here15Here", "Here");
            url = url + "/" + timestamp + "/?format=json";
        }

        Log.v("Link :", url);
        JsonObjectRequest newsRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String newTimeStamp = response.getString("timestamp");
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putString(timeCode, newTimeStamp);
                    editor.commit();

                    parseNews(response.getJSONArray("feeds"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", "Serious error");
                JSONArray arr = new JSONArray();
                parseNews(arr);
            }
        });

        if(appcontroller.getInstance() == null)
        {
            Log.d("Null object","Null object");
        }

        appcontroller.getInstance().addToRequestQueue(newsRequest);

    }

    public void parseNews(JSONArray news) {

        details.clear();

        String oldNews = preference.getString(retreiveMain, null);
        Log.v("String Formed :", ""+oldNews);

        JSONArray oldArray = null;
        if (oldNews != null) {
            try
            {
                oldArray = new JSONArray(oldNews);
                Log.v("Here1234Here", "0" + String.valueOf(oldArray));
                Log.v("Here1234Here", "1" + String.valueOf(news));
                int len = oldArray.length();
                JSONObject obj;
                for (int i = len; i < len + news.length(); i++)
                {
                    obj = news.getJSONObject(i - len);
                    Log.v("Error123", String.valueOf(obj));
                    oldArray.put(i, obj);
                }
            } catch (JSONException e)
            {
                Log.v("Crashs here", "Ye");
                e.printStackTrace();
            }
        }
        else
        {
            Log.v("Dont be here", "For 2nd time");
            oldArray = news;
        }

        Log.v("OldArray in final form ", String.valueOf(oldArray));
        int len = oldArray.length();
        Log.v("OldArray in lenght ", ""+len);
        for (int i = len - 1; i >= 0; i--) {
            newsInShort inShort = new newsInShort();
            try {

                JSONObject singleNews = oldArray.getJSONObject(i);
                Log.v("Inividual single item", String.valueOf(singleNews));
                inShort.setTitles("" + singleNews.getString("title"));
                inShort.setImageUrl("http://excelapp-ondjango.rhcloud.com" + singleNews.getString("imageurl"));
                inShort.setDescription(singleNews.getString("description"));
                String str = "" + singleNews.getString("timestamp");
                str = str.substring(8, 10) + "-" + str.substring(5, 7) + "-" + str.substring(2, 4);
                inShort.setDate("" + str);
                details.add(inShort);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences.Editor editor = preference.edit();
        editor.putString(retreiveMain, String.valueOf(oldArray));
        editor.commit();
        swipe.setRefreshing(false);
        myAdapter = new newsInShortAdapter(News.this, R.layout.news_in_short_list, details);
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(News.this,Home.class);
        startActivity(intent);
finish();
    }





//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        return residemenu.dispatchTouchEvent(ev);
//    }
}
