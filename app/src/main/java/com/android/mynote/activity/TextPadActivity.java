package com.android.mynote.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.android.mynote.R;
import com.android.mynote.operatedb.OperateTextpad;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;

public class TextPadActivity extends BaseActivity implements OnClickListener {
	private EditText editTitle;
	private EditText editNote;

	private int id = -1;		//判断是修改已存在，还是插入新条目     -1:插入新条目    >0:修改已存在条目
	// protected int activityCloseEnterAnimation;
	// protected int activityCloseExitAnimiation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_pad);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		initView();		//初始化控件视图
		Intent intent = getIntent();
		id = intent.getIntExtra("id", -1);	//获取传递过来需要修改条目的id
		String title = intent.getStringExtra("title");	//获取传递的title
		String note = intent.getStringExtra("note");	//获取传递的note
		editTitle.setText(title);
		editNote.setText(note);
		if (title != null) { //如果标题不为空，则将焦点移到标题末尾
			editTitle.setSelection(title.length());
		}

		// TypedArray activityStyle = getTheme().obtainStyledAttributes(new
		// int[] { android.R.attr.windowAnimationStyle });
		// int windowAnimationStyleid = activityStyle.getResourceId(0, 0);
		// activityStyle.recycle();
		//
		// activityStyle =
		// getTheme().obtainStyledAttributes(windowAnimationStyleid,
		// new int[] { android.R.attr.activityCloseEnterAnimation,
		// android.R.attr.activityCloseExitAnimation });
		// activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		// activityCloseExitAnimiation = activityStyle.getResourceId(1, 0);
		// activityStyle.recycle();
	}

	private void initView() {
		editTitle = (EditText) findViewById(R.id.edit_title);
		editNote = (EditText) findViewById(R.id.edit_note);
		findViewById(R.id.button_back).setOnClickListener(this);
		findViewById(R.id.button_clear).setOnClickListener(this);
		findViewById(R.id.button_getdate).setOnClickListener(this);
		findViewById(R.id.button_ok).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		EditText et = (EditText) getCurrentFocus();	//获取焦点所在的控件
		switch (v.getId()) {
		case R.id.button_back:		//返回按钮
			finish();
			break;
		case R.id.button_clear:		//清空按钮
			et.setText("");
			break;
		case R.id.button_getdate:	//在光标所在处添加时间，并且光标随添加的内容而移动
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			String da = new SimpleDateFormat("yyyy-MM-dd").format(date);
			StringBuilder sb = new StringBuilder(et.getText().toString());
			int pos = et.getSelectionStart();
			sb.insert(pos, da);
			et.setText(sb.toString());
			et.setSelection(pos + 10);
			break;
		case R.id.button_ok:		//点击OK保存条目
			String title = editTitle.getText().toString();
			String note = editNote.getText().toString();
			if (id == -1) {	// -1:插入新条目    >0:修改已存在条目
				new OperateTextpad(this).insert(title, note);
			} else {
				new OperateTextpad(this).update(id, title, note);
			}
			finish();
			break;
		}
	}

	@Override
	public void finish() {	//重写结束过度动画    PS:将style文件中的windowIsTransflucent属性去掉才有效果
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.padclosein, R.anim.padcloseout);  //关键代码，必须在finish()立即执行
	}

}
