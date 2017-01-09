package mec.mec.excel16;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class widget extends AppWidgetProvider {

    private static final String ACTION_EVENT_ONE
            = "com.greycodes.excel14.action.EVENT_ONE";
    private static final String ACTION_EVENT_TWO
            = "com.greycodes.excel14.action.EVENT_TWO";
    private static final String ACTION_EVENT_THREE
            = "com.greycodes.excel14.action.EVENT_THREE";
    private static final String ACTION_EVENT_FOUR
            = "com.greycodes.excel14.action.EVENT_FOUR";
    private static final String ACTION_EVENT_FIVE
            = "com.greycodes.excel14.action.EVENT_FIVE";
    private static final String ACTION_EVENT_SIX
            = "com.greycodes.excel14.action.EVENT_SIX";
    private static final String ACTION_EVENT_SEVEN
            = "com.greycodes.excel14.action.EVENT_SEVEN";
    private static final String ACTION_EVENT_EIGHT
            = "com.greycodes.excel14.action.EVENT_EIGHT";
    private static final String ACTION_EVENT_LOGO
            = "com.greycodes.excel14.action.EVENT_LOGO";
    private static final String ACTION_EVENT_REFRESH
            = "com.greycodes.excel14.action.EVENT_REFRESH";

    //Network variable
    private static final String urlForUserScore = "http://excelapp-ondjango.rhcloud.com/leaderboards/userscore/";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String email;

    String kryptosRank="QW";
    String techGeek="QW";
    String dalalRank="QW";
    String includeRank="QW";
    String circuimstanceRank="QW";
    String headShotRank="QW";
    String blackHoleRank="QW";
    String hackMasterRank="QW";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();

        Intent act = new Intent(context, Home.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, act, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        pref  = context.getSharedPreferences("isloggedin", 0);
        editor = pref.edit();


        getUserData(pref.getString("email",""),context);


//        if (ACTION_EVENT_REFRESH.equals(action)) {
//            getUserData("aravindcranjan54@gmail.com");
//            views.setInt(R.id.cont, "setBackgroundResource", R.drawable.one);
//            views.setTextViewText(R.id.rank,kryptosRank);
//            views.setTextViewText(R.id.event,"Kryptos");
//        }
        Log.d("kryptos",pref.getString("black","999"));

        if (ACTION_EVENT_ONE.equals(action)) {
            views.setInt(R.id.cont, "setBackgroundResource", R.drawable.one);
            views.setTextViewText(R.id.rank,pref.getString("kryptos","Na"));
            views.setTextViewText(R.id.event,"Kryptos");

        } else if (ACTION_EVENT_TWO.equals(action)) {
            views.setInt(R.id.cont, "setBackgroundResource", R.drawable.two);
            views.setTextViewText(R.id.rank,pref.getString("hack","Na"));
            views.setTextViewText(R.id.event,"Hackmaster");

        } else if (ACTION_EVENT_THREE.equals(action)) {
            views.setInt(R.id.cont, "setBackgroundResource", R.drawable.three);
            views.setTextViewText(R.id.rank,pref.getString("techgeek","Na"));
            views.setTextViewText(R.id.event,"Tech Geek");

        } else if (ACTION_EVENT_FOUR.equals(action)) {
            views.setInt(R.id.cont, "setBackgroundResource", R.drawable.four);
            views.setTextViewText(R.id.rank,pref.getString("dalal","Na"));
            views.setTextViewText(R.id.event,"Dalal Bull");


        } else if (ACTION_EVENT_FIVE.equals(action)) {
            views.setInt(R.id.cont, "setBackgroundResource", R.drawable.five);
            views.setTextViewText(R.id.rank,pref.getString("include","Na"));
            views.setTextViewText(R.id.event,"#include");

        } else if (ACTION_EVENT_SIX.equals(action)) {
            views.setInt(R.id.cont, "setBackgroundResource", R.drawable.six);
            views.setTextViewText(R.id.rank,pref.getString("circ","Na"));
            views.setTextViewText(R.id.event,"Circuimstance");

        } else if (ACTION_EVENT_SEVEN.equals(action)) {
            views.setInt(R.id.cont, "setBackgroundResource", R.drawable.seven);
            views.setTextViewText(R.id.rank,pref.getString("head","Na"));
            views.setTextViewText(R.id.event,"HeadShot");

        }else if (ACTION_EVENT_EIGHT.equals(action)) {
            views.setInt(R.id.cont, "setBackgroundResource", R.drawable.eight);
            Log.d("black",blackHoleRank);
            views.setTextViewText(R.id.rank,pref.getString("black","Na"));
            views.setTextViewText(R.id.event,"BlackHole");

        }

        appWidgetManager.partiallyUpdateAppWidget(appWidgetManager.getAppWidgetIds(new ComponentName(context,widget.class)),views);

    }

    private void getUserData(String email, final Context context)
    {

        Log.d("GetUserData", "Inside");
        String tempUrl = urlForUserScore + email;//"aravindcranjan54@gmail.com";
        Log.d("TempUrl : ", tempUrl);

        JsonObjectRequest userRequest =new JsonObjectRequest(Request.Method.GET, tempUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                editor.putString("userDataForWidget", String.valueOf(response));
                editor.commit();
                parseUserData(response,context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    JSONObject jsonObject = new JSONObject(pref.getString("userDataForWidget", ""));
                    parseUserData(jsonObject,context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        appcontroller.getInstance().addToRequestQueue(userRequest);
    }

    private void parseUserData(JSONObject response,Context context)
    {
        pref  = context.getSharedPreferences("isloggedin", 0);
        editor = pref.edit();

        kryptosRank = tryCatch(response, 6);
        techGeek = tryCatch(response, 7);
        dalalRank = tryCatch(response, 3);
        includeRank = tryCatch(response, 4);
        circuimstanceRank = tryCatch(response, 5);
        headShotRank = tryCatch(response, 1);
        blackHoleRank = tryCatch(response, 2);
        hackMasterRank = tryCatch(response, 8);
        Log.d("BlackHole data", blackHoleRank);
        Log.d("HeadShot data", headShotRank);
        editor.putString("kryptos",kryptosRank);
        editor.putString("techgeek",techGeek);
        editor.putString("dalal",dalalRank);
        editor.putString("include",includeRank);
        editor.putString("circ",circuimstanceRank);
        editor.putString("head",headShotRank);
        editor.putString("black",blackHoleRank);
        editor.putString("hack",hackMasterRank);
        editor.commit();

    }

    private String tryCatch(JSONObject response, int number)
    {
        Log.d("Data **", String.valueOf(response));
        try
        {
            JSONObject singleData = response.getJSONObject("event"+number);
            return ""+singleData.getInt("rank");
        }
        catch(JSONException e)
        {
            e.printStackTrace();
            return "Na";
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;

        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }


    }

    @Override
    public void onEnabled(Context context) {
//         Enter relevant functionality for when the first widget is created
        pref  = context.getSharedPreferences("isloggedin", 0);
        editor = pref.edit();
        email = pref.getString("email", "");
        Log.d("emaiiiiiil",email);
        if(email.equals(""))
            kryptosRank = techGeek = dalalRank = circuimstanceRank = headShotRank = blackHoleRank = includeRank = "N";
        else{
            getUserData(email,context);
        }


    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);


        views.setOnClickPendingIntent(R.id.one,
                PendingIntent.getBroadcast(context, 0,

                        new Intent(context, widget.class)
                                .setAction(ACTION_EVENT_ONE).putExtra("id", appWidgetId),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        views.setOnClickPendingIntent(R.id.two,
                PendingIntent.getBroadcast(context, 0,

                        new Intent(context, widget.class)
                                .setAction(ACTION_EVENT_TWO).putExtra("id", appWidgetId),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        views.setOnClickPendingIntent(R.id.three,
                PendingIntent.getBroadcast(context, 0,

                        new Intent(context, widget.class)
                                .setAction(ACTION_EVENT_THREE).putExtra("id", appWidgetId),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        views.setOnClickPendingIntent(R.id.four,
                PendingIntent.getBroadcast(context, 0,

                        new Intent(context, widget.class)
                                .setAction(ACTION_EVENT_FOUR).putExtra("id", appWidgetId),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        views.setOnClickPendingIntent(R.id.five,
                PendingIntent.getBroadcast(context, 0,

                        new Intent(context, widget.class)
                                .setAction(ACTION_EVENT_FIVE).putExtra("id", appWidgetId),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        views.setOnClickPendingIntent(R.id.six,
                PendingIntent.getBroadcast(context, 0,

                        new Intent(context, widget.class)
                                .setAction(ACTION_EVENT_SIX).putExtra("id", appWidgetId),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        views.setOnClickPendingIntent(R.id.seven,
                PendingIntent.getBroadcast(context, 0,

                        new Intent(context, widget.class)
                                .setAction(ACTION_EVENT_SEVEN).putExtra("id", appWidgetId),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        views.setOnClickPendingIntent(R.id.eight,
                PendingIntent.getBroadcast(context, 0,

                        new Intent(context, widget.class)
                                .setAction(ACTION_EVENT_EIGHT).putExtra("id", appWidgetId),
                        PendingIntent.FLAG_UPDATE_CURRENT));

        views.setOnClickPendingIntent(R.id.logoint,PendingIntent.getActivity(context,0,new Intent(context,SplashScreen.class),0));

//        views.setOnClickPendingIntent(R.id.ref,PendingIntent.getBroadcast(context,0,new Intent(context,widget.class)
//        .setAction(ACTION_EVENT_REFRESH).putExtra("id",appWidgetId),PendingIntent.FLAG_UPDATE_CURRENT));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

