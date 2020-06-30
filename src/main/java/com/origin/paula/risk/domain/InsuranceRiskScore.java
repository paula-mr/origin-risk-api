package com.origin.paula.risk.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.origin.paula.risk.enums.InsuranceType;

public class InsuranceRiskScore {
	private int auto;
	private int disability;
	private int home;
	private int life;
	
	public InsuranceRiskScore() {
		this.auto = 0;
		this.disability = 0;
		this.home = 0;
		this.life = 0;
	}
	
	public void calculate(RiskProfile riskProfile) {
		int baseRiskScore = calculateBaseRiskPoints(riskProfile.getRiskQuestions());
		
		this.auto = baseRiskScore;
		this.disability = baseRiskScore;
		this.home = baseRiskScore;
		this.life = baseRiskScore;

		if (riskProfile.isYoungAdult()) {
			this.deductAutoScore(2);
			this.deductDisabilityScore(2);
			this.deductHomeScore(2);
			this.deductLifeScore(2);
		} else if (riskProfile.isAdult()) {
			this.deductAutoScore(1);
			this.deductDisabilityScore(1);
			this.deductHomeScore(1);
			this.deductLifeScore(1);
		}

		if (riskProfile.hasHighIncome()) {
			this.deductAutoScore(1);
			this.deductDisabilityScore(1);
			this.deductHomeScore(1);
			this.deductLifeScore(1);
		}

		if (riskProfile.hasMortgagedHouse()) {
			this.addHomeScore(1);
			this.addDisabilityScore(1);
		}

		if (riskProfile.hasDependents()) {
			this.addLifeScore(1);
			this.addDisabilityScore(1);
		}

		if (riskProfile.isMarried()) {
			this.addLifeScore(1);
			this.deductDisabilityScore(1);
		}

		if (riskProfile.hasNewVehicle()) {
			this.addAutoScore(1);
		}
	}
	
	private int calculateBaseRiskPoints(List<Boolean> riskQuestions) {
		return riskQuestions.stream().filter(q -> q).collect(Collectors.toList()).size();
	}
	
	private String mapScore(int score) {
		if (score < 1)
			return InsuranceType.ECONOMIC.getValue();

		if (score < 3)
			return InsuranceType.REGULAR.getValue();

		return InsuranceType.RESPONSIBLE.getValue();
	}

	public String getAuto() {
		return mapScore(this.auto);
	}

	public void addAutoScore(int score) {
		this.auto += score;
	}
	
	public void deductAutoScore(int score) {
		this.auto -= score;
	}

	public String getDisability() {
		return mapScore(this.disability);
	}

	public void addDisabilityScore(int score) {
		this.disability += score;
	}
	
	public void deductDisabilityScore(int score) {
		this.disability -= score;
	}

	public String getHome() {
		return mapScore(this.home);
	}

	public void addHomeScore(int score) {
		this.home += score;
	}
	
	public void deductHomeScore(int score) {
		this.home -= score;
	}

	public String getLife() {
		return mapScore(this.life);
	}

	public void addLifeScore(int score) {
		this.life += score;
	}
	
	public void deductLifeScore(int score) {
		this.life -= score;
	}
	
}
