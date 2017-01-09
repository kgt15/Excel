package mec.mec.excel16;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ayz4sci.androidfactory.permissionhelper.PermissionHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import pl.tajchert.nammu.PermissionCallback;


/**
 * Created by jerin on 6/5/16.
 */
public class galleryfragment extends Fragment {

    private FeatureCoverFlow mCoverFlow;
    private ArrayList<gallerypojo> mData = new ArrayList<>(0);
    private TextView mTitle,mtitle1;
    private CoverFlowAdapter mAdapter;
    LinearLayout linearLayout;
    PermissionHelper permissionHelper;

    FloatingActionButton gallery;
    FloatingActionButton camera;
    FloatingActionsMenu menu;
    String str="";
    int REQUEST_CAMERA=0,SELECT_FILE = 1;

    Bitmap bm=null;

    int value=0;

    LinearLayout layout1;
    ImageView imageView;
    EditText comment;
    Button send;
    String imagesend="";

    SharedPreferences prefs;


    String[] str2={"","","",""};
    String line="";
    String reply="";
    StringBuffer str1;
    ProgressDialog progressDialog;


    //private ResideMenu resideMenu;

    LinearLayout layout;
    SharedPreferences galleryfragment;

    String url1 = "http://excelapp-ondjango.rhcloud.com/gallery/upload/";
    //  String url1="http://192.168.0.124:8080/gallery/upload/";


    final String firstTime = "isloggedin";

    @Override
    public void onDestroy() {
        super.onPause();
        progressDialog.dismiss();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionHelper.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);

            }
            else if (requestCode == REQUEST_CAMERA)
            {
                permissionHelper.verifyPermission(
                        new String[]{"take picture"},
                        new String[]{Manifest.permission.CAMERA},
                        new PermissionCallback() {
                            @Override
                            public void permissionGranted() {
                                //action to perform when permission granteed
                            }

                            @Override
                            public void permissionRefused() {
                                //action to perform when permission refused
                            }
                        }
                );
                onCaptureImageResult(data);
            }

        }


    }

    @Override
    public void onDestroyView() {
        permissionHelper.finish();
        super.onDestroyView();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void onCaptureImageResult(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        bm=getResizedBitmap(bm,300);
        imageView.setImageBitmap(bm);
        layout1.setVisibility(View.VISIBLE);
        imagesend=imagetostrimg(bm);
        // ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {


        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), data.getData());
                bm=getResizedBitmap(bm,300);
                imageView.setImageBitmap(bm);
                layout1.setVisibility(View.VISIBLE);
                imagesend=imagetostrimg(bm);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // ivImage.setImageBitmap(bm);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.galleryfragment, container, false);
        permissionHelper = PermissionHelper.getInstance(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        gallery parentActivity = (gallery) getActivity();
        // resideMenu = parentActivity.getResideMenu();
        getData();
        galleryfragment = getActivity().getSharedPreferences("firsttime", 0);
        layout =(LinearLayout) view.findViewById(R.id.linear);
        if (galleryfragment.getBoolean("galleryfragment", true))
        {
            layout.setVisibility(View.VISIBLE);

            //this line need to be removed
            galleryfragment.edit().putBoolean("galleryfragment", false).apply();
        }
        prefs=getActivity().getSharedPreferences(firstTime, 0);
        Log.d("Email",prefs.getString("email",""));
        layout1=(LinearLayout) view.findViewById(R.id.lineralayer);
        comment=(EditText) view.findViewById(R.id.comment);
        comment.requestFocus();
        send=(Button) view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comment.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please fill the comment box", Toast.LENGTH_SHORT).show();
                }
                else {
                    JSONObject json = new JSONObject();
                    try {
                        prefs=getActivity().getSharedPreferences(firstTime, 0);
                        json.put("image", imagesend);
                        json.put("username", prefs.getString("email", ""));
                        json.put("description", comment.getText().toString());
                        Log.d("Email", prefs.getString("email", ""));
                    } catch (Exception e) {

                    }

                    progressDialog.setCancelable(true);
                    progressDialog.setTitle("Processing...");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    JsonObjectRequest gameRequest = new JsonObjectRequest(Request.Method.POST, url1, json, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {

                                Log.d("response", "" + response);
                                layout1.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "The  team of highly trained monkeys are filtering your image", Toast.LENGTH_LONG).show();

                                progressDialog.hide();
                            } catch (Exception e) {

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Log.d("error", error + "");
                        }
                    });
                    gameRequest.setRetryPolicy(new DefaultRetryPolicy(1000 * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    appcontroller.getInstance().addToRequestQueue(gameRequest);
                }
            }});


        mTitle = (TextView) view.findViewById(R.id.title);
        Typeface typeFace= Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Regular.ttf");
        mTitle.setTypeface(typeFace);

        mtitle1 = (TextView) view.findViewById(R.id.title2);
        Typeface typeFace1= Typeface.createFromAsset(getActivity().getAssets(),"Roboto-Regular.ttf");
        mtitle1.setTypeface(typeFace1);
        menu=(FloatingActionsMenu)view.findViewById(R.id.right_labels);

        menu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                linearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                linearLayout.setVisibility(View.GONE);
            }
        });

        gallery=(FloatingActionButton) view.findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);//
                startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);

            }
        });
        camera=(FloatingActionButton) view.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);

            }
        });
        linearLayout=(LinearLayout) view.findViewById(R.id.midlayer);
        mAdapter = new CoverFlowAdapter(getActivity(), mData, new OnclickListener() {
            @Override
            public void OnClicked(int position) {

            }
        });

        mCoverFlow = (FeatureCoverFlow) view.findViewById(R.id.coverflow);

        imageView=(ImageView) view.findViewById(R.id.images);

        value = getArguments().getInt("position");
        mCoverFlow.setAdapter(mAdapter);
        mCoverFlow.scrollToPosition(value);
