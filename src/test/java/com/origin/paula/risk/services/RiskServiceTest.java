package com.origin.paula.risk.services;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.origin.paula.risk.domain.House;
import com.origin.paula.risk.domain.RiskProfile;
import com.origin.paula.risk.domain.Vehicle;
import com.origin.paula.risk.enums.MaritalStatus;
import com.origin.paula.risk.enums.OwnershipStatus;

@SpringBootTest
public class RiskServiceTest {
	
	private final RiskService riskService = new RiskService();
	
	@Test(expected=IllegalArgumentException.class)
	public void nullRiskProfile() throws InvalidAttributesException {
		riskService.calculate(null);
	}
	
	@Test(expected=InvalidAttributesException.class)
	public void nullAttributes() throws InvalidAttributesException {
		riskService.calculate(new RiskProfile());
	}
	
	@Test(expected=InvalidAttributesException.class)
	public void invalidAttributes() throws InvalidAttributesException {
		riskService.calculate(invalidRiskProfile());
	}
	
	
	@Test(expected=InvalidAttributesException.class)
	public void nullHouseOwnershipStatus() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileNullHouseOwnershipStatus());
	}
	
	@Test(expected=InvalidAttributesException.class)
	public void invalidHouse() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileInvalidHouseOwnershipStatus());
	}
	
	@Test(expected=InvalidAttributesException.class)
	public void invalidMaritalStatus() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileInvalidMaritalStatus());
	}
	
	@Test(expected=InvalidAttributesException.class)
	public void invalidRiskQuestions() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileInvalidRiskQuestions());
	}
	
	@Test(expected=InvalidAttributesException.class)
	public void nullVehicleFabricationYear() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileNullVehicleFabricationYear());
	}
	
	@Test(expected=InvalidAttributesException.class)
	public void invalidVehicleFabricationYear() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileInvalidVehicleFabricationYear());
	}
	
	/*------------------- mock objects ------------------------------*/
	private RiskProfile invalidRiskProfile() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(true);
		
		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(-1);
		riskProfile.setIncome(-1);
		riskProfile.setQuantityDependents(-1);
		riskProfile.setMaritalStatus(MaritalStatus.MARRIED.getValue());
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}
	
	private RiskProfile validRiskProfileNullHouseOwnershipStatus() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(true);
		
		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(20);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House());
		riskProfile.setMaritalStatus(MaritalStatus.MARRIED.getValue());
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}
	
	private RiskProfile validRiskProfileInvalidHouseOwnershipStatus() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(true);
		
		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(20);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House("invalid"));
		riskProfile.setMaritalStatus(MaritalStatus.MARRIED.getValue());
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}
	
	private RiskProfile validRiskProfileInvalidMaritalStatus() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(true);
		
		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(20);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setMaritalStatus("invalid");
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}
	
	private RiskProfile validRiskProfileInvalidRiskQuestions() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		
		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(20);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.MORTGAGED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.MARRIED.getValue());
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}
	
	private RiskProfile validRiskProfileNullVehicleFabricationYear() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(true);
		
		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(20);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.MORTGAGED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.MARRIED.getValue());
		riskProfile.setRiskQuestions(riskQuestions);
		riskProfile.setVehicleOwned(new Vehicle());
		return riskProfile;
	}
	
	private RiskProfile validRiskProfileInvalidVehicleFabricationYear() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(true);
		
		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(20);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.MORTGAGED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.MARRIED.getValue());
		riskProfile.setRiskQuestions(riskQuestions);
		riskProfile.setVehicleOwned(new Vehicle(-1));
		return riskProfile;
	}
}
