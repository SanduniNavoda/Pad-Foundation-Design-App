/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ResultDto;
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
    public double ShearReinforcementRequirementPerMm(SoilPropertiesNewDto soilDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator,UltimateLoadsDto loadsDto, ReinforcementCalculatorUtil rfUtil, double as, ShearReinforcementCalculatorUtil shearRfUtil, double effectiveDepth, ResultDto result){
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

        result.addReportLine("**Design For One Way Shear**");

        double sigmaC;
        if (d > h){
            sigmaC = axialForce/(wf*wf);

        }else{
            sigmaC = (axialForce + u)/(wf*wf);
        }
        
        double sigmaCDashVl = (12*vl*(hc_ag + hc_bg))/(2*wf*wf*wf);
        double sigmaCDashVt = (12*vt*(hc_ag + hc_bg))/(2*wf*wf*wf);
        
        double sigmaMin = sigmaC - sigmaCDashVl - sigmaCDashVt;
        double sigmaMax = sigmaC + sigmaCDashVl + sigmaCDashVt;
        result.addReportLine("Sigma Min", "=", String.format("%.2f;", sigmaMin));
        result.addReportLine("Sigma Max", "=", String.format("%.2f;", sigmaMax));
        
        //double effectiveDepth = rfUtil.calculateEffectiveDepth();
        double a = (wf - wc)*0.5 - effectiveDepth/1000;
        double sigmaCr = sigmaMin + (sigmaMax - sigmaMin)*(wf - a)/wf;
        result.addReportLine(String.format("a = (%.2f-%.2f) x 0.5-%.2f/1000;", wf, wc, effectiveDepth), "=",
                String.format("%.2f m", a));
        result.addReportLine(String.format("Sigma Cr = %.2f+(%.2f-%.2f) x (%.2f-%.2f)/%.2f;", sigmaMin, sigmaMax, sigmaMin, wf, a, wf), "=",
                String.format("%.2f kN/m²", sigmaCr));
        
        double shearForce = sigmaCr*wf*a;
        result.addReportLine(String.format("Shear Force = %.2f x %.2f x %.2f;", sigmaCr, wf, a), "=",
                String.format("%.2f kN", shearForce));
        
        double stress = shearForce*1000/(wf*1000*effectiveDepth);
        double rfPercentage = (100*as)/(wf*1000*effectiveDepth);
        double vc = shearRfUtil.getVc(rfPercentage, effectiveDepth);
        double fyv = 460;

        result.addReportLine(String.format("Shear Stress = %.2f x 1000/(%.2f x 1000 x %.2f);", shearForce, wf, effectiveDepth), "=",
                String.format("%.2f N/mm²", stress));
        result.addReportLine(String.format("Rf Percentage = (100 x %.2f)/(%.2f x 1000 x %.2f);", as, wf, effectiveDepth), "=",
                String.format("%.2f", rfPercentage));
        result.addReportLine("Fyv", "=", fyv + "");

        double asReq = 0;
         if (stress <= 0.5 * vc) {
            // Case 1: No shear reinforcement needed (minor structures)
            asReq = 0.0;
            return asReq;
        } else if (stress <= vc + 0.4) {
            // Case 2: Minimum links required
            asReq = (0.4 * wf * 1000) / (0.95 * fyv);
            result.addReportLine(String.format("As Required = (0.4 x %.2f x 1000)/(0.95 x %.2f);", wf, fyv), "=",
                String.format("%.2f mm²/mm", asReq));
            return asReq;
        } else if (stress <= Math.min(0.8 * Math.sqrt(fcu), 5.0)) {
            // Case 3: Links only provided
            asReq = (wf * 1000 * (stress - vc)) / (0.95 * fyv);
            result.addReportLine(String.format("As Required = (%.2f x 1000 x (%.2f-%.2f))/(0.95 x %.2f);", wf, stress, vc, fyv), "=",
                String.format("%.2f mm²/mm", asReq));
            return asReq;
        } else {
            throw new IllegalArgumentException("v exceeds the maximum allowable shear stress.");
        }
        
    }
    

    
}
