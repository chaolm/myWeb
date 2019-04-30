package com.dripop.promotion.dto;

import java.io.Serializable;
import java.util.Date;

public class SelectCustomerDto implements Serializable {

    private Date registerDate;

    private Date registerDateTwo;

    private String phoneNo;

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getRegisterDateTwo() {
        return registerDateTwo;
    }

    public void setRegisterDateTwo(Date registerDateTwo) {
        this.registerDateTwo = registerDateTwo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
