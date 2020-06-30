package com.origin.paula.risk.domain;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RiskProfile {	
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

	public RiskProfile() {}

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
