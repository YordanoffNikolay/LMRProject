package org.yordanoffnikolay.lmrproject.dtos;

public class VisitDto {

    private String clientName;
    private String workplaceName;
    private boolean isDouble;
    private long workdayId;

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

    public long getWorkdayId() {
        return workdayId;
    }

    public void setWorkdayId(long workdayId) {
        this.workdayId = workdayId;
    }
}
