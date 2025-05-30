/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.entity;

/**
 *
 * @author Sanduni Navoda
 */
public class StubColumnEntity {
    private double width;
    private double breadth;
    private double heightOfColumn;
    private double heightBelowSoil;
    private double heightAboveSoil;

    public StubColumnEntity() {
    }

    public StubColumnEntity(double width, double breadth, double heightBelowSoil, double heightAboveSoil) {
        this.width = width;
        this.breadth = breadth;
        this.heightBelowSoil = heightBelowSoil;
        this.heightAboveSoil = heightAboveSoil;
        this.heightOfColumn = heightAboveSoil + heightBelowSoil;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return the breadth
     */
    public double getBreadth() {
        return breadth;
    }

    /**
     * @param breadth the breadth to set
     */
    public void setBreadth(double breadth) {
        this.breadth = breadth;
    }

    /**
     * @return the heightOfColumn
     */
    public double getHeightOfColumn() {
        return heightOfColumn;
    }

    /**
     * @param heightOfColumn the heightOfColumn to set
     */
    public void setHeightOfColumn(double heightOfColumn) {
        this.heightOfColumn = heightOfColumn;
    }

    /**
     * @return the heightBelowSoil
     */
    public double getHeightBelowSoil() {
        return heightBelowSoil;
    }

    /**
     * @param heightBelowSoil the heightBelowSoil to set
     */
    public void setHeightBelowSoil(double heightBelowSoil) {
        this.heightBelowSoil = heightBelowSoil;
    }

    /**
     * @return the heightAboveSoil
     */
    public double getHeightAboveSoil() {
        return heightAboveSoil;
    }

    /**
     * @param heightAboveSoil the heightAboveSoil to set
     */
    public void setHeightAboveSoil(double heightAboveSoil) {
        this.heightAboveSoil = heightAboveSoil;
    }

    
    
    


    
    
    
    

   
    
    
    
    
    
}
