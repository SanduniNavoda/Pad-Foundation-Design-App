/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sedceng.padfoundation.service.custom;


import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.UprootingDto;
import com.sedceng.padfoundation.service.SuperService;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;
/**
 *
 * @author Sanduni Navoda
 */
public interface UprootingCheckService extends SuperService{
    
    ResultDto fosSatisfied(double foundationWeight, double rectangularSoilWeight, double weightOfPyramidsoilFrustum, FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator, ResultDto result) throws Exception;

}
