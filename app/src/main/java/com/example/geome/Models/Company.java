package com.example.geome.Models;

import java.io.Serializable;

public class Company implements Serializable {
    public int companyId;
    public String companyName;
    public String idCategory;
    public String companyPhoto;
    public String companyDescription;
    public String companyRating;
    public String email;
    public String phone;

    public Company(){}
    public Company(int companyId, String companyName, String idCategory, String companyPhoto,
                   String companyDescription, String companyRating, String email, String phone) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.idCategory = idCategory;
        this.companyPhoto = companyPhoto;
        this.companyDescription = companyDescription;
        this.companyRating = companyRating;
        this.email = email;
        this.phone = phone;
    }
    public int getCompanyId() {
        return companyId;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getCompanyPhoto() {
        return companyPhoto;
    }

    public void setCompanyPhoto(String companyPhoto) {
        this.companyPhoto = companyPhoto;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
    public String getCompanyRating() {
        return companyRating;
    }

    public void setCompanyRating(String companyRating) {
        this.companyRating = companyRating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
