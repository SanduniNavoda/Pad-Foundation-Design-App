/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.dto;

/**
 *
 * @author Sanduni Navoda
 */
public class SlidingDto {
    private double maxHorizontalForce;
    private double weightOfSoil;
    private double weightOfFoundation;
    private double compressiveForce;
    private double sigmaC;
    private double sigmaF;
    private double sigmaAtGwt;
    private double hc1;
    private double hc2;
    private double hf1;
    private double hf2;
    private double sideLengthOfFooting;
    private double sideLengthOfColumn;
    private double fr;
    private double fc1;
    private double fc2;
    private double ff1;
    private double ff2;
    private double resistanceToSliding;
    private double fos;
    private boolean isFosSatisfied;
    
   
    public SlidingDto() {
    }

    public SlidingDto(double maxHorizontalForce, double weightOfSoil, double weightOfFoundation, double compressiveForce, double sigmaC, double sigmaF, double sigmaAtGwt, double hc1, double hc2, double hf1, double hf2, double sideLengthOfFooting, double sideLengthOfColumn, double fr, double fc1, double fc2, double ff1, double ff2, double resistanceToSliding, double fos, boolean isFosSatisfied) {
        this.maxHorizontalForce = maxHorizontalForce;
        this.weightOfSoil = weightOfSoil;
        this.weightOfFoundation = weightOfFoundation;
        this.compressiveForce = compressiveForce;
        this.sigmaC = sigmaC;
        this.sigmaF = sigmaF;
        this.sigmaAtGwt = sigmaAtGwt;
        this.hc1 = hc1;
        this.hc2 = hc2;
        this.hf1 = hf1;
        this.hf2 = hf2;
        this.sideLengthOfFooting = sideLengthOfFooting;
        this.sideLengthOfColumn = sideLengthOfColumn;
        this.fr = fr;
        this.fc1 = fc1;
        this.fc2 = fc2;
        this.ff1 = ff1;
        this.ff2 = ff2;
        this.resistanceToSliding = resistanceToSliding;
        this.fos = fos;
        this.isFosSatisfied = isFosSatisfied;
    }

    /**
     * @return the maxHorizontalForce
     */
    public double getMaxHorizontalForce() {
        return maxHorizontalForce;
    }

    /**
     * @param maxHorizontalForce the maxHorizontalForce to set
     */
    public void setMaxHorizontalForce(double maxHorizontalForce) {
        this.maxHorizontalForce = maxHorizontalForce;
    }

    /**
     * @return the weightOfSoil
     */
    public double getWeightOfSoil() {
        return weightOfSoil;
    }

    /**
     * @param weightOfSoil the weightOfSoil to set
     */
    public void setWeightOfSoil(double weightOfSoil) {
        this.weightOfSoil = weightOfSoil;
    }

    /**
     * @return the weightOfFoundation
     */
    public double getWeightOfFoundation() {
        return weightOfFoundation;
    }

    /**
     * @param weightOfFoundation the weightOfFoundation to set
     */
    public void setWeightOfFoundation(double weightOfFoundation) {
        this.weightOfFoundation = weightOfFoundation;
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
     * @return the sigmaF
     */
    public double getSigmaF() {
        return sigmaF;
    }

    /**
     * @param sigmaF the sigmaF to set
     */
    public void setSigmaF(double sigmaF) {
        this.sigmaF = sigmaF;
    }

    /**
     * @return the sigmaAtGwt
     */
    public double getSigmaAtGwt() {
        return sigmaAtGwt;
    }

    /**
     * @param sigmaAtGwt the sigmaAtGwt to set
     */
    public void setSigmaAtGwt(double sigmaAtGwt) {
        this.sigmaAtGwt = sigmaAtGwt;
    }

    /**
     * @return the hc1
     */
    public double getHc1() {
        return hc1;
    }

    /**
     * @param hc1 the hc1 to set
     */
    public void setHc1(double hc1) {
        this.hc1 = hc1;
    }

    /**
     * @return the hc2
     */
    public double getHc2() {
        return hc2;
    }

    /**
     * @param hc2 the hc2 to set
     */
    public void setHc2(double hc2) {
        this.hc2 = hc2;
    }

    /**
     * @return the hf1
     */
    public double getHf1() {
        return hf1;
    }

    /**
     * @param hf1 the hf1 to set
     */
    public void setHf1(double hf1) {
        this.hf1 = hf1;
    }

    /**
     * @return the hf2
     */
    public double getHf2() {
        return hf2;
    }

    /**
     * @param hf2 the hf2 to set
     */
    public void setHf2(double hf2) {
        this.hf2 = hf2;
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
     * @return the sideLengthOfColumn
     */
    public double getSideLengthOfColumn() {
        return sideLengthOfColumn;
    }

    /**
     * @param sideLengthOfColumn the sideLengthOfColumn to set
     */
    public void setSideLengthOfColumn(double sideLengthOfColumn) {
        this.sideLengthOfColumn = sideLengthOfColumn;
    }

    /**
     * @return the fr
     */
    public double getFr() {
        return fr;
    }

    /**
     * @param fr the fr to set
     */
    public void setFr(double fr) {
        this.fr = fr;
    }

    /**
     * @return the fc1
     */
    public double getFc1() {
        return fc1;
    }

    /**
     * @param fc1 the fc1 to set
     */
    public void setFc1(double fc1) {
        this.fc1 = fc1;
    }

    /**
     * @return the fc2
     */
    public double getFc2() {
        return fc2;
    }

    /**
     * @param fc2 the fc2 to set
     */
    public void setFc2(double fc2) {
        this.fc2 = fc2;
    }

    /**
     * @return the ff1
     */
    public double getFf1() {
        return ff1;
    }

    /**
     * @param ff1 the ff1 to set
     */
    public void setFf1(double ff1) {
        this.ff1 = ff1;
    }

    /**
     * @return the ff2
     */
    public double getFf2() {
        return ff2;
    }

    /**
     * @param ff2 the ff2 to set
     */
    public void setFf2(double ff2) {
        this.ff2 = ff2;
    }

    /**
     * @return the resistanceToSliding
     */
    public double getResistanceToSliding() {
        return resistanceToSliding;
    }

    /**
     * @param resistanceToSliding the resistanceToSliding to set
     */
    public void setResistanceToSliding(double resistanceToSliding) {
        this.resistanceToSliding = resistanceToSliding;
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
        return "SlidingDto{" + "maxHorizontalForce=" + maxHorizontalForce + ", weightOfSoil=" + weightOfSoil + ", weightOfFoundation=" + weightOfFoundation + ", compressiveForce=" + compressiveForce + ", sigmaC=" + sigmaC + ", sigmaF=" + sigmaF + ", sigmaAtGwt=" + sigmaAtGwt + ", hc1=" + hc1 + ", hc2=" + hc2 + ", hf1=" + hf1 + ", hf2=" + hf2 + ", sideLengthOfFooting=" + sideLengthOfFooting + ", sideLengthOfColumn=" + sideLengthOfColumn + ", fr=" + fr + ", fc1=" + fc1 + ", fc2=" + fc2 + ", ff1=" + ff1 + ", ff2=" + ff2 + ", resistanceToSliding=" + resistanceToSliding + ", fos=" + fos + ", isFosSatisfied=" + isFosSatisfied + '}';
    }

    
    
    

    

   
    
    
    
    

}
