/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.FoundationFootingDto;
import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.StubColumnDto;
import com.sedceng.padfoundation.service.custom.UprootingCheckService;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;

/**
 *
 * @author Sanduni Navoda
 */
public class UprootingCheckServiceImpl implements UprootingCheckService{
    
    

    @Override
    public boolean fosSatisfied(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator) throws Exception {
        double foundationWeight = geometryDto.calculateWeightOfFoundation();
        double pyramidSoilWeightAboveFooting = soilCalculator.calculatePyramidSoilWeight();
        double rectangularSoilWeightAboveFooting = soilCalculator.calculateRectangularSoilWeight();
        double maximumTensileForcePerBase = serviceabilityLoadsDto.getTensileForce();
        double upthrustForce = soilCalculator.calculateUpthrustForce();
        System.out.println("w = " + foundationWeight);
        System.out.println("sw pyramid = " + pyramidSoilWeightAboveFooting);
        System.out.println("sw rectangular = " + rectangularSoilWeightAboveFooting);
        System.out.println("Ft = " + maximumTensileForcePerBase);
        System.out.println("Upthrust = " + upthrustForce);
        
        double fos_Pyramid;
        double fos_Rectangular;
        if (soilDto.getWaterTableDepth() > geometryDto.getFoundationDepth()){
            fos_Pyramid = (foundationWeight + pyramidSoilWeightAboveFooting)/maximumTensileForcePerBase;
            fos_Rectangular = (foundationWeight + rectangularSoilWeightAboveFooting)/maximumTensileForcePerBase;
        }else{
            fos_Pyramid = (foundationWeight + pyramidSoilWeightAboveFooting - upthrustForce)/maximumTensileForcePerBase;
            fos_Rectangular = (foundationWeight + rectangularSoilWeightAboveFooting - upthrustForce)/maximumTensileForcePerBase;
        }
        System.out.println("fos Pyramid = " + fos_Pyramid);
        System.out.println("fos Rectangular = " + fos_Rectangular);
        
        return (fos_Pyramid > 1.75 && fos_Rectangular > 1);
            
    }
    
    

    

    
    
    
}
