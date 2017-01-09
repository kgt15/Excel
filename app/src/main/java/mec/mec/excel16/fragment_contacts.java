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

import java.util.HashMap;

/**
 * Created by gopikm on 23/6/16.
 */
public class fragment_contacts extends Fragment {
    private ImageView imageView;
    private static final String STARTING_TEXT = "";
    SharedPreferences sp;
    public static fragment_contacts newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        fragment_contacts sampleFragment = new fragment_contacts();
        sampleFragment.setArguments(args);

        return sampleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        TextView contact1_name,contact2_name,contact1_phone,contact2_phone;
        event event_instance=new event();
        View view =inflater.inflate(R.layout.fragment_contacts,container,false);
        contact1_name=(TextView)view.findViewById(R.id.contact1_name);
        contact2_name=(TextView)view.findViewById(R.id.contact2_name);
        contact1_phone=(TextView)view.findViewById(R.id.contact1_phone);
        contact2_phone=(TextView)view.findViewById(R.id.contact2_phone);
        sp=getActivity().getSharedPreferences("CSEvents", Context.MODE_PRIVATE);
        String jsonFavorites = sp.getString("Events", null);
        Gson gson = new Gson();
        event_instance = gson.fromJson(jsonFavorites,event_instance.getClass());
        imageView = (ImageView) view.findViewById(R.id.contact_image);
        imageView.setImageResource(getActivity().getResources().getIdentifier(event_instance.getImage(),"drawable",getActivity().getPackageName()));
        //TextView t=(TextView)view.findViewById(R.id.contacts);
        //t.setText(event_instance.getContacts().toString());
        HashMap<String,String> hmap=new HashMap<>();
        hmap=event_instance.getContacts();
        contact1_name.setText(hmap.get("name0"));
        contact2_name.setText(hmap.get("name1"));
        contact1_phone.setText(hmap.get("phone0"));
        contact2_phone.setText(hmap.get("phone1"));
        return view;
    }
}
