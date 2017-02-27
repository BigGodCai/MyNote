package com.android.mynote.adapter;

import java.util.List;
import java.util.Map;

import com.android.mynote.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;

	public GridViewAdapter(List<Map<String, Object>> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context, R.layout.item_grid, null);
			holder.im = (ImageView) convertView.findViewById(R.id.imageview_icon);
			holder.tv = (TextView) convertView.findViewById(R.id.imageview_iconname);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.im.setImageResource((Integer) list.get(position).get("icon"));
		holder.tv.setText(list.get(position).get("iconname").toString());
		return convertView;
	}

	class Holder {

		ImageView im;
		TextView tv;
	}
}
