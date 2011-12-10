package com.github.astah.connector.backlog.view.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class IssueTableModel extends DefaultTableModel {
	private String[] columnNames = {"キー", "件名", "詳細", "担当者", "種別", "状態", "優先度", "URL"};
	private Object [][] rows = {};
	
	public IssueTableModel() {
		this.setDataVector(rows, columnNames);
	}

    @SuppressWarnings("rawtypes")
	Class[] types = new Class[] { String.class, String.class, String.class, String.class, String.class, String.class,
			String.class, String.class, String.class };
    
    boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false
    };

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }
    
    public void removeAll() {
    	int rowCount = this.getRowCount();
    	for (int i = 0; i < rowCount; i++) {
    		removeRow(0);
    	}
    }
    
    @SuppressWarnings("rawtypes")
	public void addRow(Map issue) {
    	super.addRow(transform(issue));
    }
    
    @SuppressWarnings("rawtypes")
	public Map<String, String> getRow(int row) {
    	Vector rowData = (Vector) getDataVector().get(row);
    	
    	Map<String, String> ret = new HashMap<String, String>();
    	ret.put("key", (String) rowData.get(0));
    	ret.put("summary", (String) rowData.get(1));
    	ret.put("description", (String) rowData.get(2));
    	ret.put("assigner", (String) rowData.get(3));
    	ret.put("issueType", (String) rowData.get(4));
    	ret.put("status", ObjectUtils.toString(rowData.get(5)));
    	ret.put("priority", (String) rowData.get(6));
    	ret.put("url", (String) rowData.get(7));
    	return ret;
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object[] transform(Map issue) {
		String key = StringUtils.defaultString((String) issue.get("key"));
		String summary = StringUtils.defaultString((String) issue.get("summary"));
		String description = StringUtils.defaultString((String) issue.get("description"));
		Object assignerObj = (Map<String, Object>) issue.get("assigner");
		String assigner = (String) ((assignerObj != null) ? ((Map) assignerObj).get("name") : "");
		Object issueTypeObj = (Map<String, Object>) issue.get("issueType");
		String issueType = (String) ((issueTypeObj != null) ? ((Map) issueTypeObj).get("name") : "");
		Object statusObj = (Map<String, Object>) issue.get("status");
		String status = (String) ((statusObj != null) ? ((Map) statusObj).get("name") : "");
		Object priorityObj = (Map<String, Object>) issue.get("priority");
		String priority = (String) ((priorityObj != null) ? ((Map) priorityObj).get("name") : "");
		String url = StringUtils.defaultString((String) issue.get("url"));

		return new Object[] {key, summary, description, assigner, issueType, status, priority, url};
	}
}
