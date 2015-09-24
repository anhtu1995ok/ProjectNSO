package com.android.nso.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Keyboard {
	// Method nay co tac dung la:
	// hide keyboard sau khi bam vao 1 button hoac textview, etc...
	/**
	 * @param context
	 *            : context cua ung dung
	 * @param view
	 *            : view dang request hien thi keyboard
	 * */
	public static void hideKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static void showKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}
}
