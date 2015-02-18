package com.noelmace.android.demo.littlenote;

import android.app.Activity;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.noelmace.android.demo.littlenote.sqlite.NoteSqliteDao;

public class NoteTitlesFragment extends ListFragment{
	
	OnTitleSelectedListener callback;
	private SQLiteDaoActivity activity;
	
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
			this.activity = (SQLiteDaoActivity) activity;
            this.callback = (OnTitleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must extends DaoActivity and implement OnTitleSelectedListener");
        }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
  	}

	@Override
	public void onStart() {
		super.onStart();
		if (getFragmentManager().findFragmentById(R.id.note_content_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
		updateList();
	}
	
	public void updateList(){
		setListAdapter(new ArrayAdapter<String>(getActivity(), LAYOUT, NoteSqliteDao.Util.getTitles(this.activity.getDao().findAll())));
	}

}
