package org.yordanoffnikolay.lmrproject.dtos;

public class OfficeWorkDto {

    private int timeSpent;
    private String date;

    public OfficeWorkDto() {
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
