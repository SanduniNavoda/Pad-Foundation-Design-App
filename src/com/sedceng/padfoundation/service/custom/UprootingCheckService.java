/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sedceng.padfoundation.service.custom;


import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.service.SuperService;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;
/**
 *
 * @author Sanduni Navoda
 */
public interface UprootingCheckService extends SuperService{
    
    boolean fosSatisfied(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, SoilPressureCalculatorUtil soilCalculator) throws Exception;

}
