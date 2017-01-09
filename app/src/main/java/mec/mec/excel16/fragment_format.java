package mec.mec.excel16;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * Created by gopikm on 23/6/16.
 */
public class fragment_format extends Fragment {
    private ImageView imageView;
    private static final String STARTING_TEXT = "";
    SharedPreferences sp;
    public static fragment_format newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        fragment_format sampleFragment = new fragment_format();
        sampleFragment.setArguments(args);

        return sampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        TextView header,format;
        event event_instance=new event();
        View view =inflater.inflate(R.layout.fragment_format,container,false);
        header= (TextView)view.findViewById(R.id.header);
        format=(TextView)view.findViewById(R.id.format);
        sp=getActivity().getSharedPreferences("CSEvents", Context.MODE_PRIVATE);
        String jsonFavorites = sp.getString("Events", null);
        Gson gson = new Gson();
        event_instance = gson.fromJson(jsonFavorites,event_instance.getClass());
        imageView = (ImageView) view.findViewById(R.id.format_image);
        imageView.setImageResource(getActivity().getResources().getIdentifier(event_instance.getImage(),"drawable",getActivity().getPackageName()));
        header.setText(event_instance.getName());
        format.setText(event_instance.getEventformat());
       /* TextView t=(TextView)view.findViewById(R.id.format);
        t.setText(event_instance.getEventformat());*/
        return view;
    }
}
