package mec.mec.excel16;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class about_adapter extends RecyclerView.Adapter<about_adapter.ViewHolder> {

    private List<sponsors_data> sponsors_datas;
    int count = 0, z=0, h=0, j1=0,j2=0,j3=0;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titles);
            subtitle = (TextView) view.findViewById(R.id.description);
            imageView = (ImageView)view.findViewById(R.id.image);
        }
    }
    public about_adapter(List<sponsors_data> datas) {

        this.sponsors_datas = datas;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.about_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final sponsors_data sponsors_data = sponsors_datas.get(position);
        holder.title.setText(sponsors_data.getTitles());
        holder.subtitle.setText(sponsors_data.getDescription());
        holder.imageView.setImageResource(sponsors_data.getImage());

        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                // Toast.makeText(v.getContext(),c,Toast.LENGTH_LONG).show();
                 if (sponsors_data.getTitles() == "Jerin"){
                        z = z + 1;
                        if (z == 5) {
                            holder.imageView.setImageResource(R.mipmap.sup);
                            z=0;
                            Toast.makeText(v.getContext(),"Suppu...",Toast.LENGTH_LONG).show();
                        }
                }
                if (sponsors_data.getTitles() == "Balram P Menon"){
                    z = z + 1;
                    if (z == 5) {
                        holder.imageView.setImageResource(R.drawable.ball);
                        z=0;
                        Toast.makeText(v.getContext(),"696GB daaa..!",Toast.LENGTH_LONG).show();
                    }
                }
                if (sponsors_data.getTitles() == "Shriram Pradeep"){
                    z = z + 1;
                    if (z == 5) {
                        z=0;
                        Toast.makeText(v.getContext(),"Poothiri Poothiri Kambipoothiri",Toast.LENGTH_LONG).show();
                    }
                }
                if (sponsors_data.getTitles() == "Ashwin"){
                    z = z + 1;
                    if (z == 5) {
                        z=0;
                        holder.imageView.setImageResource(R.drawable.as);
                       Toast.makeText(v.getContext(),"ooh! we have a winner here..",Toast.LENGTH_LONG).show();
                    }
                }
                if (sponsors_data.getTitles() == "K George"){
                    z = z + 1;
                    if (z == 5) {
                        z=0;
                        //holder.imageView.setImageResource(R.drawable.as);
                        Toast.makeText(v.getContext(),"",Toast.LENGTH_LONG).show();
                    }
                }
                if (sponsors_data.getTitles() == "Asif ali"){
                    z = z + 1;
                    if (z == 5) {
                        z=0;
                        //holder.imageView.setImageResource(R.drawable.as);
                        Toast.makeText(v.getContext(),"Nenjukkul peidhidum....",Toast.LENGTH_LONG).show();
                    }
                }





            }
        });

    }

    @Override
    public int getItemCount() {
        return sponsors_datas.size();
    }

}

