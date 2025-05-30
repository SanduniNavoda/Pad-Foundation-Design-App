/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.entity;

/**
 *
 * @author Sanduni Navoda
 */
public class SoilPropertiesEntity {
    private double bearingCapacity;
    private double internalFrictionAngle;
    private double frictionAngleBetweenFoundationAndSoil;
    private double unitWeightOfSoil;
    private double saturatedUnitWeightOfSoil;
    private double unitWeightOfWater;
    private double cohesion;
    private double waterTableDepth;
    private double soilHeightAboveFooting;
    private double widthOfFooting;
    private double breadthOfFooting;
    private double heightOfFooting;
    private double widthOfColumn;
    private double breadthOfColumn;
    private double foundationDepth;
    private double kp;
    private double pyramydSoilWeightAboveFooting;
    private double rectangularSoilWeight;
    private double frictionForceUnderBase;
    

    public SoilPropertiesEntity() {
    }

    public SoilPropertiesEntity(double bearingCapacity, double internalFrictionAngle, double frictionAngleBetweenFoundationAndSoil, double unitWeightOfSoil, double saturatedUnitWeightOfSoil, double unitWeightOfWater, double cohesion, double waterTableDepth, double soilHeightAboveFooting, double widthOfFooting, double breadthOfFooting, double heightOfFooting, double widthOfColumn, double breadthOfColumn, double foundationDepth) {
        this.bearingCapacity = bearingCapacity;
        this.internalFrictionAngle = internalFrictionAngle;
        this.frictionAngleBetweenFoundationAndSoil = frictionAngleBetweenFoundationAndSoil;
        this.unitWeightOfSoil = unitWeightOfSoil;
        this.saturatedUnitWeightOfSoil = saturatedUnitWeightOfSoil;
        this.cohesion = cohesion;
        this.waterTableDepth = waterTableDepth;
        this.soilHeightAboveFooting = soilHeightAboveFooting;
        this.widthOfFooting = widthOfFooting;
        this.breadthOfFooting = breadthOfFooting;
        this.heightOfFooting = heightOfFooting;
        this.widthOfColumn = widthOfColumn;
        this.breadthOfColumn = breadthOfColumn;
        this.foundationDepth = foundationDepth;
        this.kp = Math.pow(Math.tan(Math.toRadians(45+(internalFrictionAngle/2))), 2);
        this.pyramydSoilWeightAboveFooting = calculatePyramidSoilWeight();
        this.rectangularSoilWeight = calculateRectangularSoilWeight();
        this.frictionForceUnderBase = rectangularSoilWeight*Math.tan(Math.toRadians(frictionAngleBetweenFoundationAndSoil));
    }

    public SoilPropertiesEntity(double bearingCapacity, double internalFrictionAngle, double frictionAngleBetweenFoundationAndSoil, double unitWeightOfSoil, double cohesion, double soilHeightAboveFooting, double widthOfFooting, double breadthOfFooting, double heightOfFooting, double widthOfColumn, double breadthOfColumn, double foundationDepth) {
        this.bearingCapacity = bearingCapacity;
        this.internalFrictionAngle = internalFrictionAngle;
        this.frictionAngleBetweenFoundationAndSoil = frictionAngleBetweenFoundationAndSoil;
        this.unitWeightOfSoil = unitWeightOfSoil;
        this.cohesion = cohesion;
        this.soilHeightAboveFooting = soilHeightAboveFooting;
        this.widthOfFooting = widthOfFooting;
        this.breadthOfFooting = breadthOfFooting;
        this.heightOfFooting = heightOfFooting;
        this.widthOfColumn = widthOfColumn;
        this.breadthOfColumn = breadthOfColumn;
        this.foundationDepth = foundationDepth;
        this.kp = Math.pow(Math.tan(Math.toRadians(45+(internalFrictionAngle/2))), 2);
        this.pyramydSoilWeightAboveFooting = calculatePyramidSoilWeight();
        this.rectangularSoilWeight = calculateRectangularSoilWeight();
        this.frictionForceUnderBase = rectangularSoilWeight*Math.tan(Math.toRadians(frictionAngleBetweenFoundationAndSoil));
    }

    
    
    public double calculateArea(double width, double breadth, double t){
        return (width*breadth) + (2*width*t) + (2*breadth*t) + ((Math.PI)*t*t);
    }
    
    
    public double calculateVolume(double columnHeightBelowGround, double areaAboveGround, double areaBelowGround, double widthOfColumn, double breadthOfColumn){
        return (1/3)*(columnHeightBelowGround)*(areaAboveGround+Math.sqrt(areaAboveGround*areaBelowGround)+areaBelowGround)-(widthOfColumn*breadthOfColumn*columnHeightBelowGround);
    }
    
