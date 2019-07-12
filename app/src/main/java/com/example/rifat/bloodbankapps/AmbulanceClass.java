package com.example.rifat.bloodbankapps;

public class AmbulanceClass {

    private String OrganizationName,Address,Phone;

    public AmbulanceClass(String organizationName, String address, String phone) {
        OrganizationName = organizationName;
        Address = address;
        Phone = phone;
    }
    public AmbulanceClass(){}

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
