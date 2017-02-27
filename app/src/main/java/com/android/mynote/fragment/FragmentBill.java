package com.android.mynote.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.mynote.R;
import com.android.mynote.activity.InOrOutActivity;
import com.android.mynote.adapter.BillListAdapter;
import com.android.mynote.object.Bill;
import com.android.mynote.operatedb.OperateBill;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class FragmentBill extends Fragment implements OnClickListener {

	private List<Bill> list;
	private ListView listviewBill;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_bill, null);
		listviewBill = (ListView) view.findViewById(R.id.listview_bill);
		view.findViewById(R.id.button_add).setOnClickListener(this);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
//		listviewBill.setDivider(null);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		freshList();	//刷新列表内容
	}

	private void freshList() {
		// TODO Auto-generated method stub
		list = new ArrayList<Bill>();
		Cursor c = new OperateBill(getActivity()).selectAll();
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("_id"));
			String text = c.getString(c.getColumnIndex("message"));
			int image = c.getInt(c.getColumnIndex("image"));
			int inOrout = c.getInt(c.getColumnIndex("inorout"));
			list.add(new Bill(id, text, image, inOrout));
		}
		listviewBill.setAdapter(new BillListAdapter(list, getActivity()));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_add:	//添加按钮进入添加界面
			startActivity(new Intent(getActivity(), InOrOutActivity.class));
			break;
		}
	}

}
