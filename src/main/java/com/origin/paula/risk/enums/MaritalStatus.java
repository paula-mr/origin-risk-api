package com.origin.paula.risk.enums;

public enum MaritalStatus {
	SINGLE("single"),
	MARRIED("married");
	
	private String value;
	
	private MaritalStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public static Boolean isValid(String value) {
		for (MaritalStatus status: MaritalStatus.values()) {
			if (status.getValue().equals(value))
				return true;
		}
		
		return false;
	}
}
