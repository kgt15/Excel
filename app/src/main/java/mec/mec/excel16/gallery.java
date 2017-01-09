package mec.mec.excel16;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.special.ResideMenu.ResideMenu;

public class gallery extends AppCompatActivity {
    ResideMenu residemenu;
    LinearLayout linear;
    MediaPlayer mediaPlayer;

    String url = "http://excelapp-ondjango.rhcloud.com/gallery/album/";
    ProgressDialog progressDialog;

    @Override
    protected void onStop() {
        super.onStop();
        /*if(mediaPlayer.isPlaying())
            mediaPlayer.stop();*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(true);
        progressDialog.setInverseBackgroundForced(false);
        //mediaPlayer = MediaPlayer.create(this, R.raw.cut);

//        ResideMenu1 resideMenu1=new ResideMenu1(gallery.this,this);
//        residemenu=resideMenu1.main();
        if(isNetworkAvailable()){
            setContentView(R.layout.activity_gallery);
            getdata();
            linear=(LinearLayout) findViewById(R.id.container);

        }
        else
        {
            setContentView(R.layout.no_connection);

            //mediaPlayer.start();
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void getdata(){
     progressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response",""+response);
                progressDialog.hide();
                SharedPreferences prefs=getSharedPreferences("gallerydata", 0);
                prefs.edit().putString("gallery", ""+response).apply();
                changeFragment(new gallerylist());

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",""+error);
                progressDialog.hide();

            }
        });

        appcontroller.getInstance().addToRequestQueue(strReq);
    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//
//        return residemenu.dispatchTouchEvent(ev);
//    }
    public void changeFragment(Fragment fragment){


        FragmentManager manager5=getSupportFragmentManager();


        FragmentTransaction fragmentTransaction5 = manager5.beginTransaction();

        fragmentTransaction5.replace(R.id.container, fragment);

        fragmentTransaction5.commit();
    }
    public ResideMenu getResideMenu(){
        return residemenu;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(gallery.this, Home.class);
        startActivity(intent);
        finish();

    }
}
