package mec.mec.excel16;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hassan on 9/3/16.
 */
public class singleLeaderBoards extends Fragment
{

    int page;
    float width;

    //Data for decoView
    int series1Index;
    int series2Index;
    int series3Index;


    //User Details
    String mRank, mName, mImageLink;
    int mScore;

    //Progress Bar
    ProgressDialog progressDialog;

    SharedPreferences preference;
    private static String preferences = "Password";
    private static String retreiveMain = "main";


    String topPlayer;

    //User Details (View Objects)
    TextView vRank, vName, vScore;
    TextView top10;
    ImageView vImage;


    //For the graph vew
    DecoView arcView;

    SharedPreferences.Editor editor;
    String email;

    //Data link
//    String url = "http://excelapp-ondjango.rhcloud.com/leaders/";
    String urlForTopScores = "http://excelapp-ondjango.rhcloud.com/leaderboards/topscores/";
    String urlForUserScore = "http://excelapp-ondjango.rhcloud.com/leaderboards/userscore/";


    private List<leaderBoardDetails> leaderBoardInfo = new ArrayList<>();
    private RecyclerView recyclerView;
    private leaderBoardAdapter mAdapter;

    public static singleLeaderBoards newInstance(int page) {
        singleLeaderBoards firstfragment = new singleLeaderBoards();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        firstfragment.setArguments(args);
        return firstfragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(true);
        progressDialog.setInverseBackgroundForced(false);

    }

    private void prepareTopScoreData(int counter)
    {
        for(int i = 1; i <= counter; i++)
        {
            leaderBoardDetails singleData = new leaderBoardDetails();
            singleData.setUsername("Name"+ i);
            singleData.setImageId(R.drawable.download);
            singleData.setRank(""+i);
            singleData.setScore(""+(20 * i));
            leaderBoardInfo.add(singleData);
        }
        mAdapter.notifyDataSetChanged();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.single_leaderboard, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvToDoList);
        arcView = (DecoView) view.findViewById(R.id.dynamicArcView);

        vName = (TextView) view.findViewById(R.id.mUserName);
        vImage = (ImageView) view.findViewById(R.id.mUserImage);
        vRank = (TextView) view.findViewById(R.id.mUserRank);
        vScore = (TextView) view.findViewById(R.id.mUserScore);
        top10 = (TextView) view.findViewById(R.id.tops);

        width = getResources().getDimension(R.dimen.lineWidth);

        mAdapter = new leaderBoardAdapter(leaderBoardInfo, getContext());
        //prepareTopScoreData(10);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

//        series1Index = arcView.addSeries(initializeData("#FFFFFF", "", 32f, 4000));
//        series2Index = arcView.addSeries(initializeData("#1A1A1A", "Name1", 64f, 4000));
//        series3Index = arcView.addSeries(initializeData("#60d8ee", "User", 96f, 3000));
//
//        arcView.addEvent(new DecoEvent.Builder(75).setIndex(series1Index).setDelay(1000).build());
//        arcView.addEvent(new DecoEvent.Builder(75).setIndex(series2Index).setDelay(1000).build());
//        arcView.addEvent(new DecoEvent.Builder(55).setIndex(series3Index).setDelay(1000).build());


        if (!isNetworkAvailable()) {
            preference = getActivity().getSharedPreferences(preferences, 1);
            String main = preference.getString(retreiveMain, null);

            if (main != null) {
                try {
                    Toast.makeText(getContext(), "Old Data", Toast.LENGTH_SHORT).show();
                    JSONObject Details = new JSONObject(main);
                    parse(Details);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                top10.setText("No Connection");
            }

        } else {
            progressDialog.show();
            Log.v("Here6Here", "6");
            jsonRetrieve();
            SharedPreferences pref = getActivity().getSharedPreferences("isloggedin", 0);
            email = pref.getString("email", "");
            Log.d("Email : ", email);
            getUserData(email);
        }


        return view;
    }

    public SeriesItem initializeData(String color, String label, Float centre, int spinTime) {

        float tempWidth = (centre / 32) * width;
        SeriesItem seriesItems;
        SeriesItem.Builder seriesItem = new SeriesItem.Builder(Color.parseColor(color))
                .setRange(0, 100, 0)
                .setInitialVisibility(true)
                .setLineWidth(width)//32f
                .setInterpolator(new LinearInterpolator())
                .setShowPointWhenEmpty(false)
                .setCapRounded(true)
                .setInset(new PointF(tempWidth, tempWidth))
                .setDrawAsPoint(false)
                .setSpinClockwise(true)
                .setSpinDuration(spinTime)
                .setChartStyle(SeriesItem.ChartStyle.STYLE_DONUT);
        if (label == "") {
            seriesItems = seriesItem.build();
        } else {
            int col = Color.parseColor("#FFFFFF");
            if (centre == 96)
                col = Color.parseColor("#3AB4DD");
            seriesItems = seriesItem.setSeriesLabel(new SeriesLabel.Builder(label + " %.0f%%").setColorText(col).build()).build();
        }

        return seriesItems;
    }

