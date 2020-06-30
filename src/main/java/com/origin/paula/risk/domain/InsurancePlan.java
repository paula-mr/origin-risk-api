package com.origin.paula.risk.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsurancePlan {
	@JsonProperty("auto")
	private String auto;
	
	@JsonProperty("disability")
	private String disability;
	
	@JsonProperty("home")
	private String home;
	
	@JsonProperty("life")
	private String life;

	public InsurancePlan() {}
	
	public InsurancePlan(String auto, String disability, String home, String life) {
		super();
		this.auto = auto;
		this.disability = disability;
		this.home = home;
		this.life = life;
	}
	
	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getLife() {
		return life;
	}

	public void setLife(String life) {
		this.life = life;
	}
	
}
