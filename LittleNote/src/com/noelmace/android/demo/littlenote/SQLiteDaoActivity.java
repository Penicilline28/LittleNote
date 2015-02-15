package com.noelmace.android.demo.littlenote;

import android.database.sqlite.SQLiteTransactionListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.noelmace.android.demo.littlenote.sqlite.DbHelper;
import com.noelmace.android.demo.littlenote.sqlite.NoteSqliteDao;

public abstract class SQLiteDaoActivity extends FragmentActivity implements SQLiteTransactionListener {

	private static final String TRANSACTION_LOG_KEY = "com.noelmace.android.demo.littlenote.transaction";
	
	@Override
	public void onBegin() {
		Log.d(TRANSACTION_LOG_KEY, "new transaction");
	}

	@Override
	public void onCommit() {
		Log.d(TRANSACTION_LOG_KEY, "transaction commited");
	}

	@Override
	public void onRollback() {
		Log.w(TRANSACTION_LOG_KEY, "transaction rollback");
	}

	private NoteSqliteDao dao;
	
	public NoteSqliteDao getDao(){
			return this.dao;
	}

	@Override
	protected void onPause() {
	    DbHelper.close(this);
		super.onPause();
	}

	@Override
	protected void onResume() {
		dao = new NoteSqliteDao(this);
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dao = new NoteSqliteDao(this);
	}
	
	
	
}
