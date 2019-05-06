package com.example.rifat.bloodbankapps;

public class DonorClass {


    private String Donor_rn,Donor_key,Donor_name,Donor_bloodGroup,Donor_phoneNumber,Donor_email,Donor_district,Donor_gender;


    public DonorClass(String donor_rn, String donor_key, String donor_name, String donor_bloodGroup, String donor_phoneNumber, String donor_email, String donor_district, String donor_gender) {

        this.Donor_rn = donor_rn;
        this.Donor_key = donor_key;
        this.Donor_name = donor_name;
        this.Donor_bloodGroup = donor_bloodGroup;
        this.Donor_phoneNumber = donor_phoneNumber;
        this.Donor_email = donor_email;
        this.Donor_district = donor_district;
        this.Donor_gender = donor_gender;
    }
    public DonorClass()
    {

    }

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

    public String getDonor_email() {
        return Donor_email;
    }

    public void setDonor_email(String donor_email) {
        Donor_email = donor_email;
    }

    public String getDonor_district() {
        return Donor_district;
    }

    public void setDonor_district(String donor_district) {
        Donor_district = donor_district;
    }

    public String getDonor_gender() {
        return Donor_gender;
    }

    public void setDonor_gender(String donor_gender) {
        Donor_gender = donor_gender;
    }
}
