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
            result.addReportLine("Lataral Pressure at water table depth");
            sigmaDashD = soilCalculator.calculateLateralPressure(d);
        }
        
        
        result.addReportLine("Lataral Pressure at Column Base");
        double sigmaHc = soilCalculator.calculateLateralPressure(hc_bg);
        result.addReportLine("Lataral Pressure at Footing Base");
        double sigmaHf = soilCalculator.calculateLateralPressure(h);
        
       
        
        double fc;
        double fc1;
        double fc2;
        double ff;
        double ff1;
        double ff2;
        
        if(d > hc_bg){
            //Water table below column face
            result.addReportLine("Passive force on Column Face");
            double pressureC = soilCalculator.calculatePassivePressure(0, sigmaHc, hc_bg);
            fc = pressureC *lc;
            result.addReportLine(String.format("           = %.2f x %.2f", pressureC, lc),"=", String.format("%.2f kN", fc));
            
            
            if(d < h){
                //Footing face spans water table
                result.addReportLine("Passive force on Footing Face Above Water Table");
                double pressureF1 = soilCalculator.calculatePassivePressure(sigmaHc, sigmaDashD, d - hc_bg);
                ff1 = pressureF1*lf;
                result.addReportLine(String.format("           = %.2f x %.2f", pressureF1, lf),"=", String.format("%.2f kN", ff1));
                
                result.addReportLine("Passive force on Footing Face Below Water Table");
                double pressureF2 = soilCalculator.calculatePassivePressure(sigmaDashD, sigmaHf, h - d);
                ff2 = pressureF2*lf;
                result.addReportLine(String.format("           = %.2f x %.2f", pressureF2, lf),"=", String.format("%.2f kN", ff2));
                
                ff = ff1 + ff2;
                result.addReportLine(String.format("Passive force on Footing Face = %.2f + %.2f", ff1, ff2), "=", String.format("%.2f kN", ff));
                
                
                
            }else{
                //Entire footing face above water table
                result.addReportLine("Passive force on Footing Face");
                double pressureF = soilCalculator.calculatePassivePressure(sigmaHc, sigmaHf, hf);
                ff = pressureF *lf;
                result.addReportLine(String.format("           = %.2f x %.2f", pressureF, lf),"=", String.format("%.2f kN", ff));
                
            }
        }else{
            //water table cuts column face
            result.addReportLine("Passive force on Column Face Above Water Table");
            double pressureC1 = soilCalculator.calculatePassivePressure(0, sigmaDashD, d);
            fc1 = pressureC1*lc;
            result.addReportLine(String.format("           = %.2f x %.2f", pressureC1, lc),"=", String.format("%.2f kN", fc1));
            
            result.addReportLine("Passive force on Column Face Above Below Table");
            double pressureC2 = soilCalculator.calculatePassivePressure(sigmaDashD, sigmaHc, hc_bg - d);
            fc2 = pressureC2*lc;
            result.addReportLine(String.format("           = %.2f x %.2f", pressureC2, lc),"=", String.format("%.2f kN", fc2));
            
            
            result.addReportLine("Passive force on Column Face");
            fc = fc1 + fc2;
            result.addReportLine(String.format("Passive force on Column Face = %.2f + %.2f", fc1, fc2), "=", String.format("%.2f kN", fc));
            
            result.addReportLine("Passive force on Footing Face");
            double pressureF = soilCalculator.calculatePassivePressure(sigmaHc, sigmaHf, hf);
            ff = pressureF*lf;
            result.addReportLine(String.format("           = %.2f x %.2f", pressureF, lf),"=", String.format("%.2f kN", ff));
            
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
        double ft = loadsDto.getTensileForce();
        double frictionAngleWithFoundation = soilDto.getFrictionAngleWithFoundation();
        result.addReportLine("Weight of the Foundation","=", String.format("%.2f kN", w));
        result.addReportLine("Weight of Soil","=", String.format("%.2f kN", sw));
        result.addReportLine("Tensile Force","=", String.format("%.2f kN", ft));
        result.addReportLine("Friction Angle Between Soil and Concrete","=", String.format("%.2f", frictionAngleWithFoundation));
        
        double fr = (sw + w - ft)*Math.tan(Math.toRadians(frictionAngleWithFoundation));
        result.addReportLine(String.format("Friction Force = (%.2f + %.2f - %.2f) x tan(%.2f)", w,sw,ft,frictionAngleWithFoundation), "=", String.format("%.2f kN", fr));
        
        double fos = (fr + fc + ff)/v;
        result.setResult(fos);
        
        boolean isFosSatisfied = (fos) > 1.50;
        result.setIsSatisfied(isFosSatisfied);
        
//        result.addReportLine(String.format("Fos = (%.2f/%.2f);", bearingCapacity, maximumPressureUnderBase), ":", String.format("%.2f kN/m²", fos));
        result.addReportLine(String.format("Fos = (%.2f+%.2f+%.2f)/%.2f;", fr, fc, ff, v),  "=", String.format("%.2f kN/m²", fos));
        result.addReportLine(isFosSatisfied? "Sliding Check Pass" : "Sliding Check Fail", "", "" );
        result.addReportLine(" ");
        
        
        return result;
    }
    
    
}
