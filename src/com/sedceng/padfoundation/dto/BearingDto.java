/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.dto;

/**
 *
 * @author Sanduni Navoda
 */
public class BearingDto {
    private double compressiveForce;
    private double soilWeight;
    private double foundationWeight;
    private double upliftForce;
    private double sigmaC;
    private double sigmaDashC;
    private double sigmaMax;
    private double horizontalTransverseForce;
    private double horizontalLongitudinalForce;
    private double sideLenghtOfFooting;
    private double leverArmForHorizontalForce;
    private double fos;
    private boolean isFosSatisfied;
    
   
    public BearingDto() {
    }

    public BearingDto(double compressiveForce, double soilWeight, double foundationWeight, double upliftForce, double sigmaC, double sigmaDashC, double sigmaMax, double horizontalTransverseForce, double horizontalLongitudinalForce, double sideLenghtOfFooting, double heightOfColumn, double fos, boolean isFosSatisfied) {
        this.compressiveForce = compressiveForce;
        this.soilWeight = soilWeight;
        this.foundationWeight = foundationWeight;
        this.upliftForce = upliftForce;
        this.sigmaC = sigmaC;
        this.sigmaDashC = sigmaDashC;
        this.sigmaMax = sigmaMax;
        this.horizontalTransverseForce = horizontalTransverseForce;
        this.horizontalLongitudinalForce = horizontalLongitudinalForce;
        this.sideLenghtOfFooting = sideLenghtOfFooting;
        this.leverArmForHorizontalForce = heightOfColumn;
        this.fos = fos;
        this.isFosSatisfied = isFosSatisfied;
    }

    /**
     * @return the compressiveForce
     */
    public double getCompressiveForce() {
        return compressiveForce;
    }

    /**
     * @param compressiveForce the compressiveForce to set
     */
    public void setCompressiveForce(double compressiveForce) {
        this.compressiveForce = compressiveForce;
    }

    /**
     * @return the soilWeight
     */
    public double getSoilWeight() {
        return soilWeight;
    }

    /**
     * @param soilWeight the soilWeight to set
     */
    public void setSoilWeight(double soilWeight) {
        this.soilWeight = soilWeight;
    }

    /**
     * @return the foundationWeight
     */
    public double getFoundationWeight() {
        return foundationWeight;
    }

    /**
     * @param foundationWeight the foundationWeight to set
     */
    public void setFoundationWeight(double foundationWeight) {
        this.foundationWeight = foundationWeight;
    }

    /**
     * @return the upliftForce
     */
    public double getUpliftForce() {
        return upliftForce;
    }

    /**
     * @param upliftForce the upliftForce to set
     */
    public void setUpliftForce(double upliftForce) {
        this.upliftForce = upliftForce;
    }

    /**
     * @return the sigmaC
     */
    public double getSigmaC() {
        return sigmaC;
    }

    /**
     * @param sigmaC the sigmaC to set
     */
    public void setSigmaC(double sigmaC) {
        this.sigmaC = sigmaC;
    }

    /**
     * @return the sigmaDashC
     */
    public double getSigmaDashC() {
        return sigmaDashC;
    }

    /**
     * @param sigmaDashC the sigmaDashC to set
     */
    public void setSigmaDashC(double sigmaDashC) {
        this.sigmaDashC = sigmaDashC;
    }

    /**
     * @return the sigmaMax
     */
    public double getSigmaMax() {
        return sigmaMax;
    }

    /**
     * @param sigmaMax the sigmaMax to set
     */
    public void setSigmaMax(double sigmaMax) {
        this.sigmaMax = sigmaMax;
    }

    /**
     * @return the horizontalTransverseForce
     */
    public double getHorizontalTransverseForce() {
        return horizontalTransverseForce;
    }

    /**
     * @param horizontalTransverseForce the horizontalTransverseForce to set
     */
    public void setHorizontalTransverseForce(double horizontalTransverseForce) {
        this.horizontalTransverseForce = horizontalTransverseForce;
    }

    /**
     * @return the horizontalLongitudinalForce
     */
    public double getHorizontalLongitudinalForce() {
        return horizontalLongitudinalForce;
    }

    /**
     * @param horizontalLongitudinalForce the horizontalLongitudinalForce to set
     */
    public void setHorizontalLongitudinalForce(double horizontalLongitudinalForce) {
        this.horizontalLongitudinalForce = horizontalLongitudinalForce;
    }

    /**
     * @return the sideLenghtOfFooting
     */
    public double getSideLenghtOfFooting() {
        return sideLenghtOfFooting;
    }

    /**
     * @param sideLenghtOfFooting the sideLenghtOfFooting to set
     */
    public void setSideLenghtOfFooting(double sideLenghtOfFooting) {
        this.sideLenghtOfFooting = sideLenghtOfFooting;
    }

    /**
     * @return the leverArmForHorizontalForce
     */
    public double getLeverArmForHorizontalForce() {
        return leverArmForHorizontalForce;
    }

    /**
     * @param leverArmForHorizontalForce the leverArmForHorizontalForce to set
     */
    public void setLeverArmForHorizontalForce(double leverArmForHorizontalForce) {
        this.leverArmForHorizontalForce = leverArmForHorizontalForce;
    }

    /**
     * @return the fos
     */
    public double getFos() {
        return fos;
    }

    /**
     * @param fos the fos to set
     */
    public void setFos(double fos) {
        this.fos = fos;
    }

    /**
     * @return the isFosSatisfied
     */
    public boolean isIsFosSatisfied() {
        return isFosSatisfied;
    }

    /**
     * @param isFosSatisfied the isFosSatisfied to set
     */
    public void setIsFosSatisfied(boolean isFosSatisfied) {
        this.isFosSatisfied = isFosSatisfied;
    }

    @Override
    public String toString() {
        return "BearingDto{" + "compressiveForce=" + compressiveForce + ", soilWeight=" + soilWeight + ", foundationWeight=" + foundationWeight + ", upliftForce=" + upliftForce + ", sigmaC=" + sigmaC + ", sigmaDashC=" + sigmaDashC + ", sigmaMax=" + sigmaMax + ", horizontalTransverseForce=" + horizontalTransverseForce + ", horizontalLongitudinalForce=" + horizontalLongitudinalForce + ", sideLenghtOfFooting=" + sideLenghtOfFooting + ", leverArmForHorizontalForce=" + leverArmForHorizontalForce + ", fos=" + fos + ", isFosSatisfied=" + isFosSatisfied + '}';
    }

    
    
    
    

    
    
}
