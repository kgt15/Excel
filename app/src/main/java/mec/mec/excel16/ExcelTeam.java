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

/**
 * Created by jerin on 25/7/16.
 */
public class ExcelTeam extends Fragment {
    public RecyclerView recyclerView;
    about_adapter adapter;
    List<sponsors_data> datas = new ArrayList<>();
    public ExcelTeam() {
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
        View rootview = inflater.inflate(R.layout.fragment_excel_team, container, false);
        recyclerView = (RecyclerView)rootview.findViewById(R.id.recyclerView2);
//        ListView listView = (ListView)rootview.findViewById(R.id.excelteam);
//        listView.setAdapter(new CustomAdapter(getContext(),R.layout.card_layout,Name,excelTeamimages));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(19);
        adapter.notifyDataSetChanged();
        return rootview;

    }
    public void preparedatas(){

        sponsors_data data = new sponsors_data("Ameer Suhail ","Publicity Manager", R.drawable.ameer);
        datas.add(data);
        data = new sponsors_data("Vishnu J Prakash","Publicity Manager", R.drawable.vishnu);
        datas.add(data);
        data = new sponsors_data("Fathima Nazrin","Social Initiatives Manager", R.drawable.faby);
        datas.add(data);
        data = new sponsors_data("Fiona Antony","Talks Manager", R.drawable.fiona);
        datas.add(data);
        data = new sponsors_data("Ranjana Radhakrishnan","Confrences Manager", R.drawable.ranj);
        datas.add(data);
        data = new sponsors_data("Anil Vignesh","Confrences Manager", R.drawable.vignesh);
        datas.add(data);
        data = new sponsors_data("Nidhin Sankar","Finance Manager", R.drawable.kozhi);
        datas.add(data);
        data = new sponsors_data("Karun Thomas John","Marketing Manager", R.drawable.karun);
        datas.add(data);
        data = new sponsors_data("Shahzad Ali","Marketing Manager", R.drawable.shahzad);
        datas.add(data);
        data = new sponsors_data("Noel Abraham","Content Manager", R.drawable.noel);

        datas.add(data);
        data = new sponsors_data("Shriram Pradeep","Design Manager", R.drawable.sriram);
        datas.add(data);
        data = new sponsors_data("Akheel K M","Web Admin", R.drawable.akheel);
        datas.add(data);

        data = new sponsors_data("Shamseer P","Management Head", R.drawable.samsheer);
        datas.add(data);
        data = new sponsors_data("Karthik Suresh","Management Head", R.drawable.karthik);

        datas.add(data);
        data = new sponsors_data("Sathyanarayan S","Media Manager", R.drawable.sathya);

        datas.add(data);
        data = new sponsors_data("Abhijith Shyam","EC Tech head", R.drawable.abhi);

        datas.add(data);
        data = new sponsors_data("Mohammad Althaf","Non Tech Head", R.drawable.althf);

        datas.add(data);
        data = new sponsors_data("Balram P Menon","CS Tech head", R.drawable.bal);

        datas.add(data);
        data = new sponsors_data("Divakar Bhatt","Joint Secretary", R.drawable.divakar);

        datas.add(data);
        data = new sponsors_data("Abhishek Damodaran","Secretary", R.drawable.damu);

        datas.add(data);
        data = new sponsors_data("Anand M Davichan","Excel Chairman", R.drawable.davichan);
        datas.add(data);
        adapter = new about_adapter(datas);
        adapter.notifyDataSetChanged();

    }

}


