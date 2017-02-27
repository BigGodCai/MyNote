package com.android.mynote.fragment;

import com.android.mynote.R;
import com.android.mynote.activity.TransferActivity;
import com.android.mynote.operatedb.OperateAccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentAccount extends Fragment implements OnClickListener {
	private TextView total, cash, deposit, credit, electronic;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_account, null);
		total = (TextView) view.findViewById(R.id.textview_total);
		cash = (TextView) view.findViewById(R.id.textview_cash);
		deposit = (TextView) view.findViewById(R.id.textview_deposit);
		credit = (TextView) view.findViewById(R.id.textview_credit);
		electronic = (TextView) view.findViewById(R.id.textview_electronic);
		view.findViewById(R.id.button_transfer).setOnClickListener(this);
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		/*---------------将数据库所查询到的结果格式化放入控件中显示----------------*/
		OperateAccount op = new OperateAccount(getActivity());
		total.setText(String.format("%.1f", op.getTotal()));
		cash.setText(String.format("%.1f", op.getCash()));
		deposit.setText(String.format("%.1f", op.getDepositCard()));
		credit.setText(String.format("%.1f", op.getCreditCard()));
		electronic.setText(String.format("%.1f", op.getElectronic()));
		/*---------------------------------------------------------*/
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_transfer:	//点击转账按钮转到转账界面
			startActivity(new Intent(getActivity(), TransferActivity.class));
			break;
		}
	}

}
