package com.noelmace.android.demo.littlenote.sqlite;

import com.noelmace.android.demo.littlenote.sqlite.DbContract.NoteTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "littlenote";
	private static final int DB_VERSION = 1;

	private static final String LOG_KEY = DbContract.class.getPackage()
			.toString() + "." + DbContract.class.toString();

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_TABLE_NOTE = "CREATE TABLE "
			+ NoteTable.TABLE_NAME + " (" + NoteTable._ID
			+ " INTEGER PRIMARY KEY," + NoteTable.COLUMN_NAME_TITLE + TEXT_TYPE
			+ COMMA_SEP + NoteTable.COLUMN_NAME_CONTENT + TEXT_TYPE + " )";

	private static DbHelper instance;

	private DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_NOTE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(LOG_KEY, "onUpgrade : only version 1 is supported");
	}

	public static DbHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DbHelper(context);
		}
		return instance;
	}

	public static void close(Context context) {
		DbHelper.getInstance(context).close();
	}
}
