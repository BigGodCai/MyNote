package com.android.mynote.activity;

import java.util.ArrayList;
import java.util.List;

import com.android.mynote.R;
import com.android.mynote.adapter.MyFragmentPagerAdapter;
import com.android.mynote.fragment.FragmentAccount;
import com.android.mynote.fragment.FragmentBill;
import com.android.mynote.fragment.FragmentTextpad;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public class SecondActivity extends FragmentActivity implements OnPageChangeListener, OnClickListener {

	private List<Fragment> listFragment;
	private ViewPager viewpager;
	private Button buttonTextpad;
	private Button buttonBill;
	private Button buttonAccount;
	private ImageView scrollbar;

	private int barWidth;
	private LayoutParams layoutParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		init();	//初始化
		listFragment = new ArrayList<Fragment>();
		listFragment.add(new FragmentTextpad());
		listFragment.add(new FragmentBill());
		listFragment.add(new FragmentAccount());
		MyFragmentPagerAdapter fragmentViewPager = new MyFragmentPagerAdapter(getSupportFragmentManager(),
				listFragment);
		viewpager.setAdapter(fragmentViewPager);
//		viewpager.addOnPageChangeListener(this);
		viewpager.setOnPageChangeListener(this);
		buttonTextpad.setTextColor(getResources().getColor(R.color.button_pressed));

		layoutParams = (LayoutParams) scrollbar.getLayoutParams();
		setSlideBar();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		layoutParams.leftMargin = (int) (barWidth * (arg0 + arg1));	//滑动条移动宽度
		scrollbar.setLayoutParams(layoutParams);
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		initColor();
		switch (arg0) {
		case 0:
			buttonTextpad.setTextColor(getResources().getColor(R.color.button_pressed));
			break;
		case 1:
			buttonBill.setTextColor(getResources().getColor(R.color.button_pressed));
			break;
		case 2:
			buttonAccount.setTextColor(getResources().getColor(R.color.button_pressed));
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		initColor();
		switch (v.getId()) {
		case R.id.button_textpad:
			viewpager.setCurrentItem(0);
			buttonTextpad.setTextColor(getResources().getColor(R.color.button_pressed));
			break;
		case R.id.button_bill:
			viewpager.setCurrentItem(1);
			buttonBill.setTextColor(getResources().getColor(R.color.button_pressed));
			break;
		case R.id.button_account:
			viewpager.setCurrentItem(2);
			buttonAccount.setTextColor(getResources().getColor(R.color.button_pressed));
			break;
		}
	}

	private void initColor() {	//按钮文字颜色还原
		buttonTextpad.setTextColor(getResources().getColor(R.color.button_pressed_not));
		buttonBill.setTextColor(getResources().getColor(R.color.button_pressed_not));
		buttonAccount.setTextColor(getResources().getColor(R.color.button_pressed_not));
	}

	private void setSlideBar() {		//设置滑动条的宽度
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		barWidth = metric.widthPixels / 3;
		layoutParams.width = barWidth;
		scrollbar.setLayoutParams(layoutParams);
	}

	private void init() {
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		buttonTextpad = (Button) findViewById(R.id.button_textpad);
		buttonBill = (Button) findViewById(R.id.button_bill);
		buttonAccount = (Button) findViewById(R.id.button_account);
		scrollbar = (ImageView) findViewById(R.id.scroll_bar);
		buttonTextpad.setOnClickListener(this);
		buttonBill.setOnClickListener(this);
		buttonAccount.setOnClickListener(this);
	}
}
