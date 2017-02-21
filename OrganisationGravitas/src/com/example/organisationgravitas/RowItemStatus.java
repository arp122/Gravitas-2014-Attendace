package com.example.organisationgravitas;

public class RowItemStatus {
    private String sub_date,apply_date,status;
    public RowItemStatus(String sub_date,String apply_date,String status) {
        this.sub_date=sub_date;
        this.apply_date=apply_date;
        this.status=status;
    }
    public String getSubDate() {
        return sub_date;
    }
    public void setSubDate(String SubDate) {
        this.sub_date = SubDate;
    }
    public String getApplyDate() {
        return apply_date;
    }
    public void setApplyDate(String apply_date) {
        this.apply_date = apply_date;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "";
    }
}
