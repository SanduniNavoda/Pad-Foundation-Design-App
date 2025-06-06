/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.dto;

/**
 *
 * @author Sanduni Navoda
 */
public class UprootingDto {
    private double maxTensileForce;
    private double foundationWeight;
    private double rectangularSoilVolume;
    private double rectangularSoilWeight;
    private double pyramidSoilVolume;
    private double pyramidSoilWeight;
    private double upliftForce;
    private double fosPyramid;
    private double fosRectangular;
    private boolean isFosSatisfied;
   
    public UprootingDto() {
    }

    public UprootingDto(double maxTensileForce, double foundationWeight, double rectangularSoilVolume, double rectangularSoilWeight, double pyramidSoilVolume, double pyramidSoilWeight, double upliftForce, double fosPyramid, double fosRectangular, boolean isFosSatisfied) {
        this.maxTensileForce = maxTensileForce;
        this.foundationWeight = foundationWeight;
        this.rectangularSoilVolume = rectangularSoilVolume;
        this.rectangularSoilWeight = rectangularSoilWeight;
        this.pyramidSoilVolume = pyramidSoilVolume;
        this.pyramidSoilWeight = pyramidSoilWeight;
        this.upliftForce = upliftForce;
        this.fosPyramid = fosPyramid;
        this.fosRectangular = fosRectangular;
        this.isFosSatisfied = isFosSatisfied;
    }

    /**
     * @return the maxTensileForce
     */
    public double getMaxTensileForce() {
        return maxTensileForce;
    }

    /**
     * @param maxTensileForce the maxTensileForce to set
     */
    public void setMaxTensileForce(double maxTensileForce) {
        this.maxTensileForce = maxTensileForce;
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
     * @return the rectangularSoilVolume
     */
    public double getRectangularSoilVolume() {
        return rectangularSoilVolume;
    }

    /**
     * @param rectangularSoilVolume the rectangularSoilVolume to set
     */
    public void setRectangularSoilVolume(double rectangularSoilVolume) {
        this.rectangularSoilVolume = rectangularSoilVolume;
    }

    /**
     * @return the rectangularSoilWeight
     */
    public double getRectangularSoilWeight() {
        return rectangularSoilWeight;
    }

    /**
     * @param rectangularSoilWeight the rectangularSoilWeight to set
     */
    public void setRectangularSoilWeight(double rectangularSoilWeight) {
        this.rectangularSoilWeight = rectangularSoilWeight;
    }

    /**
     * @return the pyramidSoilVolume
     */
    public double getPyramidSoilVolume() {
        return pyramidSoilVolume;
    }

    /**
     * @param pyramidSoilVolume the pyramidSoilVolume to set
     */
    public void setPyramidSoilVolume(double pyramidSoilVolume) {
        this.pyramidSoilVolume = pyramidSoilVolume;
    }

    /**
     * @return the pyramidSoilWeight
     */
    public double getPyramidSoilWeight() {
        return pyramidSoilWeight;
    }

    /**
     * @param pyramidSoilWeight the pyramidSoilWeight to set
     */
    public void setPyramidSoilWeight(double pyramidSoilWeight) {
        this.pyramidSoilWeight = pyramidSoilWeight;
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
     * @return the fosPyramid
     */
    public double getFosPyramid() {
        return fosPyramid;
    }

    /**
     * @param fosPyramid the fosPyramid to set
     */
    public void setFosPyramid(double fosPyramid) {
        this.fosPyramid = fosPyramid;
    }

    /**
     * @return the fosRectangular
     */
    public double getFosRectangular() {
        return fosRectangular;
    }

    /**
     * @param fosRectangular the fosRectangular to set
     */
    public void setFosRectangular(double fosRectangular) {
        this.fosRectangular = fosRectangular;
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
        return "UprootingDto{" + "maxTensileForce=" + maxTensileForce + ", foundationWeight=" + foundationWeight + ", rectangularSoilVolume=" + rectangularSoilVolume + ", rectangularSoilWeight=" + rectangularSoilWeight + ", pyramidSoilVolume=" + pyramidSoilVolume + ", pyramidSoilWeight=" + pyramidSoilWeight + ", upliftForce=" + upliftForce + ", fosPyramid=" + fosPyramid + ", fosRectangular=" + fosRectangular + ", isFosSatisfied=" + isFosSatisfied + '}';
    }

    
    
    
    

    
    
}
