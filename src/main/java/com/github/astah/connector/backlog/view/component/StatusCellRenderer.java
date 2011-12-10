package com.github.astah.connector.backlog.view.component;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class StatusCellRenderer extends JComboBox implements TableCellRenderer {
	private static final Color even = new Color(240, 240, 255);
	private final JTextField editor;

	public StatusCellRenderer() {
		super();
		setEditable(true);
		setBorder(BorderFactory.createEmptyBorder());

		editor = (JTextField) getEditor().getEditorComponent();
		editor.setBorder(BorderFactory.createEmptyBorder());
		editor.setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		removeAllItems();
		if (isSelected) {
			editor.setForeground(table.getSelectionForeground());
			editor.setBackground(table.getSelectionBackground());
		} else {
			editor.setForeground(table.getForeground());
			editor.setBackground((row % 2 == 0) ? even : table.getBackground());
		}
		addItem((value == null) ? "" : value.toString());
		return this;
	}
}
