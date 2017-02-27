package com.android.mynote.operatedb;

import com.android.mynote.databasehelper.MyDatabaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OperateBill {
	private SQLiteDatabase dbReader, dbWriter;

	public OperateBill(Context context) {
		MyDatabaseHelper helper = new MyDatabaseHelper(context);
		dbReader = helper.getReadableDatabase();
		dbWriter = helper.getReadableDatabase();
	}

	public boolean insert(String text, int image, int inorout) {
		dbWriter.beginTransaction();
		try {
			dbWriter.execSQL("insert into iniconout(message,image,inorout) values(?,?,?)",
					new String[] { text, image + "", inorout + "" });
			dbWriter.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			dbWriter.endTransaction();
		}
	}

	public Cursor selectAll() {
		return dbReader.rawQuery("select * from iniconout", null);
	}

	public void delete(int id) {
		dbWriter.beginTransaction();
		try {
			dbWriter.execSQL("delete from iniconout where _id=?", new String[] { id + "" });
			dbWriter.setTransactionSuccessful();
		} catch (Exception e) {
		} finally {
			dbWriter.endTransaction();
		}
	}
}
