/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.util;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.entity.FoundationGeometryEntity;
import com.sedceng.padfoundation.entity.SoilProperties;

/**
 *
 * @author Sanduni Navoda
 */
public class SoilPressureCalculatorUtil {
    
    private SoilPropertiesNewDto soil;
    private FoundationGeometryDto geometry;

    public SoilPressureCalculatorUtil(SoilPropertiesNewDto soil, FoundationGeometryDto geometry) {
        this.soil = soil;
        this.geometry = geometry;
    }
    
    
    
    public double calculateAreaOfPyramidVolume(double sideLenghtOfFooting, double t){
        return (sideLenghtOfFooting*sideLenghtOfFooting) + (4*sideLenghtOfFooting*t) + ((Math.PI)*t*t);
    }
    
    
    public double calculatePyramidVolume(double height, double areaAboveGround, double areaBelowGround, double sideLenghtOfColumn){
        return ((1d/3)*(height)*(areaAboveGround+Math.sqrt(areaAboveGround*areaBelowGround)+areaBelowGround)-(sideLenghtOfColumn*sideLenghtOfColumn*height));
    }
    
    public double calculateRectangularVolume(){
        double sideLengthOfFooting = geometry.getSideLengthOfFooting();
        double sideLengthOfColumn = geometry.getSideLenghtOfColumn();
        double soilHeightAboveFooting = geometry.getColumnHeightBelowGround();
        return (sideLengthOfFooting*sideLengthOfFooting - sideLengthOfColumn*sideLengthOfColumn)*soilHeightAboveFooting;
    }
    
    
    public double calculateRectangularSoilWeight(){
        
        double soilHeightAboveFooting = geometry.getColumnHeightBelowGround();
        double v = calculateRectangularVolume();
        
        double waterTableDepth = soil.getWaterTableDepth();
        double foundationDepth = geometry.getFoundationDepth();
        double gamma = soil.getBulkUnitWeight();
        double gammaSat = soil.getSaturatedUnitWeight();
        double gammaWat = soil.getWaterUnitWeight();
        
        double soilWeight;
        if(waterTableDepth >= foundationDepth){
            soilWeight = v*gamma;
        }else{
            soilWeight = ((v*waterTableDepth*gamma)/soilHeightAboveFooting)+
                    ((v*(soilHeightAboveFooting-waterTableDepth)*(gammaSat - gammaWat))/soilHeightAboveFooting);        
        }
        return soilWeight;
    }
    
    
    public double calculatePyramidSoilWeight(){
        
        double sideLengthOfFooting = geometry.getSideLengthOfFooting();
        double sideLengthOfColumn = geometry.getSideLenghtOfColumn();
        double soilHeightAboveFooting = geometry.getColumnHeightBelowGround();
       
        
        double waterTableDepth = soil.getWaterTableDepth();
        double foundationDepth = geometry.getFoundationDepth();
        double gamma = soil.getBulkUnitWeight();
        double gammaSat = soil.getSaturatedUnitWeight();
        double gammaWat = soil.getWaterUnitWeight();
        double gammaDash = gammaSat - gammaWat;
        double phi = soil.getInternalFrictionAngle();
        
        double t = soilHeightAboveFooting*Math.tan(Math.toRadians(phi));
        double a1 = calculateAreaOfPyramidVolume(sideLengthOfFooting, t);
        double a2 = sideLengthOfFooting*sideLengthOfFooting;
        double v = calculatePyramidVolume(soilHeightAboveFooting,a1,a2, sideLengthOfColumn);
        
        double soilWeight;
        if(waterTableDepth >= foundationDepth){
            soilWeight = v*gamma;
        }else{
            double t0 = (soilHeightAboveFooting-waterTableDepth)*Math.tan(Math.toRadians(phi));
            double a0 = calculateAreaOfPyramidVolume(sideLengthOfFooting,t0);
            double v0 = calculatePyramidVolume(waterTableDepth,a1,a0, sideLengthOfColumn);
            soilWeight = v0*gamma+(v-v0)*gammaDash;
        }
        
        return soilWeight;
    }
    
