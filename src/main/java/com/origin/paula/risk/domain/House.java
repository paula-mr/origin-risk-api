package com.origin.paula.risk.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class House {
	@NotNull(message = "Ownership status of the house is required.")
	@JsonProperty("ownership_status")
	private String ownershipStatus;

	public House() {}

	public House(String ownershipStatus) {
		super();
		this.ownershipStatus = ownershipStatus;
	}

	public String getOwnershipStatus() {
		return ownershipStatus;
	}

	public void setOwnershipStatus(String ownershipStatus) {
		this.ownershipStatus = ownershipStatus;
	}

}