//        resideMenu.addIgnoredView(mCoverFlow);
        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(mData.get(position).getUploader());
                mtitle1.setText("\" "+mData.get(position).getUploaderscomment()+" \"");
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
                mtitle1.setText("");
            }
        });
        setBackButtonOverriding(view);
        return  view;
    }
    public String imagetostrimg(Bitmap bm){
        String bal="";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        bal = Base64.encodeToString(b, Base64.DEFAULT);
        return bal;
    }

    private void setBackButtonOverriding(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    if(layout1.getVisibility()==View.VISIBLE){
                        layout1.setVisibility(View.GONE);
                    }
                    else {
                        //resideMenu.clearIgnoredViewList();
                        Fragment fragment4 = new gallerylist();
                        FragmentManager manager3 = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction3 = manager3.beginTransaction();

                        fragmentTransaction3.replace(R.id.container, fragment4);

                        fragmentTransaction3.commit();
                    }
                    return  true;

                } else {
                    return false;
                }
            }
        });
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public void getData(){
        SharedPreferences prefs;
        prefs= getActivity().getSharedPreferences("gallerydata", 0);
        String str=  prefs.getString("gallery", "");
        try{
            JSONArray jArray= new JSONArray(str);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jsonObject = jArray.getJSONObject(i);
                gallerypojo pojo=new gallerypojo();
                pojo.setimage(jsonObject.getString("image"));
                pojo.setnooflikes(jsonObject.getInt("likes"));
                pojo.setUploader(jsonObject.getString("uploader"));
                pojo.setUploaderscomment(jsonObject.getString("description"));
                mData.add(pojo);


                // byte[] decodedString = Base64.decode(str, Base64.DEFAULT);
                //       Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                //      friend.image=decodedByte;
            }

        }
        catch(Exception e){

        }
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.excellogo), "Ramu","how do u do"));
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.excellogo),"Raju","how are u"));
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.excellogotext),"Mayavi","how is your leg"));
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.image_1),"Luttappi","how old are you"));
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.image_1),"Joseph","how old are you"));
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.image_1),"Anil","how old are you"));
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.image_1),"Sunny","how old are you"));
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.image_1),"Rahul","how old are you"));
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.image_1),"Antony","how old are you"));
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.image_1),"Sarath","how old are you"));
//        mData.add(new gallerypojo(getResources().getDrawable(R.drawable.image_1),"Roshan","how old are you"));

    }
}
