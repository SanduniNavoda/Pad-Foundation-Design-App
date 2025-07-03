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
public class SoilPropertiesNewDto {
    private double bearingCapacity;
    private double internalFrictionAngle;
    private double frictionAngleWithFoundation;
    private double bulkUnitWeight;
    private double saturatedUnitWeight;
    private double waterUnitWeight;
    private double cohesion;
    private double waterTableDepth;
    private double kp;

    public SoilPropertiesNewDto() {
    }

    public SoilPropertiesNewDto(double bearingCapacity, double internalFrictionAngle, double frictionAngleWithFoundation, double bulkUnitWeight, double saturatedUnitWeight, double waterUnitWeight, double cohesion, double waterTableDepth) {
        this.bearingCapacity = bearingCapacity;
        this.internalFrictionAngle = internalFrictionAngle;
        this.frictionAngleWithFoundation = frictionAngleWithFoundation;
        this.bulkUnitWeight = bulkUnitWeight;
        this.saturatedUnitWeight = saturatedUnitWeight;
        this.waterUnitWeight = waterUnitWeight;
        this.cohesion = cohesion;
        this.waterTableDepth = waterTableDepth;
        this.kp = Math.pow(Math.tan(Math.toRadians(45+(internalFrictionAngle/2))), 2);
    }
    
    public List<String[]> getReportLines() {

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"**Material Properties**"});
        data.add(new String[]{"Following Engineering Parameters was recommended for  the soil in the provided Geo Investigation Report "});
        data.add(new String[]{"Effective soil Angle of Internal Friction (Ø’)", "=", internalFrictionAngle + ""});
        data.add(new String[]{"Friction angle between concrete foundation and soil (δ)", "=", frictionAngleWithFoundation + ""});
        data.add(new String[]{"Safe bearing capacity of Soil", "=", bearingCapacity + " kN/m²"});
        data.add(new String[]{"Effective soil Cohesion/ Adhesion (c’) ", "=", cohesion + " kN/m²"});
        data.add(new String[]{"Bulk unit weight of soil", "=", bulkUnitWeight + " kN/m3"});
        data.add(new String[]{"Saturated unit weight of soil", "=", saturatedUnitWeight + " kN/m3"});
        data.add(new String[]{"Depth of water table from ground level", "=", waterTableDepth + " kN/m3"});
        // Add more rows as needed
        return data;
    }

    /**
     * @return the internalFrictionAngle
     */
    public double getInternalFrictionAngle() {
        return internalFrictionAngle;
    }

    /**
     * @param internalFrictionAngle the internalFrictionAngle to set
     */
    public void setInternalFrictionAngle(double internalFrictionAngle) {
        this.internalFrictionAngle = internalFrictionAngle;
    }

    /**
     * @return the frictionAngleWithFoundation
     */
    public double getFrictionAngleWithFoundation() {
        return frictionAngleWithFoundation;
    }

    /**
     * @param frictionAngleWithFoundation the frictionAngleWithFoundation to set
     */
    public void setFrictionAngleWithFoundation(double frictionAngleWithFoundation) {
        this.frictionAngleWithFoundation = frictionAngleWithFoundation;
    }

    /**
     * @return the bulkUnitWeight
     */
    public double getBulkUnitWeight() {
        return bulkUnitWeight;
    }

    /**
     * @param bulkUnitWeight the bulkUnitWeight to set
     */
    public void setBulkUnitWeight(double bulkUnitWeight) {
        this.bulkUnitWeight = bulkUnitWeight;
    }

    /**
     * @return the saturatedUnitWeight
     */
    public double getSaturatedUnitWeight() {
        return saturatedUnitWeight;
    }

    /**
     * @param saturatedUnitWeight the saturatedUnitWeight to set
     */
    public void setSaturatedUnitWeight(double saturatedUnitWeight) {
        this.saturatedUnitWeight = saturatedUnitWeight;
    }

    /**
     * @return the waterUnitWeight
     */
    public double getWaterUnitWeight() {
        return waterUnitWeight;
    }

    /**
     * @param waterUnitWeight the waterUnitWeight to set
     */
    public void setWaterUnitWeight(double waterUnitWeight) {
        this.waterUnitWeight = waterUnitWeight;
    }

    /**
     * @return the cohesion
     */
    public double getCohesion() {
        return cohesion;
    }

    /**
     * @param cohesion the cohesion to set
     */
    public void setCohesion(double cohesion) {
        this.cohesion = cohesion;
    }

    /**
     * @return the waterTableDepth
     */
    public double getWaterTableDepth() {
        return waterTableDepth;
    }

    /**
     * @param waterTableDepth the waterTableDepth to set
     */
    public void setWaterTableDepth(double waterTableDepth) {
        this.waterTableDepth = waterTableDepth;
    }

    /**
     * @return the kp
     */
    public double getKp() {
        return kp;
    }

    /**
     * @param internalFrictionAngle
     * the kp to set
     */
    public void setKp(double internalFrictionAngle) {
        this.kp = Math.pow(Math.tan(Math.toRadians(45+(internalFrictionAngle/2))), 2);
    }

    /**
     * @return the bearingCapacity
     */
    public double getBearingCapacity() {
        return bearingCapacity;
    }

    /**
     * @param bearingCapacity the bearingCapacity to set
     */
    public void setBearingCapacity(double bearingCapacity) {
        this.bearingCapacity = bearingCapacity;
    }

    @Override
    public String toString() {
        return "SoilPropertiesNewDto{" + "bearingCapacity=" + bearingCapacity + ", internalFrictionAngle=" + internalFrictionAngle + ", frictionAngleWithFoundation=" + frictionAngleWithFoundation + ", bulkUnitWeight=" + bulkUnitWeight + ", saturatedUnitWeight=" + saturatedUnitWeight + ", waterUnitWeight=" + waterUnitWeight + ", cohesion=" + cohesion + ", waterTableDepth=" + waterTableDepth + ", kp=" + kp + '}';
    }

    
    
    
    
    
}
