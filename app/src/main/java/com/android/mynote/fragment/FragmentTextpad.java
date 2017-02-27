package com.android.mynote.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.mynote.R;
import com.android.mynote.activity.TextPadActivity;
import com.android.mynote.adapter.TextpadListAdapter;
import com.android.mynote.object.Textpad;
import com.android.mynote.operatedb.OperateTextpad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentTextpad extends Fragment implements OnClickListener, OnItemLongClickListener, OnItemClickListener {

	private List<Textpad> list;
	private ListView listviewTextpad;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_textpad, null);
		view.findViewById(R.id.button_add).setOnClickListener(this);
		listviewTextpad = (ListView) view.findViewById(R.id.listview_textpad);
		listviewTextpad.setOnItemLongClickListener(this);
		listviewTextpad.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		freshList(); // 因为设置了动画透明效果，此方法必须放入onResume，涉及生命周期
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_add:
			startActivity(new Intent(getActivity(), TextPadActivity.class));
		}
	}

	private void freshList() {
		list = new ArrayList<>();
		Cursor c = new OperateTextpad(getActivity()).selectAll();
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex("_id"));
			String title = c.getString(c.getColumnIndex("title"));
			String note = c.getString(c.getColumnIndex("note"));
			list.add(new Textpad(id, title, note));
		}
		listviewTextpad.setAdapter(new TextpadListAdapter(getActivity(), list));
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) { // 长按删除
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle("确认").setMessage("确定删除？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						OperateTextpad op = new OperateTextpad(getActivity());
						Cursor c = op.selectAll();
						c.moveToPosition(position);
						int idtemp = c.getInt(c.getColumnIndex("_id"));
						op.delete(idtemp);
						freshList();
						Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();

					}
				}).setNegativeButton("取消", null);
		builder.create().show();
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // 单击条目修改，并将条目内容传递到修改界面
		// TODO Auto-generated method stub
		Cursor cursor = new OperateTextpad(getActivity()).selectAll();
		cursor.moveToPosition(position);
		int updateid = cursor.getInt(cursor.getColumnIndex("_id"));
		String title = cursor.getString(cursor.getColumnIndex("title"));
		String note = cursor.getString(cursor.getColumnIndex("note"));
		Intent intent = new Intent(getActivity(), TextPadActivity.class);
		intent.putExtra("id", updateid);
		intent.putExtra("title", title);
		intent.putExtra("note", note);
		startActivity(intent);
	}

}
