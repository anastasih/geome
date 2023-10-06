package com.example.geome.Models;

public class CompanyDetails {
    public String companyAddress;
    public String companyPhoto;
    public String companyCityId;
    public CompanyDetails(){}
    public CompanyDetails(String companyAddress, String companyPhoto, String companyCityId) {
        this.companyAddress = companyAddress;
        this.companyPhoto = companyPhoto;
        this.companyCityId = companyCityId;
    }
    public String getCompanyAddress() {
        return companyAddress;
    }
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }
    public String getCompanyPhoto() {
        return companyPhoto;
    }
    public void setCompanyPhoto(String companyPhoto) {
        this.companyPhoto = companyPhoto;
    }
    public String getCompanyCityId() {
        return companyCityId;
    }
    public void setCompanyCityId(String companyCityId) {
        this.companyCityId = companyCityId;
    }
}
