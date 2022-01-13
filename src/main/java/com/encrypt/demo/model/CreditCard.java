package com.encrypt.demo.model;

public class CreditCard {
    private String number;
    private String securityCode;
    
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", securityCode=" + securityCode + "]";
	}
}