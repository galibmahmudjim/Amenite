package com.example.amenite.Admin.JobApplication;

public class JobApplicationList {
    String Name,  Email,  Address, Phone_Number, UserID, Date_of_Birth,Username;

    public JobApplicationList()
    {

    }

    public JobApplicationList(String name, String email, String address, String phone_Number, String userID, String date_of_Birth, String username) {
        Name = name;
        Email = email;
        Address = address;
        Phone_Number = phone_Number;
        UserID = userID;
        Date_of_Birth = date_of_Birth;
        Username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDate_of_Birth() {
        return Date_of_Birth;
    }

    public void setDate_of_Birth(String date_of_Birth) {
        Date_of_Birth = date_of_Birth;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
