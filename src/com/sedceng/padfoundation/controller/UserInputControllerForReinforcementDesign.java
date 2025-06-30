/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.controller;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ReinforcementDto;
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
        

    public double CompressiveRfRequirementForSagging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal) throws Exception{
        double axialCompressiveForce = loadsDto.getCompressiveForce();
        System.out.println(axialCompressiveForce);
        return designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce);
    }
    
    public double getAsProvided(double asRequired, FoundationGeometryDto geometryDto, ReinforcementDto rfDto, ReinforcementCalculatorUtil rfCal) throws Exception{
        return designForBendingService.getAsProvided(asRequired, rfDto, geometryDto, rfCal);
    }
    
    public String compressiveRfDesignForSagging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal) throws Exception{
        double axialCompressiveForce = loadsDto.getCompressiveForce();
        double asRequired = designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce);
        if(asRequired > 0){
            return designForBendingService.TensionOrCompressionReinforcement(asRequired, rfDto, geometryDto, rfCal);
        }else{
            return "No Compressive Reinforcement Required";
        }
    }
    
    public double compressiveRfRequirementForHogging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal) throws Exception{
        double axialTensileForce = loadsDto.getTensileForce() - geometryDto.calculateWeightOfFoundation();
        return designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce);
    }
    
    public String compressiveRfDesignForHogging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal) throws Exception{
        double axialTensileForce = loadsDto.getTensileForce();
        double asRequired =  designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce);
        if(asRequired > 0){
            return designForBendingService.TensionOrCompressionReinforcement(asRequired, rfDto, geometryDto, rfCal);
        }else{
            return "No Compressive Reinforcement Required";
        }
    }
    
    //Tensile Reinforcement Design
    
    public double tensileRfRequirementForSagging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal) throws Exception{
        double axialCompressiveForce = loadsDto.getCompressiveForce();
        double asDash = designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce);
        return designForBendingService.tensionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce, asDash);
    }
    
    public String tensileRfDesignForSagging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal) throws Exception{
        double axialCompressiveForce = loadsDto.getCompressiveForce();
        double asDash = designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce);
        double asRequired = designForBendingService.tensionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialCompressiveForce, asDash);
        
        return designForBendingService.TensionOrCompressionReinforcement(asRequired, rfDto, geometryDto, rfCal);
        
    }
    
    public double tensileRfRequirementForHogging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal) throws Exception{
        double axialTensileForce = loadsDto.getTensileForce();
        double asDash = designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce);
        return designForBendingService.tensionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce, asDash);
    }
    
    public String tensileRfDesignForHogging(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal) throws Exception{
        double axialTensileForce = loadsDto.getTensileForce();
        double asDash = designForBendingService.compressionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce);
        double asRequired = designForBendingService.tensionReinforcementRequirement(soilDto, rfDto, geometryDto, soilCalculator, loadsDto, rfCal, axialTensileForce, asDash);
        
        return designForBendingService.TensionOrCompressionReinforcement(asRequired, rfDto, geometryDto, rfCal);
        
    }
    
    public double ShearReinforcementRequirement(FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, UltimateLoadsDto loadsDto, SoilPressureCalculatorUtil soilCalculator, ReinforcementCalculatorUtil rfCal, ShearReinforcementCalculatorUtil shearRfUtil) throws Exception{
        double asRequired = tensileRfRequirementForSagging(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCal);
        double asProvided = designForBendingService.getAsProvided(asRequired, rfDto, geometryDto, rfCal);
        double asvForOneWayShear = designForOneWayShearService.ShearReinforcementRequirementPerMm(soilDto, geometryDto, soilCalculator, loadsDto, rfCal, asProvided, shearRfUtil);
        double asvForPunchingShear = designForPunchingShearService.ShearReinforcementRequirementPerMm(soilDto, geometryDto, soilCalculator, loadsDto, rfCal, asProvided, shearRfUtil);
        
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
