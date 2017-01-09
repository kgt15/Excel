package mec.mec.excel16;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hassan on 9/3/16.
 */
public class leaderBoardAdapter extends RecyclerView.Adapter<leaderBoardAdapter.MyViewHolder>
{
    private List<leaderBoardDetails> rankData;
    Context context2;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, rank, score;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.userImage);
            name = (TextView) view.findViewById(R.id.userName);
            rank = (TextView) view.findViewById(R.id.userRank);
            score = (TextView) view.findViewById(R.id.userScore);
        }
    }

    public leaderBoardAdapter(List<leaderBoardDetails> rankData, Context context)
    {
        this.rankData = rankData;
        this.context2 = context;
    }

    @Override
    public leaderBoardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_leaderboards, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(leaderBoardAdapter.MyViewHolder holder, int position) {

        leaderBoardDetails singleData = new leaderBoardDetails();
        singleData = rankData.get(position);
        holder.name.setText(singleData.getUserName());
        holder.rank.setText(singleData.getRank());
        holder.score.setText(singleData.getScore());


        if(holder.image != null)
        {
            Bitmap bm = Home.cache.get(""+singleData.getUserName());
            if(bm == null)
            {
                imgCache img = new imgCache();
                Log.v("Here10Here","10");
                bm = img.getImageFromInternal(singleData.getUserName(), context2);
                if(bm != null)
                {
                    //addBitmapToMemoryCache(""+d.getImageUrl(), bm);
                    Home.cache.put(""+singleData.getUserName(), bm);
                    Log.v("Here1Here","1");
                    holder.image.setImageBitmap(bm);
                }
                else
                {
                    Log.v("Here2Here","2");
                    img.saveToInternalMemory(singleData.getUserName(), singleData.getImageUrl(), context2, holder.image);
                    //img.saveToRealmDatabase(d.getUserName(), d.getImageUrl(), getContext(), image);
                }
            }
            else
            {
                Log.v("Here11Here","11");
                holder.image.setImageBitmap(bm);
            }
        }

    }

    @Override
    public int getItemCount() {
        return rankData.size();
    }
}
