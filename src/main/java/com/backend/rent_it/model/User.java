package com.backend.rent_it.model;

public class User {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String add1;       // Address line 1
    private String add2;       // Address line 2
    private String state;      // State
    private String district;   // District
    private String pincode;    // Pincode

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getAdd1() { return add1; }
    public void setAdd1(String add1) { this.add1 = add1; }
    
    public String getAdd2() { return add2; }
    public void setAdd2(String add2) { this.add2 = add2; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    
    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }
}
