package com.example.geome.Models;

public class JobOfferServices {

    private int Id;
    private String company_name;
    private String offer_name;
    private String offer_description;
    private String address;
    private String city_id;
    private String salary;
    private String companyCategory;
    private String keywords;
    private String icon;
    private String link;

    public JobOfferServices(int id, String company_name, String offer_name, String offer_description,
                            String address, String city_id, String salary, String companyCategory, String keywords, String icon, String link) {
        Id = id;
        this.company_name = company_name;
        this.offer_name = offer_name;
        this.offer_description = offer_description;
        this.address = address;
        this.city_id = city_id;
        this.salary = salary;
        this.companyCategory = companyCategory;
        this.keywords = keywords;
        this.icon = icon;
        this.link = link;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getOffer_name() {
        return offer_name;
    }

    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
    }

    public String getOffer_description() {
        return offer_description;
    }

    public void setOffer_description(String offer_description) {
        this.offer_description = offer_description;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(String companyCategory) {
        this.companyCategory = companyCategory;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
}
