package com.example.geome.Models;

public class Company {
    public int companyId;
    public String companyName;
    public String idCategory;
    public String companyPhoto;
    public String companyDescription;
    public String companyRating;

    public Company(){}
    public Company(int companyId, String companyName, String idCategory, String companyPhoto, String companyDescription, String companyRating) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.idCategory = idCategory;
        this.companyPhoto = companyPhoto;
        this.companyDescription = companyDescription;
        this.companyRating = companyRating;
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
}
