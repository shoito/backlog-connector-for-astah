package com.github.astah.connector.backlog.model;

public class IssueType {
	private int id;
	private String name;
	private String color;

	public IssueType(int id, String name, String color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}