package com.voter.exception;

public enum CustomHttpStatus {
	VOTER_ADDED(200, "VOTER ADDED");

	CustomHttpStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private final int code;
	private final String message;

	public int value() {
		return this.value();
	}

	public String getStatusMessage() {
		return this.message;
	}
}
