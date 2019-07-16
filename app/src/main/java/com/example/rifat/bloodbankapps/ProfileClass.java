package com.example.rifat.bloodbankapps;

public class ProfileClass {

    private String Donor_rn,Donor_key,Donor_name,Donor_bloodGroup,Donor_phoneNumber,Donor_district,Donor_department,Donor_session,Donor_lastDonationDate;

    public ProfileClass(String donor_rn, String donor_key, String donor_name, String donor_bloodGroup, String donor_phoneNumber, String donor_district, String donor_department, String donor_session, String donor_lastDonationDate) {
        Donor_rn = donor_rn;
        Donor_key = donor_key;
        Donor_name = donor_name;
        Donor_bloodGroup = donor_bloodGroup;
        Donor_phoneNumber = donor_phoneNumber;
        Donor_district = donor_district;
        Donor_department = donor_department;
        Donor_session = donor_session;
        Donor_lastDonationDate = donor_lastDonationDate;
    }
    ProfileClass(){}

    public String getDonor_rn() {
        return Donor_rn;
    }

    public void setDonor_rn(String donor_rn) {
        Donor_rn = donor_rn;
    }

    public String getDonor_key() {
        return Donor_key;
    }

    public void setDonor_key(String donor_key) {
        Donor_key = donor_key;
    }

    public String getDonor_name() {
        return Donor_name;
    }

    public void setDonor_name(String donor_name) {
        Donor_name = donor_name;
    }

    public String getDonor_bloodGroup() {
        return Donor_bloodGroup;
    }

    public void setDonor_bloodGroup(String donor_bloodGroup) {
        Donor_bloodGroup = donor_bloodGroup;
    }

    public String getDonor_phoneNumber() {
        return Donor_phoneNumber;
    }

    public void setDonor_phoneNumber(String donor_phoneNumber) {
        Donor_phoneNumber = donor_phoneNumber;
    }

    public String getDonor_district() {
        return Donor_district;
    }

    public void setDonor_district(String donor_district) {
        Donor_district = donor_district;
    }

    public String getDonor_department() {
        return Donor_department;
    }

    public void setDonor_department(String donor_department) {
        Donor_department = donor_department;
    }

    public String getDonor_session() {
        return Donor_session;
    }

    public void setDonor_session(String donor_session) {
        Donor_session = donor_session;
    }

    public String getDonor_lastDonationDate() {
        return Donor_lastDonationDate;
    }

    public void setDonor_lastDonationDate(String donor_lastDonationDate) {
        Donor_lastDonationDate = donor_lastDonationDate;
    }
}
