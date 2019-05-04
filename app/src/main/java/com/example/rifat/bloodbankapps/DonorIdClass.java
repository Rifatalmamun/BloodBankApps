package com.example.rifat.bloodbankapps;

public class DonorIdClass {

    private String Donor_id,Donor_key,Donor_phone_number;

    public DonorIdClass(String donor_id, String donor_key, String donor_phone_number) {
        Donor_id = donor_id;
        Donor_key = donor_key;
        Donor_phone_number = donor_phone_number;
    }

    public String getDonor_id() {
        return Donor_id;
    }

    public void setDonor_id(String donor_id) {
        Donor_id = donor_id;
    }

    public String getDonor_key() {
        return Donor_key;
    }

    public void setDonor_key(String donor_key) {
        Donor_key = donor_key;
    }

    public String getDonor_phone_number() {
        return Donor_phone_number;
    }

    public void setDonor_phone_number(String donor_phone_number) {
        Donor_phone_number = donor_phone_number;
    }
}
