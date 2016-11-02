package com.example.joonheepak.finalproject.data;

import android.graphics.Bitmap;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Created by joonheepak on 9/30/16.
 */

public class TripData {
    private String tripName;
    private String startDate;
    private String endDate;
    private String budget;
    private byte[] countryFlag;
    private byte[] backgroundImage;
    private String numberDay;
    private String monthOfDay;
    private String specificBudgetName;
    private String specificBudgetAmount;
    private Integer budgetImage;
    private String iconDescription;
    private Integer iconImage;
    private String budgetID;


    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public byte[] getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(byte[] countryFlag) {
        this.countryFlag = countryFlag;
    }

    public byte[] getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(byte[] backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getNumberDay() {
        return numberDay;
    }

    public void setDay(String numberDay) {
        this.numberDay = numberDay;
    }

    public String getMonthOfDay() {
        return monthOfDay;
    }

    public void setMonth(String monthOfDay) {
        this.monthOfDay = monthOfDay;
    }

    public String getSpecificBudgetName() {
        return specificBudgetName;
    }

    public void setSpecificBudgetName(String specificBudgetName) {
        this.specificBudgetName = specificBudgetName;
    }

    public String getSpecificBudgetAmount() {
        return specificBudgetAmount;
    }

    public void setSpecificBudgetAmount(String specificBudgetAmount) {
        this.specificBudgetAmount = specificBudgetAmount;
    }

    public Integer getBudgetImage() {
        return budgetImage;
    }

    public void setBudgetImage(Integer budgetImage) {
        this.budgetImage = budgetImage;
    }

    public String getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(String budgetID) {
        this.budgetID = budgetID;
    }

    public String getIconDescription() {
        return iconDescription;
    }

    public void setIconDescription(String iconDescription) {
        this.iconDescription = iconDescription;
    }

    public Integer getIconImage() {
        return iconImage;
    }

    public void setIconImage(Integer iconImage) {
        this.iconImage = iconImage;
    }

}


