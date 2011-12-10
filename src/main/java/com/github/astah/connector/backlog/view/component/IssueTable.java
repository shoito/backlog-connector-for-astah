package com.github.astah.connector.backlog.view.component;

import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.Locale;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.astah.connector.backlog.Activator;
import com.github.astah.connector.backlog.controller.IssueController;
import com.github.astah.connector.backlog.model.Project;
import com.github.astah.connector.backlog.view.IssueForm;
import com.github.astah.connector.backlog.view.model.IssueTableModel;


@SuppressWarnings("serial")
public class IssueTable extends JTable {
	private static final Logger logger = LoggerFactory.getLogger(IssueTable.class);
	private Project currentProject;

	public IssueTable(IssueTableModel issueTableModel) {
		super(issueTableModel);
		
		setDefaultRenderer(Object.class, new IssueTableCellRenderer());
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
//		setCellSelectionEnabled(true);
		setRowSelectionAllowed(true);
        setAutoCreateRowSorter(true);
        
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/github/astah/connector/backlog/view/Bundle", Locale.getDefault(), Activator.class.getClassLoader());
        getColumnModel().getColumn(0).setHeaderValue(bundle.getString("IssueList.issueTable.columnModel.key"));
        getColumnModel().getColumn(0).setPreferredWidth(42);
        getColumnModel().getColumn(1).setHeaderValue(bundle.getString("IssueList.issueTable.columnModel.summary"));
        getColumnModel().getColumn(1).setPreferredWidth(180);
        getColumnModel().getColumn(2).setHeaderValue(bundle.getString("IssueList.issueTable.columnModel.description"));
        getColumnModel().getColumn(2).setPreferredWidth(240);
        getColumnModel().getColumn(3).setHeaderValue(bundle.getString("IssueList.issueTable.columnModel.assigner"));
        getColumnModel().getColumn(3).setPreferredWidth(42);
        getColumnModel().getColumn(4).setHeaderValue(bundle.getString("IssueList.issueTable.columnModel.type"));
        getColumnModel().getColumn(4).setPreferredWidth(42);
        getColumnModel().getColumn(5).setHeaderValue(bundle.getString("IssueList.issueTable.columnModel.status"));
        getColumnModel().getColumn(5).setPreferredWidth(42);
//        getColumnModel().getColumn(5).setCellEditor(new StatusCellEditor(new StatusComboBox())); TODO
        getColumnModel().getColumn(6).setHeaderValue(bundle.getString("IssueList.issueTable.columnModel.priority"));
        getColumnModel().getColumn(6).setPreferredWidth(32);
        getColumnModel().getColumn(7).setHeaderValue(bundle.getString("IssueList.issueTable.columnModel.url"));
        
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 2) {
					mouseDoubleClicked(event);
				}
			}
		});
	}
	
	@Override
	public String getToolTipText(MouseEvent e){
        return (String)getModel().getValueAt(
                rowAtPoint(e.getPoint()), 
                columnAtPoint(e.getPoint()));
    }
	
    private void mouseDoubleClicked(MouseEvent event) {
		Point point = event.getPoint();
		int colAtPoint = columnAtPoint(point);
		int rowAtPoint = rowAtPoint(point);
		if (colAtPoint >= 0 && rowAtPoint >= 0) {
			int col = convertColumnIndexToModel(colAtPoint);
			if (col == 7) {
				int row = convertRowIndexToModel(rowAtPoint);
				String url = ((IssueTableModel) getModel()).getRow(row).get("url");
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (Exception e) {
					logger.warn("Can't open url", e);
				}
				return;
			}
		}
		
		if (rowAtPoint >= 0) {
			int row = convertRowIndexToModel(rowAtPoint);
			Map<String, String> issueData = ((IssueTableModel) getModel()).getRow(row);
			
			Window window = SwingUtilities.windowForComponent(this);
			IssueForm dialog = new IssueForm((Frame) window, currentProject, issueData);
			dialog.setLocationByPlatform(true);
			dialog.setModal(true);
			dialog.setVisible(true);
		}
	}

	public Project getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
		IssueController.getInstance().loadIssues(currentProject);
	}
}
