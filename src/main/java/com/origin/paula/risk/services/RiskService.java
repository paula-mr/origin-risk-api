package com.origin.paula.risk.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RiskService {
	private final Logger log;

	public RiskService() {
		this.log = LoggerFactory.getLogger(RiskService.class);
	}

	public Object calculate(Object riskProfile) {
		return null;
	}
}
