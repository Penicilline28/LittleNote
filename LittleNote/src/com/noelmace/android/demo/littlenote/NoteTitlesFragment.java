package com.noelmace.android.demo.littlenote;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.noelmace.android.demo.littlenote.entitties.Note;
import com.noelmace.android.demo.littlenote.sqlite.NoteSqliteDao;

public class NoteTitlesFragment extends ListFragment{
	
	OnTitleSelectedListener callback;
	
	private static final int LAYOUT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
            android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
	
	public interface OnTitleSelectedListener {
		public void onNoteSelected(int position);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		callback.onNoteSelected(position);
		getListView().setItemChecked(position, true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
            this.callback = (OnTitleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must extends DaoActivity and implement OnTitleSelectedListener");
        }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getFragmentManager().findFragmentById(R.id.note_content_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
  	}

	@Override
	public void onStart() {
		super.onStart();
		(new UpdateTask()).execute();
	}

	private class UpdateTask extends AsyncTask<Void, Void, List<Note>> {


		// TODO : un sacré ménage au niveau du contexte et des DAO
		// incompatibilité < 11
		@SuppressLint("NewApi") @Override
		protected List<Note> doInBackground(Void... fragments) {
			return ((SQLiteDaoActivity) getActivity()).getDao().findAll();
		}

		@Override
		protected void onPostExecute(List<Note> result) {
			setListAdapter(new ArrayAdapter<String>(getActivity(), LAYOUT, NoteSqliteDao.Util.getTitles(((SQLiteDaoActivity) getActivity()).getDao().findAll())));
			super.onPostExecute(result);
		}
		
	}
	
	/*public void updateList(){
		setListAdapter(new ArrayAdapter<String>(getActivity(), LAYOUT, NoteSqliteDao.Util.getTitles(this.activity.getDao().findAll())));
	}*/

}
