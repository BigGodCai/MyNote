package com.android.mynote.activity;

import com.android.mynote.R;
import com.android.mynote.operatedb.OperateAccount;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

public class TransferActivity extends BaseActivity implements OnClickListener {

	private TextView number;
	private Button buttonout;
	private Button buttonin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer);
		init();
		reset();
	}

	private void init() {
		// TODO Auto-generated method stub
		buttonout = (Button) findViewById(R.id.button_transferout);
		buttonin = (Button) findViewById(R.id.button_transferin);
		buttonout.setOnClickListener(this);
		buttonin.setOnClickListener(this);
		number = (TextView) findViewById(R.id.textview_number);
		findViewById(R.id.button_one).setOnClickListener(this);
		findViewById(R.id.button_two).setOnClickListener(this);
		findViewById(R.id.button_three).setOnClickListener(this);
		findViewById(R.id.button_four).setOnClickListener(this);
		findViewById(R.id.button_five).setOnClickListener(this);
		findViewById(R.id.button_six).setOnClickListener(this);
		findViewById(R.id.button_seven).setOnClickListener(this);
		findViewById(R.id.button_eight).setOnClickListener(this);
		findViewById(R.id.button_nine).setOnClickListener(this);
		findViewById(R.id.button_zero).setOnClickListener(this);
		findViewById(R.id.button_point).setOnClickListener(this);
		findViewById(R.id.button_ac).setOnClickListener(this);
		findViewById(R.id.button_del).setOnClickListener(this);
		findViewById(R.id.button_ok).setOnClickListener(this);
		findViewById(R.id.button_plus).setOnClickListener(this);

		findViewById(R.id.button_back).setOnClickListener(this);
	}

	private int numCount;
	private boolean hasPoint;
	private boolean isPressOp;
	private double sum;
	private double temp;
	private boolean reInput;

	private void reset() {
		// TODO Auto-generated method stub
		numCount = 0;
		hasPoint = false;
		reInput = false;
		sum = 0;
		temp = 0;
		number.setText("0");
		isPressOp = false;
	}

	private Button b;// 记录触发事件的按钮

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		b = (Button) findViewById(v.getId());
		String text = number.getText().toString();
		switch (v.getId()) {
		case R.id.button_one:
		case R.id.button_two:
		case R.id.button_three:
		case R.id.button_four:
		case R.id.button_five:
		case R.id.button_six:
		case R.id.button_seven:
		case R.id.button_eight:
		case R.id.button_nine:
			if (!reInput) {
				if (numCount < 9) {
					if (text.charAt(0) != '0') {
						number.setText(text + b.getText());
					} else {
						if (hasPoint) {
							number.setText(text + b.getText());
						} else {
							number.setText(b.getText());
						}
					}
					++numCount;
				}
			} else {
				number.setText(b.getText());
				reInput = false;
				++numCount;
			}
			isPressOp = false;
			break;
		case R.id.button_zero:
			if (numCount < 9 && (text.charAt(0) != '0' || hasPoint)) {
				number.setText(text + '0');
				++numCount;
			}
			break;
		case R.id.button_point:
			if (!hasPoint) {
				if (text.charAt(0) == '0') {
					++numCount;
				}
				number.setText(text + b.getText());
				hasPoint = true;
			}
			isPressOp = false;
			break;
		case R.id.button_ok:
			if (isPressOp == false) {
				temp = Double.parseDouble(number.getText().toString());
				sum += temp;
				int type1 = getType(buttonout.getText().toString());	//获取转出类型
				int type2 = getType(buttonin.getText().toString());			//获取转入类型
				if (type1 != 0 && type2 != 0) {		//当两者都有选择才有效
					new OperateAccount(this).insert(type1, -1 * sum);
					new OperateAccount(this).insert(type2, sum);
					Toast.makeText(this, "转账成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			} else {
				Toast.makeText(this, "输入格式有误", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.button_plus:
			if (isPressOp == false) {
				isPressOp = true;
				temp = Double.parseDouble(number.getText().toString());
				sum += temp;
				reInput = true;
				hasPoint = false;
			}
			break;
		case R.id.button_del:
			if (text.length() > 1) {
				number.setText(text.substring(0, text.length() - 1));
			} else {
				number.setText("0");
			}
			break;
		case R.id.button_ac:
			reset();
			break;
		case R.id.button_back:
			finish();
			break;
		case R.id.button_transferin:
			resetButtonColor();
			b.setBackgroundResource(R.color.button_pressed);
			b.setTextColor(getResources().getColorStateList(R.color.white));
			showPopupWindow();
			break;
		case R.id.button_transferout:
			resetButtonColor();
			b.setBackgroundResource(R.color.button_pressed);
			b.setTextColor(getResources().getColorStateList(R.color.white));
			showPopupWindow();
			break;
		}
	}

	public int getType(String typename) { // 获取选择账户类型
		switch (typename) {
		case "现金":
			return 1;
		case "储蓄卡":
			return 2;
		case "信用卡":
			return 3;
		case "支付宝":
			return 4;
		}
		return 0;
	}

	private void resetButtonColor() {
		buttonin.setTextColor(getResources().getColor(R.color.black));
		buttonin.setBackgroundResource(R.color.white);
		buttonout.setTextColor(getResources().getColor(R.color.black));
		buttonout.setBackgroundResource(R.color.white);
	}

	private void showPopupWindow() { // 账户选择框
		LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.7f;
		getWindow().setAttributes(lp);
		View view = LayoutInflater.from(this).inflate(R.layout.pop_choices, null);
		final PopupWindow pop = new PopupWindow(view, LayoutParams.MATCH_PARENT, 400, true);
		view.findViewById(R.id.button_cash).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				b.setText(getResources().getString(R.string.cash));
				pop.dismiss();
			}
		});
		view.findViewById(R.id.button_deposit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				b.setText(getResources().getString(R.string.depositcard));
				pop.dismiss();
			}
		});
		view.findViewById(R.id.button_credit).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				b.setText(getResources().getString(R.string.creditcard));
				pop.dismiss();
			}
		});
		view.findViewById(R.id.button_zhifubao).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				b.setText(getResources().getString(R.string.zhifubao));
				pop.dismiss();
			}
		});
		pop.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
		pop.setAnimationStyle(R.style.popstyle); // 对话框的显示效果
		pop.setBackgroundDrawable(getResources().getDrawable(R.color.popback));
		pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}

}
