package com.example.rifat.bloodbankapps;

public class BankClass {

    private String Blood_Bank_Name,Blood_Bank_Number,Blood_Bank_Location;

    public BankClass(String blood_Bank_Name, String blood_Bank_Number, String blood_Bank_Location) {
        Blood_Bank_Name = blood_Bank_Name;
        Blood_Bank_Number = blood_Bank_Number;
        Blood_Bank_Location = blood_Bank_Location;
    }

    public String getBlood_Bank_Name() {
        return Blood_Bank_Name;
    }

    public void setBlood_Bank_Name(String blood_Bank_Name) {
        Blood_Bank_Name = blood_Bank_Name;
    }

    public String getBlood_Bank_Number() {
        return Blood_Bank_Number;
    }

    public void setBlood_Bank_Number(String blood_Bank_Number) {
        Blood_Bank_Number = blood_Bank_Number;
    }

    public String getBlood_Bank_Location() {
        return Blood_Bank_Location;
    }

    public void setBlood_Bank_Location(String blood_Bank_Location) {
        Blood_Bank_Location = blood_Bank_Location;
    }
}
