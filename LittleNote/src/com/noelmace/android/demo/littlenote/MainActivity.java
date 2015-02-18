package com.noelmace.android.demo.littlenote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends SQLiteDaoActivity implements
		NoteTitlesFragment.OnTitleSelectedListener {

	private static final String LOG_TAG = "com.noelmace.android.demo.littlenote.MainActivity";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (findViewById(R.id.fragment_container) != null) {
			
			if (savedInstanceState != null)
				return;

			NoteTitlesFragment titlesFragment = new NoteTitlesFragment();
			

			titlesFragment.setArguments(getIntent().getExtras());

			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, titlesFragment, "titles_fragment").commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_add:
			addNote();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void addNote() {
		Intent intent = new Intent(this, EditActivity.class);
		startActivity(intent);
	}

	@Override
	public void onNoteSelected(int position) {

		NoteContentFragment contentFrag = (NoteContentFragment) getSupportFragmentManager()
				.findFragmentById(R.id.note_content_fragment);

		if (contentFrag != null) {
			contentFrag.updateNoteView(position);
		} else {
			contentFrag = new NoteContentFragment();

			Bundle args = new Bundle();
			args.putInt(NoteContentFragment.POS_KEY, position);
			contentFrag.setArguments(args);
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();

			transaction.replace(R.id.fragment_container, contentFrag, "content_fragment");
			transaction.addToBackStack(null);

			transaction.commit();
		}
	}

	/*
	@Override
	public void onCommit() {
		NoteTitlesFragment titlesFrag = null;
		try {
			titlesFrag = (NoteTitlesFragment) getSupportFragmentManager().findFragmentById(R.id.note_titles_fragment);
			if(titlesFrag == null){
				titlesFrag = (NoteTitlesFragment) getSupportFragmentManager().findFragmentByTag("titles_fragment");
			}
			if(titlesFrag == null){
				Log.e(LOG_TAG, "the Title Fragment is nowhere !!!");
			}
		} catch (ClassCastException e) { 
			Log.w(LOG_TAG, titlesFrag.toString() + "must be a NoteTitlesFragment");
		}
		updateTitles();
		super.onCommit();
	}*/
}
