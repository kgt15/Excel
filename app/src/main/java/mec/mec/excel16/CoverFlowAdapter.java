package mec.mec.excel16;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class CoverFlowAdapter extends BaseAdapter {
	
	private ArrayList<gallerypojo> mData = new ArrayList<>(0);
	private Context mContext;
	OnclickListener listener;
	ImageLoader imageLoader;
	ViewHolder holder;

	public CoverFlowAdapter(Context context, ArrayList<gallerypojo> mData, OnclickListener listener) {
		mContext = context;
		  this.mData=mData;
		  this.listener=listener;
	}
	

	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int pos) {
		return mData.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow, null);
            rowView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					listener.OnClicked(position);
				}
			});


            ViewHolder viewHolder = new ViewHolder();

            viewHolder.image = (NetworkImageView) rowView
                    .findViewById(R.id.image);
			viewHolder.text=(TextView)rowView.findViewById(R.id.label);
            rowView.setTag(viewHolder);
        }

       holder = (ViewHolder) rowView.getTag();

		imageLoader = CustomVolleyRequest.getInstance(mContext.getApplicationContext())
				.getImageLoader();

		imageLoader.get("http://excelapp-ondjango.rhcloud.com/media/"+ mData.get(position).getImage(),
				ImageLoader.getImageListener(holder.image,
						R.mipmap.ic_launcher,
						R.mipmap.ic_launcher));

		//Loading image
		        holder.image.setImageUrl( "http://excelapp-ondjango.rhcloud.com/media/"+mData.get(position).getImage(), imageLoader);


		holder.text.setText(mData.get(position).getUploaderscomment());


		return rowView;
	}


    static class ViewHolder {
        public TextView text;

		public NetworkImageView image;

    }
}
