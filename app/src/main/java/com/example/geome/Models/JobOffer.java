package com.example.geome.Models;

import java.util.Date;

public class JobOffer {
    private int Id;
    private int id_company;
    private String company_name;
    private String offer_name;
    private String offer_description;
    private String salary;
    private String email;
    private String phone;
    private String requirements;
    private String category;
    private String companyCategory;
    private String keywords;
    public JobOffer(int id, int id_company, String company_name, String offer_name,
                    String offer_description, String salary, String email, String phone,
                    String requirements, String category, String companyCategory, String keywords) {
        Id = id;
        this.id_company = id_company;
        this.company_name = company_name;
        this.offer_name = offer_name;
        this.offer_description = offer_description;
        this.salary = salary;
        this.email = email;
        this.phone = phone;
        this.requirements = requirements;
        this.category = category;
        this.companyCategory = companyCategory;
        this.keywords = keywords;
    }

    public int getId_company() {
        return id_company;
    }

    public void setId_company(int id_company) {
        this.id_company = id_company;
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

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
