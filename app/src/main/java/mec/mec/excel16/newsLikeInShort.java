package mec.mec.excel16;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Hassan on 09-07-2016.
 */
public class newsLikeInShort extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_DESCRIP = "descrip";
    private static final String ARG_TITLE = "title";
    private static final String ARG_IMAGE_URL = "image_url";
    private static final String ARG_DATE = "date";
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static newsLikeInShort newInstance(int sectionNumber, String title, String imageUrl, String date, String descrip) {
        newsLikeInShort fragment = new newsLikeInShort();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_IMAGE_URL, imageUrl);
        args.putString(ARG_DATE, date);
        args.putString(ARG_DESCRIP, descrip);
        fragment.setArguments(args);
        return fragment;
    }

    public newsLikeInShort() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_details_inshorts, container, false);
        ImageView imageDetail = (ImageView) rootView.findViewById(R.id.detailImage);
        TextView titleDetail = (TextView) rootView.findViewById(R.id.detailTitle);
        TextView dateDetail = (TextView) rootView.findViewById(R.id.detailDate);
        TextView descripDetail = (TextView) rootView.findViewById(R.id.detailDescrip);

        titleDetail.setText(getArguments().getString(ARG_TITLE));
        dateDetail.setText(getArguments().getString(ARG_DATE));
        descripDetail.setText(getArguments().getString(ARG_DESCRIP));
        if(imageDetail != null)
        {
            Bitmap bm;
            bm = Home.cache.get(getArguments().getString(ARG_IMAGE_URL));
            if(bm == null)
            {
                //String url = "http://excelapp-ondjango.rhcloud.com/media/ashwink/profilepic.jpg";
                imgCache img =new imgCache();
                bm = img.getImageFromInternal(""+getArguments().getString(ARG_TITLE), getContext());

                if(bm == null)
                {
                    img.saveToInternalMemory(""+getArguments().getString(ARG_TITLE), ""+getArguments().getString(ARG_IMAGE_URL), getContext(), imageDetail);
                }
                else
                {
                    imageDetail.setImageBitmap(bm);
                }

            }
            else
            {
                imageDetail.setImageBitmap(bm);
            }

        }


        return rootView;
    }

}
