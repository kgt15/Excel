package mec.mec.excel16;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hassan on 17-06-2016.
 */
public class newsInShortAdapter extends ArrayAdapter<newsInShort>
{
    private ArrayList<newsInShort> news;

    public newsInShortAdapter(Context context, int textViewResourceId, ArrayList<newsInShort>news)
    {
        super(context, textViewResourceId, news);
        this.news = news;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if(v == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.news_in_short_list, null);
        }

        newsInShort details = news.get(position);

        if (details != null)
        {

            ImageView imageView = (ImageView)v.findViewById(R.id.image);
            TextView titleView = (TextView)v.findViewById(R.id.titles);
            TextView dateView = (TextView) v.findViewById(R.id.date);
            final TextView despcriptionView = (TextView) v.findViewById(R.id.description);


            if(imageView != null)
            {
                Bitmap bm;
                bm = Home.cache.get(details.getImageUrl());
                if(bm == null)
                {
                    //String url = "http://excelapp-ondjango.rhcloud.com/media/ashwink/profilepic.jpg";
                    imgCache img =new imgCache();
                    bm = img.getImageFromInternal(""+details.getTitles(), getContext());

                    if(bm == null)
                    {
                        img.saveToInternalMemory(""+details.getTitles(), ""+details.getImageUrl(), getContext(), imageView);
                    }
                    else
                    {
                        imageView.setImageBitmap(bm);
                    }

                }
                else
                {
                    imageView.setImageBitmap(bm);
                }

            }


            if(titleView != null){   titleView.setText("" + details.getTitles());}
            if(dateView != null){   dateView.setText("" + details.getDate());}
            if(despcriptionView != null)
            {   String str = null;
                try
                {
                    str = ""+details.getDescription().substring(0, 28)+"..";
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    str = ""+details.getDescription();
                }
                despcriptionView.setText("" + str);
            }

        }

        return v;
    }

}

