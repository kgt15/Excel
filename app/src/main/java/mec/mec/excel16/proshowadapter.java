package mec.mec.excel16;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.library.FocusResizeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerin on 25/7/16.
 */
public class proshowadapter extends FocusResizeAdapter<RecyclerView.ViewHolder> {
    private List<proshowobject> items;

    public proshowadapter(Context context, int height) {
        super(context, height);
        items = new ArrayList<>();
    }
    public void addItems(List<proshowobject> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getFooterItemCount() {
        // Return items size
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        // Inflate your custom item layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.proshow_layout, parent, false);
        return new DefaultCustomViewHolder(v);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Set your data into your custom layout
        proshowobject customObject = items.get(position);
        fill((DefaultCustomViewHolder) holder, customObject);
    }

    private void fill(DefaultCustomViewHolder holder, proshowobject customObject) {
        holder.titleTextView.setText(customObject.getTitle());
        holder.subtitleTextView.setText(customObject.getSubTitle());
        holder.image.setImageResource(customObject.getDrawable());
    }

    @Override
    public void onItemBigResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        // The focused item will resize to big size while is scrolling
    }

    @Override
    public void onItemBigResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        // The focused item resize to big size when scrolled is finished
    }

    @Override
    public void onItemSmallResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        // All items except the focused item will resize to small size when scrolled is finished
    }

    @Override
    public void onItemSmallResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {
        // All items except the focused item will resize to small size while is scrolling
    }

    @Override
    public void onItemInit(RecyclerView.ViewHolder viewHolder) {
        // Init first item when the view is loaded
    }
    public class DefaultCustomViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView titleTextView;
        TextView subtitleTextView;

        public DefaultCustomViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image_custom_item);
            titleTextView = (TextView) v.findViewById(R.id.title_custom_item);
            subtitleTextView = (TextView) v.findViewById(R.id.subtitle_custom_item);
        }
    }
}
