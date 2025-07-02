/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.UltimateLoadsDto;
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
        //double effectiveDepth = rfUtil.calculateEffectiveDepth()/1000;

        result.addReportLine("**Design For Punching Shear**");
        
        double areaOutsideThePerimeter = (wf * wf) - ((wc + 3*effectiveDepth)*(wc + 3*effectiveDepth));
        double p = 4*(wc + 3*effectiveDepth);
        result.addReportLine(String.format("Area Outside The Perimeter = (%.2f x %.2f)-((%.2f+3 x %.2f) x (%.2f+3 x %.2f));", wf, wf, wc, effectiveDepth, wc, effectiveDepth));
        result.addReportLine(String.format("P = 4 x (%.2f+3 x %.2f);", wc, effectiveDepth));

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
        result.addReportLine("Sigma Min", "=", String.format("%.2f;", sigmaMin));
        result.addReportLine("Sigma Max", "=", String.format("%.2f;", sigmaMax));
        
        
        double b = (wf - (wc + 3*effectiveDepth))*0.5;
        double sigma1_5d = sigmaMin + (sigmaMax - sigmaMin)*(wf - b)/(wf);
        result.addReportLine(String.format("B = (%.2f-(%.2f+3 x %.2f)) x 0.5;", wf, wc, effectiveDepth));
        result.addReportLine(String.format("Sigma1 5d = %.2f+(%.2f-%.2f) x (%.2f-%.2f)/(%.2f);", sigmaMin, sigmaMax, sigmaMin, wf, b, wf));

        double punchingShearForce = sigma1_5d*areaOutsideThePerimeter;
        result.addReportLine(String.format("Punching Shear Force = %.2f x %.2f;", sigma1_5d, areaOutsideThePerimeter));

        double shearStress = punchingShearForce*1000/((p*1000)*(effectiveDepth*1000));
        double rfPercentage = (100*as)/((p*1000)*(effectiveDepth*1000));
        double vc = shearRfUtil.getVc(rfPercentage, effectiveDepth*1000);
        double fyv = 460;
        result.addReportLine(String.format("Shear Stress = %.2f x 1000/((%.2f x 1000) x (%.2f x 1000));", punchingShearForce, p, effectiveDepth));
        result.addReportLine(String.format("Rf Percentage = (100 x %.2f)/((%.2f x 1000) x (%.2f x 1000));", as, p, effectiveDepth));
        result.addReportLine("vc", "=", vc + "");
        result.addReportLine("Fyv", "=", fyv + "");


        double asReq = 0;
         if (shearStress <= 0.5 * vc) {
            // Case 1: No shear reinforcement needed (minor structures)
            asReq = 0.0;
            result.addReportLine("As Req", "=", asReq + "mm²/mm");
            return asReq;
        } else if (shearStress <= vc + 0.4) {
            // Case 2: Minimum links required
            asReq = (0.4 * wf) / (0.95 * fyv);
            result.addReportLine(String.format("As Req = (0.4 x %.2f)/(0.95 x %.2f);", wf, fyv), "=",
                String.format("%.2f mm²/mm", asReq));
            return asReq;
        } else if (shearStress <= Math.min(0.8 * Math.sqrt(fcu), 5.0)) {
            // Case 3: Links only provided
            asReq = (wf * (shearStress - vc)) / (0.95 * fyv);
            result.addReportLine(String.format("As Req = (%.2f x (%.2f-%.2f))/(0.95 x %.2f);", wf, shearStress, vc, fyv), "=",
                String.format("%.2f mm²/mm", asReq));
            return asReq;
        } else {
            throw new IllegalArgumentException("v exceeds the maximum allowable shear stress.");
        }
        
    }
    
    
    

    
}