    private double calculateRectangularSoilWeight(){
        double v = (getWidthOfFooting()*getBreadthOfFooting()*getSoilHeightAboveFooting())- (getWidthOfColumn()*getBreadthOfColumn()*getSoilHeightAboveFooting());
        
        double soilWeight;
        if(getWaterTableDepth() >= getFoundationDepth()){
            soilWeight = v*getUnitWeightOfSoil();
        }else{
            soilWeight = ((v*getWaterTableDepth()*getUnitWeightOfSoil())/getSoilHeightAboveFooting())+
                    ((v*(getSoilHeightAboveFooting()-getWaterTableDepth())*(getSaturatedUnitWeightOfSoil() - getUnitWeightOfWater()))/getSoilHeightAboveFooting());
        
        }
        return soilWeight;
    }
    
    
    private double calculatePyramidSoilWeight(){
        double t = getSoilHeightAboveFooting()*Math.tan(Math.toRadians(getInternalFrictionAngle()));
        double a1 = calculateArea(getWidthOfFooting(), getBreadthOfFooting(),t);
        double a2 = getWidthOfFooting()*getBreadthOfFooting();
        double v = calculateVolume(getSoilHeightAboveFooting(),a1,a2, getWidthOfColumn(), getBreadthOfColumn());
        
        double soilWeight;
        if(getWaterTableDepth() >= getFoundationDepth()){
            soilWeight = v*getUnitWeightOfSoil();
        }else{
            double t0 = (getSoilHeightAboveFooting()-getWaterTableDepth())*Math.tan(Math.toRadians(getInternalFrictionAngle()));
            double a0 = calculateArea(getWidthOfFooting(), getBreadthOfFooting(),t0);
            double v0 = calculateVolume(getSoilHeightAboveFooting(),a1,a0, getWidthOfColumn(), getBreadthOfColumn());
            soilWeight = v0*getUnitWeightOfSoil()+(v-v0)*(getSaturatedUnitWeightOfSoil()-getUnitWeightOfWater());
        }
        
        return soilWeight;
    }
    
    public double calculateUpthrustForce(){
        return widthOfFooting * breadthOfFooting * foundationDepth * unitWeightOfWater;
    }
    
    private double CalculateLateralPressure(double unitWeight, double saturatedUnitWeight, double unitWeightOfWater, double height){
        if(waterTableDepth >= foundationDepth){
            return ((kp*unitWeight*height) + (2*cohesion*Math.sqrt(kp)));
        }else{
            return ((kp*(saturatedUnitWeight - unitWeightOfWater) *height) + (2*cohesion*Math.sqrt(kp)));
        }
        
    }
    
    public double calculatePassiveForceOnColumnFace(){
        double sideLength = breadthOfColumn;
       
        double fc;
        if (waterTableDepth >= foundationDepth){
            double sigmaDashHc = (kp*unitWeightOfSoil*soilHeightAboveFooting) + (2*cohesion*Math.sqrt(kp));
            fc = (0.5 * sigmaDashHc*soilHeightAboveFooting)*sideLength;
            
            
        }else{
            double gammaDash = saturatedUnitWeightOfSoil - unitWeightOfWater;
            double sigmaDashD1 = (kp*unitWeightOfSoil*waterTableDepth) + 2*cohesion*Math.sqrt(kp);
            double sigmaDashD2 = (kp*gammaDash*waterTableDepth) + 2*cohesion*Math.sqrt(kp);
            
            double sigmaDashHc = (kp*gammaDash*soilHeightAboveFooting) + (2*cohesion*Math.sqrt(kp));
            
            double fc1 = 0.5*sigmaDashD1*waterTableDepth*sideLength;
            double fc2 = 0.5*(sigmaDashD2 + sigmaDashHc)*(soilHeightAboveFooting - waterTableDepth)*sideLength;
            fc = fc1 + fc2;

        }
        return fc;
        
    }
    
