package com.android.mynote.databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	public MyDatabaseHelper(Context context) {
		super(context, "data.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//记录记事簿内容的表，一个自增id，两个字符串字段
		db.execSQL(
				"create table if not exists noterecord(_id integer primary key autoincrement, title text,note text)");
		//记录账单的表
		db.execSQL(
				"create table if not exists iniconout(_id integer primary key autoincrement,message text,image integer,inorout integer)");
		//记录转账记录的表，type:转账类型   action:转账金额(正负)
		db.execSQL(
				"create table if not exists account(type integer,action real)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
