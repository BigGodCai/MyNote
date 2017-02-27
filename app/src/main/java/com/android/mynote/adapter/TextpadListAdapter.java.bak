package com.android.mynote.adapter;

import java.util.List;
import java.util.Map;

import com.android.mynote.R;
import com.android.mynote.object.Textpad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TextpadListAdapter extends BaseAdapter {

	private Context context;
	private List<Textpad> list;

	public TextpadListAdapter(Context context, List<Textpad> list) {
		super();
		this.context = context;
		this.list = list;
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
			convertView = View.inflate(context, R.layout.item_textpad, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.content = (TextView) convertView.findViewById(R.id.note);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.title.setText(list.get(position).getTitle());
		holder.content.setText(list.get(position).getNote());
		return convertView;
	}

	class Holder {

		TextView title;
		TextView content;
	}
}
