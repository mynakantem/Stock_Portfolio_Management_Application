package com.stockapp.service;


import com.stockapp.dto.AlertDTO;
import com.stockapp.repository.AlertRepository;
import com.stockapp.model.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockapp.service.StockPriceCache;
import java.math.BigDecimal; 


import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AlertService {
	
	// injecting the Alert repository for interacting with the database. 
	@Autowired
	private AlertRepository alertRepository;
	@Autowired
	private StockPriceCache stockPriceCache;
	
	// this method to get all the alerts from database and return them as DTO
	public List<AlertDTO> getAllAlerts() {
		 List<Alert> alertList = alertRepository.findAll();
	        List<AlertDTO> dtoList = new ArrayList<>();
	        for (Alert alert : alertList) {
	            AlertDTO dto = convertToDTO(alert);
	            dtoList.add(dto);
	        }
	        return dtoList;
	    }
	
	// method to create a new alert
	public AlertDTO createAlert(AlertDTO dto) {
		Alert alert = convertToEntity(dto); // convert DTO to an Entity
		Alert savedAlert = alertRepository.save(alert); // save the alert to the DB
		return convertToDTO(savedAlert); // convert the saved entity back to DTO and return it
	}
	
	 // method to update an existing alert by id
	public AlertDTO updateAlert(Long id, AlertDTO dto) {
		Optional<Alert> optionalAlert = alertRepository.findById(id); // check if alert exists
		if (optionalAlert.isPresent()) {
			Alert alert = optionalAlert.get(); // get the existing alert
			//update fields with the values from DTO
			alert.setSymbol(dto.symbol);
			alert.setAlertType(dto.alertType);
			alert.setThresholdValue(dto.thresholdValue);
			Alert updatedAlert = alertRepository.save(alert); // save the updated alert
            return convertToDTO(updatedAlert); // return the updated alert as dto
		}
		return null; // if not found then return null
	}
	// method to delete 
	public void deleteAlert(Long id) {
		alertRepository.deleteById(id); // removes the alert from database
	}

	// helper method to convert an entity to dto
	private AlertDTO convertToDTO(Alert alert) {
		AlertDTO dto = new AlertDTO();
		dto.id = alert.getId();
		dto.symbol = alert.getSymbol();
		dto.alertType = alert.getAlertType();
		dto.thresholdValue = alert.getThresholdValue();
		return dto;
		}

	// helper method to convert an dto to entity
	private Alert convertToEntity(AlertDTO dto) {
		Alert alert = new Alert();
		alert.setId(dto.id);
		alert.setSymbol(dto.symbol);
		alert.setAlertType(dto.alertType);
		alert.setThresholdValue(dto.thresholdValue);
		return alert;
	}
	
	// method to evaluate alerts by checking if current price crosses threshold
	public void evaluateAlerts() {
	    // get all alerts from database
	    List<Alert> alerts = alertRepository.findAll();
	    // loop through each alert
	    for (Alert alert : alerts) {
	        // get current stock price
	        BigDecimal currentPrice = stockPriceCache.getPrice(alert.getSymbol());
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
	        // if condition is true, print to console 
	        if (isTriggered) {
	            System.out.println("ALERT TRIGGERED for " + alert.getSymbol() + " | Type: " + alert.getAlertType() + " | Price: " + currentPrice + " | Threshold: " + alert.getThresholdValue());
	        }
	    }
	}
}
