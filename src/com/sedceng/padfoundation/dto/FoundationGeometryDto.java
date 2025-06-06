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
public class FoundationGeometryDto {
    private double foundationDepth;
    private double sideLengthOfFooting;
    private double heightOfFooting;
    private double sideLenghtOfColumn;
    private double columnHeightAboveGround;
    private double columnHeightBelowGround;
    private double unitWeightOfConcrete;

    public FoundationGeometryDto() {
    }

    public FoundationGeometryDto(double foundationDepth, double sideLengthOfFooting, double heightOfFooting, double sideLenghtOfColumn, double columnHeightAboveGround, double columnHeightBelowGround, double unitWeightOfConcrete) {
        this.foundationDepth = foundationDepth;
        this.sideLengthOfFooting = sideLengthOfFooting;
        this.heightOfFooting = heightOfFooting;
        this.sideLenghtOfColumn = sideLenghtOfColumn;
        this.columnHeightAboveGround = columnHeightAboveGround;
        this.columnHeightBelowGround = columnHeightBelowGround;
        this.unitWeightOfConcrete = unitWeightOfConcrete;
    }
    
    public List<String[]> getReportLines() {
//        List<String> lines = new ArrayList<>();
//        lines.add("Footing Side Length: " + sideLengthOfFooting + " m");
//        lines.add("Height of The Footing: " + heightOfFooting + " m");
//        lines.add("Unit Weight of Concrete: " + unitWeightOfConcrete);
//        // Add more as needed
//        return lines;
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"Footing Side Length", ":", sideLengthOfFooting + " m"});
        data.add(new String[]{"Height of The Footing", ":", heightOfFooting + " m"});
        data.add(new String[]{"Unit Weight of Concrete", ":", unitWeightOfConcrete + "kN/m3"});
        // Add more rows as needed
        return data;
    }

    /**
     * @return the foundationDepth
     */
    public double getFoundationDepth() {
        return foundationDepth;
    }

    /**
     * @param foundationDepth the foundationDepth to set
     */
    public void setFoundationDepth(double foundationDepth) {
        this.foundationDepth = foundationDepth;
    }

    /**
     * @return the sideLengthOfFooting
     */
    public double getSideLengthOfFooting() {
        return sideLengthOfFooting;
    }

    /**
     * @param sideLengthOfFooting the sideLengthOfFooting to set
     */
    public void setSideLengthOfFooting(double sideLengthOfFooting) {
        this.sideLengthOfFooting = sideLengthOfFooting;
    }

    /**
     * @return the heightOfFooting
     */
    public double getHeightOfFooting() {
        return heightOfFooting;
    }

    /**
     * @param heightOfFooting the heightOfFooting to set
     */
    public void setHeightOfFooting(double heightOfFooting) {
        this.heightOfFooting = heightOfFooting;
    }

    /**
     * @return the sideLenghtOfColumn
     */
    public double getSideLenghtOfColumn() {
        return sideLenghtOfColumn;
    }

    /**
     * @param sideLenghtOfColumn the sideLenghtOfColumn to set
     */
    public void setSideLenghtOfColumn(double sideLenghtOfColumn) {
        this.sideLenghtOfColumn = sideLenghtOfColumn;
    }

    /**
     * @return the columnHeightAboveGround
     */
    public double getColumnHeightAboveGround() {
        return columnHeightAboveGround;
    }

    /**
     * @param columnHeightAboveGround the columnHeightAboveGround to set
     */
    public void setColumnHeightAboveGround(double columnHeightAboveGround) {
        this.columnHeightAboveGround = columnHeightAboveGround;
    }

    /**
     * @return the columnHeightBelowGround
     */
    public double getColumnHeightBelowGround() {
        return columnHeightBelowGround;
    }

    /**
     * @param columnHeightBelowGround the columnHeightBelowGround to set
     */
    public void setColumnHeightBelowGround(double columnHeightBelowGround) {
        this.columnHeightBelowGround = columnHeightBelowGround;
    }
    

    /**
     * @return the unitWeightOfConcrete
     */
    public double getUnitWeightOfConcrete() {
        return unitWeightOfConcrete;
    }

    /**
     * @param unitWeightOfConcrete the unitWeightOfConcrete to set
     */
    public void setUnitWeightOfConcrete(double unitWeightOfConcrete) {
        this.unitWeightOfConcrete = unitWeightOfConcrete;
    }

    @Override
    public String toString() {
        return "FoundationGeometryDto{" + "foundationDepth=" + foundationDepth + ", sideLengthOfFooting=" + sideLengthOfFooting + ", heightOfFooting=" + heightOfFooting + ", sideLenghtOfColumn=" + sideLenghtOfColumn + ", columnHeightAboveGround=" + columnHeightAboveGround + ", columnHeightBelowGround=" + columnHeightBelowGround + ", unitWeightOfConcrete=" + unitWeightOfConcrete + '}';
    }
    
    public double calculateWeightOfFoundation(){
        return (sideLengthOfFooting*sideLengthOfFooting*heightOfFooting + sideLenghtOfColumn*sideLenghtOfColumn*(columnHeightAboveGround + columnHeightBelowGround))*unitWeightOfConcrete;
    }
    
    
    
}
