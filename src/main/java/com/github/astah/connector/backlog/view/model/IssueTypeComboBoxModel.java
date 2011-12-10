package com.github.astah.connector.backlog.view.model;

import javax.swing.DefaultComboBoxModel;

import com.github.astah.connector.backlog.model.IssueType;


@SuppressWarnings("serial")
public class IssueTypeComboBoxModel extends DefaultComboBoxModel {
	private static final IssueType[] DEFAULT = new IssueType[] { 
		new IssueType(5, "バグ", "#990000"),
		new IssueType(6, "タスク", "#7ea800"), 
		new IssueType(7, "要望", "#ff9200"), 
		new IssueType(8, "その他", "#2779ca") 
	};

	public IssueTypeComboBoxModel() {
		super(DEFAULT);
	}
}
