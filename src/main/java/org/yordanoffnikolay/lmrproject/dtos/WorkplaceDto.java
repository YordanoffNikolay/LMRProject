package org.yordanoffnikolay.lmrproject.dtos;

public class WorkplaceDto {

    private String name;
    private String address;
    private String brickName;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrickName() {
        return brickName;
    }
    public void setBrickName(String brickName) {
        this.brickName = brickName;
    }
}
