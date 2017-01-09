package mec.mec.excel16;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Sponsors extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    List<sponsors_data> datas = new ArrayList<>();
    about_adapter adapter;
    public Sponsors() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        preparedatas();
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootview = inflater.inflate(R.layout.fragment_app_team, container, false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(8);
        adapter.notifyDataSetChanged();
        return rootview;


    }
    public void preparedatas(){
        sponsors_data data = new sponsors_data("Geojit Technologies","Techzone", R.drawable.geojit);
        datas.add(data);
        data = new sponsors_data("Acumen"," DalalBull", R.drawable.acumen);
        datas.add(data);
        data = new sponsors_data("Canon Marikar","Others", R.drawable.marikar);
        datas.add(data);
        data = new sponsors_data("Career Launcher","Others", R.drawable.career);
        datas.add(data);
        data = new sponsors_data("PowerMech","Others", R.drawable.powermech);
        datas.add(data);
        data = new sponsors_data("Nice Chemicals","Others", R.drawable.nice);
        datas.add(data);
        data = new sponsors_data("Technical Tradelinks","Others", R.drawable.ttl);
        datas.add(data);
        data = new sponsors_data("Sasthra Robotics","Ultimate Engineer", R.drawable.sastra);
        datas.add(data);
        data = new sponsors_data("Comptree","Others", R.drawable.comptree);
        datas.add(data);
        data = new sponsors_data("State Bank of Hyderabad","Others", R.drawable.sbi);
        datas.add(data);


        adapter = new about_adapter(datas);
        adapter.notifyDataSetChanged();

    }

}
