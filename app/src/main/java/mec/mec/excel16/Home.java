package mec.mec.excel16;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ayz4sci.androidfactory.permissionhelper.PermissionHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.special.ResideMenu.ResideMenu;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Calendar;


public class Home extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {

    private CallbackManager callbackManager;
    ResideMenu residemenu;
    LoginButton b;
    Uri uri;
    String url = "http://excelapp-ondjango.rhcloud.com/users/signup/";

    //Signin button
    private SignInButton signInButton;

    //Signing Options
    private GoogleSignInOptions gso;

    //google api client
    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;

    ShareDialog shareDialog;

    ProgressDialog progressDialog;


    LinearLayout middile;
    CardView signuplayer;

    final String firstTime = "isloggedin";
    SharedPreferences settings;

    static int cacheSize;
    public static LruCache<String, Bitmap> cache;


    String[] str={"","",""};


    //LinearLayout layout;

    SharedPreferences home;

    TextView text,day,togo,daysleft,link;

    myalert1 myAlert = new myalert1();

    JSONObject jsonObject=new JSONObject();

    ImageView image2;

    int visibility=0;
    SharedPreferences.Editor editor;



    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {


        myAlert.show(getFragmentManager(), "A");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this line need to be removed
        PermissionHelper permissionHelper = PermissionHelper.getInstance(this);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        shareDialog = new ShareDialog(this);
        setContentView(R.layout.activity_home);
        ResideMenu1 resideMenu1=new ResideMenu1(Home.this,this);
        residemenu=resideMenu1.main();
        middile= (LinearLayout) findViewById(R.id.middile);
        signuplayer= (CardView) findViewById(R.id.signuplayer);
        settings = getSharedPreferences(firstTime, 0);
        editor = settings.edit();
        image2=(ImageView) findViewById(R.id.image2);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(visibility==1)
                      middile.setVisibility(View.GONE);
            }
        });

        Log.v("i guess im ", "here ? ");
        LinearLayout tutorial = new LinearLayout(Home.this);
        tutorial.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        tutorial.setOrientation(LinearLayout.HORIZONTAL);

        ImageView tutorialImage = new ImageView(Home.this);
        tutorialImage.setImageResource(R.mipmap.ic_launcher);
        tutorial.addView(tutorialImage);


        if (settings.getBoolean("notloggedin", true)) {
            callingfunction();

         //this line need to be removed

         //   settings.edit().putBoolean("notloggedin", false).apply();
        }
        home = getSharedPreferences("firsttime", 0);

            //this line need to be removed




        //finding max size for cache
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        cacheSize = maxMemory / 8;

        //Initialize cache
        cache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

        myAlert.setButtonClickListener(new myalert1.ButtonClickListener() {
            @Override
            public void onClick(int option) {
                finish();
               System.exit(0);
            }
        });
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
            finish();
        }
        text = (TextView) findViewById(R.id.link);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.excelmec.org"));
                startActivity(browserIntent);
            }
        });
        day=(TextView)findViewById(R.id.daysrem);
        togo=(TextView)findViewById(R.id.togo);

        daysleft=(TextView)findViewById(R.id.daysleft);
        int days=0;
        int day_of_month,month;
        Calendar now= Calendar.getInstance();
        day_of_month=now.get(Calendar.DAY_OF_MONTH);
        month=now.get(Calendar.MONTH);
        month+=1;
        Log.d("Month",""+month);
        Log.d("day",""+day_of_month);
        switch(month)
        {
            case 7:
                days = 65+31-day_of_month+2;
                break;
            case 8:
                days = 34+30-day_of_month+2;
                break;
            case 9:
                days = 5+30-day_of_month;
                break;
            case 10:
                if(day_of_month<=5)
                    days = 5-day_of_month;
                else
                    days=0;
                break;

        }
        if(days==0){
            day.setVisibility(View.GONE);
            togo.setVisibility(View.GONE);
            daysleft.setVisibility(View.GONE);
        }
        else
            day.setText(""+days);




    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return residemenu.dispatchTouchEvent(ev);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }


    public void callingfunction(){
        Log.d("login","working");
        middile.setVisibility(View.VISIBLE);
        signuplayer.setVisibility(View.VISIBLE);

        setupfacebook();
        setupgoogle();



//
//        shareDialog = new ShareDialog(this);
//        callbackManager = CallbackManager.Factory.create();
//        shareDialog.registerCallback(callbackManager, new
//
//                FacebookCallback<Sharer.Result>() {
//                    @Override
//                    public void onSuccess(Sharer.Result result) {}
//
//                    @Override
//                    public void onCancel() {}
//
//                    @Override
//                    public void onError(FacebookException error) {}
//                });
//        uri  = Uri.parse("https://static.pexels.com/photos/12073/pexels-photo-12073-medium.jpeg");

    }
    private void handleSignInResult(GoogleSignInResult result) {

        //Toast.makeText(this, ""+getCertificateSHA1Fingerprint(), Toast.LENGTH_LONG).show();
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.v("i guess im ", "here ? ");
            /*LinearLayout tutorial = new LinearLayout(Home.this);
            tutorial.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            tutorial.setOrientation(LinearLayout.HORIZONTAL);

            ImageView tutorialImage = new ImageView(Home.this);
            tutorialImage.setImageResource(R.mipmap.ic_launcher);
            tutorial.addView(tutorial);*/


            Toast.makeText(this, ""+acct.getEmail(), Toast.LENGTH_LONG).show();
            Log.d("gmailsignin",acct.getDisplayName()+" "+acct.getEmail()+" "+acct.getPhotoUrl());
            str[0]=acct.getDisplayName();
            str[1]=acct.getEmail();
            if(acct.getPhotoUrl()!=null){
                str[2]=acct.getPhotoUrl().toString();
            }
            else{
                str[2]="https://pbs.twimg.com/profile_images/707587072815554560/Dr3Mc5eM.jpg";
            }
            success();


        } else {
            //Log.d("stat",result.getStatus().getStatusMessage());

            Toast.makeText(this, ""+result.getStatus().getStatusCode(), Toast.LENGTH_LONG).show();
        }
    }


    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();
        try {

            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));



        }
        catch(Exception e){

        }
        return bundle;
    }
    public void success(){

        signuplayer.setVisibility(View.GONE);


        home.edit().putBoolean("home", false).apply();


        progressDialog = new ProgressDialog(this);

        progressDialog.setCancelable(true);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        try{

            jsonObject.put("username",str[0]);
            jsonObject.put("email",str[1]);
            jsonObject.put("dp",str[2]);
            Log.d("email",str[1]);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try
        {
            Log.v("Starting try", "Checking");
            File path = getExternalFilesDir(null);
            File file = new File(path, "email.txt");
            FileOutputStream output = new FileOutputStream(file);
            output.write(str[1].getBytes());
            output.close();
            Log.v("Ending try", "Checking");
        }
        catch (IOException e)
        {
            Log.v("Error catch", "Checking");
            e.printStackTrace();
        }
        JsonObjectRequest gameRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    Log.d("response",""+response);
                    editor.putBoolean("notloggedin", false);
                    editor.putString("name",str[0]);
                    editor.putString("email",str[1]);
                    editor.putString("dp",str[2]);
                    editor.commit();
                    progressDialog.hide();
                    visibility=1;
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle("Excel 2016")
                                .setContentDescription(
                                        "Excel is around the corner, Check out the Excel 2016 App")

                                .setImageUrl(Uri.parse("https://pbs.twimg.com/profile_images/707587072815554560/Dr3Mc5eM.jpg"))
                                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=mec.mec.excel16"))
                                .build();

                        shareDialog.show(linkContent);
                    }
                    image2.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response",""+error);
                progressDialog.hide();
            }
        });

        appcontroller.getInstance().addToRequestQueue(gameRequest);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    public void setupgoogle(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) findViewById(R.id.btn_yes);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating an intent
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

                //Starting intent for result
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    public void setupfacebook(){

        b=(LoginButton) findViewById(R.id.btn_no);
       // b.setBackgroundResource(R.drawable.login);
        b.setReadPermissions(Arrays.asList(
                "public_profile", "email","user_friends"));
        b.setLoginBehavior(LoginBehavior.SUPPRESS_SSO);
        callbackManager = CallbackManager.Factory.create();
        b.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),loginResult.toString(),Toast.LENGTH_SHORT).show();
                Log.d("data",loginResult.toString());
                Log.d("data1",loginResult.getAccessToken().getUserId());
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Log.i("LoginActivity", response.toString());
                        Bundle bFacebookData = getFacebookData(object);
                        String email=bFacebookData.getString("email");
                        Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();
                        str[0]=bFacebookData.getString("first_name")+" "+bFacebookData.getString("last_name");
                        str[1]=bFacebookData.getString("email");
                        if(bFacebookData.getString("profile_pic")==null){
                            str[2]="https://pbs.twimg.com/profile_images/707587072815554560/Dr3Mc5eM.jpg";
                        }
                        else{
                            str[2]=bFacebookData.getString("profile_pic");
                        }

                        Log.d("name",str[0]+""+str[1]+""+str[2]);
                       success();


                    }
                });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }
    private String getCertificateSHA1Fingerprint() {
        PackageManager pm = this.getPackageManager();
        String packageName = this.getPackageName();
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        InputStream input = new ByteArrayInputStream(cert);
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(c.getEncoded());
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    public static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1) h = "0" + h;
            if (l > 2) h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1)) str.append(':');
        }
        return str.toString();
    }

}



