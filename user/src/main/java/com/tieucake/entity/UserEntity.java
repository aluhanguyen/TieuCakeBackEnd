package com.tieucake.entity;

public class UserEntity {
	private Long userId;
	private String userName;
	private String password;

	public static final String USERID = "userId";
	public static final String USERNAME = "userName";
	public static final String PASSWORD = "password";

	private UserEntity(Builder builder) {
		super();
		this.userId = builder.userId;
		this.userName = builder.userName;
		this.password = builder.password;
	}

	public static class Builder {
		private Long userId;
		private String userName;
		private String password;

		public Builder userId(Long userId) {
			this.userId = userId;
			return this;
		}

		public Builder userName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public UserEntity build() {
			return new UserEntity(this);
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
