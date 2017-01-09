package mec.mec.excel16;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by gopikm on 2/8/16.
 */
public class cardAdapter_initiatives extends RecyclerView.Adapter<cardAdapter_initiatives.ViewHolder>{

    private ImageLoader imageLoader;
    private Context context;
    List<event> eventList;
    SharedPreferences sp;
    SharedPreferences.Editor sped;

    public cardAdapter_initiatives(List<event> eventList, Context context) {
        super();
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public cardAdapter_initiatives.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        event event_instance = eventList.get(position);
        String id=event_instance.getImage();
        Log.d("Name is not null", event_instance.getName());
        Log.d("Image",event_instance.getImage());
        holder.imageView.setImageResource(context.getResources().getIdentifier(id,"drawable",context.getPackageName()));
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
           // plbtn =(ImageButton)itemView.findViewById(R.id.join);
            //shrbtn=(ImageButton) itemView.findViewById(R.id.share);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Context context = view.getContext();
                    Intent intent = new Intent(context, exhibitions_view.class);
                    sp=context.getSharedPreferences("CSEvents", Context.MODE_PRIVATE);
                    sped=sp.edit();
                    Gson gson = new Gson();
                    String jsonFavorites = gson.toJson(eventList.get(getAdapterPosition()));
                    sped.putString("Events", jsonFavorites);
                    sped.commit();
                    context.startActivity(intent);
                }
            });
            /*plbtn.setOnClickListener(new View.OnClickListener() {
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
