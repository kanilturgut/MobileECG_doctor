package com.tobbetu.MobileECG_Doctor.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kanilturgut on 16/03/14.
 */
public class User implements Serializable{

    private Date birthday;

    private String name;
    private String surname;
    private String username;
    private String password;
    private String phoneNumber;
    private String address;
    private String deviceID;

    private int sex;
    private int activityFrequency;
    private int weight;                 // In kilograms
    private int height;                 // In centimeters
    private int smokingFrequency;
    private int alcoholUsageFrequency;
    private int kolesterolLDL;
    private int kolesterolHDL;

    private boolean hasHypertension;
    private boolean hasDiabetes;

    private double  bmi;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getActivityFrequency() {
        return activityFrequency;
    }

    public void setActivityFrequency(int activityFrequency) {
        this.activityFrequency = activityFrequency;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSmokingFrequency() {
        return smokingFrequency;
    }

    public void setSmokingFrequency(int smokingFrequency) {
        this.smokingFrequency = smokingFrequency;
    }

    public int getAlcoholUsageFrequency() {
        return alcoholUsageFrequency;
    }

    public void setAlcoholUsageFrequency(int alcoholUsageFrequency) {
        this.alcoholUsageFrequency = alcoholUsageFrequency;
    }

    public int getKolesterolLDL() {
        return kolesterolLDL;
    }

    public void setKolesterolLDL(int kolesterolLDL) {
        this.kolesterolLDL = kolesterolLDL;
    }

    public int getKolesterolHDL() {
        return kolesterolHDL;
    }

    public void setKolesterolHDL(int kolesterolHDL) {
        this.kolesterolHDL = kolesterolHDL;
    }

    public boolean isHasHypertension() {
        return hasHypertension;
    }

    public void setHasHypertension(boolean hasHypertension) {
        this.hasHypertension = hasHypertension;
    }

    public boolean isHasDiabetes() {
        return hasDiabetes;
    }

    public void setHasDiabetes(boolean hasDiabetes) {
        this.hasDiabetes = hasDiabetes;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }
}
