package com.origin.paula.risk.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.origin.paula.risk.enums.OwnershipStatus;

public class House {
	@NotNull(message = "Ownership status of the house is required.")
	@JsonProperty("ownership_status")
	private String ownershipStatus;

	public House() {
	}

	public House(String ownershipStatus) {
		super();
		this.ownershipStatus = ownershipStatus;
	}
	
	@JsonIgnore
	public boolean isMortgaged() {
		return OwnershipStatus.MORTGAGED.getValue().equals(this.ownershipStatus);
	}

	public String getOwnershipStatus() {
		return ownershipStatus;
	}

	public void setOwnershipStatus(String ownershipStatus) {
		this.ownershipStatus = ownershipStatus;
	}

}
