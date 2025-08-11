package com.litmus7.employeemanager.dto;

public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String emailID;
    private String joiningDate;
    private String activeStatus;

    
    public Employee(int id, String firstName, String lastName, String mobileNumber, String emailID, String joiningDate, String activeStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.emailID = emailID;
        this.joiningDate = joiningDate;
        this.activeStatus = activeStatus;
    }

    




	// Getters
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

}

