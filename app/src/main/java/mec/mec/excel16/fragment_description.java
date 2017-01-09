package mec.mec.excel16;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
 * Created by gopikm on 22/6/16.
 */
public class fragment_description extends Fragment {
    private ImageView imageView;
    SharedPreferences sp;
    event event_instance;

    private static final String STARTING_TEXT = "";
    public static fragment_description newInstance(String text,event event_instance) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);
        /*Gson gson = new Gson();
        args.putString("event",gson.toJson(event_instance));*/
        fragment_description sampleFragment = new fragment_description();
        sampleFragment.setArguments(args);
        return sampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        event_instance=new event();
       /* Gson gson = new Gson();
        event_instance=gson.fromJson(savedInstanceState.getString("event", ""),event_instance.getClass());*/
        TextView header,description,link;
        View view =inflater.inflate(R.layout.fragment_description,container,false);
        header=(TextView)view.findViewById(R.id.header);
        description=(TextView)view.findViewById(R.id.description);
        link=(TextView)view.findViewById(R.id.link);
        sp=getActivity().getSharedPreferences("CSEvents", Context.MODE_PRIVATE);
        String jsonFavorites = sp.getString("Events", null);
        Gson gson = new Gson();
        event_instance = gson.fromJson(jsonFavorites,event_instance.getClass());
        imageView = (ImageView) view.findViewById(R.id.description_image);
        imageView.setImageResource(getActivity().getResources().getIdentifier(event_instance.getImage(),"drawable",getActivity().getPackageName()));
        if(event_instance.getName().equals("Circumstance"))
            header.setText("Circumstance Online ");
        else
            if(event_instance.getName().equals("Elequest"))
                header.setText("Elequest-Online Treasure Hunt");
        else if(event_instance.getName().equals("CSI"))
                header.setText("Crime Scene Investigation");
        else
            header.setText(event_instance.getName());
        description.setText(event_instance.getDescription());
        if(event_instance.getName().equals("Papyrus Of Ani")||event_instance.getName().equals("Robo War")||event_instance.getName().equals("Robo Soccer")) {
            link.setText("Register Here");
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(event_instance.getName().equals("Papyrus Of Ani")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/forms/JlTo0esC87kCoyx03"));
                        startActivity(browserIntent);
                    }
                    if (event_instance.getName().equals("Robo War")){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/forms/zwoiTgWL20LS8OWk1"));
                        startActivity(browserIntent);
                    }
                    if (event_instance.getName().equals("Robo Soccer"))
                    {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/forms/s1odgnAkVtTEPvY53"));
                        startActivity(browserIntent);
                    }

                }
            });
        }
        else
        link.setVisibility(View.GONE);
        /*TextView t=(TextView)view.findViewById(R.id.details);
        t.setText(event_instance.getDescription());*/
        return view;
    }
}