    public double calculateUpthrustForce(){
        double sideLengthOfFooting = geometry.getSideLengthOfFooting();
        return sideLengthOfFooting * sideLengthOfFooting * soil.getWaterUnitWeight();
    }
    
    public double calculateLateralPressure(double depth){
        double kp = Math.pow(Math.tan(Math.toRadians(45+(soil.getInternalFrictionAngle()/2))), 2);
        double cohesion = soil.getCohesion();
        double unitWeight = soil.getBulkUnitWeight();
        double saturatedUnitWeight = soil.getSaturatedUnitWeight();
        double unitWeightOfWater = soil.getWaterUnitWeight();
        double waterTableDepth = soil.getWaterTableDepth();
        
        
        double pressure;
        if(depth <= waterTableDepth){
            pressure = kp*unitWeight*depth ;
        }else{
            // Mixed: part above water table, part below
            double heightAboveWT = waterTableDepth;
            double heightBelowWT = depth - waterTableDepth;
        
            pressure = (kp * unitWeight * heightAboveWT) +
                   (kp * (saturatedUnitWeight - unitWeightOfWater) * heightBelowWT);
        }
        
        // Add cohesion-based pressure component
        pressure += 2 * cohesion * Math.sqrt(kp);
        
        return pressure;
        
    }
    
    public double calculateLeaverArm(double shorterSide, double longerSide, double h){
        return (h/3)*((longerSide + 2*shorterSide)/(longerSide + shorterSide));
    }
    
    public double calculatePassiveForceOnColumnFace(){
        double sideLength = geometry.getSideLenghtOfColumn();
        double kp = soil.getKp();
        double cohesion = soil.getCohesion();
        double unitWeightOfSoil = soil.getBulkUnitWeight();
        double saturatedUnitWeightOfSoil = soil.getSaturatedUnitWeight();
        double unitWeightOfWater = soil.getWaterUnitWeight();
        double soilHeightAboveFooting = geometry.getColumnHeightBelowGround();
        double waterTableDepth = soil.getWaterTableDepth();
               
       
        double fc;
        if (soil.getWaterTableDepth() >= geometry.getFoundationDepth()){
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
        
        double sideLength = geometry.getSideLengthOfFooting();
        double kp = soil.getKp();
        double cohesion = soil.getCohesion();
        double unitWeightOfSoil = soil.getBulkUnitWeight();
        double saturatedUnitWeightOfSoil = soil.getSaturatedUnitWeight();
        double unitWeightOfWater = soil.getWaterUnitWeight();
        double soilHeightAboveFooting = geometry.getColumnHeightBelowGround();
        double waterTableDepth = soil.getWaterTableDepth();
        double foundationDepth = geometry.getFoundationDepth();

        double ff;
        if (waterTableDepth >= foundationDepth){
            double sigmaDashHc = (kp*unitWeightOfSoil*soilHeightAboveFooting) + (2*cohesion*Math.sqrt(kp));
            double sigmaDashHf = (kp*unitWeightOfSoil*foundationDepth) + (2*cohesion*Math.sqrt(kp));
            
            ff = ((sigmaDashHc + sigmaDashHf)/2)*sideLength*sideLength;
        }else{
            double gammaDash = saturatedUnitWeightOfSoil - unitWeightOfWater;
            
            double sigmaDashHc = (kp*gammaDash*soilHeightAboveFooting) + (2*cohesion*Math.sqrt(kp));
            double sigmaDashHf = (kp*gammaDash*foundationDepth) + (2*cohesion*Math.sqrt(kp));
            
            ff = 0.5*(sigmaDashHc + sigmaDashHf)*sideLength*sideLength;
            
        }
        return ff;
        
    }
    
    public double calculatePassivePressure(double pressureAbove, double pressureBelow, double height){
        return ((pressureAbove+pressureBelow)/2)*height;
    }
    
    
   
}
