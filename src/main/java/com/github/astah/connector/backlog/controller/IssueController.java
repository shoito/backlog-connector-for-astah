package com.github.astah.connector.backlog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.astah.connector.backlog.client.BacklogClient;
import com.github.astah.connector.backlog.model.Project;
import com.github.astah.connector.backlog.util.ConfigurationUtils;
import com.github.astah.connector.backlog.view.IssueListTabView;
import com.github.astah.connector.backlog.view.model.IssueTableModel;
import com.github.astah.connector.backlog.view.model.ProjectComboBoxModel;

public class IssueController {
	private static final Logger logger = LoggerFactory.getLogger(IssueController.class);
	private static IssueController instance;
	private BacklogClient client;
	private ProjectComboBoxModel projectModel;
	private IssueTableModel issueTableModel;
	private IssueListTabView issueList;
	
	public static IssueController getInstance() {
		if (instance == null) {
			instance = new IssueController();
		}
		return instance;
	}
	
	public IssueController() {
		projectModel = new ProjectComboBoxModel();
		issueTableModel = new IssueTableModel();
		loadConfiguration();
	}
	
	private void loadConfiguration() {
        Map<String, String> config = ConfigurationUtils.load();
		String space = config.get(ConfigurationUtils.SPACE);
		String userName = config.get(ConfigurationUtils.USER_NAME);
		String password = config.get(ConfigurationUtils.PASSWORD);
		
		if (StringUtils.isBlank(space) || StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			return;
		}
		
    	BacklogClient client = new BacklogClient(space, userName, password);
    	try {
			List<Project> projects = client.getProjects();
			projectModel.removeAllElements();
			for(Project project : projects) {
				projectModel.addElement(project);
			}
			this.client = client;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}
	
	public void loadIssuesFromCurrentProject() {
        issueTableModel.removeAll();
        
		Object selectedItem = projectModel.getSelectedItem();
		if (!(selectedItem instanceof Project)) {
			return;
		}
		
        Project project = (Project) selectedItem;
		loadIssues(project);
	}

	@SuppressWarnings("rawtypes")
	public void loadIssues(Project project) {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("projectId", project.getId());
		query.put("statusId", new Integer[]{1, 2, 3});
        try {
			List<Map> issues = client.findIssue(query);
			for (Map issue : issues) {
				issueTableModel.addRow(issue);
//				{"キー", "件名", "詳細", "担当者", "種別", "状態", "優先度", "URL"};
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}
	
	public BacklogClient getBacklogClient() {
		return client;
	}
	
	public void setBacklogClient(BacklogClient client) {
		this.client = client;
	}
	
	public ProjectComboBoxModel getProjectModel() {
		return projectModel;
	}
	
	public IssueTableModel getIssueTableModel() {
		return issueTableModel;
	}
	
	public IssueListTabView getIssueList() {
		return issueList;
	}
}
