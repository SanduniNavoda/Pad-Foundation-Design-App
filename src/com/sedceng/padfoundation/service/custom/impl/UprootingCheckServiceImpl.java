/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.FoundationFootingDto;
import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.StubColumnDto;
import com.sedceng.padfoundation.dto.UprootingDto;
import com.sedceng.padfoundation.service.custom.UprootingCheckService;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;

/**
 *
 * @author Sanduni Navoda
 */
public class UprootingCheckServiceImpl implements UprootingCheckService{
    
    

    @Override
    public ResultDto fosSatisfied(double foundationWeight, double rectangularSoilWeight, double weightOfPyramidsoilFrustum, FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator, ResultDto result) throws Exception {
        soilCalculator.setResult(result);
        result.addReportLine("**Uprooting Check**");
        
        double pyramidSoilWeightAboveFooting = weightOfPyramidsoilFrustum;
        
        double maximumTensileForcePerBase = serviceabilityLoadsDto.getTensileForce();
        result.addReportLine("Maximum Tensile force", "=", maximumTensileForcePerBase + "kN");
        result.addReportLine("Weight of the Foundation", "=", String.format("%.2f", foundationWeight) + "kN");
        double upthrustForce = soilCalculator.calculateUpthrustForce();
        
        result.addReportLine("Pyramid soil weight above footing", "=", String.format("%.2f", weightOfPyramidsoilFrustum) + "kN");
        result.addReportLine("Rectangular soil weight above footing", "=",String.format("%.2f", rectangularSoilWeight)  + "kN");
        System.out.println("w = " + foundationWeight);
        System.out.println("sw pyramid = " + pyramidSoilWeightAboveFooting);
        System.out.println("sw rectangular = " + rectangularSoilWeight);
        System.out.println("Ft = " + maximumTensileForcePerBase);
        System.out.println("Upthrust = " + upthrustForce);
        
        
        
        
        double fos_Pyramid;
        double fos_Rectangular;
        if (soilDto.getWaterTableDepth() > geometryDto.getFoundationDepth()){
            fos_Pyramid = (foundationWeight + pyramidSoilWeightAboveFooting)/maximumTensileForcePerBase;
            fos_Rectangular = (foundationWeight + rectangularSoilWeight)/maximumTensileForcePerBase;
            result.addReportLine(String.format("Fos Pyramid = (%.2f+%.2f)/%.2f;", foundationWeight, pyramidSoilWeightAboveFooting, maximumTensileForcePerBase),"=", String.format("%.2f", fos_Pyramid));
            result.addReportLine(String.format("Fos Rectangular = (%.2f+%.2f)/%.2f;", foundationWeight, rectangularSoilWeight, maximumTensileForcePerBase), "=", String.format("%.2f", fos_Rectangular));

        }else{
            fos_Pyramid = (foundationWeight + pyramidSoilWeightAboveFooting - upthrustForce)/maximumTensileForcePerBase;
            fos_Rectangular = (foundationWeight + rectangularSoilWeight - upthrustForce)/maximumTensileForcePerBase;
            result.addReportLine(String.format("Fos Pyramid (>1.75) = (%.2f+%.2f-%.2f)/%.2f;", foundationWeight, pyramidSoilWeightAboveFooting, upthrustForce, maximumTensileForcePerBase), "=", String.format("%.2f", fos_Pyramid));
            result.addReportLine(String.format("Fos Rectangular(>1.00) = (%.2f+%.2f-%.2f)/%.2f;", foundationWeight, rectangularSoilWeight, upthrustForce, maximumTensileForcePerBase), "=", String.format("%.2f", fos_Rectangular));
        }
        System.out.println("fos Pyramid (>1.75) = " + fos_Pyramid);
        System.out.println("fos Rectangular(>1.00) = " + fos_Rectangular);
        
        
        
        boolean isFosSatisfied = (fos_Pyramid > 1.75 && fos_Rectangular > 1);
        result.setIsSatisfied(isFosSatisfied);
        result.addReportLine(isFosSatisfied? "Uprooting Check Pass" : "Uprooting Check Fail", "", "" );
        result.addReportLine(" ");
        
        return result;
            
    }
    
    
}
