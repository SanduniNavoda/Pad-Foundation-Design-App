/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SlidingDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.service.custom.SlidingCheckService;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;

/**
 *
 * @author Sanduni Navoda
 */
public class SlidingCheckServiceImpl implements SlidingCheckService{
    

    @Override
    public ResultDto fosSatisfied(double foundationWeight, double rectangularSoilWeight, SoilPressureCalculatorUtil soilCalculator, FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto loadsDto, ResultDto result) throws Exception {
        soilCalculator.setResult(result);
        result.addReportLine("**Sliding Check**");
        
        double lf = geometryDto.getSideLengthOfFooting();
        double lc = geometryDto.getSideLenghtOfColumn();
        //set values to slidingDto
        
        
        
        double hc_bg = geometryDto.getColumnHeightBelowGround();
        double hf = geometryDto.getHeightOfFooting();
        double d = soilDto.getWaterTableDepth();
        double h = geometryDto.getFoundationDepth();
        
        
        double sigmaDashD;
        if(d > h){
            sigmaDashD = 0;
        }else{
            sigmaDashD = soilCalculator.calculateLateralPressure(d);
        }
        
        
       
        double sigmaHc = soilCalculator.calculateLateralPressure(hc_bg);
        double sigmaHf = soilCalculator.calculateLateralPressure(h);
        
       
        
        double fc;
        double fc1;
        double fc2;
        double ff;
        double ff1;
        double ff2;
        
        if(d > hc_bg){
            //Water table below column face
            fc = soilCalculator.calculatePassivePressure(0, sigmaHc, hc_bg)*lc;
            
            
            if(d < h){
                //Footing face spans water table
                ff1 = soilCalculator.calculatePassivePressure(sigmaHc, sigmaDashD, d - hc_bg)*lf;
                ff2 = soilCalculator.calculatePassivePressure(sigmaDashD, sigmaHf, h - d)*lf;
                ff = ff1 + ff2;
                
                
                
            }else{
                //Entire footing face above water table
                ff = soilCalculator.calculatePassivePressure(sigmaHc, sigmaHf, hf)*lf;
                
            }
        }else{
            //water table cuts column face
            fc1 = soilCalculator.calculateLeaverArm(0, sigmaDashD, d)*lc;
            fc2 = soilCalculator.calculateLeaverArm(sigmaDashD, sigmaHc, hc_bg - d)*lc;
            ff = soilCalculator.calculatePassivePressure(sigmaHc, sigmaHf, hf)*lf;
            fc = fc1 + fc2;
            
        }
        
        double vl = loadsDto.getHorizontalLongitudinalForce();
        double vt = loadsDto.getHorizontalTransverseForce();
        double v;
        if (vl > vt){
            v = vl;
        }else{
            v = vt;
        }
        
        
        double w = foundationWeight;
        double sw = rectangularSoilWeight;
        
        double fr = (sw + w)*Math.tan(Math.toRadians(soilDto.getFrictionAngleWithFoundation()));
        
        
        double fos = (fr + fc + ff)/v;
        result.setResult(fos);
        
        boolean isFosSatisfied = (fos) > 1.50;
        result.setIsSatisfied(isFosSatisfied);
        
//        result.addReportLine(String.format("Fos = (%.2f/%.2f);", bearingCapacity, maximumPressureUnderBase), ":", String.format("%.2f kN/m²", fos));
        result.addReportLine(String.format("Fos = (%.2f+%.2f+%.2f)/%.2f;", fr, fc, ff, v),  ":", String.format("%.2f kN/m²", fos));
        result.addReportLine(isFosSatisfied? "Sliding Check Pass" : "Sliding Check Fail", "", "" );
        result.addReportLine(" ");
        
        
        return result;
    }
    
    
}
