/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sedceng.padfoundation.service.custom;

import com.sedceng.padfoundation.dto.FoundationFootingDto;
import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ReinforcementDto;
import com.sedceng.padfoundation.dto.ResultDto;
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
    double CriticalBendingMomentAboutTheFaceOfTheColumn(SoilPropertiesNewDto soilDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator, UltimateLoadsDto loadsDto, double axialForce, ResultDto result) throws Exception;
    public double calculateKValue(double sideLengthOfFooting, double effectiveDepth, double concreteGrade, double mCr, ResultDto result) throws Exception;
    double compressionReinforcementRequirement(SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator,UltimateLoadsDto loadsDto, ReinforcementCalculatorUtil rfCal, double axialForce, double mCr, double d, double k, ResultDto result) throws Exception;
    double tensionReinforcementRequirement(SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator,UltimateLoadsDto loadsDto, ReinforcementCalculatorUtil rfCal, double axialForce, double asDash, double d, double mCr, double k, ResultDto result) throws Exception;
    String TensionOrCompressionReinforcement(double asRequired, ReinforcementDto rfDto, FoundationGeometryDto geometry, ReinforcementCalculatorUtil rfUtil, double d, ResultDto result) throws Exception;
    double getAsProvided(
            double asRequired, 
            ReinforcementDto rfDto, 
            FoundationGeometryDto geometry, 
            ReinforcementCalculatorUtil rfUtil,
            double d,
            ResultDto result);
    
}
