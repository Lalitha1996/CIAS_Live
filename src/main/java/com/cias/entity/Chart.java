package com.cias.entity;

import java.math.BigDecimal;

public class Chart 
{
	
	private BigDecimal y;
	private String label;
	
	
	
	public Chart(String label,BigDecimal y){
		this.y=y;	
		this.label=label;
	}
	
	
	
	
	public BigDecimal getY() {
		return y;
	}
	public void setY(BigDecimal y) {
		this.y = y;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}





}
