/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.controller;

import com.sedceng.padfoundation.dto.BearingDto;
import com.sedceng.padfoundation.dto.FoundationFootingDto;
import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SlidingDto;
import com.sedceng.padfoundation.dto.SoilPropertiesDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.StubColumnDto;
import com.sedceng.padfoundation.dto.UprootingDto;
import com.sedceng.padfoundation.service.ServiceFactory;
import com.sedceng.padfoundation.service.custom.BearingCheckService;
import com.sedceng.padfoundation.service.custom.OverturningCheckService;
import com.sedceng.padfoundation.service.custom.SlidingCheckService;
import com.sedceng.padfoundation.service.custom.UprootingCheckService;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;
/**
 *
 * @author Sanduni Navoda
 */
public class UserInputController {
        private UprootingCheckService uprootingCheckService = (UprootingCheckService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.UPROOTING);
        private BearingCheckService bearingCheckService = (BearingCheckService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.BEARING);
        private SlidingCheckService slidingCheckService = (SlidingCheckService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.SLIDING);
        private OverturningCheckService overturningCheck = (OverturningCheckService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.OVERTURNING);
        

    public ResultDto fosCheckForUprooting(double foundationWeight, double rectangularSoilWeight, double weightOfPyramidsoilFrustum, FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator, ResultDto result) throws Exception{
        return uprootingCheckService.fosSatisfied(foundationWeight, rectangularSoilWeight, weightOfPyramidsoilFrustum, geometryDto, soilDto, serviceabilityLoadsDto, soilCalculator, result);
    }
    
    public ResultDto fosCheckForBearing(double foundationWeight, double rectangularSoilWeight, FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator, ResultDto result) throws Exception{
        return bearingCheckService.fosSatisfied(foundationWeight, rectangularSoilWeight, geometryDto, soilDto, soilCalculator, serviceabilityLoadsDto, result);
    }
    
    public ResultDto fosCheckForSliding(double foundationWeight, double rectangularSoilWeight, FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator, ResultDto result) throws Exception{
        return slidingCheckService.fosSatisfied(foundationWeight, rectangularSoilWeight, soilCalculator, geometryDto, soilDto, serviceabilityLoadsDto, result);
    }
    
    public ResultDto fosCheckForOverturning(double foundationWeight, double pyramidSoilWeight, FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator, ResultDto result) throws Exception{
        return overturningCheck.fosSatisfied(foundationWeight, pyramidSoilWeight, geometryDto, soilCalculator, soilDto, serviceabilityLoadsDto, result);
    }
    
    
}
