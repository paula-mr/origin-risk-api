package com.origin.paula.risk.enums;

public enum OwnershipStatus {
	OWNED("owned"),
	MORTGAGED("mortgaged");
	
	private String value;
	
	private OwnershipStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public static Boolean isValid(String value) {
		for (OwnershipStatus status: OwnershipStatus.values()) {
			if (status.getValue().equals(value))
				return true;
		}
		
		return false;
	}
}
