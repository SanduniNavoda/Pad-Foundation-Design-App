/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.controller;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ReinforcementDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.UltimateLoadsDto;
import com.sedceng.padfoundation.service.ServiceFactory;
import com.sedceng.padfoundation.service.custom.DesignForBendingService;
import com.sedceng.padfoundation.service.custom.DesignForOneWayShearService;
import com.sedceng.padfoundation.service.custom.DesignForPunchingShearService;
import com.sedceng.padfoundation.util.ReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.ShearReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;
/**
 *
 * @author Sanduni Navoda
 */
public class UserInputControllerForReinforcementDesign {
        private DesignForBendingService designForBendingService = (DesignForBendingService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.BENDING);
        private DesignForOneWayShearService designForOneWayShearService = (DesignForOneWayShearService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.ONE_WAY_SHEAR);
        private DesignForPunchingShearService designForPunchingShearService = (DesignForPunchingShearService) ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.PUNCHING_SHEAR);
    
    public double CriticalBendingMomentAboutTheFaceOfTheColumn(SoilPropertiesNewDto soilDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator, UltimateLoadsDto loadsDto, ResultDto result) throws Exception {
        double axialForce = loadsDto.getCompressiveForce();
        return designForBendingService.CriticalBendingMomentAboutTheFaceOfTheColumn(soilDto, geometry, soilCalculator, loadsDto, axialForce, result);
    }
    
    public double calculateKValue(double sideLengthOfFooting, double effectiveDepth, double concreteGrade, double mCr, ResultDto result) throws Exception{
        return designForBendingService.calculateKValue(sideLengthOfFooting, effectiveDepth, concreteGrade, mCr, result);
    }

    public double CompressiveRfRequirementForSagging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal, double mCr, double d, double k, ResultDto resultDto) throws Exception{
        double axialCompressiveForce = loadsDto.getCompressiveForce();
        System.out.println(axialCompressiveForce);
        return designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce, mCr, d, k, resultDto);
    }
    
    public double getAsProvided(double asRequired, FoundationGeometryDto geometryDto, ReinforcementDto rfDto, ReinforcementCalculatorUtil rfCal, double d, ResultDto result) throws Exception{
        return designForBendingService.getAsProvided(asRequired, rfDto, geometryDto, rfCal, d, result);
    }
    
    public String compressiveRfDesignForSagging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal, double asRequiredComp, double d, ResultDto resultDto) throws Exception{
        double axialCompressiveForce = loadsDto.getCompressiveForce();
        //double asRequired = designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce);
        if(asRequiredComp > 0){
            return designForBendingService.TensionOrCompressionReinforcement(asRequiredComp, rfDto, geometryDto, rfCal, d, resultDto);
        }else{
            return "No Compressive Reinforcement Required";
        }
    }
    
    public double compressiveRfRequirementForHogging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal, double mCr, double d, double k, ResultDto resultDto) throws Exception{
        double axialTensileForce = loadsDto.getTensileForce() - geometryDto.calculateWeightOfFoundation();
        return designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce, mCr, d, k, resultDto);
    }
    
    public String compressiveRfDesignForHogging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal, double asRequiredComp, double d, ResultDto resultDto) throws Exception{
        double axialTensileForce = loadsDto.getTensileForce();
        //double asRequired =  designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce);
        if(asRequiredComp > 0){
            return designForBendingService.TensionOrCompressionReinforcement(asRequiredComp, rfDto, geometryDto, rfCal, d, resultDto);
        }else{
            return "No Compressive Reinforcement Required";
        }
    }
    
    //Tensile Reinforcement Design
    
    public double tensileRfRequirementForSagging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal, double asCompSag , double d, double mCr, double k, ResultDto resultDto) throws Exception{
        double axialCompressiveForce = loadsDto.getCompressiveForce();
        //double asDash = designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce);
        return designForBendingService.tensionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce, asCompSag, d, mCr, k, resultDto);
    }
    
    public String tensileRfDesignForSagging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal, double asReqTensSag, double d, ResultDto resultDto) throws Exception{
        double axialCompressiveForce = loadsDto.getCompressiveForce();
        //double asDash = designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce);
        //double asRequired = designForBendingService.tensionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce, asDash);
        
        return designForBendingService.TensionOrCompressionReinforcement(asReqTensSag, rfDto, geometryDto, rfCal, d, resultDto);
        
    }
    
    public double tensileRfRequirementForHogging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal, double asCompHog , double d, double mCr, double k, ResultDto resultDto) throws Exception{
        double axialTensileForce = loadsDto.getTensileForce();
        //double asDash = designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce);
        return designForBendingService.tensionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce, asCompHog, d, mCr, k, resultDto);
    }
    
    public String tensileRfDesignForHogging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal, double asReqTensHog, double d, ResultDto resultDto) throws Exception{
        double axialTensileForce = loadsDto.getTensileForce();
        //double asDash = designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce);
        //double asRequired = designForBendingService.tensionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce, asDash);
        
        return designForBendingService.TensionOrCompressionReinforcement(asReqTensHog, rfDto, geometryDto, rfCal, d, resultDto);
        
    }
    
    public double ShearReinforcementRequirement(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal, ShearReinforcementCalculatorUtil shearRfUtil, double asProvTensSag, double d, ResultDto resultDto) throws Exception{
        //double asRequired = tensileRfRequirementForSagging(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCal);
        //double asProvided = designForBendingService.getAsProvided(asRequired, rfDto, geometryDto, rfCal);
        double asvForOneWayShear = designForOneWayShearService.ShearReinforcementRequirementPerMm(soilDto, geometryDto, soilCalculator, loadsDto, rfCal, asProvTensSag, shearRfUtil, d, resultDto);
        double asvForPunchingShear = designForPunchingShearService.ShearReinforcementRequirementPerMm(soilDto, geometryDto, soilCalculator, loadsDto, rfCal, asProvTensSag, shearRfUtil, d, resultDto);
        
        if(asvForOneWayShear > asvForPunchingShear){
            return asvForOneWayShear;
        }else{
            return asvForPunchingShear;
        }
    }
    
    public String ShearReinforcementDesign(ShearReinforcementCalculatorUtil shearRfUtil, double shearReinforcementRequirement, int numLege, double barDiameter){
        return shearRfUtil.getShearReinforcementConfig(shearReinforcementRequirement, numLege, barDiameter);
    }                        
    
  
}
