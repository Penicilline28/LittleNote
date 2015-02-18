package com.noelmace.android.demo.littlenote;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noelmace.android.demo.littlenote.entitties.Note;
import com.noelmace.android.demo.littlenote.sqlite.NoteSqliteDao;

public class NoteContentFragment extends Fragment {

	public final static String POS_KEY = "position";
	private int currentPosition = -1;
		
	private SQLiteDaoActivity activity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);

	    // Sets "up navigation" for both phone/tablet configurations
	    ActionBarActivity act = ((ActionBarActivity) getActivity());
	    if (act.findViewById(R.id.fragment_container) != null) {
	    	((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    }
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
            this.activity = (SQLiteDaoActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must extends DaoActivity");
        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(savedInstanceState != null){
			currentPosition = savedInstanceState.getInt(POS_KEY);
		}
		return inflater.inflate(R.layout.note_content_view, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
				
		Bundle args = getArguments();
        if (args != null) {
            updateNoteView(args.getInt(POS_KEY));
        } else if (currentPosition != -1) {
            updateNoteView(currentPosition);
        }
        
	}

	public void updateNoteView(int position) {
		TextView noteContentView = (TextView) getActivity().findViewById(R.id.note_content);
		
		NoteSqliteDao dao = activity.getDao();
		List<Note> notes = dao.findAll();
		List<String> contents = NoteSqliteDao.Util.getContents(notes);
		String content = contents.get(position);
		Spanned spannedContent = Html.fromHtml(content);
		noteContentView.setText(spannedContent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	// TODO : nettoyer
	                Intent parentActivityIntent = new Intent(getActivity(), MainActivity.class);
	                startActivity(parentActivityIntent);
	                getActivity().finish();
	            return true;

	        // Other case statements...

	        default:
	            return super.onOptionsItemSelected(item);
	    }

	}
	
}
