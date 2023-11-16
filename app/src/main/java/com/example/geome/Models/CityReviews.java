package com.example.geome.Models;

import java.util.Date;

public class CityReviews {
    public int id;
    public int idUser;
    public int idCity;
    public String datePublication;
    public String comment;
    public String rating;
    public String photo;

    public CityReviews(){}

    public CityReviews(int idUser, int idCity, String datePublication, String comment, String rating, String photo) {
        this.idUser = idUser;
        this.idCity = idCity;
        this.datePublication = datePublication;
        this.comment = comment;
        this.rating = rating;
        this.photo = photo;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