    public void jsonRetrieve() {
        leaderBoardInfo.clear();
        preference = getActivity().getSharedPreferences(preferences, 1);
        editor = preference.edit();
        JsonObjectRequest gameRequest = new JsonObjectRequest(Request.Method.GET, urlForTopScores, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    editor.putString(retreiveMain, String.valueOf(response));
                    editor.commit();

                    JSONArray test = response.getJSONArray("event"+(page+1));
                    Log.v("Here7Here", "7");
                    parse(response);
                } catch (JSONException e) {
                    top10.setText("Stay Tuned!!");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                top10.setText("No Connection");
                Log.v("Here7Here", "10");

            }
        });

        appcontroller.getInstance().addToRequestQueue(gameRequest);
    }

    public void parse(JSONObject info) {
        leaderBoardInfo.clear();
        String topScore = "0";
        try {
            //JSONObject games = info.getJSONObject("Games");
            JSONArray singleGame = info.getJSONArray("event" + (page + 1));

            for (int i = 0; i < singleGame.length(); i++) {

                leaderBoardDetails playerDetails = new leaderBoardDetails();
                JSONObject playerJsonDetail = singleGame.getJSONObject(i);
                Log.v("Player Details Bitchs ", "" + playerJsonDetail);
                JSONObject userDetails = playerJsonDetail.getJSONObject("user");
                playerDetails.setUsername("" + userDetails.getString("username"));
                playerDetails.setImageUrl("" + userDetails.getString("dp"));
                playerDetails.setRank("" +(i+1));
                playerDetails.setScore("" + playerJsonDetail.getString("score"));
                if (i == 0) {
                    topPlayer = userDetails.getString("username");
                    topScore = playerJsonDetail.getString("score");
                }
                leaderBoardInfo.add(playerDetails);
            }


            series1Index = arcView.addSeries(initializeData("#FFFFFF", "", 32f, 4000));
            series2Index = arcView.addSeries(initializeData("#000000", topPlayer, 64f, 4000));

            int precent = Integer.parseInt(topScore);
            arcView.addEvent(new DecoEvent.Builder(precent).setIndex(series1Index).setDelay(1000).build());
            arcView.addEvent(new DecoEvent.Builder(precent).setIndex(series2Index).setDelay(1000).build());

        } catch (JSONException e) {
            top10.setText("Stay Tuned!!");
            e.printStackTrace();
        }

        mAdapter.notifyDataSetChanged();
    }

    private void getUserData(String email)
    {
        Log.d("GetUserData", "Inside");
        String tempUrl = urlForUserScore + email;
               Log.d("TempUrl : ", tempUrl);
        JsonObjectRequest userRequest =new JsonObjectRequest(Request.Method.GET, tempUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                editor.putString("userData", String.valueOf(response));
                editor.commit();
                parseUserData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        appcontroller.getInstance().addToRequestQueue(userRequest);
    }

    public void parseUserData(JSONObject response)
    {
        Log.d("Err_Page_No", page+"");
        try
        {
            JSONObject singleEventData = response.getJSONObject("event"+(page+1));
            Log.v("JsonObj", ""+singleEventData);
            Log.d("Page No", ""+page);
            JSONObject user = singleEventData.getJSONObject("user");
            mName = user.getString("name");
            mImageLink = user.getString("dp");
            Log.v("Dp is ", mName);
            mScore = singleEventData.getInt("score");
            mRank = "" + singleEventData.getInt("rank");

            Log.d("Testing_here", "Testing1");
            vName.setText(mName);
            vScore.setText("" + mScore);
            vRank.setText(mRank);

            if(vImage != null)
            {
                Bitmap bm = Home.cache.get(""+mName);
                if(bm == null)
                {
                    imgCache img = new imgCache();
                    bm = img.getImageFromInternal(mName, getContext());
                    if(bm != null)
                    {
                        Home.cache.put(""+mName, bm);
                        vImage.setImageBitmap(bm);
                    }
                    else
                        img.saveToInternalMemory(mName, mImageLink, getContext(), vImage);
                }
                else
                    vImage.setImageBitmap(bm);
            }


            series3Index = arcView.addSeries(initializeData("#60d8ee", mName, 96f, 3000));

            arcView.addEvent(new DecoEvent.Builder(mScore).setIndex(series3Index).setDelay(1000).build());
        }
        catch(JSONException err)
        {
            Log.d("Err_Page_No", page+"");
            err.printStackTrace();
        }
    }

    private boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
