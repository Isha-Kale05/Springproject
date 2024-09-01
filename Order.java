package com.mbi.order;

import java.util.Objects;

public class Order {
	private int orderID;
	private String productName;
	private String productCode;

	// Constructor
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(int orderID, String productName, String productCode) {
		this.orderID = orderID;
		this.productName = productName;
		this.productCode = productCode;
	}

	// Getters and Setters
	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", productName=" + productName + ", productCode=" + productCode + "]";
	}
}