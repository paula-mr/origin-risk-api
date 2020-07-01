package com.origin.paula.risk.services;

import java.util.List;
import java.util.Set;

import javax.naming.directory.InvalidAttributesException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.origin.paula.risk.domain.House;
import com.origin.paula.risk.domain.InsurancePlan;
import com.origin.paula.risk.domain.RiskProfile;
import com.origin.paula.risk.domain.Vehicle;
import com.origin.paula.risk.enums.MaritalStatus;
import com.origin.paula.risk.enums.OwnershipStatus;

@Service
public class RiskService {
	private static final int NUMBER_OF_RISK_QUESTIONS = 3;
	private final Logger log;

	public RiskService() {
		this.log = LoggerFactory.getLogger(RiskService.class);
	}

	public InsurancePlan calculate(RiskProfile riskProfile) throws InvalidAttributesException {
		validateRiskProfile(riskProfile);
		
		InsurancePlan insurancePlan = new InsurancePlan();
		insurancePlan.calculate(riskProfile);
		
		return insurancePlan;
	}
	
	private void validateRiskProfile(RiskProfile riskProfile) throws InvalidAttributesException {
		if (riskProfile == null)
			throw new IllegalArgumentException("Risk Profile must be provided.");

		Validator validator = getValidator();
		Set<ConstraintViolation<RiskProfile>> violations = validator.validate(riskProfile);

		if (!CollectionUtils.isEmpty(violations)) {
			violations.forEach(v -> log.error(v.getMessage()));
			throw new InvalidAttributesException("User fields are not valid.");
		}

		validateHouse(riskProfile.getHouseOwned());
		validateMaritalStatus(riskProfile.getMaritalStatus());
		validateRiskQuestions(riskProfile.getRiskQuestions());
		validateVehicle(riskProfile.getVehicleOwned());
	}

	private void validateVehicle(Vehicle vehicle) throws InvalidAttributesException {
		if (vehicle == null)
			return;

		Validator validator = getValidator();
		Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);

		if (!CollectionUtils.isEmpty(violations)) {
			violations.forEach(v -> log.error(v.getMessage()));
			throw new InvalidAttributesException("Vehicle fields are not valid.");
		}

	}

	private void validateRiskQuestions(List<Boolean> riskQuestions) throws InvalidAttributesException {
		if (riskQuestions.size() != NUMBER_OF_RISK_QUESTIONS)
			throw new InvalidAttributesException("Invalid number of risk questions.");

	}

	private void validateMaritalStatus(String maritalStatus) throws InvalidAttributesException {
		if (!MaritalStatus.isValid(maritalStatus))
			throw new InvalidAttributesException("Marital status is not valid.");

	}

	private void validateHouse(House house) throws InvalidAttributesException {
		if (house == null)
			return;

		Validator validator = getValidator();
		Set<ConstraintViolation<House>> violations = validator.validate(house);

		if (!CollectionUtils.isEmpty(violations)) {
			violations.forEach(v -> log.error(v.getMessage()));
			throw new InvalidAttributesException("Home fields are not valid.");
		}

		if (!OwnershipStatus.isValid(house.getOwnershipStatus()))
			throw new InvalidAttributesException("Ownership status is not valid.");

	}

	private Validator getValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}
}
