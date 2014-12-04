package com.noelmace.android.demo.littlenote;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MainActivity extends Activity {

	private static final String NOTE_HTML_KEY = "com.noelmace.android.demo.littlenote.noteHtml";
	private static final String IS_BOLD_KEY = "com.noelmace.android.demo.littlenote.boldMode";
	private static final String IS_ITALIC_KEY = "com.noelmace.android.demo.littlenote.italicMode";
	private static final String IS_UNDERLINED_KEY = "com.noelmace.android.demo.littlenote.underlineMode";

	 private static final String LOG_TAG =
	 "com.noelmace.android.demo.littlenote.warning";

	private EditText noteView;

	private boolean isBold;
	private boolean isItalic;
	private boolean isUnderlined;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		noteView = (EditText) findViewById(R.id.note_view);

		if (savedInstanceState != null) {
			noteView.setText(Html.fromHtml(savedInstanceState
					.getString(NOTE_HTML_KEY)));
			isBold = savedInstanceState.getBoolean(IS_BOLD_KEY);
			isItalic = savedInstanceState.getBoolean(IS_ITALIC_KEY);
			isUnderlined = savedInstanceState.getBoolean(IS_UNDERLINED_KEY);
		} else {
			isBold = false;
			isItalic = false;
			isUnderlined = false;
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(NOTE_HTML_KEY, noteView.getText().toString());
		outState.putBoolean(IS_BOLD_KEY, isBold);
		outState.putBoolean(IS_ITALIC_KEY, isItalic);
		outState.putBoolean(IS_UNDERLINED_KEY, isUnderlined);
		super.onSaveInstanceState(outState);
	}

	// TODO : unifier, simplifier

	public void changeStyle(View view) {
		int start = noteView.getSelectionStart();
		int end = noteView.getSelectionEnd();
		SpannableStringBuilder ssb = new SpannableStringBuilder(noteView.getText());
		SpannableStringBuilder subSsb = new SpannableStringBuilder(ssb, start, end);
		CharacterStyle style = new StyleSpan(android.graphics.Typeface.NORMAL);
		
		
		if(view.getId() == R.id.noeffect_button){
			CharacterStyle[] toRemoveSpans = subSsb.getSpans(0, subSsb.length(), CharacterStyle.class);
			Log.d(LOG_TAG, ssb.toString());
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

		noteView.setText(ssb);
		noteView.setSelection(start, end);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(noteView, InputMethodManager.SHOW_IMPLICIT);
	}

	/*
	public void bold(View view) {

		if (!isBold) {
			// noteHtmlText = noteHtmlText.substring(0, noteHtmlText.length() -
			// 5) + "<b>{}</b>" + noteHtmlText.substring(noteHtmlText.length() -
			// 5);
		} else {
			noteHtmlText = noteHtmlText.replace("{", "");
			noteHtmlText = noteHtmlText.replace("}", "");
		}
		isBold = !isBold;
		updateText();
	}

	public void italic(View view) {
		noteHtmlText = Html.toHtml(noteView.getText());
		if (!isItalic) {
			noteHtmlText = noteHtmlText.substring(0, noteHtmlText.length() - 5)
					+ "<i>{}</i>"
					+ noteHtmlText.substring(noteHtmlText.length() - 5);
		} else {
			noteHtmlText = noteHtmlText.replace("{", "");
			noteHtmlText = noteHtmlText.replace("}", "");
		}
		isItalic = !isItalic;
		updateText();
	}

	public void underline(View view) {
		noteHtmlText = Html.toHtml(noteView.getText());
		if (!isUnderlined) {
			noteHtmlText = noteHtmlText.substring(0, noteHtmlText.length() - 5)
					+ "<u>{}</u>"
					+ noteHtmlText.substring(noteHtmlText.length() - 5);
		} else {
			noteHtmlText = noteHtmlText.replace("{", "");
			noteHtmlText = noteHtmlText.replace("}", "");
		}
		isUnderlined = !isUnderlined;
		updateText();
	}
	*/

	public void send(View view) {
		// TODO
	}

	/*
	protected void updateText() {
		noteView.setText(Html.fromHtml(noteHtmlText));
		int pos = noteView.getText().length() - 2;
		if (isBold || isItalic || isUnderlined) {
			pos -= 1;
		}
		noteView.setSelection(pos);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(noteView, InputMethodManager.SHOW_IMPLICIT);
	}
	*/
}
