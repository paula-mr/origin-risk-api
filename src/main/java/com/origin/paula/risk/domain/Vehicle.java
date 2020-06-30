package com.origin.paula.risk.domain;

import java.util.Calendar;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Vehicle {
	public static final int YEARS_CONSIDERED_NEW = 5;

	@NotNull(message = "Fabrication year of the vehicle is required.")
	@Min(value = 0, message = "Fabrication year can not be negative.")
	@JsonProperty("year")
	private Integer fabricationYear;

	public Vehicle() {
	}

	public Vehicle(Integer fabricationYear) {
		super();
		this.fabricationYear = fabricationYear;
	}
	
	@JsonIgnore
	public boolean isNew() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);	
		return (currentYear - fabricationYear) <= YEARS_CONSIDERED_NEW;
	}

	public Integer getFabricationYear() {
		return fabricationYear;
	}

	public void setFabricationYear(Integer fabricationYear) {
		this.fabricationYear = fabricationYear;
	}

}
