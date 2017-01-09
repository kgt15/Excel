package mec.mec.excel16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;




public class SplashScreen extends AppCompatActivity {

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    final String firstTime = "isloggedin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        settings = getSharedPreferences(firstTime, 0);
        editor = settings.edit();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if(settings.getBoolean("key",true))
                {
                    editor.putBoolean("key",false);
                    editor.commit();
                    Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);

                }
                else {
                    Intent intent = new Intent(SplashScreen.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2500);


    }
}
