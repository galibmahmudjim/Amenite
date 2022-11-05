package com.example.amenite.Employee.AppointmentReq;

public class AppointmentReqList {
    String Request_Time, Request_Date, Appointment_Id, Appointment_Status, Service,Choose_Service, Appointment_Time, Appointment_Date;
    String Email;
    public AppointmentReqList()
    {

    }
    public AppointmentReqList(String request_Time, String request_Date, String appointment_Id, String appointment_status, String service, String choose_Service, String appointment_Time, String appointment_Date, String email) {
        Request_Time = request_Time;
        Request_Date = request_Date;
        Appointment_Id = appointment_Id;
        Appointment_Status = appointment_status;
        Service = service;
        Choose_Service = choose_Service;
        Appointment_Time = appointment_Time;
        Appointment_Date = appointment_Date;
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRequest_Time() {
        return Request_Time;
    }

    public void setRequest_Time(String request_Time) {
        Request_Time = request_Time;
    }

    public String getRequest_Date() {
        return Request_Date;
    }

    public void setRequest_Date(String request_Date) {
        Request_Date = request_Date;
    }

    public String getAppointment_Id() {
        return Appointment_Id;
    }

    public void setAppointment_Id(String appointment_Id) {
        Appointment_Id = appointment_Id;
    }

    public String getAppointment_Status() {
        return Appointment_Status;
    }

    public void setAppointment_Status(String appointment_status) {
        Appointment_Status = appointment_status;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getChoose_Service() {
        return Choose_Service;
    }

    public void setChoose_Service(String choose_Service) {
        Choose_Service = choose_Service;
    }

    public String getAppointment_Time() {
        return Appointment_Time;
    }

    public void setAppointment_Time(String appointment_Time) {
        Appointment_Time = appointment_Time;
    }

    public String getAppointment_Date() {
        return Appointment_Date;
    }

    public void setAppointment_Date(String appointment_Date) {
        Appointment_Date = appointment_Date;
    }
}
