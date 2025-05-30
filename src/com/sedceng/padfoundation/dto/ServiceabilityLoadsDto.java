/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.dto;

/**
 *
 * @author Sanduni Navoda
 */
public class ServiceabilityLoadsDto {
    private double compressiveForce;
    private double tensileForce;
    private double horizontalTransverseForce;
    private double horizontalLongitudinalForce;

    public ServiceabilityLoadsDto() {
    }

    public ServiceabilityLoadsDto(double compressiveForce, double tensileForce, double horizontalTransverseForce, double horizontalLongitudinalForce) {
        this.compressiveForce = compressiveForce;
        this.tensileForce = tensileForce;
        this.horizontalTransverseForce = horizontalTransverseForce;
        this.horizontalLongitudinalForce = horizontalLongitudinalForce;
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
     * @return the tensileForce
     */
    public double getTensileForce() {
        return tensileForce;
    }

    /**
     * @param tensileForce the tensileForce to set
     */
    public void setTensileForce(double tensileForce) {
        this.tensileForce = tensileForce;
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

    @Override
    public String toString() {
        return "UltimateLoads{" + "compressiveForce=" + compressiveForce + ", tensileForce=" + tensileForce + ", horizontalTransverseForce=" + horizontalTransverseForce + ", horizontalLongitudinalForce=" + horizontalLongitudinalForce + '}';
    }
    
    
    
}
