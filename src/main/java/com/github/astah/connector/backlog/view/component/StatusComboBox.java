package com.github.astah.connector.backlog.view.component;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;

import com.github.astah.connector.backlog.view.model.StatusComboBoxModel;


public class StatusComboBox extends JComboBox {
	private static final long serialVersionUID = 1L;
	
	public StatusComboBox() {
		super(new StatusComboBoxModel());
		setBorder(BorderFactory.createEmptyBorder()); 
	}
}
