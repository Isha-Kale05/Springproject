package com.mbi.order;

public class Product  extends Order{
	private String newProduct;
	private Long barCode;
	
	public Product() {
		
	}
	
	public Product(String newProduct, Long barCode)
	{
		this.barCode=barCode;
		this.newProduct=newProduct;
	}
	
	public String getNewProduct() {
		return newProduct;
	}
	
	public void setNewProduct(String newProduct) {
		this.newProduct=newProduct;
	}
	
	public Long getBarCode() {
		return barCode;
	}
	public void setBarCode(Long barCode) {
		this.barCode=barCode;
	}
	
	@Override
	public String toString() {
		return "Product[newProduct="+ newProduct + ",barCode="+ barCode +"]";
	}
}
