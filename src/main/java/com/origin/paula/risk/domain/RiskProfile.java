package com.origin.paula.risk.domain;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.origin.paula.risk.enums.MaritalStatus;

public class RiskProfile {
	public static final int MIN_HIGH_INCOME = 200001;
	public static final int MAX_AGE_YOUNG_ADULT = 29;
	public static final int MAX_AGE_ADULT = 39;
	public static final int MIN_AGE_ELDERLY = 61;

	@NotNull(message = "Age is required.")
	@Min(value = 0, message = "Age can not be negative.")
	@JsonProperty("age")
	private Integer age;

	@NotNull(message = "The number of dependents is required.")
	@Min(value = 0, message = "The number of dependents can not be negative.")
	@JsonProperty("dependents")
	private Integer quantityDependents;

	@JsonProperty("house")
	private House houseOwned;

	@NotNull(message = "Income is required.")
	@Min(value = 0, message = "Income can not be negative.")
	@JsonProperty("income")
	private Integer income;

	@NotNull(message = "Marital status is required.")
	@JsonProperty("marital_status")
	private String maritalStatus;

	@NotNull(message = "Risk answers are required.")
	@JsonProperty("risk_questions")
	private List<Boolean> riskQuestions;

	@JsonProperty("vehicle")
	private Vehicle vehicleOwned;

	public RiskProfile() {
	}

	public RiskProfile(Integer age, Integer quantityDependents, House houseOwned, Integer income, String maritalStatus,
			List<Boolean> riskQuestions, Vehicle vehicleOwned) {
		super();
		this.age = age;
		this.quantityDependents = quantityDependents;
		this.houseOwned = houseOwned;
		this.income = income;
		this.maritalStatus = maritalStatus;
		this.riskQuestions = riskQuestions;
		this.vehicleOwned = vehicleOwned;
	}

	@JsonIgnore
	public boolean isElderly() {
		return this.age >= MIN_AGE_ELDERLY;
	}

	@JsonIgnore
	public boolean isYoungAdult() {
		return this.age <= MAX_AGE_YOUNG_ADULT;
	}

	@JsonIgnore
	public boolean isAdult() {
		return this.age <= MAX_AGE_ADULT;
	}
	
	@JsonIgnore
	public boolean hasIncome() {
		return this.income > 0;
	}

	@JsonIgnore
	public boolean hasHighIncome() {
		return this.income >= MIN_HIGH_INCOME;
	}
	
	@JsonIgnore
	public boolean ownsHouse() {
		return this.houseOwned != null;
	}

	@JsonIgnore
	public boolean ownsMortgagedHouse() {
		return this.houseOwned != null && this.houseOwned.isMortgaged();
	}
	
	@JsonIgnore
	public boolean ownsVehicle() {
		return this.vehicleOwned != null;
	}

	@JsonIgnore
	public boolean ownsNewVehicle() {
		return this.vehicleOwned != null && this.vehicleOwned.isNew();
	}

	@JsonIgnore
	public boolean hasDependents() {
		return this.quantityDependents > 0;
	}

	@JsonIgnore
	public boolean isMarried() {
		return MaritalStatus.MARRIED.getValue().equals(this.maritalStatus);
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getQuantityDependents() {
		return quantityDependents;
	}

	public void setQuantityDependents(Integer quantityDependents) {
		this.quantityDependents = quantityDependents;
	}

	public House getHouseOwned() {
		return houseOwned;
	}

	public void setHouseOwned(House houseOwned) {
		this.houseOwned = houseOwned;
	}

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public List<Boolean> getRiskQuestions() {
		return riskQuestions;
	}

	public void setRiskQuestions(List<Boolean> riskQuestions) {
		this.riskQuestions = riskQuestions;
	}

	public Vehicle getVehicleOwned() {
		return vehicleOwned;
	}

	public void setVehicleOwned(Vehicle vehicleOwned) {
		this.vehicleOwned = vehicleOwned;
	}

}
