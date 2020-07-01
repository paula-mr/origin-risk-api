package com.origin.paula.risk.domain;

import java.util.List;
import java.util.stream.Collectors;

public class RiskScore {
	private int autoRisk;
	private int disabilityRisk;
	private int homeRisk;
	private int lifeRisk;
	
	public void calculate(RiskProfile riskProfile) {
		applyBaseRiskScoreRule(riskProfile.getRiskQuestions());

		applyAgeRule(riskProfile);
		applyIncomeRule(riskProfile);
		applyHouseOwnershipRule(riskProfile);
		applyDependentsRule(riskProfile);
		applyMaritalStatusRule(riskProfile);
		applyVehicleOwnershipRule(riskProfile);
	}

	private void applyBaseRiskScoreRule(List<Boolean> riskQuestions) {
		int baseRiskScore = riskQuestions.stream().filter(q -> q).collect(Collectors.toList()).size();
		
		this.autoRisk = baseRiskScore;
		this.disabilityRisk = baseRiskScore;
		this.homeRisk = baseRiskScore;
		this.lifeRisk = baseRiskScore;
	}
	
	private void applyAgeRule(RiskProfile riskProfile) {
		if (riskProfile.isYoungAdult()) {
			this.deductAutoRisk(2);
			this.deductDisabilityRisk(2);
			this.deductHomeRisk(2);
			this.deductLifeRisk(2);
		} else if (riskProfile.isAdult()) {
			this.deductAutoRisk(1);
			this.deductDisabilityRisk(1);
			this.deductHomeRisk(1);
			this.deductLifeRisk(1);
		}
	}
	
	private void applyIncomeRule(RiskProfile riskProfile) {
		if (riskProfile.hasHighIncome()) {
			this.deductAutoRisk(1);
			this.deductDisabilityRisk(1);
			this.deductHomeRisk(1);
			this.deductLifeRisk(1);
		}
	}
	
	private void applyHouseOwnershipRule(RiskProfile riskProfile) {
		if (riskProfile.ownsMortgagedHouse()) {
			this.addHomeRisk(1);
			this.addDisabilityRisk(1);
		}		
	}
	
	private void applyDependentsRule(RiskProfile riskProfile) {
		if (riskProfile.hasDependents()) {
			this.addLifeRisk(1);
			this.addDisabilityRisk(1);
		}
	}
	
	private void applyMaritalStatusRule(RiskProfile riskProfile) {
		if (riskProfile.isMarried()) {
			this.addLifeRisk(1);
			this.deductDisabilityRisk(1);
		}
	}
	
	private void applyVehicleOwnershipRule(RiskProfile riskProfile) {
		if (riskProfile.ownsNewVehicle()) {
			this.addAutoRisk(1);
		}
	}

	public int getAutoRisk() {
		return this.autoRisk;
	}

	public void addAutoRisk(int score) {
		this.autoRisk += score;
	}
	
	public void deductAutoRisk(int score) {
		this.autoRisk -= score;
	}

	public int getDisabilityRisk() {
		return this.disabilityRisk;
	}

	public void addDisabilityRisk(int score) {
		this.disabilityRisk += score;
	}
	
	public void deductDisabilityRisk(int score) {
		this.disabilityRisk -= score;
	}

	public int getHomeRisk() {
		return this.homeRisk;
	}

	public void addHomeRisk(int score) {
		this.homeRisk += score;
	}
	
	public void deductHomeRisk(int score) {
		this.homeRisk -= score;
	}

	public int getLifeRisk() {
		return this.lifeRisk;
	}

	public void addLifeRisk(int score) {
		this.lifeRisk += score;
	}
	
	public void deductLifeRisk(int score) {
		this.lifeRisk -= score;
	}
	
}
