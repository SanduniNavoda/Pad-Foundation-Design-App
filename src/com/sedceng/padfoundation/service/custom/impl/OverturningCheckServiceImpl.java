/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;


import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.service.custom.OverturningCheckService;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;

/**
 *
 * @author Sanduni Navoda
 */
public class OverturningCheckServiceImpl implements OverturningCheckService{

    @Override
    public ResultDto fosSatisfied(double weightOfFoundation, double weightOFPyramidSoilFrustum, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto loadsDto, ResultDto result) throws Exception {
        
        result.addReportLine("**Overturning Check**");
        //double fc = soilCalculator.calculatePassiveForceOnColumnFace();
        //double ff = soilCalculator.calculatePassiveForceOnFootingFace();
        double ft = loadsDto.getTensileForce();
        double lf = geometry.getSideLengthOfFooting();
        double lc = geometry.getSideLenghtOfColumn();
        double vl = loadsDto.getHorizontalLongitudinalForce();
        double vt = loadsDto.getHorizontalTransverseForce();
        double hc_bg = geometry.getColumnHeightBelowGround();
        double hc_ag = geometry.getColumnHeightAboveGround();
        double hf = geometry.getHeightOfFooting();
        double d = soilDto.getWaterTableDepth();
        double h = geometry.getFoundationDepth();
        
        double w = weightOfFoundation;
        double sw = weightOFPyramidSoilFrustum;
        result.addReportLine("Weight of Foundation (W)", "=", String.format("%.2f m³", w));
        result.addReportLine("Weight of Soil (Sw)", "=", String.format("%.2f m³", sw));
        
        double v;
        if (vl > vt){
            v = vl;
        }else{
            v = vt;
        }
        
        double sigmaDashD;
        if(d > h){
            sigmaDashD = 0;
        }else{
            sigmaDashD = soilCalculator.calculateLateralPressure(d);
        }
       
        double sigmaHc = soilCalculator.calculateLateralPressure(hc_bg);
        double sigmaHf = soilCalculator.calculateLateralPressure(h);
        
        double leaverArmFc = 0;
        double leaverArmFc1 = 0;
        double leaverArmFc2 = 0;
        double leaverArmFf = 0;
        double leaverArmFf1 = 0;
        double leaverArmFf2 = 0;
//        if(d > hc_bg){
//            //Water table below column face
//            result.addReportLine("Force on Column Face, ");
//            leaverArmFc = soilCalculator.calculateLeaverArm(0, sigmaHc, hc_bg)+(hf);
//            if(d < h){
//                //Footing face spans water table
//                result.addReportLine("Force On Footing Face, ");
//                leaverArmFf1 = soilCalculator.calculateLeaverArm(sigmaHc, sigmaDashD, (d-hc_bg)) + (h - d);
//                leaverArmFf2 = soilCalculator.calculateLeaverArm(sigmaDashD, sigmaHf, h-d);
//            }else{
//                result.addReportLine("Force On Footing Face, ");
//                //Entire footing face above water table
//                leaverArmFf = soilCalculator.calculateLeaverArm(sigmaHc, sigmaHf, hf);
//            }
//        }else{
//            //water table cuts column face
//            result.addReportLine("Force on Column Face, ");
//            leaverArmFc1 = soilCalculator.calculateLeaverArm(0, sigmaDashD, d) + (h - d);
//            leaverArmFc2 = soilCalculator.calculateLeaverArm(sigmaDashD, sigmaHc, hc_bg - d) + (hf);
//            
//            result.addReportLine("Force On Footing Face, ");
//            leaverArmFf = soilCalculator.calculateLeaverArm(sigmaHc, sigmaHf, hf);
//        }
        
        double fc;
        double fc1;
        double fc2;
        double ff;
        double ff1;
        double ff2;
        double lateralMoment;
        if(d > hc_bg){
            //Water table below column face
            result.addReportLine("On Column Face, ");
            //fc = soilCalculator.calculatePassivePressure(0, sigmaHc, hc_bg)*lc;
            double pressureC = soilCalculator.calculatePassivePressure(0, sigmaHc, hc_bg);
            fc = pressureC *lc;
            result.addReportLine(String.format("           = %.2f x %.2f", pressureC, lc),"=", String.format("%.2f kN", fc));
            leaverArmFc = soilCalculator.calculateLeaverArm(0, sigmaHc, hc_bg)+(hf);
            if(d < h){
                //Footing face spans water table
                result.addReportLine("On Footing Face (Above water table), ");
                //ff1 = soilCalculator.calculatePassivePressure(sigmaHc, sigmaDashD, d - hc_bg)*lf;
                double pressureF1 = soilCalculator.calculatePassivePressure(sigmaHc, sigmaDashD, d - hc_bg);
                ff1 = pressureF1*lf;
                result.addReportLine(String.format("           = %.2f x %.2f", pressureF1, lf),"=", String.format("%.2f kN", ff1));
                leaverArmFf1 = soilCalculator.calculateLeaverArm(sigmaHc, sigmaDashD, (d-hc_bg)) + (h - d);
                
                result.addReportLine("On Footing Face (Below water table), ");
                //ff2 = soilCalculator.calculatePassivePressure(sigmaDashD, sigmaHf, h - d)*lf;
                double pressureF2 = soilCalculator.calculatePassivePressure(sigmaDashD, sigmaHf, h - d);
                ff2 = pressureF2*lf;
                result.addReportLine(String.format("           = %.2f x %.2f", pressureF2, lf),"=", String.format("%.2f kN", ff2));
                leaverArmFf2 = soilCalculator.calculateLeaverArm(sigmaDashD, sigmaHf, h-d);
                
                lateralMoment = fc*leaverArmFc + ff1*leaverArmFf1 + ff2*leaverArmFf2;
                result.addReportLine(String.format("Lateral Moment = %.2f x %.2f+%.2f x %.2f+%.2f x %.2f;", fc, leaverArmFc, ff1, leaverArmFf1, ff2, leaverArmFf2));
                
                
            }else{
                //Entire footing face above water table
                result.addReportLine("On Footing Face, ");
                //ff = soilCalculator.calculatePassivePressure(sigmaHc, sigmaHf, hf)*lf;
                double pressureF = soilCalculator.calculatePassivePressure(sigmaHc, sigmaHf, hf);
                ff = pressureF *lf;
                result.addReportLine(String.format("           = %.2f x %.2f", pressureF, lf),"=", String.format("%.2f kN", ff));
                leaverArmFf = soilCalculator.calculateLeaverArm(sigmaHc, sigmaHf, hf);
                
                lateralMoment = fc*leaverArmFc + ff*leaverArmFf;
                result.addReportLine(String.format("Lateral Moment = %.2f x %.2f+%.2f x %.2f;", fc, leaverArmFc, ff, leaverArmFf));
            }
        }else{
            //water table cuts column face
            result.addReportLine("On Column Face (Above water table), ");
            //fc1 = soilCalculator.calculatePassivePressure(0, sigmaDashD, d)*lc;
            double pressureC1 = soilCalculator.calculatePassivePressure(0, sigmaDashD, d);
            fc1 = pressureC1*lc;
            result.addReportLine(String.format("           = %.2f x %.2f", pressureC1, lc),"=", String.format("%.2f kN", fc1));
            leaverArmFc1 = soilCalculator.calculateLeaverArm(0, sigmaDashD, d) + (h - d);
            
            result.addReportLine("On Column Face (Below water table), ");
            //fc2 = soilCalculator.calculatePassivePressure(sigmaDashD, sigmaHc, hc_bg - d)*lc;
            double pressureC2 = soilCalculator.calculatePassivePressure(sigmaDashD, sigmaHc, hc_bg - d);
            fc2 = pressureC2*lc;
            result.addReportLine(String.format("           = %.2f x %.2f", pressureC2, lc),"=", String.format("%.2f kN", fc2));
            leaverArmFc2 = soilCalculator.calculateLeaverArm(sigmaDashD, sigmaHc, hc_bg - d) + (hf);
            
            result.addReportLine("On Footing Face , ");
            //ff = soilCalculator.calculatePassivePressure(sigmaHc, sigmaHf, hf)*lf;
            double pressureF = soilCalculator.calculatePassivePressure(sigmaHc, sigmaHf, hf);
            ff = pressureF*lf;
            result.addReportLine(String.format("           = %.2f x %.2f", pressureF, lf),"=", String.format("%.2f kN", ff));
            leaverArmFf = soilCalculator.calculateLeaverArm(sigmaHc, sigmaHf, hf);
            
            lateralMoment = fc1*leaverArmFc1 + fc2*leaverArmFc2 + ff*leaverArmFf;
            result.addReportLine(String.format("Lateral Moment = %.2f x %.2f+%.2f x %.2f+%.2f x %.2f;", fc1, leaverArmFc1, fc2, leaverArmFc2, ff, leaverArmFf));
        }
        
        
        double overturningMoment = ft*(lf/2)+ v*(h + hc_ag);
        
        double resistantMoment = (w + sw)*(lf/2) + lateralMoment;
        
        double fos = (resistantMoment/overturningMoment);
        result.addReportLine(String.format("Fos (>1.75) = %.2f/%.2f;", resistantMoment, overturningMoment), "=", String.format("%.2f", fos));
        System.out.println("fos Overturning = " + (resistantMoment/overturningMoment));
       
        boolean isSatisfied = resistantMoment/overturningMoment > 1.75;
        
        result.setResult(fos);
        
        result.setIsSatisfied(isSatisfied);
        result.addReportLine(isSatisfied? "Overturning Check Pass" : "Overturning Check Fail", "", "" );
        
        return result;

    }
    
    

    
    
}
