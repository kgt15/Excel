package mec.mec.excel16;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Hassan on 16-06-2016.
 */
public class imgCache {

    public Bitmap getImageFromInternal(String userName, Context context) {
        Bitmap bm = null;
        File filePath = context.getFileStreamPath(userName);
        try {
            final FileInputStream inStream = new FileInputStream(filePath);
            bm = BitmapFactory.decodeStream(inStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.v("Bitmapcheck for getting", "Check Ch");
        if (bm == null) {
            Log.v("Mapisnull ", "Very null");
        }
        return bm;
    }

    public void saveToInternalMemory(final String userName, String imageUrl, Context context, final ImageView imageView) {
        Log.v("SaveToMemory", "Check ");
        FileOutputStream fileOutput = null;
        ImageRequest imageRequest = null;
        final Bitmap[] bm = new Bitmap[1];

        try {
            //fileOp = new FileOutputStream(insideBitmap);
            final FileOutputStream fileOp = context.openFileOutput(userName, 0);
            imageRequest = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageView.setImageBitmap(response);
                    Home.cache.put(userName, response);
                    response.compress(Bitmap.CompressFormat.PNG, 100, fileOp);
                    Log.v("Mapissaved ", "For full use");
                }
            }, 0, 0, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("No image :", "I repaeat no image");
                }
            });
            fileOutput.flush();
            fileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        appcontroller.getInstance().addToRequestQueue(imageRequest);
    }

}
