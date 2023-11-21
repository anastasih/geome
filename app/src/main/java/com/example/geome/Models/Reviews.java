package com.example.geome.Models;

public class Reviews {
    public int IdCompany;
    public double Location;
    public double Service;
    public double Availability;
    public double Comfort;
    public String userComment;
    public Reviews(){}

    public Reviews(int idCompany, double location, double service, double availability, double comfort, String userComment) {
        IdCompany = idCompany;
        Location = location;
        Service = service;
        Availability = availability;
        Comfort = comfort;
        this.userComment = userComment;
    }

    public int getIdCompany() {
        return IdCompany;
    }

    public void setIdCompany(int idCompany) {
        IdCompany = idCompany;
    }

    public double getLocation() {
        return Location;
    }

    public void setLocation(double location) {
        Location = location;
    }

    public double getService() {
        return Service;
    }

    public void setService(double service) {
        Service = service;
    }

    public double getAvailability() {
        return Availability;
    }

    public void setAvailability(double availability) {
        Availability = availability;
    }

    public double getComfort() {
        return Comfort;
    }

    public void setComfort(double comfort) {
        Comfort = comfort;
    }
    public String getUserComment() {
        return userComment;
    }
    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
