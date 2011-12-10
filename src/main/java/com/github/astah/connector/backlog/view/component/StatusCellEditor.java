package com.github.astah.connector.backlog.view.component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;


@SuppressWarnings("serial")
public class StatusCellEditor extends DefaultCellEditor {
	public StatusCellEditor(JComboBox comboBox) {
		super(comboBox);
	}
	
	@Override
	public boolean stopCellEditing() {
//		Status.E newStatus = (E) super.getCellEditorValue();
		return false;
	}
}
