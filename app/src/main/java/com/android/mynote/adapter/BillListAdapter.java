package com.android.mynote.adapter;

import java.util.List;

import com.android.mynote.R;
import com.android.mynote.object.Bill;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BillListAdapter extends BaseAdapter {
	private List<Bill> list;
	private Context context;

	public BillListAdapter(List<Bill> list, Context context) {
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
			convertView = View.inflate(context, R.layout.item_bill, null);
			holder.tvIn = (TextView) convertView.findViewById(R.id.textview_in);
			holder.tvOut = (TextView) convertView.findViewById(R.id.textview_out);
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.imageview_icon);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.ivIcon.setImageResource(list.get(position).getImage());
		if (list.get(position).getInOrout() == 0) {	//判断支出还是收入，决定放入左|右
			holder.tvIn.setText(list.get(position).getText());
		} else {
			holder.tvOut.setText(list.get(position).getText());
		}
		return convertView;
	}

	class Holder {
		TextView tvIn;
		TextView tvOut;
		ImageView ivIcon;
	}

}
