package com.android.mynote.operatedb;

import com.android.mynote.databasehelper.MyDatabaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OperateAccount {
	private SQLiteDatabase dbReader, dbWriter;

	public OperateAccount(Context context) {
		MyDatabaseHelper helper = new MyDatabaseHelper(context);
		dbReader = helper.getReadableDatabase();
		dbWriter = helper.getReadableDatabase();
	}

	public boolean insert(int type, double action) {
		dbWriter.beginTransaction();
		try {
			dbWriter.execSQL("insert into account values(?,?)", new String[] { type + "", action + "" });
			dbWriter.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			dbWriter.endTransaction();
		}
	}

	public double getTotal() {
		Cursor c = dbReader.rawQuery("select sum(action) total from account", null);
		if (true == c.moveToNext()) {
			return c.getDouble(c.getColumnIndex("total"));
		}
		return 0.0;
	}

	public double getCash() {
		Cursor c = dbReader.rawQuery("select sum(action) cash from account where type=1", null);
		if (true == c.moveToNext()) {
			return c.getDouble(c.getColumnIndex("cash"));
		}
		return 0.0;
	}

	public double getDepositCard() {
		Cursor c = dbReader.rawQuery("select sum(action) deposit from account where type=2", null);
		if (true == c.moveToNext()) {
			return c.getDouble(c.getColumnIndex("deposit"));
		}
		return 0.0;
	}

	public double getCreditCard() {
		Cursor c = dbReader.rawQuery("select sum(action) credit from account where type=3", null);
		if (true == c.moveToNext()) {
			return c.getDouble(c.getColumnIndex("credit"));
		}
		return 0.0;
	}

	public double getElectronic() {
		Cursor c = dbReader.rawQuery("select sum(action) electronic from account where type=4", null);
		if (true == c.moveToNext()) {
			return c.getDouble(c.getColumnIndex("electronic"));
		}
		return 0.0;
	}
}
