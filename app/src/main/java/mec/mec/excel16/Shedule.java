package mec.mec.excel16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.special.ResideMenu.ResideMenu;

public class Shedule extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Shedule.this,Home.class);
        startActivity(intent);
        finish();
    }

    ResideMenu residemenu;

    int mNotificationId = 001;
    private FlipView mFlipView;

    private FlipAdapter mAdapter;
    LinearLayout collapseLayout;
    LinearLayout lin;
    String json;

    LinearLayout ind1,ind2,ind3,ind4;

    LinearLayout layout;

    SharedPreferences schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_fail);

        /*SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        mNotificationId = sp.getInt("your_int_key", 0);

        mFlipView = (FlipView) findViewById(R.id.flip_view);
        mAdapter = new FlipAdapter(this);
        mAdapter.setCallback(this);

        mFlipView.setAdapter(mAdapter);
        mFlipView.setOnFlipListener(this);
        mFlipView.peakNext(false);
        mFlipView.setOverFlipMode(OverFlipMode.GLOW);
        // mFlipView.setEmptyView(findViewById(R.id.empty_view));
        mFlipView.setOnOverFlipListener(this);

//        ind1= (LinearLayout) findViewById(R.id.indicatorone);
//        ind2= (LinearLayout) findViewById(R.id.indicatortwo);
//        ind3= (LinearLayout) findViewById(R.id.indicatorthree);
//        ind4= (LinearLayout) findViewById(R.id.indicatorfour);



//
        collapseLayout = (LinearLayout) findViewById(R.id.cont);
//        json = "{\"status\":1,\"schedule\":[{\"date\":\"5\",\"day\":\"Thursday\",\"timings\":[{\"time\":\"10:00 AM\",\"events\":[{\"title\":\"Inauguration\",\"id\":\"inuag111\",\"imagename\":\"inuagration\"},{\"title\":\"4*120\",\"id\":\"CMP04E1\",\"imagename\":\"fourx\"}]},{\"time\":\"11:00 AM\",\"events\":[{\"title\":\"Defacto - School Quiz (P)\",\"id\":\"CMP05E11\",\"imagename\":\"defacto\"},{\"title\":\"Spiderweb (P)\",\"id\":\"CMP05E6\",\"imagename\":\"spiderweb\"},{\"title\":\"Robowars (P)\",\"id\":\"CMP02E5\",\"imagename\":\"robowars\"},{\"title\":\"Papyrus of Ani (Tech)\",\"id\":\"CMP05E16\",\"imagename\":\"papany\"},{\"title\":\"App Genius\",\"id\":\"CMP01E6\",\"imagename\":\"appgenius\"}]}]},{\"date\":\"5\",\"day\":\"Thursday\",\"timings\":[{\"time\":\"11:00 AM\",\"events\":[{\"title\":\"Defacto - School Quiz (P)\",\"id\":\"CMP05E11\",\"imagename\":\"defacto\"},{\"title\":\"Spiderweb (P)\",\"id\":\"CMP05E6\",\"imagename\":\"spiderweb\"},{\"title\":\"Robowars (P)\",\"id\":\"CMP02E5\",\"imagename\":\"robowars\"},{\"title\":\"Papyrus of Ani (Tech)\",\"id\":\"CMP05E16\",\"imagename\":\"papany\"},{\"title\":\"App Genius\",\"id\":\"CMP01E6\",\"imagename\":\"appgenius\"}]},{\"time\":\"1:00 PM\",\"events\":[{\"title\":\"Defacto - School Quiz (F)\",\"id\":\"\",\"imagename\":\"defacto\"}]},{\"time\":\"2:00 PM \",\"events\":[{\"title\":\"Circuimstance (F)\",\"id\":\"\",\"imagename\":\"circuimstance\"},{\"title\":\"Papyrus of Ani (NonTech)\",\"id\":\"CMP05E16\",\"imagename\":\"papany\"},{\"title\":\"Spiderweb (F))\",\"id\":\"\",\"imagename\":\"spiderweb\"},{\"title\":\"Lord of the Code (F)\",\"id\":\"\",\"imagename\":\"loc\"}]},{\"time\":\"5:30 PM\",\"events\":[{\"title\":\"Untitled\",\"id\":\"\",\"imagename\":\"untitled\"}]}]},{\"date\":\"5\",\"day\":\"Thursday\",\"timings\":[{\"time\":\"11:00 AM\",\"events\":[{\"title\":\"Defacto - School Quiz (P)\",\"id\":\"CMP05E11\",\"imagename\":\"defacto\"},{\"title\":\"Spiderweb (P)\",\"id\":\"CMP05E6\",\"imagename\":\"spiderweb\"},{\"title\":\"Robowars (P)\",\"id\":\"CMP02E5\",\"imagename\":\"robowars\"},{\"title\":\"Papyrus of Ani (Tech)\",\"id\":\"CMP05E16\",\"imagename\":\"papany\"},{\"title\":\"App Genius\",\"id\":\"CMP01E6\",\"imagename\":\"appgenius\"}]},{\"time\":\"1:00 PM\",\"events\":[{\"title\":\"Defacto - School Quiz (F)\",\"id\":\"\",\"imagename\":\"defacto\"}]},{\"time\":\"2:00 PM \",\"events\":[{\"title\":\"Circuimstance (F)\",\"id\":\"\",\"imagename\":\"circuimstance\"},{\"title\":\"Papyrus of Ani (NonTech)\",\"id\":\"CMP05E16\",\"imagename\":\"papany\"},{\"title\":\"Spiderweb (F))\",\"id\":\"\",\"imagename\":\"spiderweb\"},{\"title\":\"Lord of the Code (F)\",\"id\":\"\",\"imagename\":\"loc\"}]},{\"time\":\"5:30 PM\",\"events\":[{\"title\":\"Untitled\",\"id\":\"\",\"imagename\":\"untitled\"}]}]},{\"date\":\"5\",\"day\":\"Thursday\",\"timings\":[{\"time\":\"11:00 AM\",\"events\":[{\"title\":\"Defacto - School Quiz (F)\",\"id\":\"\",\"imagename\":\"defacto\"}]},{\"time\":\"1:00 PM\",\"events\":[{\"title\":\"Defacto - School Quiz (P)\",\"id\":\"CMP05E11\",\"imagename\":\"defacto\"},{\"title\":\"Spiderweb (P)\",\"id\":\"CMP05E6\",\"imagename\":\"spiderweb\"},{\"title\":\"Robowars (P)\",\"id\":\"CMP02E5\",\"imagename\":\"robowars\"},{\"title\":\"Papyrus of Ani (Tech)\",\"id\":\"CMP05E16\",\"imagename\":\"papany\"},{\"title\":\"App Genius\",\"id\":\"CMP01E6\",\"imagename\":\"appgenius\"}]},{\"time\":\"2:00 PM \",\"events\":[{\"title\":\"Untitled\",\"id\":\"\",\"imagename\":\"untitled\"}]},{\"time\":\"5:30 PM\",\"events\":[{\"title\":\"Circuimstance (F)\",\"id\":\"\",\"imagename\":\"circuimstance\"},{\"title\":\"Papyrus of Ani (NonTech)\",\"id\":\"CMP05E16\",\"imagename\":\"papany\"},{\"title\":\"Spiderweb (F))\",\"id\":\"\",\"imagename\":\"spiderweb\"},{\"title\":\"Lord of the Code (F)\",\"id\":\"\",\"imagename\":\"loc\"}]}]}]}";
//        lin= (LinearLayout) findViewById(R.id.cont);
        //setData(0);



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Shedule.this,Home.class);
        startActivity(intent);

        finish();
    }

    void setData(int page){
        lin.removeAllViews();
        try {
            JSONObject o1 = new JSONObject(json);
            JSONArray sched = o1.getJSONArray("schedule");

            //for(int i=0;i<sched.length();i++)
            {
                JSONObject o2 = sched.getJSONObject(page);
                JSONArray timings = o2.getJSONArray("timings");

                for (int j = 0; j < timings.length(); j++) {
                    JSONObject o3 = timings.getJSONObject(j);
                    JSONArray events = o3.getJSONArray("events");

                    final TextView time = new TextView(this);
                    time.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));
                    time.setText(o3.optString("time"));
                    lin.addView(time);

                    HorizontalScrollView h = new HorizontalScrollView(this);
                    h.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                    h.setId(Integer.parseInt("1"));
                    h.removeAllViews();
                    lin.addView(h);
                    LinearLayout lay = new LinearLayout(this);

                    FrameLayout.LayoutParams ll = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


                    lay.setLayoutParams(ll);
                    lay.setOrientation(LinearLayout.HORIZONTAL);

                    h.addView(lay);

                    //System.out.println("TIMING" + j);


                    for (int k = 0; k < events.length(); k++) {
                        JSONObject o4 = events.getJSONObject(k);

                        //System.out.println("events" + k);

                        CardView view = new CardView(this);

                        FrameLayout.LayoutParams ll2 = new FrameLayout.LayoutParams(250,250);
                        ll2.gravity= Gravity.BOTTOM;
                        view.setId(Integer.parseInt("12"));
                        view.setLayoutParams(ll2);
                        view.setForegroundGravity(Gravity.BOTTOM);
                        //view.setCardBackgroundColor(Color.rgb(25, 36, 42));
                        view.setCardElevation(15);
                        view.setClickable(true);


                        //view.setBackgroundDrawable( getResources().getDrawable(R.drawable.appgenius) );

                        RelativeLayout layout = new RelativeLayout(this);
                        FrameLayout.LayoutParams ll4 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        layout.setLayoutParams(ll4);




                        final TextView t = new TextView(this);

                        FrameLayout.LayoutParams ll1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        ll1.gravity= Gravity.CENTER;
                        t.setText(o4.getString("title"));
                        //t.setTextSize(1);
                        t.setLayoutParams(ll1);
                        t.setBackgroundDrawable( getResources().getDrawable(R.drawable.round) );
                        t.setGravity(Gravity.CENTER_HORIZONTAL);
                        t.setTextSize(7);
                        t.setTextColor(Color.rgb(255, 255, 255));
                        t.setLayoutParams(new FrameLayout.LayoutParams(250, 35));


                        FrameLayout.LayoutParams ll3 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        ll3.gravity= Gravity.CENTER;


                        view.addView(layout);

                        //layout.addView(image);
                        layout.addView(t);

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Toast.makeText(MainActivity.this,"sdvgsfg",Toast.LENGTH_LONG).show();

                                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
                                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                                //View popupView = layoutInflater.inflate(R.layout.popup,null);

                                Resources r = getResources();
                                float pxx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics());

                                int px = (int) pxx;

                                float pyy = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 275, r.getDisplayMetrics());

                                int py = (int) pyy;
//                                /
//
//                                window.setTouchable(true);
//                                // window.setFocusable(true);
//
//                                window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
//                                try{
//                                    View container = (View) window.getContentView().getParent();
//                                    WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//                                    WindowManager.LayoutParams par = (WindowManager.LayoutParams) container.getLayoutParams();
//                                    par.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//                                    par.dimAmount = 0.89f;
//                                    wm.updateViewLayout(container, par);
//                                }
//                                catch(Exception e){
//
//                                }



//                                NotificationCompat.Builder mBuilder =
//                                        (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
//                                                .setSmallIcon(R.drawable.ad)
//                                                .setContentTitle(t.getText())
//                                                .setContentText("Hello World!").setColor(Color.rgb(150,150,150));
//                                // Sets an ID for the notification
//
//                                // Gets an instance of the NotificationManager service
//                                NotificationManager mNotifyMgr =
//                                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                                // Builds the notification and issues it.
//                                mNotifyMgr.notify(mNotificationId, mBuilder.build());
//                                mNotificationId++;

//                                SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sp.edit();
//                                editor.putInt("your_int_key", mNotificationId);
//                                editor.commit();

//                                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//                                // Vibrate for 500 milliseconds
//                                v.vibrate(500);

//                                DateFormat df = new SimpleDateFormat("h:mm a");
//                                String timestamp = df.format(Calendar.getInstance().getTime());
//
//                                Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_LONG).show();

//                                DateFormat df = new SimpleDateFormat("d,MM,yyyy");
//                                String date = df.format(Calendar.getInstance().getTime());
//
//                                Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();


                            }
                        });


                        lay.setGravity(Gravity.CENTER);
                        lay.addView(view);

                        LinearLayout temp = new LinearLayout(this);
                        temp.setLayoutParams(new FrameLayout.LayoutParams(25, ViewGroup.LayoutParams.WRAP_CONTENT));
                        lay.addView(temp);

                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onPageRequested(int page) {
        mFlipView.smoothFlipTo(page);
        //System.out.println(page);
*/


    }

    /*@Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        System.out.println(position);
       // setData(position);


        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);



//        if(position > mFlipView.getPageCount()-3 && mFlipView.getPageCount()<30){
//            mAdapter.addItems(5);
//        }
    }

    @Override
    public void onOverFlip(FlipView v, OverFlipMode mode, boolean overFlippingPrevious, float overFlipDistance, float flipDistancePerPage) {

    }*/
}