    public double calculatePassiveForceOnFootingFace(){

        double ff;
        if (waterTableDepth >= foundationDepth){
            double sigmaDashHc = (kp*unitWeightOfSoil*soilHeightAboveFooting) + (2*cohesion*Math.sqrt(kp));
            double sigmaDashHf = (kp*unitWeightOfSoil*foundationDepth) + (2*cohesion*Math.sqrt(kp));
            
            ff = ((sigmaDashHc + sigmaDashHf)/2)*heightOfFooting*widthOfFooting;
        }else{
            double gammaDash = saturatedUnitWeightOfSoil - unitWeightOfWater;
            
            double sigmaDashHc = (kp*gammaDash*soilHeightAboveFooting) + (2*cohesion*Math.sqrt(kp));
            double sigmaDashHf = (kp*gammaDash*foundationDepth) + (2*cohesion*Math.sqrt(kp));
            
            ff = 0.5*(sigmaDashHc + sigmaDashHf)*heightOfFooting*widthOfFooting;
            //double ff = 0.5*(sigmaDashHc + sigmaDashHf)*heightOfFooting*breadthOfFooting;
        }
        return ff;
        
    }
    
    
    public double calculateLeaverArm(double shorterSide, double longerSide, double h){
        return (h/3)*((longerSide + 2*shorterSide)/(longerSide + shorterSide));
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
     * @return the frictionAngleBetweenFoundationAndSoil
     */
    public double getFrictionAngleBetweenFoundationAndSoil() {
        return frictionAngleBetweenFoundationAndSoil;
    }

    /**
     * @param frictionAngleBetweenFoundationAndSoil the frictionAngleBetweenFoundationAndSoil to set
     */
    public void setFrictionAngleBetweenFoundationAndSoil(double frictionAngleBetweenFoundationAndSoil) {
        this.frictionAngleBetweenFoundationAndSoil = frictionAngleBetweenFoundationAndSoil;
    }

    /**
     * @return the unitWeightOfSoil
     */
    public double getUnitWeightOfSoil() {
        return unitWeightOfSoil;
    }

    /**
     * @param unitWeightOfSoil the unitWeightOfSoil to set
     */
    public void setUnitWeightOfSoil(double unitWeightOfSoil) {
        this.unitWeightOfSoil = unitWeightOfSoil;
    }

    /**
     * @return the saturatedUnitWeightOfSoil
     */
    public double getSaturatedUnitWeightOfSoil() {
        return saturatedUnitWeightOfSoil;
    }

    /**
     * @param saturatedUnitWeightOfSoil the saturatedUnitWeightOfSoil to set
     */
    public void setSaturatedUnitWeightOfSoil(double saturatedUnitWeightOfSoil) {
        this.saturatedUnitWeightOfSoil = saturatedUnitWeightOfSoil;
    }

    /**
     * @return the unitWeightOfWater
     */
    public double getUnitWeightOfWater() {
        return unitWeightOfWater;
    }

    /**
     * @param foundationDepth
     * @param waterTableDepth
     * the unitWeightOfWater to set
     */
    public void setUnitWeightOfWater(double foundationDepth, double waterTableDepth) {
        if (foundationDepth > waterTableDepth){
            this.unitWeightOfWater = 9.81;
        }else{
            this.unitWeightOfWater = 0.00;
        }
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
     * @return the soilHeightAboveFooting
     */
    public double getSoilHeightAboveFooting() {
        return soilHeightAboveFooting;
    }

    /**
     * @param soilHeightAboveFooting the soilHeightAboveFooting to set
     */
    public void setSoilHeightAboveFooting(double soilHeightAboveFooting) {
        this.soilHeightAboveFooting = soilHeightAboveFooting;
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
     * @return the frictionForceUnderBase
     */
    public double getFrictionForceUnderBase() {
        return frictionForceUnderBase;
    }

    /**
     * @return the pyramydSoilWeightAboveFooting
     */
    public double getPyramydSoilWeightAboveFooting() {
        return pyramydSoilWeightAboveFooting;
    }

    /**
     * @return the rectangularSoilWeight
     */
    public double getRectangularSoilWeight() {
        return rectangularSoilWeight;
    }

    
    
    @Override
    public String toString() {
        return "SoilPropertiesEntity{" + "bearingCapacity=" + bearingCapacity + ", internalFrictionAngle=" + internalFrictionAngle + ", frictionAngleBetweenFoundationAndSoil=" + frictionAngleBetweenFoundationAndSoil + ", unitWeightOfSoil=" + unitWeightOfSoil + ", saturatedUnitWeightOfSoil=" + saturatedUnitWeightOfSoil + ", unitWeightOfWater=" + unitWeightOfWater + ", cohesion=" + cohesion + ", waterTableDepth=" + waterTableDepth + ", soilHeightAboveFooting=" + soilHeightAboveFooting + ", widthOfFooting=" + widthOfFooting + ", breadthOfFooting=" + breadthOfFooting + ", heightOfFooting=" + heightOfFooting + ", widthOfColumn=" + widthOfColumn + ", breadthOfColumn=" + breadthOfColumn + ", foundationDepth=" + foundationDepth + ", kp=" + kp + ", pyramydSoilWeightAboveFooting=" + pyramydSoilWeightAboveFooting + ", rectangularSoilWeight=" + rectangularSoilWeight + ", frictionForceUnderBase=" + frictionForceUnderBase + '}';
    }

   
}
