package com.example.rifat.bloodbankapps;

public class BloodBankClass {

    private String District,OrganizationName,Address,Open,Phone;

    public BloodBankClass(String district, String organizationName, String address, String open, String phone) {
        District = district;
        OrganizationName = organizationName;
        Address = address;
        Open = open;
        Phone = phone;
    }
    public BloodBankClass()
    {

    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
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

    public String getOpen() {
        return Open;
    }

    public void setOpen(String open) {
        Open = open;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
