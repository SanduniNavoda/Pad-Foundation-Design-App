/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;


import com.sedceng.padfoundation.dto.FoundationGeometryDto;
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
    public boolean fosSatisfied(FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto loadsDto) throws Exception {
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
        
        double w = geometry.calculateWeightOfFoundation();
        double sw = soilCalculator.calculatePyramidSoilWeight();
        
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
        if(d > hc_bg){
            //Water table below column face
            leaverArmFc = soilCalculator.calculateLeaverArm(0, sigmaHc, hc_bg)+(hf);
            if(d < h){
                //Footing face spans water table
                leaverArmFf1 = soilCalculator.calculateLeaverArm(sigmaHc, sigmaDashD, (d-hc_bg)) + (h - d);
                leaverArmFf2 = soilCalculator.calculateLeaverArm(sigmaDashD, sigmaHf, h-d);
            }else{
                //Entire footing face above water table
                leaverArmFf = soilCalculator.calculateLeaverArm(sigmaHc, sigmaHf, hf);
            }
        }else{
            //water table cuts column face
            leaverArmFc1 = soilCalculator.calculateLeaverArm(0, sigmaDashD, d) + (h - d);
            leaverArmFc2 = soilCalculator.calculateLeaverArm(sigmaDashD, sigmaHc, hc_bg - d) + (hf);
            leaverArmFf = soilCalculator.calculateLeaverArm(sigmaHc, sigmaHf, hf);
        }
        
        double fc;
        double fc1;
        double fc2;
        double ff;
        double ff1;
        double ff2;
        double lateralMoment;
        if(d > hc_bg){
            //Water table below column face
            fc = soilCalculator.calculatePassivePressure(0, sigmaHc, hc_bg)*lc;
            
            if(d < h){
                //Footing face spans water table
                ff1 = soilCalculator.calculatePassivePressure(sigmaHc, sigmaDashD, d - hc_bg)*lf;
                ff2 = soilCalculator.calculatePassivePressure(sigmaDashD, sigmaHf, h - d)*lf;
                lateralMoment = fc*leaverArmFc + ff1*leaverArmFf1 + ff2*leaverArmFf2;
                
            }else{
                //Entire footing face above water table
                ff = soilCalculator.calculatePassivePressure(sigmaHc, sigmaHf, hf)*lf;
                lateralMoment = fc*leaverArmFc + ff*leaverArmFf;
            }
        }else{
            //water table cuts column face
            fc1 = soilCalculator.calculateLeaverArm(0, sigmaDashD, d)*lc;
            fc2 = soilCalculator.calculateLeaverArm(sigmaDashD, sigmaHc, hc_bg - d)*lc;
            ff = soilCalculator.calculatePassivePressure(sigmaHc, sigmaHf, hf)*lf;
            lateralMoment = fc1*leaverArmFc1 + fc2*leaverArmFc2 + ff*leaverArmFf;
        }
        
        
        double overturningMoment = ft*(lf/2)+ v*(h + hc_ag);
        
        double resistantMoment = (w + sw)*(lf/2) + lateralMoment;
        
        System.out.println("fos Overturning = " + (resistantMoment/overturningMoment));
        return resistantMoment/overturningMoment > 1.75;
        
        
    }
    
    

    
    
}
