package org.htmlparser.tags.data;

import java.util.Vector;

public class FormData {
	private String formURL;
	private String formName;
	private String formMethod;
	private Vector formInputVector;
	private Vector textAreaVector;
	 
	public FormData(String formURL, String formName, String formMethod,
	Vector formInputVector, Vector textAreaVector) {
		this.formURL = formURL;
		this.formName = formName;
		this.formMethod = formMethod;
		this.formInputVector = formInputVector;
		this.textAreaVector = textAreaVector;
	}

	public Vector getFormInputVector() {
		return formInputVector;
	}

	public String getFormMethod() {
		return formMethod;
	}

	public String getFormName() {
		return formName;
	}

	public String getFormURL() {
		return formURL;
	}

	public Vector getTextAreaVector() {
		return textAreaVector;
	}

}
