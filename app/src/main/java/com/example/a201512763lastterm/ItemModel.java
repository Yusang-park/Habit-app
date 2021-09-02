package com.example.a201512763lastterm;

import android.graphics.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class ItemModel {
    private String name;
    double percent;
    String lastly;
    Boolean[] checkToday;
    Boolean onNotificationBar;

    int period;
    int totalClearTimes;
    String color;
    int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public ItemModel(String name, int period, Boolean onNotificationBar, String color, int index
    ) {
        this.name = name;
        this.period = period;
        this.onNotificationBar = onNotificationBar;
        this.totalClearTimes = 0;
        percent = 0;
        this.lastly = "0";
        this.checkToday = new Boolean[]{false, false, false, false, false};
        this.color = color;
        this.index = index;
    }


    public ItemModel(String name, Boolean onNotificationBar, int totalClearTimes, String lastly, Boolean[] checkToday, String color, int index
                   ) {
        this.name = name;
        this.onNotificationBar = onNotificationBar;
        this.totalClearTimes = totalClearTimes;
        percent = (double) (totalClearTimes / 5 / period * 100);
        this.lastly = lastly;
        this.checkToday = checkToday;
        this.color = color;
        this.index = index;
    }}