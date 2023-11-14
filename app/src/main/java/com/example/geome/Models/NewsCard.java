package com.example.geome.Models;

import java.util.Date;

public class NewsCard {
    private int Id;
    private int id_company;
    private String image;
    private String description;
    private Date publication_time;

    public NewsCard(){}
    public NewsCard(int id_company, String image, String description, Date publication_time) {
        this.id_company = id_company;
        this.image = image;
        this.description = description;
        this.publication_time = publication_time;
    }

    public NewsCard(int id, int id_company, String image, String description, Date publication_time) {
        Id = id;
        this.id_company = id_company;
        this.image = image;
        this.description = description;
        this.publication_time = publication_time;
    }

//    public void setId(int id) {
//        Id = id;
//    }

    public void setId_company(int id_company) {
        this.id_company = id_company;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublication_time(Date publication_time) {
        this.publication_time = publication_time;
    }

    public int getId() {
        return Id;
    }

    public int getId_company() {
        return id_company;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public Date getPublication_time() {
        return publication_time;
    }
}
