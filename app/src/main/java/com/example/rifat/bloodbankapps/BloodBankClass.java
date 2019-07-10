package com.example.rifat.bloodbankapps;

public class BloodBankClass {

    private String District,OrganizationName,Address,Open,Phone;

    public BloodBankClass(String organizationName, String address, String phone) {
        //District = district;
        OrganizationName = organizationName;
        Address = address;
        //Open = open;
        Phone = phone;
    }
    public BloodBankClass()
    {

    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
