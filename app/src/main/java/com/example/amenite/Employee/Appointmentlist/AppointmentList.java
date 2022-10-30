package com.example.amenite.Employee.Appointmentlist;

public class AppointmentList {
    String Request_Time, Request_Date, Appointment_Id, Status, Service,Choose_Service, Appointment_Time, Appointment_Date;

    public AppointmentList()
    {

    }
    public AppointmentList(String request_Time, String request_Date, String appointment_Id, String status, String service, String choose_Service, String appointment_Time, String appointment_Date) {
        Request_Time = request_Time;
        Request_Date = request_Date;
        Appointment_Id = appointment_Id;
        Status = status;
        Service = service;
        Choose_Service = choose_Service;
        Appointment_Time = appointment_Time;
        Appointment_Date = appointment_Date;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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
