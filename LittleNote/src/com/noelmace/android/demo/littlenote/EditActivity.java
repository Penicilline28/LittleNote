package com.noelmace.android.demo.littlenote;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.noelmace.android.demo.littlenote.dao.DaoException;
import com.noelmace.android.demo.littlenote.entitties.Note;
import com.noelmace.android.demo.littlenote.sqlite.DbHelper;

public class EditActivity extends SQLiteDaoActivity {

	 private static final String LOG_TAG =
	 "com.noelmace.android.demo.littlenote.warning";

	private EditText noteContentView;
	private EditText noteTitleView;
	

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}
	
	@Override
	protected void onPause() {
	    DbHelper.close(EditActivity.this);
		super.onPause();
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		noteContentView = (EditText) findViewById(R.id.note_content_view);
		noteTitleView = (EditText) findViewById(R.id.note_title_view);

		if (savedInstanceState != null) {
			return;
		}

	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_edit_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		
		super.onSaveInstanceState(outState);
	}

	// TODO : unifier, simplifier

	public void changeStyle(View view) {
		int start = noteContentView.getSelectionStart();
		int end = noteContentView.getSelectionEnd();
		SpannableStringBuilder ssb = new SpannableStringBuilder(noteContentView.getText());
		SpannableStringBuilder subSsb = new SpannableStringBuilder(ssb, start, end);
		CharacterStyle style = new StyleSpan(android.graphics.Typeface.NORMAL);
		
		
		if(view.getId() == R.id.noeffect_button){
			CharacterStyle[] toRemoveSpans = subSsb.getSpans(0, subSsb.length(), CharacterStyle.class);
			for(int i = 0; i < toRemoveSpans.length; i++){
				ssb.removeSpan(toRemoveSpans[i]);
			}
		}
		switch (view.getId()) {
			case R.id.bold_button:
				style = new StyleSpan(android.graphics.Typeface.BOLD);
				break;
			case R.id.italic_button:
				style = new StyleSpan(android.graphics.Typeface.ITALIC);
				break;
			case R.id.underline_button:
				style = new UnderlineSpan();
				break;
			case R.id.noeffect_button:
				style = null;
				break;
			default:
				Log.w(LOG_TAG, view.getId() + "is not implemented");
				break;
		}
		
		if(start != end){
			ssb.setSpan(style, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		} else {
			// TODO
		}

		noteContentView.setText(ssb);
		noteContentView.setSelection(start, end);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(noteContentView, InputMethodManager.SHOW_IMPLICIT);
	}


	public void save() {
		// TODO fragment for verification
		try {
			getDao().create(new Note(noteTitleView.getText().toString(), Html.toHtml(noteContentView.getText())));
		} catch (DaoException e) {
			// TODO : use string resources for text
			// TODO : externalize
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					EditActivity.this);
	 
				// set title
				alertDialogBuilder.setTitle("Error");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("An error occured during this note's saving. Try again or contact the dev.")
					.setCancelable(true);
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
			Log.e(LOG_TAG, e.getMessage());
		}
		Log.d(LOG_TAG, getDao().findAll().toString());
	}

}
