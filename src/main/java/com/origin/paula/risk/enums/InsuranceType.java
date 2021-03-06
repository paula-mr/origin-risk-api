package com.origin.paula.risk.enums;

public enum InsuranceType {
	INELIGIBLE("ineligible"),
	ECONOMIC("economic"),
	REGULAR("regular"),
	RESPONSIBLE("responsible");
	
	private String value;
	
	private InsuranceType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
