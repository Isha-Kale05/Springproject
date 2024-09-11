package com.mbi.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
 
@Entity
@Table(name="tabledetails")
public class TableAssignmentDetails {

	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String tableNumber;
	private String restaurantName;
	private String restaurantStatus;
	
	public TableAssignmentDetails() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantStatus() {
		return restaurantStatus;
	}

	public void setRestaurantStatus(String restaurantStatus) {
		this.restaurantStatus = restaurantStatus;
	}

}
