package com.example.geome.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String userName;
    private String gender;
    private String userPhone;
    private String userPassword;
    //private String userCity;

    private int userCity;
    private int age;
    private boolean accessGeo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserCity() {
        return userCity;
    }

    public void setUserCity(int userCity) {
        this.userCity = userCity;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAccessGeo() {
        return accessGeo;
    }

    public void setAccessGeo(boolean accessGeo) {
        this.accessGeo = accessGeo;
    }

    public boolean isPrivatePolicy() {
        return privatePolicy;
    }

    public void setPrivatePolicy(boolean privatePolicy) {
        this.privatePolicy = privatePolicy;
    }

    public boolean isUserOffers() {
        return userOffers;
    }

    public void setUserOffers(boolean userOffers) {
        this.userOffers = userOffers;
    }

    public boolean isNotificationPromotions() {
        return notificationPromotions;
    }

    public void setNotificationPromotions(boolean notificationPromotions) {
        this.notificationPromotions = notificationPromotions;
    }

    private boolean privatePolicy;
    private boolean  userOffers;
    private boolean notificationPromotions;

    private List<String> userCategories = new ArrayList<>();

    public List<String> getUserCategories() {
        return userCategories;
    }

    public void setUserCategories(List<String> userCategories) {
        this.userCategories = userCategories;
    }

    public User() {
    }
}
