package com.github.astah.connector.backlog.view.model;

import javax.swing.DefaultComboBoxModel;

import com.github.astah.connector.backlog.model.Status;
import com.github.astah.connector.backlog.model.Status.E;


public class StatusComboBoxModel extends DefaultComboBoxModel {
	private static final long serialVersionUID = 1L;
	private static final Status.E[] fields = { E.未対応, E.処理中, E.処理済み, E.完了 };

	public StatusComboBoxModel() {
		super(fields);
	}
}
