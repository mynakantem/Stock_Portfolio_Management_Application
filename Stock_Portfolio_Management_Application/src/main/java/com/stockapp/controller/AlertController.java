package com.stockapp.controller;

import com.stockapp.dto.AlertDTO;
import com.stockapp.service.AlertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // it tells Spring that this class handles REST API requests
@RequestMapping("/api/alerts") // all APIs inside this controller will start with /api/alerts
public class AlertController {
	
	@Autowired  // automatically injects the AlertService class
	private AlertService alertService;

	 // this API returns all alerts stored in the database
	@GetMapping
	public List<AlertDTO> getAllAlerts() {
	    return alertService.getAllAlerts();
	}

	// this API creates a new alert using the data sent in the request body
	@PostMapping
	public AlertDTO createAlert(@Valid @RequestBody AlertDTO dto) {
	    return alertService.createAlert(dto);
	}

	// this API updates an existing alert by ID using the new data
	@PutMapping("/{id}")
	public AlertDTO updateAlert(@PathVariable Long id, @Valid @RequestBody AlertDTO dto) {
	    return alertService.updateAlert(id, dto);
	}

	// this API deletes an alert from the database by its ID
	@DeleteMapping("/{id}")
	public void deleteAlert(@PathVariable Long id) {
	    alertService.deleteAlert(id);
	}

	// This API manually checks if any alerts need to be triggered based on current price
	@GetMapping("/evaluate")
	public void evaluateAlerts() {
	    alertService.evaluateAlerts();
	}
}