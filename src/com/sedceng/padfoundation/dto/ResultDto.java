/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sanduni Navoda
 */
public class ResultDto {
    private double result;
    private boolean isSatisfied;
    private List<String[]> data;

    public ResultDto() {
        this.data = new ArrayList<>();
    }


    public void addReportLine(String... values) {

        data.add(values);//values is a new String[]
        System.out.println(data.toString());
        
    }

    /**
     * @return the result
     */
    public double getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(double result) {
        this.result = result;
    }

    /**
     * @return the data
     */
    public List<String[]> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<String[]> data) {
        this.data = data;
    }

    /**
     * @return the isSatisfied
     */
    public boolean isIsSatisfied() {
        return isSatisfied;
    }

    /**
     * @param isSatisfied the isSatisfied to set
     */
    public void setIsSatisfied(boolean isSatisfied) {
        this.isSatisfied = isSatisfied;
    }
    
    

}
