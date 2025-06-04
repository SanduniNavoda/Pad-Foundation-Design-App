/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.dto;


/**
 *
 * @author Sanduni Navoda
 */
public class ReinforcementDto {
    private double yieldStrenghtOfReinforcement;
    private double gradeOfConcrete;
    private double clearCover;
    private double barDiameter;
    private double shearLinksDiameter;
    private int noOfLegs;
    

    public ReinforcementDto() {
    }

    public ReinforcementDto(double yieldStrenghtOfReinforcement, double gradeOfConcrete, double clearCover, double barDiameter, double shearLinksDiameter, int noOfLegs) {
        this.yieldStrenghtOfReinforcement = yieldStrenghtOfReinforcement;
        this.gradeOfConcrete = gradeOfConcrete;
        this.clearCover = clearCover;
        this.barDiameter = barDiameter;
        this.shearLinksDiameter = shearLinksDiameter;
        this.noOfLegs = noOfLegs;
        
    }

    /**
     * @return the yieldStrenghtOfReinforcement
     */
    public double getYieldStrenghtOfReinforcement() {
        return yieldStrenghtOfReinforcement;
    }

    /**
     * @param yieldStrenghtOfReinforcement the yieldStrenghtOfReinforcement to set
     */
    public void setYieldStrenghtOfReinforcement(double yieldStrenghtOfReinforcement) {
        this.yieldStrenghtOfReinforcement = yieldStrenghtOfReinforcement;
    }

    /**
     * @return the gradeOfConcrete
     */
    public double getGradeOfConcrete() {
        return gradeOfConcrete;
    }

    /**
     * @param gradeOfConcrete the gradeOfConcrete to set
     */
    public void setGradeOfConcrete(double gradeOfConcrete) {
        this.gradeOfConcrete = gradeOfConcrete;
    }

    /**
     * @return the clearCover
     */
    public double getClearCover() {
        return clearCover;
    }

    /**
     * @param clearCover the clearCover to set
     */
    public void setClearCover(double clearCover) {
        this.clearCover = clearCover;
    }

    /**
     * @return the barDiameter
     */
    public double getBarDiameter() {
        return barDiameter;
    }

    /**
     * @param barDiameter the barDiameter to set
     */
    public void setBarDiameter(double barDiameter) {
        this.barDiameter = barDiameter;
    }

    /**
     * @return the shearLinksDiameter
     */
    public double getShearLinksDiameter() {
        return shearLinksDiameter;
    }

    /**
     * @param shearLinksDiameter the shearLinksDiameter to set
     */
    public void setShearLinksDiameter(double shearLinksDiameter) {
        this.shearLinksDiameter = shearLinksDiameter;
    }

    /**
     * @return the noOfLegs
     */
    public int getNoOfLegs() {
        return noOfLegs;
    }

    /**
     * @param noOfLegs the noOfLegs to set
     */
    public void setNoOfLegs(int noOfLegs) {
        this.noOfLegs = noOfLegs;
    }

    @Override
    public String toString() {
        return "ReinforcementDto{" + "yieldStrenghtOfReinforcement=" + yieldStrenghtOfReinforcement + ", gradeOfConcrete=" + gradeOfConcrete + ", clearCover=" + clearCover + ", barDiameter=" + barDiameter + ", shearLinksDiameter=" + shearLinksDiameter + ", noOfLegs=" + noOfLegs + '}';
    }

    
    
    
    
    

    
    
    
}
