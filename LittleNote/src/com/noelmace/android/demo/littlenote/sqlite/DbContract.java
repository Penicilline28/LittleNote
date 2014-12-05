package com.noelmace.android.demo.littlenote.sqlite;

import android.provider.BaseColumns;

public final class DbContract {
	
	public DbContract(){};
		
	public static abstract class NoteTable implements BaseColumns {
		public static final String TABLE_NAME = "Note";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_CONTENT = "content";
		
		public static final String[] PROJECTION_ALL = {_ID, COLUMN_NAME_TITLE, COLUMN_NAME_CONTENT}; 
	}
	
	//private static final String SQL_DELETE_TABLE_NOTE = "DROP TABLE IF EXISTS " + NoteTable.TABLE_NAME;
}
