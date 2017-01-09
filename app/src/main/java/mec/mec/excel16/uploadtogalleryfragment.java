package mec.mec.excel16;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created  Toast.makeText(getActivity(), "acces gallery", Toast.LENGTH_SHORT).show();----y jerin on 5/6/16.
 */
public class uploadtogalleryfragment extends Fragment {
    String value="";
    ImageView image1;
    Bitmap bitmap;
    LinearLayout layout;

    SharedPreferences upload;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.uploadgalleryfragment, container, false);
        image1=(ImageView) view.findViewById(R.id.images);
        try {
             value = getArguments().getString("imagestring");
            try {
                File f=new File(value, "profile.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

                Drawable d = new BitmapDrawable(getResources(), b);
                image1.setImageDrawable(d);

            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }


        }
        catch (Exception e){

        }
setBackButtonOverriding(view);
        return  view;
    }
    private void setBackButtonOverriding(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Fragment fragment4 = new galleryfragment();
                    FragmentManager manager3=getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction3 = manager3.beginTransaction();

                    fragmentTransaction3.replace(R.id.container, fragment4);

                    fragmentTransaction3.commit();

                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
