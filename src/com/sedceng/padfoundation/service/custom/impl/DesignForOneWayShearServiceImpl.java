/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.UltimateLoadsDto;
import com.sedceng.padfoundation.service.custom.DesignForOneWayShearService;
import com.sedceng.padfoundation.util.ReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.ShearReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;

/**
 *
 * @author Sanduni Navoda
 */
public class DesignForOneWayShearServiceImpl implements DesignForOneWayShearService{
    
    
    
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
        double sigmaC;
        if (d > h){
            sigmaC = axialForce/wf*wf;
        }else{
            sigmaC = (axialForce + u)/wf*wf;
        }
        
        double sigmaCDashVl = (vl*(hc_ag + hc_bg))/(wf*wf*wf);
        double sigmaCDashVt = (vt*(hc_ag + hc_bg))/(wf*wf*wf);
        
        double sigmaMin = sigmaC - sigmaCDashVl - sigmaCDashVt;
        double sigmaMax = sigmaC + sigmaCDashVl + sigmaCDashVt;
        
        double effectiveDepth = rfUtil.calculateEffectiveDepth();
        double a = (wf - wc)*0.5 - effectiveDepth;
        double sigmaCr = sigmaMin + (sigmaMax - sigmaMin)*(wf - a)/wf;
        
        double shearForce = sigmaCr*wf*a;
        
        double stress = shearForce*1000/(wf*1000*effectiveDepth);
        double rfPercentage = (100*as)/(wf*1000*effectiveDepth);
        double vc = shearRfUtil.getVc(rfPercentage, effectiveDepth);
        double fyv = 460;
         if (stress <= 0.5 * vc) {
            // Case 1: No shear reinforcement needed (minor structures)
            return 0.0;
        } else if (stress <= vc + 0.4) {
            // Case 2: Minimum links required
            return (0.4 * wf * 1000) / (0.95 * fyv);
        } else if (stress <= Math.min(0.8 * Math.sqrt(fcu), 5.0)) {
            // Case 3: Links only provided
            return (wf * 1000 * (stress - vc)) / (0.95 * fyv);
        } else {
            throw new IllegalArgumentException("v exceeds the maximum allowable shear stress.");
        }
        
    }
    

    
}
