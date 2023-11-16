package com.example.geome.Models;

public class AppliedVacancies {
    public int id;
    public int idVacancy;
    public int idUser;
    public String experience;
    public String whyYou;
    public String languages;
    public String employment;
    public String filesNames;
    public AppliedVacancies(){}
    public int getId() {
        return id;
    }
    public int getIdVacancy() {
        return idVacancy;
    }

    public void setIdVacancy(int idVacancy) {
        this.idVacancy = idVacancy;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWhyYou() {
        return whyYou;
    }

    public void setWhyYou(String whyYou) {
        this.whyYou = whyYou;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getFilesNames() {
        return filesNames;
    }

    public void setFilesNames(String filesNames) {
        this.filesNames = filesNames;
    }
}
