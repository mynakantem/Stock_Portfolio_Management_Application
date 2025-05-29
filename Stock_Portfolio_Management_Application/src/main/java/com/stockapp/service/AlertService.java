package com.stockapp.service;

import com.stockapp.dto.AlertDTO;
import com.stockapp.repository.AlertRepository;
import com.stockapp.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockapp.service.StockPriceCache;
import com.stockapp.exception.AlertNotFoundException;
import java.math.BigDecimal; 

import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AlertService {
	// logger for tracking actions
	private static final Logger logger = LoggerFactory.getLogger(AlertService.class);
	// injecting the Alert repository for interacting with the database. 
	@Autowired
	private AlertRepository alertRepository;
	@Autowired
	private StockPriceCache stockPriceCache;
	
	//  this method retrives all the alerts from database and converts them into AlertDTOs for response.
	public List<AlertDTO> getAllAlerts() {
		logger.info("Fetching all alerts from the database");
		 List<Alert> alertList = alertRepository.findAll();
	        List<AlertDTO> dtoList = new ArrayList<>();
	        for (Alert alert : alertList) {
	            AlertDTO dto = convertToDTO(alert);
	            dtoList.add(dto);
	        }
	        return dtoList;
	    }
	
	// this method to create a new alert from AlertDTO and stores them in database
	public AlertDTO createAlert(AlertDTO dto) {
		logger.info("Creating alert for symbol: {}", dto.getSymbol());
		Alert alert = convertToEntity(dto); // convert DTO to an Entity
		Alert savedAlert = alertRepository.save(alert); // save the alert to the DB
		return convertToDTO(savedAlert); // convert the saved entity back to DTO and return it
	}
	
	// this method updates an existing alert by ID using the values from AlertDTO.
	// if the alert with the given ID doesn't exist, it throws AlertNotFoundException.
	public AlertDTO updateAlert(Long id, AlertDTO dto) {
	    // Log that the update operation is starting
	    logger.info("Updating alert with ID: {}", id);

	    // find the alert by ID, if not found, throws a custom exception and logs a warning
	    Alert alert = alertRepository.findById(id)
	        .orElseThrow(() -> {
	            logger.warn("Alert not found with ID: {}", id);
	            return new AlertNotFoundException("Alert not found with ID: {}" + id);
	        });
	    
	    
	    // updates the alert entity fields with the values from the DTO
	    alert.setSymbol(dto.getSymbol());
	    alert.setAlertType(dto.getAlertType());
	    alert.setThresholdValue(dto.getThresholdValue());


	    // save the updated alert back to the database
	    Alert updatedAlert = alertRepository.save(alert);

	    // convert the updated entity back to DTO and return it
	    return convertToDTO(updatedAlert);
	}


	// this method deletes the alert for given id 
	public void deleteAlert(Long id) {
		logger.info("Deleting alert with ID: {}", id);
		alertRepository.deleteById(id); // removes the alert from database
	}

	// helper method to convert an entity to dto
	private AlertDTO convertToDTO(Alert alert) {
		AlertDTO dto = new AlertDTO();
		dto.setId(alert.getId());
		dto.setSymbol(alert.getSymbol());
		dto.setAlertType(alert.getAlertType());
		dto.setThresholdValue(alert.getThresholdValue());
		return dto;
		}

	// helper method to convert an dto to entity
	private Alert convertToEntity(AlertDTO dto) {
		Alert alert = new Alert();
		alert.setId(dto.getId());
		alert.setSymbol(dto.getSymbol());
		alert.setAlertType(dto.getAlertType());
		alert.setThresholdValue(dto.getThresholdValue());
		return alert;
	}
	
	// this method evaluates all alerts and checks if current stock price crosses the threshold value for triggering
	public void evaluateAlerts() {
		logger.info("Evaluating alerts...");
	    // get all alerts from database
	    List<Alert> alerts = alertRepository.findAll();
	    // loop through each alert
	    for (Alert alert : alerts) {
	        // get current stock price
	        BigDecimal currentPrice = stockPriceCache.getPrice(alert.getSymbol());
	        if (currentPrice == null) {
                logger.error("Price for symbol {} is null; skipping evaluation.", alert.getSymbol());
                continue;
            }
	        boolean isTriggered = false;
	        // check if alert condition is met
	        if (alert.getAlertType().equals("Price Above") &&
	            currentPrice != null &&
	            currentPrice.doubleValue() > alert.getThresholdValue()) {
	            isTriggered = true;
	        } else if (alert.getAlertType().equals("Price Below") &&
	                   currentPrice != null &&
	                   currentPrice.doubleValue() < alert.getThresholdValue()) {
	            isTriggered = true;
	        }
	        // if condition is true, then it prints to console 
	        if (isTriggered) {
	        	logger.info("ALERT TRIGGERED for {} | Type: {} | Price: {} | Threshold: {}", alert.getSymbol(), alert.getAlertType(), currentPrice, alert.getThresholdValue());
	            System.out.println("ALERT TRIGGERED for " + alert.getSymbol() + " | Type: " + alert.getAlertType() + " | Price: " + currentPrice + " | Threshold: " + alert.getThresholdValue());
	        }
	    }
	}
}