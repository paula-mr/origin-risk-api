package com.origin.paula.risk.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.origin.paula.risk.domain.House;
import com.origin.paula.risk.domain.InsurancePlan;
import com.origin.paula.risk.domain.RiskProfile;
import com.origin.paula.risk.domain.Vehicle;
import com.origin.paula.risk.enums.InsuranceType;
import com.origin.paula.risk.enums.MaritalStatus;
import com.origin.paula.risk.enums.OwnershipStatus;

@SpringBootTest
public class RiskServiceTest {

	private final int FABRICATION_YEAR_NEW_VEHICLE = Calendar.getInstance().get(Calendar.YEAR)
			- Vehicle.YEARS_CONSIDERED_NEW;
	private final int FABRICATION_YEAR_OLD_VEHICLE = Calendar.getInstance().get(Calendar.YEAR)
			- Vehicle.YEARS_CONSIDERED_NEW - 1;

	private final RiskService riskService = new RiskService();

	@Test(expected = IllegalArgumentException.class)
	public void nullRiskProfile() throws InvalidAttributesException {
		riskService.calculate(null);
	}

	@Test(expected = InvalidAttributesException.class)
	public void nullAttributes() throws InvalidAttributesException {
		riskService.calculate(new RiskProfile());
	}

	@Test(expected = InvalidAttributesException.class)
	public void invalidAttributes() throws InvalidAttributesException {
		riskService.calculate(invalidRiskProfile());
	}

	@Test(expected = InvalidAttributesException.class)
	public void nullHouseOwnershipStatus() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileNullHouseOwnershipStatus());
	}

	@Test(expected = InvalidAttributesException.class)
	public void invalidHouse() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileInvalidHouseOwnershipStatus());
	}

	@Test(expected = InvalidAttributesException.class)
	public void invalidMaritalStatus() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileInvalidMaritalStatus());
	}

	@Test(expected = InvalidAttributesException.class)
	public void invalidRiskQuestions() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileInvalidRiskQuestions());
	}

	@Test(expected = InvalidAttributesException.class)
	public void nullVehicleFabricationYear() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileNullVehicleFabricationYear());
	}

	@Test(expected = InvalidAttributesException.class)
	public void invalidVehicleFabricationYear() throws InvalidAttributesException {
		riskService.calculate(validRiskProfileInvalidVehicleFabricationYear());
	}

	@Test
	public void noIncome() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileNoIncome());
		assertEquals(InsuranceType.INELIGIBLE.getValue(), result.getDisability());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getHome());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getLife());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getAuto());
	}

	@Test
	public void noVehicle() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileNoVehicle());
		assertEquals(InsuranceType.INELIGIBLE.getValue(), result.getAuto());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getHome());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getLife());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getDisability());
	}

	@Test
	public void noHouse() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileNoHouse());
		assertEquals(InsuranceType.INELIGIBLE.getValue(), result.getHome());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getDisability());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getLife());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getAuto());
	}

	@Test
	public void elderly() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileElderly());
		assertEquals(InsuranceType.INELIGIBLE.getValue(), result.getLife());
		assertEquals(InsuranceType.INELIGIBLE.getValue(), result.getDisability());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getAuto());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getHome());
	}

	@Test
	public void economicResult() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileEconomicResult());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getLife());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getDisability());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getHome());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getAuto());
	}

	@Test
	public void youngAdult() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileYoungAdult());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getLife());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getDisability());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getHome());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getAuto());
	}

	@Test
	public void adult() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileAdult());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getLife());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getDisability());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getHome());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getAuto());
	}

	@Test
	public void highIncome() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileHighIncome());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getLife());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getDisability());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getHome());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getAuto());
	}

	@Test
	public void mortgagedHouse() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileMortgagedHouse());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getLife());
		assertEquals(InsuranceType.RESPONSIBLE.getValue(), result.getDisability());
		assertEquals(InsuranceType.RESPONSIBLE.getValue(), result.getHome());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getAuto());
	}

	@Test
	public void hasDependents() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileHasDependents());
		assertEquals(InsuranceType.RESPONSIBLE.getValue(), result.getLife());
		assertEquals(InsuranceType.RESPONSIBLE.getValue(), result.getDisability());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getHome());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getAuto());
	}

	@Test
	public void isMarriedHighRisk() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileIsMarriedHighRisk());
		assertEquals(InsuranceType.RESPONSIBLE.getValue(), result.getLife());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getDisability());
		assertEquals(InsuranceType.RESPONSIBLE.getValue(), result.getHome());
		assertEquals(InsuranceType.RESPONSIBLE.getValue(), result.getAuto());
	}

	@Test
	public void isMarriedLowRisk() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileIsMarriedLowRisk());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getLife());
		assertEquals(InsuranceType.ECONOMIC.getValue(), result.getDisability());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getHome());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getAuto());
	}

	@Test
	public void newVehicle() throws InvalidAttributesException {
		InsurancePlan result = riskService.calculate(validRiskProfileNewVehicle());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getLife());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getDisability());
		assertEquals(InsuranceType.REGULAR.getValue(), result.getHome());
		assertEquals(InsuranceType.RESPONSIBLE.getValue(), result.getAuto());
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

	private RiskProfile validRiskProfileNoIncome() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(false);
		riskQuestions.add(false);
		riskQuestions.add(false);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(20);
		riskProfile.setIncome(0);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileNoHouse() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(false);
		riskQuestions.add(false);
		riskQuestions.add(false);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(20);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileNoVehicle() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(false);
		riskQuestions.add(false);
		riskQuestions.add(false);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(20);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileElderly() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(false);
		riskQuestions.add(false);
		riskQuestions.add(false);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(RiskProfile.MIN_AGE_ELDERLY);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileEconomicResult() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(false);
		riskQuestions.add(false);
		riskQuestions.add(false);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(25);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileYoungAdult() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(false);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(RiskProfile.MAX_AGE_YOUNG_ADULT);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileAdult() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(true);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(RiskProfile.MAX_AGE_ADULT);
		riskProfile.setIncome(70000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileHighIncome() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(true);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(45);
		riskProfile.setIncome(RiskProfile.MIN_HIGH_INCOME);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileMortgagedHouse() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(false);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(45);
		riskProfile.setIncome(75000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.MORTGAGED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileHasDependents() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(false);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(45);
		riskProfile.setIncome(75000);
		riskProfile.setQuantityDependents(2);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileIsMarriedHighRisk() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(true);
		riskQuestions.add(true);
		riskQuestions.add(true);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(45);
		riskProfile.setIncome(75000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.MARRIED.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileIsMarriedLowRisk() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(false);
		riskQuestions.add(false);
		riskQuestions.add(true);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(45);
		riskProfile.setIncome(75000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.MARRIED.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_OLD_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}

	private RiskProfile validRiskProfileNewVehicle() {
		List<Boolean> riskQuestions = new ArrayList<>();
		riskQuestions.add(false);
		riskQuestions.add(true);
		riskQuestions.add(true);

		RiskProfile riskProfile = new RiskProfile();
		riskProfile.setAge(45);
		riskProfile.setIncome(75000);
		riskProfile.setQuantityDependents(0);
		riskProfile.setHouseOwned(new House(OwnershipStatus.OWNED.getValue()));
		riskProfile.setMaritalStatus(MaritalStatus.SINGLE.getValue());
		riskProfile.setVehicleOwned(new Vehicle(FABRICATION_YEAR_NEW_VEHICLE));
		riskProfile.setRiskQuestions(riskQuestions);
		return riskProfile;
	}
}
