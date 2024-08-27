package org.yordanoffnikolay.lmrproject.dtos;

public class VisitDto {

    private String clientName;
    private String workplaceName;
    private boolean isDouble;
    private String date;

    private VisitDto() {
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getWorkplaceName() {
        return workplaceName;
    }

    public void setWorkplaceName(String workplaceName) {
        this.workplaceName = workplaceName;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public void setDouble(boolean aDouble) {
        isDouble = aDouble;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
