/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.entity;

/**
 *
 * @author Sanduni Navoda
 */
public class UltimateLoadsEntity {
    private Double compressiveForce;
    private Double tensileForce;
    private Double horizontalTransverseForce;
    private Double horizontalLongitudinalForce;

    public UltimateLoadsEntity() {
    }

    public UltimateLoadsEntity(Double compressiveForce, Double tensileForce, Double horizontalTransverseForce, Double horizontalLongitudinalForce) {
        this.compressiveForce = compressiveForce;
        this.tensileForce = tensileForce;
        this.horizontalTransverseForce = horizontalTransverseForce;
        this.horizontalLongitudinalForce = horizontalLongitudinalForce;
    }

    /**
     * @return the compressiveForce
     */
    public Double getCompressiveForce() {
        return compressiveForce;
    }

    /**
     * @param compressiveForce the compressiveForce to set
     */
    public void setCompressiveForce(Double compressiveForce) {
        this.compressiveForce = compressiveForce;
    }

    /**
     * @return the tensileForce
     */
    public Double getTensileForce() {
        return tensileForce;
    }

    /**
     * @param tensileForce the tensileForce to set
     */
    public void setTensileForce(Double tensileForce) {
        this.tensileForce = tensileForce;
    }

    /**
     * @return the horizontalTransverseForce
     */
    public Double getHorizontalTransverseForce() {
        return horizontalTransverseForce;
    }

    /**
     * @param horizontalTransverseForce the horizontalTransverseForce to set
     */
    public void setHorizontalTransverseForce(Double horizontalTransverseForce) {
        this.horizontalTransverseForce = horizontalTransverseForce;
    }

    /**
     * @return the horizontalLongitudinalForce
     */
    public Double getHorizontalLongitudinalForce() {
        return horizontalLongitudinalForce;
    }

    /**
     * @param horizontalLongitudinalForce the horizontalLongitudinalForce to set
     */
    public void setHorizontalLongitudinalForce(Double horizontalLongitudinalForce) {
        this.horizontalLongitudinalForce = horizontalLongitudinalForce;
    }

    @Override
    public String toString() {
        return "UltimateLoads{" + "compressiveForce=" + compressiveForce + ", tensileForce=" + tensileForce + ", horizontalTransverseForce=" + horizontalTransverseForce + ", horizontalLongitudinalForce=" + horizontalLongitudinalForce + '}';
    }
    
    
    
    
    
}
