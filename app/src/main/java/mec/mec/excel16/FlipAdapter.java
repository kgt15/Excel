package mec.mec.excel16;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FlipAdapter extends BaseAdapter implements OnClickListener {

	public interface Callback{
		public void onPageRequested(int page);
	}
	
	static class Item {
		static long id = 4;
		
		long mId;
		
		public Item() {
			mId = id++;
		}
		
		long getId(){
			return mId;
		}
	}
	
	private LayoutInflater inflater;
	private Callback callback;
	private List<Item> items = new ArrayList<Item>();
	
	public FlipAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		for(int i = 0 ; i<4 ; i++){
			items.add(new Item());
		}
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.page, parent, false);
			
			holder.text = (TextView) convertView.findViewById(R.id.text);
			holder.text2 = (TextView) convertView.findViewById(R.id.day);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		//TODO set a text with the id as well
		if(position==0){
			holder.text.setText(5+"");
			holder.text2.setText("WEDNESDAY");
		}
		if(position==1){
			holder.text.setText(6+"");
			holder.text2.setText("THURSDAY");
		}
		if(position==2){
			holder.text.setText(7+"");
			holder.text2.setText("FRIDAY");
		}
		if(position==3){
			holder.text.setText(8+"");
			holder.text2.setText("SATURDAY");
		}

		
		return convertView;
	}

	static class ViewHolder{
		TextView text;
		TextView text2;

	}

	@Override
	public void onClick(View v) {

	}

	public void addItems(int amount) {
		for(int i = 0 ; i<amount ; i++){
			items.add(new Item());
		}
		notifyDataSetChanged();
	}

	public void addItemsBefore(int amount) {
		for(int i = 0 ; i<amount ; i++){
			items.add(0, new Item());
		}
		notifyDataSetChanged();
	}

}
