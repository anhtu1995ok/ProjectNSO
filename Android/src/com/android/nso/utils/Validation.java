package com.android.nso.utils;

import java.util.regex.Pattern;

import com.android.nso.R;

import android.content.Context;
import android.widget.EditText;

public class Validation {

	// Regular Expression
	// you can change the expression based on your need
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String NAME_REGEX = "^[_A-Za-z0-9-\\+]{5,20}$";
	private static final String PASS_REGEX = "^[A-Za-z0-9\\+]{6,20}$";
	private static final String FULLNAME_REGEX = "^([a-zA-Z\\s]|á|à|ã|ả|ạ|ă|ằ|ắ|ẳ|ẵ|ặ|â|ấ|ầ|ẫ|ẩ|ậ|é|è|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ|ó|ò|õ|ỏ|ọ|ô|ố|ồ|ỗ|ổ|ộ|ơ|ớ|ỡ|ở|ợ|í|ỉ|ĩ|ị|ú|ũ|ủ|ù|ụ|ý|ỳ|ỷ|ỹ|ỵ|Á|À|Ã|Ả|Ạ|Ă|Ắ|Ằ|Ẵ|Ẳ|Ặ|Â|Ấ|Ầ|Ẫ|Ẩ|Ậ|É|È|Ẽ|Ẻ|Ẹ|Ê|Ế|Ễ|Ể|Ệ|Ó|Ò|Õ|Ỏ|Ọ|Ô|Ố|Ồ|Ỗ|Ổ|Ộ|Ơ|Ớ|Ờ|Ỡ|Ở|Ợ|Í|Ì|Ĩ|Ỉ|Ị|Ú|Ù|Ũ|Ủ|Ụ|Ý|Ỳ|Ỹ|Ỷ|Ỵ){3,25}$";
	// "^[_A-Za-z0-9-\\+] +(\\s[_A-Za-z0-9-]+)";
	// private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
	private static final String PHONE_REGEX = "\\d{10,11}$";
	// /^\d{10}$/
	// "###-#######"

	// call this method when you need to check email validation
	public static boolean isEmailAddress(Context context, EditText editText, boolean required) {
		String EMAIL_MSG = context.getResources().getString(R.string.email_error);
		return isValid(context, editText, EMAIL_REGEX, EMAIL_MSG, 4, required);
	}

	public static boolean isSamePassword(Context context, EditText editText, String matchPassword, boolean required) {
		String PASS_MSG = context.getResources().getString(R.string.password_error_1);
		return isPasswordValid(context, editText, PASS_MSG, 6, matchPassword, required);
	}

	public static boolean isPassword(Context context, EditText editText, boolean required) {
		String PASS_MSG = context.getResources().getString(R.string.password_error);
		return isValid(context, editText, PASS_REGEX, PASS_MSG, 6, required);
	}

	// call this method when you need to check name validation
	public static boolean isName(Context context, EditText editText, boolean required) {
		String NAME_MSG = context.getResources().getString(R.string.name_error);
		return isValid(context, editText, NAME_REGEX, NAME_MSG, 5, required);
	}
	
	public static boolean isAddress(Context context, EditText editText, boolean required) {
		String ADDRESS_MSG = context.getResources().getString(R.string.address_error);
		return isValid(context, editText, NAME_REGEX, ADDRESS_MSG, 5, required);
	}

	public static boolean isFullName(Context context, EditText editText, boolean required) {
		String FULLNAME_MSG = context.getResources().getString(R.string.fullname_error);
		return isValid(context, editText, FULLNAME_REGEX, FULLNAME_MSG, 3, required);
	}

	// call this method when you need to check phone number validation
	public static boolean isPhoneNumber(Context context, EditText editText, boolean required) {
		String PHONE_MSG = context.getResources().getString(R.string.phone_error);
		return isValid(context, editText, PHONE_REGEX, PHONE_MSG, 10, required);
	}

	// return true if the input field is valid, based on the parameter passed
	public static boolean isValid(Context context, EditText editText, String regex, String errMsg, int numberRequired,
			boolean required) {

		String text = editText.getText().toString().trim();
		// clearing the error, if it was previously set by some other values
		editText.setError(null);

		// text required and editText is blank, so return false
		if (required && !hasText(context, editText, numberRequired))
			return false;

		// pattern doesn't match so returning false
		if (required && !Pattern.matches(regex, text)) {
			editText.setError(errMsg);
			return false;
		}
		;

		return true;
	}

	public static boolean isPasswordValid(Context context, EditText editText, String errMsg, int numberRequired,
			String matchPassword, boolean required) {

		String value = editText.getText().toString();

		editText.setError(null);

		if (required && !hasTheSameText(context, editText, numberRequired, matchPassword)) {
			return false;
		}
		if (required && !value.equalsIgnoreCase(matchPassword)) {
			editText.setError(errMsg);
			return false;
		}

		return true;
	}

	// check the input field has any text or not
	// return true if it contains text otherwise false
	public static boolean hasText(Context context, EditText editText, int numberRequired) {

		String text = editText.getText().toString().trim();
		if (text.length() >= numberRequired) {
			editText.setError(null);
		}

		// length 0 means there is no text
		if (text.length() == 0) {
			String REQUIRED_MSG = context.getResources().getString(R.string.required);
			editText.setError(REQUIRED_MSG);
			return false;
		}

		return true;
	}

	public static boolean hasTheSameText(Context context, EditText editText, int numberRequired, String matchPassword) {

		String text = editText.getText().toString().trim();
		String value = editText.getText().toString();
		if (text.length() >= numberRequired && !value.equalsIgnoreCase(matchPassword)) {
			editText.setError(null);
		}

		// length 0 means there is no text
		if (text.length() == 0) {
			String REQUIRED_MSG = context.getResources().getString(R.string.required);
			editText.setError(REQUIRED_MSG);
			return false;
		}

		return true;
	}
}
