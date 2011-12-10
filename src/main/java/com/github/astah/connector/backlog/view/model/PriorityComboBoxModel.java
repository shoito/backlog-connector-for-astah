package com.github.astah.connector.backlog.view.model;

import javax.swing.DefaultComboBoxModel;

import com.github.astah.connector.backlog.model.Priority;


@SuppressWarnings("serial")
public class PriorityComboBoxModel extends DefaultComboBoxModel {
	private static final Priority[] DEFAULT = new Priority[] {
		new Priority(2, "高"), 
		new Priority(3, "中"), 
		new Priority(4, "低")
	};

	public PriorityComboBoxModel() {
		super(DEFAULT);
	}
}
