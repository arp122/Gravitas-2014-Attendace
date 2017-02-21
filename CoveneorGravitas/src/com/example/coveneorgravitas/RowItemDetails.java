package com.example.coveneorgravitas;

public class RowItemDetails {
    private String name,reg;
    public RowItemDetails(String name,String reg) {
        this.name=name;
        this.reg=reg;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getReg() {
        return reg;
    }
    public void setReg(String reg) {
        this.reg = reg;
    }
    
    @Override
    public String toString() {
        return name+" "+reg;
    }
}
