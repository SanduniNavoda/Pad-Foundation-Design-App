/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.BearingDto;
import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.service.custom.BearingCheckService;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;

/**
 *
 * @author Sanduni Navoda
 */
public class BearingCheckServiceImpl implements BearingCheckService{
    
    

    @Override
    public BearingDto fosSatisfied(FoundationGeometryDto geometryDto,  SoilPropertiesNewDto soilDto, SoilPressureCalculatorUtil soilCalculator, ServiceabilityLoadsDto serviceabilityLoadsDto, BearingDto bearingDto) throws Exception {
        double sw = soilCalculator.calculateRectangularSoilWeight();
        double w = geometryDto.calculateWeightOfFoundation();
        double fc = serviceabilityLoadsDto.getCompressiveForce();
        double u = soilCalculator.calculateUpthrustForce();
        double h = geometryDto.getHeightOfFooting();
        double d = soilDto.getWaterTableDepth();
        double sideLength = geometryDto.getSideLengthOfFooting();
        double vt = serviceabilityLoadsDto.getHorizontalTransverseForce();
        double vl = serviceabilityLoadsDto.getHorizontalLongitudinalForce();
        double hc_ag = geometryDto.getColumnHeightAboveGround();
        
        bearingDto.setSoilWeight(sw);
        bearingDto.setFoundationWeight(w);
        bearingDto.setCompressiveForce(fc);
        bearingDto.setUpliftForce(u);
        
        bearingDto.setSideLenghtOfFooting(sideLength);
        bearingDto.setHorizontalLongitudinalForce(vl);
        bearingDto.setHorizontalTransverseForce(vt);
        bearingDto.setLeverArmForHorizontalForce(h + hc_ag);
        
        
        double sigmaC;
        if(h <= d){
            //sigmaC = (sw + w + fc)/(wf*bf);
            sigmaC = (sw + w + fc)/(sideLength*sideLength);
        }else{
            //sigmaC = (sw + w + fc - u)/(wf*bf);
            sigmaC = (sw + w + fc - u)/(sideLength*sideLength);
        }
        
        bearingDto.setSigmaC(sigmaC);
        
        double v;
        double x;
        double y;
        if (vt > vl){
            v = vt;
            //x = bf;
            //y = wf;
        }else{
            v = vl;
            //x = wf;
            //y = bf;
        }
        
        //double sigmaCDash = (v * (h + hc_ag) * y * 12) / (2 * x * (y*y*y));
        double sigmaCDash = (v * (h + hc_ag) * sideLength * 12) / (2 * sideLength * (sideLength*sideLength*sideLength));
               
        double maximumPressureUnderBase = sigmaC + 2*sigmaCDash;
        
        bearingDto.setSigmaDashC(sigmaCDash);
        bearingDto.setSigmaMax(maximumPressureUnderBase);
        
        double fos = (soilDto.getBearingCapacity()/maximumPressureUnderBase);
        bearingDto.setFos(fos);

        boolean isFosSatisfied = (soilDto.getBearingCapacity()/maximumPressureUnderBase) > 1.00;
        bearingDto.setIsFosSatisfied(isFosSatisfied);
        
        return bearingDto;
        
    }
    
}
