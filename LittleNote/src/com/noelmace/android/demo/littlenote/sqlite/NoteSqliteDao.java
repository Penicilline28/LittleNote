package com.noelmace.android.demo.littlenote.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;

import com.noelmace.android.demo.littlenote.SQLiteDaoActivity;
import com.noelmace.android.demo.littlenote.dao.Dao;
import com.noelmace.android.demo.littlenote.dao.DaoException;
import com.noelmace.android.demo.littlenote.entitties.Note;
import com.noelmace.android.demo.littlenote.sqlite.DbContract.NoteTable;

public class NoteSqliteDao implements Dao<Note> {
	
	private SQLiteDatabase db;
	private SQLiteDaoActivity context;
	
	public NoteSqliteDao(SQLiteDaoActivity context){
		this.context = context;
		this.db = DbHelper.getInstance(context).getWritableDatabase();
	}
	
	@Override
	public void update(Note obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Note obj) throws DaoException {
		ContentValues values = new ContentValues();
		values.put(NoteTable.COLUMN_NAME_TITLE, obj.getTitle());
		values.put(NoteTable.COLUMN_NAME_CONTENT, obj.getContent());

		db.beginTransactionWithListener(context);
		long id = db.insert(NoteTable.TABLE_NAME, null, values);
		if(id != -1){
			obj.setId(id);
			db.setTransactionSuccessful();
		} else {
			throw new DaoException(obj.toString() + " couldn't be persisted to sqlite database");
		}
		db.endTransaction();
	}

	@Override
	public Note findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Note obj) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Note> findAll() {
		return Util.curs2Notes(db.query(DbContract.NoteTable.TABLE_NAME,
				DbContract.NoteTable.PROJECTION_ALL,
				null,
				null,
				null,
				null,
				DbContract.NoteTable._ID));
	}
	
	public final static class Util {
		
		public static List<Note> curs2Notes(Cursor cursor){
			cursor.moveToFirst();
			List<Note> notes = new ArrayList<Note>();
			while(cursor.moveToNext()){
				notes.add(new Note(cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.NoteTable._ID)),
						cursor.getString(cursor.getColumnIndexOrThrow(DbContract.NoteTable.COLUMN_NAME_TITLE)),
						cursor.getString(cursor.getColumnIndexOrThrow(DbContract.NoteTable.COLUMN_NAME_CONTENT))));
			}
			cursor.close();
			return notes;
			
		}
		
		public static List<String> getTitles(List<Note> notes){
			List<String> titles = new ArrayList<String>();
			for (Note note : notes) {
				titles.add(note.getTitle());
			}
			return titles;
		}
		
		public static List<String> getContents(List<Note> notes){
			List<String> contents = new ArrayList<String>();
			for (Note note : notes) {
				contents.add(note.getContent());
			}
			return contents;
		}
	}

}
