package com.android.mynote.activity;

import com.android.mynote.R;
import com.android.mynote.databasehelper.MyDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

public class MainActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.button_login_directly).setOnClickListener(this);
		new MyDatabaseHelper(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.button_login_directly:	//点击直接登录进入
			startActivity(new Intent(MainActivity.this,SecondActivity.class));
			finish();
			break;
		}
	}

}
