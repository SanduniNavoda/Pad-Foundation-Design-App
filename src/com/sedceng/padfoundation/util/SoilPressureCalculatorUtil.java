/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.util;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ResultDto;
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
    private ResultDto result;

    public SoilPressureCalculatorUtil(SoilPropertiesNewDto soil, FoundationGeometryDto geometry) {
        this.soil = soil;
        this.geometry = geometry;
    }
    
    
    
    public double calculateAreaOfPyramidVolume(double sideLenghtOfFooting, double t){
        double surfaceAreaOfPyramidSoilFrustum = (sideLenghtOfFooting*sideLenghtOfFooting) + (4*sideLenghtOfFooting*t) + ((Math.PI)*t*t);
        getResult().addReportLine(String.format("Surface Area Of Pyramid Soil Frustum = (%.2f x %.2f)+(4 x %.2f x %.2f)+(phi x %.2f x %.2f);", sideLenghtOfFooting, sideLenghtOfFooting, sideLenghtOfFooting, t, t, t), " = ", String.format("%.2f m²", surfaceAreaOfPyramidSoilFrustum));
        return surfaceAreaOfPyramidSoilFrustum;
    }
    
    
    public double calculatePyramidVolume(double height, double areaAboveGround, double areaBelowGround, double sideLenghtOfColumn){
        double pyramidSoilVolume = (1d/3)*(height)*(areaAboveGround+Math.sqrt(areaAboveGround*areaBelowGround)+areaBelowGround)-(sideLenghtOfColumn*sideLenghtOfColumn*height);
        getResult().addReportLine(String.format("Pyramid Soil Volume = (1/3) x (%.2f) x (%.2f + sqrt(%.2f x %.2f)+%.2f)-(%.2f x %.2f x %.2f);", height, areaAboveGround, areaAboveGround, areaBelowGround, areaBelowGround, sideLenghtOfColumn, sideLenghtOfColumn, height), " = ", String.format("%.2f m³", pyramidSoilVolume));

        return pyramidSoilVolume;
    }
    
    public double calculateRectangularVolume(){
        double sideLengthOfFooting = geometry.getSideLengthOfFooting();
        double sideLengthOfColumn = geometry.getSideLenghtOfColumn();
        double soilHeightAboveFooting = geometry.getColumnHeightBelowGround();
        double rectangularSoilVolume = (sideLengthOfFooting*sideLengthOfFooting - sideLengthOfColumn*sideLengthOfColumn)*soilHeightAboveFooting;
        getResult().addReportLine(String.format("Rectangular Soil Volume = (%.2f x %.2f-%.2f x %.2f) x %.2f;", sideLengthOfFooting, sideLengthOfFooting, sideLengthOfColumn, sideLengthOfColumn, soilHeightAboveFooting), " = ", String.format("%.2f m³", rectangularSoilVolume));

        return rectangularSoilVolume;
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
            getResult().addReportLine(String.format("Soil Weight = %.2f x %.2f;", v, gamma), " = ", String.format("%.2f kN/m²", soilWeight));

        }else{
            soilWeight = ((v*waterTableDepth*gamma)/soilHeightAboveFooting)+
                    ((v*(soilHeightAboveFooting-waterTableDepth)*(gammaSat - gammaWat))/soilHeightAboveFooting);  
            getResult().addReportLine(String.format("Soil Weight = ((%.2f x %.2f x %.2f)/%.2f)+;", v, waterTableDepth, gamma, soilHeightAboveFooting), " = ", String.format("%.2f kN", soilWeight));
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
        result.addReportLine(String.format("T = %.2f x tan(%.2f);", soilHeightAboveFooting, phi), " = ", String.format("%.2f", t));
        double a1 = calculateAreaOfPyramidVolume(sideLengthOfFooting, t);
        double a2 = sideLengthOfFooting*sideLengthOfFooting;
        result.addReportLine(String.format("A2 = %.2f x %.2f;", sideLengthOfFooting, sideLengthOfFooting), " = ", String.format("%.2f m²", a2));
        double v = calculatePyramidVolume(soilHeightAboveFooting,a1,a2, sideLengthOfColumn);
        
        double soilWeight;
        if(waterTableDepth >= foundationDepth){
            soilWeight = v*gamma;
            result.addReportLine(String.format("Soil Weight = %.2f x %.2f;", v, gamma), " = ", String.format("%.2f kN", soilWeight));
        }else{
            double t0 = (soilHeightAboveFooting-waterTableDepth)*Math.tan(Math.toRadians(phi));
            result.addReportLine(String.format("T0 = (%.2f-%.2f) x tan(%.2f);", soilHeightAboveFooting, waterTableDepth, phi), " = ", String.format("%.2f", t0));
            double a0 = calculateAreaOfPyramidVolume(sideLengthOfFooting,t0);
            double v0 = calculatePyramidVolume(waterTableDepth,a1,a0, sideLengthOfColumn);
            soilWeight = v0*gamma+(v-v0)*gammaDash;
            result.addReportLine(String.format("Soil Weight = %.2f x %.2f+(%.2f-%.2f) x %.2f;", v0, gamma, v, v0, gammaDash), " = ", String.format("%.2f kN", soilWeight));
        }
        
        return soilWeight;
    }
    
    public double calculateUpthrustForce(){
        
        double waterTableDepth = soil.getWaterTableDepth();
        double sideLengthOfFooting = geometry.getSideLengthOfFooting();
        double foundationDepth = geometry.getFoundationDepth();
        if(waterTableDepth > foundationDepth){
            result.addReportLine("Upthrust Force ", " = " , 0 + "kN");
            return 0;
            
        }

        double waterHeight = geometry.getFoundationDepth()-soil.getWaterTableDepth();
        double unitWeightOfWater = soil.getWaterUnitWeight();
        double upthrustForce = sideLengthOfFooting * sideLengthOfFooting * unitWeightOfWater * waterHeight;
        result.addReportLine(String.format("Upthrust Force = %.2f x %.2f x %.2f x %.2f;", sideLengthOfFooting, sideLengthOfFooting, unitWeightOfWater, waterHeight), " = " , upthrustForce + " kN");
        return upthrustForce;
    }
    
    public double calculateLateralPressure(double depth){
        double soilFrictionAngle = soil.getInternalFrictionAngle();
        double kp = Math.pow(Math.tan(Math.toRadians(45+(soilFrictionAngle/2))), 2);
        result.addReportLine(String.format("Kp = ((tan(45+(%.2f/2))2))^2;", soilFrictionAngle), " = " , String.format("%.2f", kp));
        double cohesion = soil.getCohesion();
        double unitWeight = soil.getBulkUnitWeight();
        double saturatedUnitWeight = soil.getSaturatedUnitWeight();
        double unitWeightOfWater = soil.getWaterUnitWeight();
        double waterTableDepth = soil.getWaterTableDepth();
        
        
        double lateralPressure;
        if(depth <= waterTableDepth){
            lateralPressure = kp*unitWeight*depth + (2 * cohesion * Math.sqrt(kp));
            result.addReportLine(String.format("Lateral Pressure = %.2f x %.2f x %.2f + 2 x %.2f x sqrt(%.2f);", kp, unitWeight, depth, cohesion, kp), " = ", String.format("%.2f kN/m²", lateralPressure));
        }else{
            // Mixed: part above water table, part below
            double heightAboveWT = waterTableDepth;
            double heightBelowWT = depth - waterTableDepth;
        
            lateralPressure = (kp * unitWeight * heightAboveWT) +
                   (kp * (saturatedUnitWeight - unitWeightOfWater) * heightBelowWT) + (2 * cohesion * Math.sqrt(kp));
            result.addReportLine(String.format("Lateral Pressure = (%.2f x %.2f x %.2f) + 2 x %.2f x sqrt(%.2f);", kp, unitWeight, heightAboveWT, cohesion, kp), " = ", String.format("%.2f kN/m²", lateralPressure));
        }

        
        return lateralPressure;
        
    }
    
    public double calculateLeaverArm(double shorterSide, double longerSide, double h){
        double leaverArm = (h/3)*((longerSide + 2*shorterSide)/(longerSide + shorterSide));
        result.addReportLine(String.format("Leaver Arm = (%.2f/3) x ((%.2f+2 x %.2f)/(%.2f+%.2f));", h, longerSide, shorterSide, longerSide, shorterSide), " = ", String.format("%.2f m", leaverArm));
        return leaverArm;
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
            result.addReportLine(String.format("Sigma Dash Hc = (%.2f x %.2f x %.2f)+(2 x %.2f x sqrt(%.2f));", kp, unitWeightOfSoil, soilHeightAboveFooting, cohesion, kp), " = ", String.format("%.2f kN/m²", sigmaDashHc));
            fc = (0.5 * sigmaDashHc*soilHeightAboveFooting)*sideLength;
            result.addReportLine(String.format("Fc = (0.5 x %.2f x %.2f) x %.2f;", sigmaDashHc, soilHeightAboveFooting, sideLength), " = ", String.format("%.2f kN", fc));
            
            
        }else{
            double gammaDash = saturatedUnitWeightOfSoil - unitWeightOfWater;
            result.addReportLine(String.format("Gamma Dash = %.2f-%.2f;", saturatedUnitWeightOfSoil, unitWeightOfWater), " = ", String.format("%.2f kN/m²", gammaDash));
            double sigmaDashD1 = (kp*unitWeightOfSoil*waterTableDepth) + 2*cohesion*Math.sqrt(kp);
            result.addReportLine(String.format("Sigma Dash D1 = (%.2f x %.2f x %.2f)+2 x %.2f x sqrt(%.2f);", kp, unitWeightOfSoil, waterTableDepth, cohesion, kp), " = ", String.format("%.2f kN/m²", sigmaDashD1));
            double sigmaDashD2 = (kp*gammaDash*waterTableDepth) + 2*cohesion*Math.sqrt(kp);
            result.addReportLine(String.format("Sigma Dash D2 = (%.2f x %.2f x %.2f)+2 x %.2f x sqrt(%.2f);", kp, gammaDash, waterTableDepth, cohesion, kp), " = ", String.format("%.2f kN/m²", sigmaDashD2));
            
            double sigmaDashHc = (kp*gammaDash*soilHeightAboveFooting) + (2*cohesion*Math.sqrt(kp));
            result.addReportLine(String.format("Sigma Dash Hc = (%.2f x %.2f x %.2f)+(2 x %.2f x sqrt(%.2f));", kp, gammaDash, soilHeightAboveFooting, cohesion, kp), " = ", String.format("%.2f kN/m²", sigmaDashHc));
            
            double fc1 = 0.5*sigmaDashD1*waterTableDepth*sideLength;
            double fc2 = 0.5*(sigmaDashD2 + sigmaDashHc)*(soilHeightAboveFooting - waterTableDepth)*sideLength;
            fc = fc1 + fc2;
            result.addReportLine(String.format("Fc1 = 0.5 x %.2f x %.2f x %.2f;", sigmaDashD1, waterTableDepth, sideLength), " = ", String.format("%.2f kN", fc1));
            result.addReportLine(String.format("Fc2 = 0.5 x (%.2f+%.2f) x (%.2f-%.2f) x %.2f;", sigmaDashD2, sigmaDashHc, soilHeightAboveFooting, waterTableDepth, sideLength), " = ", String.format("%.2f kN", fc2));
            result.addReportLine(String.format("Fc = %.2f+%.2f;", fc1, fc2), " = ", String.format("%.2f kN", fc));

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
            
            result.addReportLine(String.format("Sigma Dash Hc = (%.2f x %.2f x %.2f)+(2 x %.2f x sqrt(%.2f));", kp, unitWeightOfSoil, soilHeightAboveFooting, cohesion, kp), " = ", String.format("%.2f kN/m²", sigmaDashHc));
            result.addReportLine(String.format("Sigma Dash Hf = (%.2f x %.2f x %.2f)+(2 x %.2f x sqrt(%.2f));", kp, unitWeightOfSoil, foundationDepth, cohesion, kp), " = ", String.format("%.2f kN/m²", sigmaDashHf));
            result.addReportLine(String.format("Ff = ((%.2f+%.2f)/2) x %.2f x %.2f;", sigmaDashHc, sigmaDashHf, sideLength, sideLength), " = ", String.format("%.2f kN", ff));
        }else{
            double gammaDash = saturatedUnitWeightOfSoil - unitWeightOfWater;
            
            double sigmaDashHc = (kp*gammaDash*soilHeightAboveFooting) + (2*cohesion*Math.sqrt(kp));
            double sigmaDashHf = (kp*gammaDash*foundationDepth) + (2*cohesion*Math.sqrt(kp));
            
            ff = 0.5*(sigmaDashHc + sigmaDashHf)*sideLength*sideLength;
            
            result.addReportLine(String.format("Gamma Dash = %.2f-%.2f;", saturatedUnitWeightOfSoil, unitWeightOfWater), " = ", String.format("%.2f kN/m²", gammaDash));
            result.addReportLine(String.format("Sigma Dash Hc = (%.2f x %.2f x %.2f)+(2 x %.2f x sqrt(%.2f));", kp, gammaDash, soilHeightAboveFooting, cohesion, kp), " = ", String.format("%.2f kN/m²", sigmaDashHc));
            result.addReportLine(String.format("Sigma Dash Hf = (%.2f x %.2f x %.2f)+(2 x %.2f x sqrt(%.2f));", kp, gammaDash, foundationDepth, cohesion, kp), " = ", String.format("%.2f kN/m²", sigmaDashHf));
            result.addReportLine(String.format("Ff = 0.5 x (%.2f+%.2f) x %.2f x %.2f;", sigmaDashHc, sigmaDashHf, sideLength, sideLength), " = ", String.format("%.2f kN/m²", ff));
        }
        return ff;
        
    }
    
    public double calculatePassivePressure(double pressureAbove, double pressureBelow, double height){
        double passivePressure = ((pressureAbove+pressureBelow)/2)*height;
        result.addReportLine(String.format("Passive Pressure = ((%.2f+%.2f)/2) x %.2f;", pressureAbove, pressureBelow, height), " = ", String.format("%.2f kN/m²", passivePressure));
        return passivePressure;
    }

    /**
     * @return the result
     */
    public ResultDto getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(ResultDto result) {
        this.result = result;
    }
      
}
