package com.github.astah.connector.backlog.view.model;

import javax.swing.DefaultComboBoxModel;

import com.github.astah.connector.backlog.model.User;


public class UserComboBoxModel extends DefaultComboBoxModel {
	private static final long serialVersionUID = 1L;
	private static final User[] DEFAULT = new User[] { new User() };

	public UserComboBoxModel() {
		super(DEFAULT);
	}
}
