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
import com.sedceng.padfoundation.util.ShearReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;
/**
 *
 * @author Sanduni Navoda
 */
public interface DesignForPunchingShearService extends SuperService{

    double ShearReinforcementRequirementPerMm(SoilPropertiesNewDto soilDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator,UltimateLoadsDto loadsDto, ReinforcementCalculatorUtil rfUtil, double as, ShearReinforcementCalculatorUtil shearRfUtil, double effectiveDepth, ResultDto result) throws Exception;
}
