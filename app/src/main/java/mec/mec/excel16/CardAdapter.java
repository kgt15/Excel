package mec.mec.excel16;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by gopikm on 18/6/16.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    List<event> eventList;
    SharedPreferences sp;
    SharedPreferences.Editor sped;

    public CardAdapter(List<event> eventList, Context context) {
        super();
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, int position) {
        event event_instance = eventList.get(position);
        String image=event_instance.getImage();
        holder.imageView.setImageResource(context.getResources().getIdentifier(image,"drawable",context.getPackageName()));
        holder.name.setText(event_instance.getName());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name;
        public ImageButton plbtn,shrbtn;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            //plbtn =(ImageButton)itemView.findViewById(R.id.join);
            //shrbtn=(ImageButton) itemView.findViewById(R.id.share);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Context context = view.getContext();
                    Intent intent = new Intent(context, Details.class);
                    sp=context.getSharedPreferences("CSEvents", Context.MODE_PRIVATE);
                    sped=sp.edit();
                    Gson gson = new Gson();
                    String jsonFavorites = gson.toJson(eventList.get(getAdapterPosition()));
                    sped.putString("Events", jsonFavorites);
                    sped.commit();
                    context.startActivity(intent);
                }
            });
           /* plbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"join",Toast.LENGTH_SHORT).show();
                }
            });
            shrbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"share",Toast.LENGTH_SHORT).show();
                }
            });*/

        }

    }
}
