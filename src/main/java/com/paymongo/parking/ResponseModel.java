package com.paymongo.parking;

public class ResponseModel {
	private final String status;
	private final Object data;

	public ResponseModel(String status, Object data) {
		this.status = status;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public Object getData() {
		return data;
	}

}
