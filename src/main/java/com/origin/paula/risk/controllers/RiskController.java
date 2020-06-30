package com.origin.paula.risk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.origin.paula.risk.services.RiskService;

@RestController
@RequestMapping("/risk")
public class RiskController {

	private final Logger log;
	private final RiskService riskService;

	public RiskController(RiskService riskService) {
		this.riskService = riskService;
		this.log = LoggerFactory.getLogger(RiskController.class);
	}

	@PostMapping(value = "/calculate")
	public ResponseEntity<Object> calculate() {
		return ResponseEntity.ok(riskService.calculate(null));
	}
}
