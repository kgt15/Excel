package mec.mec.excel16;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerin on 25/7/16.
 */
public class AppTeam extends Fragment {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    List<sponsors_data> datas = new ArrayList<>();
    about_adapter adapter;
    ListView lv;
    Context context;
    int aswin = R.drawable.avatar;

    public AppTeam() {
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
        recyclerView.scrollToPosition(7);
        adapter.notifyDataSetChanged();
        return rootview;
    }
    public void preparedatas(){
        sponsors_data data = new sponsors_data("Sebin","Game Developer", R.drawable.sebin);
        datas.add(data);
        data = new sponsors_data("Rajeev","3D model Designer", R.drawable.rajiv);
        datas.add(data);
        data = new sponsors_data("Shwetha","Designer",R.drawable.swetha);
        datas.add(data);
        data = new sponsors_data("K George","The one who knocks", R.drawable.george);
        datas.add(data);
        data = new sponsors_data("Hassan","When Edison steals your ideas :(", R.drawable.hassanabout);
        datas.add(data);
        data = new sponsors_data("Asif ali","Jedi Knight", R.drawable.asifabout);
        datas.add(data);
        data = new sponsors_data("Aravind","Why so serious?", R.drawable.aravi);
        datas.add(data);
        data = new sponsors_data("Gokul","The designer you need, not the one you deserve", R.drawable.gokulabout);
        datas.add(data);
        data = new sponsors_data("Ashwin","Humans are.. so interesting", R.drawable.aswin);
        datas.add(data);
        data = new sponsors_data("Gopi","I drink and I know things", R.drawable.gopiabout);
        datas.add(data);
        data = new sponsors_data("Jerin","AppTeam Head(Thala)", R.drawable.jerin);
        datas.add(data);
        adapter = new about_adapter(datas);
        adapter.notifyDataSetChanged();

    }

}
