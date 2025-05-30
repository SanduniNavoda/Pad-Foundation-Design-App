/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.controller;

import com.sedceng.padfoundation.dto.FoundationFootingDto;
import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.StubColumnDto;
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
        

    public boolean fosCheckForUprooting(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator) throws Exception{
        return uprootingCheckService.fosSatisfied(geometryDto, soilDto, serviceabilityLoadsDto, soilCalculator);
    }
    
    public boolean fosCheckForBearing(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator) throws Exception{
        return bearingCheckService.fosSatisfied(geometryDto, soilDto, soilCalculator, serviceabilityLoadsDto);
    }
    
    public boolean fosCheckForSliding(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator) throws Exception{
        return slidingCheckService.fosSatisfied(soilCalculator, geometryDto, soilDto, serviceabilityLoadsDto);
    }
    
    public boolean fosCheckForOverturning(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator) throws Exception{
        return overturningCheck.fosSatisfied(geometryDto, soilCalculator, soilDto, serviceabilityLoadsDto);
    }
    
    
}
