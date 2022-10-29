package com.example.amenite.Admin;

public class CustomerList {
    String Name,Phone_Number,Address ;

    CustomerList()
    {

    }

    CustomerList(String Name,String Phone_Number,String Address)
    {
        this.Name=Name;
        this.Phone_Number=Phone_Number;
        this.Address=Address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
