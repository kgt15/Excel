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

import java.util.ArrayList;

/**
 * Created by gopikm on 23/6/16.
 */
public class fragment_rules extends Fragment {
    private ImageView imageView;
    private static final String STARTING_TEXT = "";
    SharedPreferences sp;

    public static fragment_rules newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        fragment_rules sampleFragment = new fragment_rules();
        sampleFragment.setArguments(args);

        return sampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        TextView header,rules;
        event event_instance=new event();
        View view =inflater.inflate(R.layout.fragment_rules,container,false);
        header= (TextView)view.findViewById(R.id.header);
        rules=(TextView)view.findViewById(R.id.rules);
        sp=getActivity().getSharedPreferences("CSEvents", Context.MODE_PRIVATE);
        String jsonFavorites = sp.getString("Events", null);
        Gson gson = new Gson();
        event_instance = gson.fromJson(jsonFavorites,event_instance.getClass());
        imageView = (ImageView) view.findViewById(R.id.rules_image);
        imageView.setImageResource(getActivity().getResources().getIdentifier(event_instance.getImage(),"drawable",getActivity().getPackageName()));
        header.setText(event_instance.getName());
        ArrayList<String> rulesList=event_instance.getRules();
        String ruleFinal="";
        for(int i=0;i<rulesList.size();i++)
            ruleFinal+=rulesList.get(i);
        rules.setText(ruleFinal);
       /* TextView t=(TextView)view.findViewById(R.id.rules);
        t.setText(event_instance.getRules().toString());*/
        return view;
    }
}
