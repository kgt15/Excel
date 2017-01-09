package mec.mec.excel16;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.special.ResideMenu.ResideMenu;

import java.io.File;

public class FunZone extends AppCompatActivity {
    ResideMenu residemenu;
    LinearLayout layout;
    SharedPreferences funzone;
    DownloadManager downloadManager;
    String path;
    long idForBlachole;
    long idForHeadshot;
    PackageManager packageManager;
    ImageButton actionBlackhole;
    ImageButton actionHeadshot;
    boolean isBlackHoleInstalled = false;
    boolean isHeadshotInstalled = false;
    boolean isBlackHoleDownloading = false;
    boolean isHeadshotDownloading = false;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_zone);
        ActivityCompat.requestPermissions(FunZone.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
//        ResideMenu1 resideMenu1 = new ResideMenu1(FunZone.this, this);
//        residemenu = resideMenu1.main();
        funzone = getSharedPreferences("firsttime", 0);
        layout = (LinearLayout) findViewById(R.id.linear);
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        packageManager = getPackageManager();
        actionBlackhole = (ImageButton) findViewById(R.id.actionForBlackholes);
        actionHeadshot = (ImageButton) findViewById(R.id.actionForHeadshots);

        if (funzone.getBoolean("funzone", true)) {
            layout.setVisibility(View.VISIBLE);

            //this line need to be removed
            funzone.edit().putBoolean("funzone", false).apply();
        }


        BroadcastReceiver receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long ids = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(idForBlachole, idForHeadshot);
                    Cursor c = downloadManager.query(query);
                    if (c.moveToFirst()) {
                        int index = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(index)) {
                            if (ids == idForBlachole) {
                                isBlackHoleDownloading = false;
                                installApp("Blackhole.apk");
                                actionBlackhole.setImageResource(R.drawable.playbutton);
                            } else
                            {
                                isHeadshotDownloading = false;
                                installApp("Headshot.apk");
                                actionHeadshot.setImageResource(R.drawable.playbutton);
                            }
                        }
                    }
                }


            }
        };

        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        if (isPackageInstalled("com.censwib.blackholexexcel")) {
            isBlackHoleInstalled = true;
            actionBlackhole.setImageResource(R.drawable.playbutton);
        } else
            actionBlackhole.setImageResource(R.drawable.download);


        if (isPackageInstalled("com.censwib.headshot")) {
            actionHeadshot.setImageResource(R.drawable.playbutton);
            isHeadshotInstalled = true;
        } else
            actionHeadshot.setImageResource(R.drawable.download);

        actionBlackhole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBlackHoleInstalled) {
                    playApp("com.censwib.blackholexexcel");
                } else {
                    File f = new File(path + "/" + "Blackhole.apk");
                    if (f.exists())
                    {
                        if (isBlackHoleDownloading)
                        {
                            downloadManager.remove(idForBlachole);
                            f.delete();
                            isBlackHoleDownloading = false;
                            actionBlackhole.setImageResource(R.drawable.download);
                        }
                        else
                            installApp("Blackhole.apk");
                    }
                    else
                        download("Blackhole.apk", "https://docs.google.com/uc?export=download&id=0B1YxM_ZdW8nVTFJ4TGV6N25ISzg");
                }
            }
        });

        actionHeadshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHeadshotInstalled)
                    playApp("com.censwib.headshot");
                else {
                    File f = new File(path + "/" + "Headshot.apk");
                    if (f.exists())
                    {
                       if (isHeadshotDownloading)
                       {
                           downloadManager.remove(idForHeadshot);
                           f.delete();
                           isHeadshotDownloading = false;
                           actionHeadshot.setImageResource(R.drawable.download);
                       }
                        else
                       {
                            installApp("Headshot.apk");
                       }
                    }
                    else
                        download("Headshot.apk", "https://dl.dropboxusercontent.com/s/yjdvmsrle5fd2ka/headshot.apk?dl=0");
                }
            }
        });



    }



    private boolean isPackageInstalled(String packageName) {
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FunZone.this, Home.class);
        startActivity(intent);
    }

    private void download(String appName, String stringUri) {

        if (appName == "Blackhole.apk") {
            isBlackHoleDownloading = true;
            actionBlackhole.setImageResource(R.drawable.delete);
            Toast.makeText(FunZone.this, "Downloading Blackhole... ", Toast.LENGTH_SHORT).show();
        } else {
            isHeadshotDownloading = true;
            actionHeadshot.setImageResource(R.drawable.delete);
            Toast.makeText(FunZone.this, "Downloading Headshot... ", Toast.LENGTH_SHORT).show();
        }
        Uri downloadLocation = Uri.fromFile(new File(path, appName));//Environment.DIRECTORY_DOWNLOADS
        //String strUri = "https://download.apkpure.com/c/APK/1627/0225023863e86c17?_fn=RmFjZWJvb2sgTGl0ZV92MTIuMC4wLjcuMTQwX2Fwa3B1cmUuY29tLmFwaw%3D%3D&k=43c31322f5ed4a670864f23fd8276b6757977879&_p=Y29tLmZhY2Vib29rLmxpdGU%3D&c=1%7CSOCIAL";
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(stringUri);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Downloading..");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Log.v("The path : ", path);
        request.setDestinationUri(downloadLocation);
        long id = downloadManager.enqueue(request);
        if(appName == "Blackhole.apk")
            idForBlachole = id;
        else
            idForHeadshot = id;
    }

    private void installApp(String appName) {
        String str;
        str = path + "/" + appName;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + str), "application/vnd.android.package-archive");
        startActivity(intent);

    }

    private void playApp(String packageName) {
        Intent playGame = getPackageManager().getLaunchIntentForPackage(packageName);
        startActivity(playGame);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(FunZone.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
        }
}
