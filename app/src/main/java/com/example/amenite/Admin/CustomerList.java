package com.example.amenite.Admin;

public class CustomerList {
    String name,address,phonenumber;

    CustomerList()
    {

    }

    CustomerList(String name,String address,String phonenumber)
    {
        this.name=name;
        this.address=address;
        this.phonenumber=phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phonenumber;
    }

    public void setPhone(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
