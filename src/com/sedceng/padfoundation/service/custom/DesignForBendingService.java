/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sedceng.padfoundation.service.custom;

import com.sedceng.padfoundation.dto.FoundationFootingDto;
import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ReinforcementDto;
import com.sedceng.padfoundation.dto.SoilPropertiesDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.StubColumnDto;
import com.sedceng.padfoundation.dto.UltimateLoadsDto;
import com.sedceng.padfoundation.service.SuperService;
import com.sedceng.padfoundation.util.ReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;
/**
 *
 * @author Sanduni Navoda
 */
public interface DesignForBendingService extends SuperService{

    double compressionReinforcementRequirement(SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator,UltimateLoadsDto loadsDto, ReinforcementCalculatorUtil rfCal, double axialForce) throws Exception;
    double tensionReinforcementRequirement(SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator,UltimateLoadsDto loadsDto, ReinforcementCalculatorUtil rfCal, double axialForce, double asDash) throws Exception;
    String TensionOrCompressionReinforcement(double asRequired, ReinforcementDto rfDto, FoundationGeometryDto geometry, ReinforcementCalculatorUtil rfUtil) throws Exception;
    double getAsProvided(
            double asRequired, 
            ReinforcementDto rfDto, 
            FoundationGeometryDto geometry, 
            ReinforcementCalculatorUtil rfUtil);
    
}
