package com.noelmace.android.demo.littlenote;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class MainActivity extends Activity {

	private static final String NOTE_HTML_KEY = "com.noelmace.android.demo.littlenote.noteHtml";
	private static final String IS_BOLD_KEY = "com.noelmace.android.demo.littlenote.boldMode";
	private static final String IS_ITALIC_KEY = "com.noelmace.android.demo.littlenote.italicMode";
	private static final String IS_UNDERLINED_KEY = "com.noelmace.android.demo.littlenote.underlineMode";
	
	//private static final String LOG_TAG = "com.noelmace.android.demo.littlenote.debug";
	
	private EditText noteView;
	private String noteHtmlText;
	
	private boolean isBold;
	private boolean isItalic;
	private boolean isUnderlined;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        noteView = (EditText) findViewById(R.id.note_view);
        
        if(savedInstanceState != null){
        	noteHtmlText = savedInstanceState.getString(NOTE_HTML_KEY);
        	noteView.setText(Html.fromHtml(noteHtmlText));
        	isBold = savedInstanceState.getBoolean(IS_BOLD_KEY);
        	isItalic = savedInstanceState.getBoolean(IS_ITALIC_KEY);
        	isUnderlined = savedInstanceState.getBoolean(IS_UNDERLINED_KEY);
            updateText();
        } else {
        	isBold = false;
        	isItalic = false;
        	isUnderlined = false;
        	noteHtmlText = "";
        }
        
        //noteView.addTextChangedListener(textWatcher);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(NOTE_HTML_KEY, noteView.getText().toString());
		outState.putBoolean(IS_BOLD_KEY, isBold);
		outState.putBoolean(IS_ITALIC_KEY, isItalic);
		outState.putBoolean(IS_UNDERLINED_KEY, isUnderlined);
		super.onSaveInstanceState(outState);
	}
	
	// TODO : unifier, simplifier

	public void bold(View view){
		noteHtmlText = Html.toHtml(noteView.getText());
		if(!isBold){
			noteHtmlText = noteHtmlText.substring(0, noteHtmlText.length() - 5) + "<b>{}</b>" + noteHtmlText.substring(noteHtmlText.length() - 5);
		}else{
			noteHtmlText = noteHtmlText.replace("{", "");
			noteHtmlText = noteHtmlText.replace("}", "");
		}
		isBold = !isBold;
		updateText();
	}
	
	public void italic(View view){
		noteHtmlText = Html.toHtml(noteView.getText());
		if(!isItalic){
			noteHtmlText = noteHtmlText.substring(0, noteHtmlText.length() - 5) + "<i>{}</i>" + noteHtmlText.substring(noteHtmlText.length() - 5);
		}else{
			noteHtmlText = noteHtmlText.replace("{", "");
			noteHtmlText = noteHtmlText.replace("}", "");
		}
		isItalic = !isItalic;
		updateText();
	}
	
	public void underline(View view){
		noteHtmlText = Html.toHtml(noteView.getText());
		if(!isUnderlined){
			noteHtmlText = noteHtmlText.substring(0, noteHtmlText.length() - 5) + "<u>{}</u>" + noteHtmlText.substring(noteHtmlText.length() - 5);
		}else{
			noteHtmlText = noteHtmlText.replace("{", "");
			noteHtmlText = noteHtmlText.replace("}", "");
		}
		isUnderlined = !isUnderlined;
		updateText();
	}
	
	public void send(View view){
		// TODO
	}
	
	protected void updateText(){
		noteView.setText(Html.fromHtml(noteHtmlText));
		int pos = noteView.getText().length() -2;
		if(isBold || isItalic || isUnderlined){
			pos -= 1;
		}
		noteView.setSelection(pos);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(noteView, InputMethodManager.SHOW_IMPLICIT);
	}
}
