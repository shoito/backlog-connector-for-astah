package com.github.astah.connector.backlog.model;

public class Status {
	public enum E {
		未対応(4), 処理中(2), 処理済み(3), 完了(1);
		
		private int id;
		
		E(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		@Override
		public String toString() {
			return name();
		}
	}
	
	private int id;
	private String name;
	
	public Status(int id, String name) {
		this.id = id;
		this.name = name;
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
}
