/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.UltimateLoadsDto;
import com.sedceng.padfoundation.service.custom.DesignForOneWayShearService;
import com.sedceng.padfoundation.service.custom.DesignForPunchingShearService;
import com.sedceng.padfoundation.util.ReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.ShearReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;

/**
 *
 * @author Sanduni Navoda
 */
public class DesignForPunchingShearServiceImpl implements DesignForPunchingShearService{
    
    
    
    @Override
    public double ShearReinforcementRequirementPerMm(SoilPropertiesNewDto soilDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator,UltimateLoadsDto loadsDto, ReinforcementCalculatorUtil rfUtil, double as, ShearReinforcementCalculatorUtil shearRfUtil){
        double d = soilDto.getWaterTableDepth();
        double h = geometry.getFoundationDepth();
        double wf = geometry.getSideLengthOfFooting();
        double wc = geometry.getSideLenghtOfColumn();
        double u = soilCalculator.calculateUpthrustForce();
        double vl = loadsDto.getHorizontalLongitudinalForce();
        double vt = loadsDto.getHorizontalTransverseForce();
        double hc_ag = geometry.getColumnHeightAboveGround();
        double hc_bg = geometry.getColumnHeightBelowGround();
        double axialForce = loadsDto.getCompressiveForce();
        double fcu = geometry.getUnitWeightOfConcrete();
        double effectiveDepth = rfUtil.calculateEffectiveDepth()/1000;
        
        double areaOutsideThePerimeter = (wf * wf) - ((wc + 3*effectiveDepth)*(wc + 3*effectiveDepth));
        double p = 4*(wc + 3*effectiveDepth);
        
        double sigmaC;
        if (d > h){
            sigmaC = axialForce/(wf*wf);
        }else{
            sigmaC = (axialForce + u)/(wf*wf);
        }
        
        double sigmaCDashVl = (vl*(hc_ag + hc_bg))/(wf*wf*wf);
        double sigmaCDashVt = (vt*(hc_ag + hc_bg))/(wf*wf*wf);
        
        double sigmaMin = sigmaC - sigmaCDashVl - sigmaCDashVt;
        double sigmaMax = sigmaC + sigmaCDashVl + sigmaCDashVt;
        
        
        double b = (wf - (wc + 3*effectiveDepth))*0.5;
        double sigma1_5d = sigmaMin + (sigmaMax - sigmaMin)*(wf - b)/(wf);
        
        double punchingShearForce = sigma1_5d*areaOutsideThePerimeter;
        
        double shearStress = punchingShearForce*1000/((p*1000)*(effectiveDepth*1000));
        double rfPercentage = (100*as)/((p*1000)*(effectiveDepth*1000));
        double vc = shearRfUtil.getVc(rfPercentage, effectiveDepth*1000);
        double fyv = 460;
         if (shearStress <= 0.5 * vc) {
            // Case 1: No shear reinforcement needed (minor structures)
            return 0.0;
        } else if (shearStress <= vc + 0.4) {
            // Case 2: Minimum links required
            return (0.4 * wf) / (0.95 * fyv);
        } else if (shearStress <= Math.min(0.8 * Math.sqrt(fcu), 5.0)) {
            // Case 3: Links only provided
            return (wf * (shearStress - vc)) / (0.95 * fyv);
        } else {
            throw new IllegalArgumentException("v exceeds the maximum allowable shear stress.");
        }
        
    }
    
    
    

    
}
