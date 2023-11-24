package com.example.geome.Models;

import java.util.Date;

public class Payment {
    public int id;
    public int userId;
    public int idCompany;
    public Date date;
    public String typePayment;
    public double amount;

    public Payment(int userId, int idCompany, Date date, String typePayment, double amount) {
        this.userId = userId;
        this.idCompany = idCompany;
        this.date = date;
        this.typePayment = typePayment;
        this.amount = amount;
    }
    public Payment(int id, int userId, int idCompany, Date date, String typePayment, double amount) {
        this.id = id;
        this.userId = userId;
        this.idCompany = idCompany;
        this.date = date;
        this.typePayment = typePayment;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTypePayment() {
        return typePayment;
    }

    public void setTypePayment(String typePayment) {
        this.typePayment = typePayment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
