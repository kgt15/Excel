package mec.mec.excel16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

//import com.bartoszlipinski.flippablestackview.FlippableStackView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by jerin on 6/5/16.
 */
public class newsfeedfragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public newsInShortAdapter myAdapter;
    public ArrayList<newsInShort> details = new ArrayList<>();
    public Intent intent;
    private ListView listView;


    SharedPreferences preference;
    private static String preferences = "Password";
    private static String retreiveMain = "NewsFeed";
    private static String timeCode = "time";
    private SwipeRefreshLayout swipe;

//    private flipAdapter adapter;
//    private FlippableStackView stack;
//    private List<Fragment> lists;
//    int counter = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.newsfeedfragment, container, false);

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        listView = (ListView) view.findViewById(R.id.shortList);
        preference = getContext().getSharedPreferences(preferences, 1);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                getNews("http://excelapp-ondjango.rhcloud.com/feeds");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getContext(), newsfeedActivity.class);
                intent.putExtra("TAB_NUM", position);
                startActivity(intent);
            }
        });

//        createPagerAdapter();
//
//        adapter = new flipAdapter(getChildFragmentManager(), lists);
//
//        stack = (FlippableStackView) view.findViewById(R.id.stack);
//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Fab Working", Toast.LENGTH_LONG).show();
//            }
//        });
//        stack.initStack(3);
//        stack.setAdapter(adapter);
//        stack.setCurrentItem(2);

        return  view;
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
        myAdapter = new newsInShortAdapter(getContext(), R.layout.news_in_short_list, details);
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

}
