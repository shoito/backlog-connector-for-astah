package com.github.astah.connector.backlog.model;

public class Project {
	private Integer id;
	private String key;
	private String name;
	private String url;
	private Boolean archived;
	
	public Project(int id, String key, String name, String url, boolean archived) {
		this.id = id;
		this.key = key;
		this.name = name;
		this.url = url;
		this.archived = archived;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}
}
