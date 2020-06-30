package com.origin.paula.risk.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.origin.paula.risk.domain.InsurancePlan;
import com.origin.paula.risk.domain.RiskProfile;

@Service
public class RiskService {
	private final Logger log;

	public RiskService() {
		this.log = LoggerFactory.getLogger(RiskService.class);
	}

	public InsurancePlan calculate(RiskProfile riskProfile) {
		return null;
	}
}
