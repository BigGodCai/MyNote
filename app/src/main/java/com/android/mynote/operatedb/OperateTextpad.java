package com.android.mynote.operatedb;

import com.android.mynote.databasehelper.MyDatabaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OperateTextpad {
	private SQLiteDatabase dbReader, dbWriter;

	public OperateTextpad(Context context) {
		MyDatabaseHelper helper = new MyDatabaseHelper(context);
		dbReader = helper.getReadableDatabase();
		dbWriter = helper.getReadableDatabase();
	}

	public boolean insert(String title, String note) {
		dbWriter.beginTransaction();
		try {
			dbWriter.execSQL("insert into noterecord(title,note) values(?,?)", new String[] { title, note });
			dbWriter.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			dbWriter.endTransaction();
		}
	}

	public Cursor selectAll() {
		return dbReader.rawQuery("select * from noterecord", null);
	}

	public void delete(int id) {
		dbWriter.beginTransaction();
		try {
			dbWriter.execSQL("delete from noterecord where _id=?", new String[] { id + "" });
			dbWriter.setTransactionSuccessful();
		} catch (Exception e) {
		} finally {
			dbWriter.endTransaction();
		}
	}

	public boolean update(int id, String title, String note) {
		dbWriter.beginTransaction();
		try {
			dbWriter.execSQL("update noterecord set title=?,note=? where _id=?", new String[] { title, note, id + "" });
			dbWriter.setTransactionSuccessful();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			dbWriter.endTransaction();
		}
	}
}
