package mec.mec.excel16;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ayz4sci.androidfactory.permissionhelper.PermissionHelper;
import com.etsy.android.grid.StaggeredGridView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import pl.tajchert.nammu.PermissionCallback;

/**
 * Created by jerin on 11/7/16.
 */
public class gallerylist extends Fragment {
    FloatingActionButton gallery;
    FloatingActionButton camera;
    FloatingActionsMenu menu;
    LinearLayout linearLayout;
    private StaggeredGridView mGridView;
    private CoverFlowAdapter mAdapter;
    private ArrayList<gallerypojo> mData = new ArrayList<>(0);
    LinearLayout layout1;
    EditText comment;
    Button send;

    String url1 = "http://excelapp-ondjango.rhcloud.com/gallery/upload/";

    int REQUEST_CAMERA=0,SELECT_FILE = 1;
    PermissionHelper permissionHelper;
    ImageView imageView;

    Bitmap bm=null;
    String imagesend="";
    ProgressDialog progressDialog;
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    LinearLayout layout;
    SharedPreferences prefs;
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
                        new String[]{android.Manifest.permission.CAMERA},
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
//
//    @Override
//    public void onDestroyView() {
//        permissionHelper.finish();
//        super.onDestroyView();
//    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // showRationale = false if user clicks Never Ask Again, otherwise true
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);

                if (showRationale) {
                    // do something here to handle degraded mode
                }
                else {
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public String imagetostrimg(Bitmap bm){
        String bal="";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        bal = Base64.encodeToString(b, Base64.DEFAULT);
        return bal;
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
    private void onCaptureImageResult(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        bm=getResizedBitmap(bm,300);
        imageView.setImageBitmap(bm);
        layout1.setVisibility(View.VISIBLE);
        imagesend=imagetostrimg(bm);
        // ivImage.setImageBitmap(thumbnail);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.gallerylistfragment, container, false);
        getPermissionForCamera();
        permissionHelper = PermissionHelper.getInstance(getActivity());
        linearLayout=(LinearLayout) view.findViewById(R.id.midlayer);
        imageView=(ImageView) view.findViewById(R.id.images);
        progressDialog=new ProgressDialog(getActivity());
        layout1=(LinearLayout) view.findViewById(R.id.lineralayer);
        send=(Button) view.findViewById(R.id.send);
        comment=(EditText) view.findViewById(R.id.comment);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comment.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please fill the comment box", Toast.LENGTH_SHORT).show();
                }
                else {
                    JSONObject json = new JSONObject();
                    try {

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
        mGridView = (StaggeredGridView) view.findViewById(R.id.grid_view);
        getData();


        if (mAdapter == null) {
            mAdapter = new CoverFlowAdapter(getActivity(), mData, new OnclickListener() {
                @Override
                public void OnClicked(int position) {
                    Log.d("pos",""+position);
                    changeFragment(new galleryfragment(),position);
                }
            });
        }

        mGridView.setAdapter(mAdapter);
    //    setBackButtonOverriding(view);

        return  view;
    }



    public void changeFragment(Fragment fragment, int posi){

        Log.d("pos_in",""+posi);
        FragmentManager manager5=getActivity().getSupportFragmentManager();
        Log.d("pos_af",""+posi);
        Bundle args = new Bundle();
        args.putInt("position", posi);
        fragment.setArguments(args);

        FragmentTransaction fragmentTransaction5 = manager5.beginTransaction();

        fragmentTransaction5.replace(R.id.container, fragment);

        fragmentTransaction5.commit();
    }
    public void getData(){
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


                }

                }
            catch(JSONException e){
                e.printStackTrace();

            }
    }

    public void getPermissionForCamera() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA)) {
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    READ_CONTACTS_PERMISSIONS_REQUEST);
        }
    }


}
