package com.github.astah.connector.backlog.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.astah.connector.backlog.model.IssueType;
import com.github.astah.connector.backlog.model.Project;
import com.github.astah.connector.backlog.model.User;

public class BacklogClient {
	private static final Logger logger = LoggerFactory.getLogger(BacklogClient.class);
	private XmlRpcClient rpcClient;
	private String space;
	private String userName;
	private String password;

	public BacklogClient(String space, String userName, String password) {
		this.space = space;
		this.userName = userName;
		this.password = password;
		
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setBasicUserName(userName);
		config.setBasicPassword(password);
		try {
			config.setServerURL(new URL("https://" + space + ".backlog.jp/XML-RPC"));
		} catch (MalformedURLException e) {
			logger.error("Space is wrong.", e);
		}
		
		rpcClient = new XmlRpcClient();
		rpcClient.setConfig(config);
	}
	
	@SuppressWarnings("rawtypes")
	private List<Map> transform(Object[] objects) {
		if (objects == null) {
			objects = new Object[]{};
		}
		
		List<Map> list = new ArrayList<Map>();
		for (Object object : objects) {
			list.add((Map) object);
		}
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public List<User> getUsers(int projectId) throws XmlRpcException {
		Object[] ret = (Object[]) rpcClient.execute("backlog.getUsers", new Object[] { projectId });
		List<Map> transformed = transform(ret);
		List<User> users = new ArrayList<User>();
		for (Map map : transformed) {
			User user = new User();
			user.setId((Integer) map.get("id"));
			user.setName((String) map.get("name"));
			users.add(user);
		}
		return users;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Project> getProjects() throws XmlRpcException {
		Object[] ret = (Object[]) rpcClient.execute("backlog.getProjects", new Object[] {});
		List<Map> transformed = transform(ret);
		List<Project> projects = new ArrayList<Project>();
		for (Map map : transformed) {
			projects.add(new Project((Integer) map.get("id"), (String) map.get("key"), 
					(String) map.get("name"), (String) map.get("url"), (Boolean) map.get("archived")));
		}
		return projects;
	}
	
	@SuppressWarnings("rawtypes")
	public List<IssueType> getIssueTypes(int projectId) throws XmlRpcException {
		Object[] ret = (Object[]) rpcClient.execute("backlog.getIssueTypes", new Object[] { projectId });
		List<Map> transformed = transform(ret);
		List<IssueType> issueTypes = new ArrayList<IssueType>();
		for (Map map : transformed) {
			IssueType issueType = new IssueType((Integer) map.get("id"), (String) map.get("name"), (String) map.get("color"));
			issueTypes.add(issueType);
		}
		return issueTypes;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> findIssue(Map<String, Object> query) throws XmlRpcException {
		if (!query.containsKey("projectId")) {
			throw new IllegalArgumentException("projectId is null");
		}

		Object[] ret = (Object[]) rpcClient.execute("backlog.findIssue", new Object[] { query });
		return transform(ret);
	}
	
	public boolean isConnectable() {
		try {
			getProjects();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Map createIssue(Map<String, Object> issueData) throws XmlRpcException {
		if (!issueData.containsKey("projectId")) {
			throw new IllegalArgumentException("projectId is null");
		}

		Map ret = (Map) rpcClient.execute("backlog.createIssue", new Object[] { issueData });
		return ret;
	}
	
	@SuppressWarnings("rawtypes")
	public Map updateIssue(Map<String, Object> issueData) throws XmlRpcException {
		if (!issueData.containsKey("key")) {
			throw new IllegalArgumentException("key is null");
		}
		
		Map ret = (Map) rpcClient.execute("backlog.updateIssue", new Object[] { issueData });
		return ret;
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Throwable {
		BacklogClient client = new BacklogClient("connector", "developer", "developer");
		
		List<Project> projects = client.getProjects();
		for (Project project : projects) {
			System.out.println(project);
		}
		
		Project project = projects.get(0);
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("projectId", project.getId());
		query.put("statusId", new Integer[]{1, 2, 3});
		List<Map> issues = client.findIssue(query);
		for (Map issue : issues) {
			System.out.println(issue);
		}
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
