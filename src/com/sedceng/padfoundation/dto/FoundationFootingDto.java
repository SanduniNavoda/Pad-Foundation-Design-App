/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.dto;

/**
 *
 * @author Sanduni Navoda
 */
public class FoundationFootingDto {
    private double widthOfFooting;
    private double breadthOfFooting;
    private double heightOfFooting;
    private double widthOfColumn;
    private double breadthOfColumn;
    private double columnHeightAboveGround;
    private double columnHeightBelowGround;
    private double unitWeightOfConcrete;
    private double foundationDepth;

    public FoundationFootingDto() {
    }

    public FoundationFootingDto(double widthOfFooting, double breadthOfFooting, double heightOfFooting, double widthOfColumn, double breadthOfColumn, double columnHeightAboveGround, double columnHeightBelowGround, double unitWeightOfConcrete, double foundationDepth) {
        this.widthOfFooting = widthOfFooting;
        this.breadthOfFooting = breadthOfFooting;
        this.heightOfFooting = heightOfFooting;
        this.widthOfColumn = widthOfColumn;
        this.breadthOfColumn = breadthOfColumn;
        this.columnHeightAboveGround = columnHeightAboveGround;
        this.columnHeightBelowGround = columnHeightBelowGround;
        this.unitWeightOfConcrete = unitWeightOfConcrete;
        this.foundationDepth = foundationDepth;
    }


    /**
     * @return the widthOfFooting
     */
    public double getWidthOfFooting() {
        return widthOfFooting;
    }

    /**
     * @param widthOfFooting the widthOfFooting to set
     */
    public void setWidthOfFooting(double widthOfFooting) {
        this.widthOfFooting = widthOfFooting;
    }

    /**
     * @return the breadthOfFooting
     */
    public double getBreadthOfFooting() {
        return breadthOfFooting;
    }

    /**
     * @param breadthOfFooting the breadthOfFooting to set
     */
    public void setBreadthOfFooting(double breadthOfFooting) {
        this.breadthOfFooting = breadthOfFooting;
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
     * @return the widthOfColumn
     */
    public double getWidthOfColumn() {
        return widthOfColumn;
    }

    /**
     * @param widthOfColumn the widthOfColumn to set
     */
    public void setWidthOfColumn(double widthOfColumn) {
        this.widthOfColumn = widthOfColumn;
    }

    /**
     * @return the breadthOfColumn
     */
    public double getBreadthOfColumn() {
        return breadthOfColumn;
    }

    /**
     * @param breadthOfColumn the breadthOfColumn to set
     */
    public void setBreadthOfColumn(double breadthOfColumn) {
        this.breadthOfColumn = breadthOfColumn;
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

    public double getFoundationWeight(){
        return ((getWidthOfFooting()*getBreadthOfFooting()*getHeightOfFooting()) + (getWidthOfColumn()*getBreadthOfColumn()*(getColumnHeightAboveGround()+getColumnHeightBelowGround())))*getUnitWeightOfConcrete();
    }
    
}
