package com.github.astah.connector.backlog.view.model;

import javax.swing.DefaultComboBoxModel;

public class ProjectComboBoxModel extends DefaultComboBoxModel {
	private static final long serialVersionUID = 1L;
	private static final String[] DEFAULT = new String[] {"Backlog未接続"};

	public ProjectComboBoxModel() {
		super(DEFAULT);
	}
	
	public void reset() {
		this.removeAllElements();
		this.addElement(DEFAULT[0]);
	}
}
