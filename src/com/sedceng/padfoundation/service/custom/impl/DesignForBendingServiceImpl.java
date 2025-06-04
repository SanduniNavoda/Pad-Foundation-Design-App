/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.UltimateLoadsDto;
import com.sedceng.padfoundation.dto.ReinforcementDto;
import com.sedceng.padfoundation.service.custom.BearingCheckService;
import com.sedceng.padfoundation.service.custom.DesignForBendingService;
import com.sedceng.padfoundation.util.ReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;

/**
 *
 * @author Sanduni Navoda
 */
public class DesignForBendingServiceImpl implements DesignForBendingService{

    public double CriticalBendingMomentAboutTheFaceOfTheColumn(SoilPropertiesNewDto soilDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator,UltimateLoadsDto loadsDto, double axialForce){
        double d = soilDto.getWaterTableDepth();
        double h = geometry.getFoundationDepth();
        double wf = geometry.getSideLengthOfFooting();
        double wc = geometry.getSideLenghtOfColumn();
        double u = soilCalculator.calculateUpthrustForce();
        double vl = loadsDto.getHorizontalLongitudinalForce();
        double vt = loadsDto.getHorizontalTransverseForce();
        double hc_ag = geometry.getColumnHeightAboveGround();
        double hc_bg = geometry.getColumnHeightBelowGround();
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
        
        double y = (wf - wc)*0.5;
        double sigmaCr = sigmaMin + (sigmaMax - sigmaMin)*(wf - y)/wf;
        
        double forceAtCriticalPortion = 0.5 * (sigmaCr + sigmaMax) * y * wf;
        double distanceFromFaceOfColumn = (2*y/3) * ((2*sigmaCr + sigmaMax)/(sigmaCr + sigmaMax));
        
        return forceAtCriticalPortion * distanceFromFaceOfColumn;
    }
    
    public double calculateKValue(double sideLengthOfFooting, double effectiveDepth, double concreteGrade, double mCr){
        double wf = sideLengthOfFooting;
        double d = effectiveDepth;
        double fcu = concreteGrade;
        return mCr * 1000/(wf * d * d * fcu);
    }
    
    public double calculateLeverArm(double sideLenghtOfFooting, double concreteGrade, double effectiveDepth, double mCr){

        double wf = sideLenghtOfFooting; 
        double fcu = concreteGrade;
                
        double d = effectiveDepth;
        
        double k = calculateKValue(wf, d, fcu, mCr);
        double kDash = 0.156;
        double z;
        
        if (k > kDash){
            z = d * (0.5 + Math.sqrt(0.25 - (kDash)/0.9));
        }else {
            z = d * (0.5 + Math.sqrt(0.25 - (k/0.9)));
            if (z > (0.95 * d)){
                z = 0.95 * d;
            }
        }
        
        return z;
    }
   
    public double calculateNutralAxisDepth(double effectiveDepth, double z){
        double d = effectiveDepth;
        
        return (d - z)/0.45;
    }
    
    @Override
    public double compressionReinforcementRequirement(SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator,UltimateLoadsDto loadsDto, ReinforcementCalculatorUtil rfCal, double axialForce){
        double wf = geometry.getSideLengthOfFooting(); 
        
        double fcu = rfDto.getGradeOfConcrete();
        double fy = rfDto.getYieldStrenghtOfReinforcement();
                
        double d = rfCal.calculateEffectiveDepth();
        double mCr = CriticalBendingMomentAboutTheFaceOfTheColumn(soilDto, geometry, soilCalculator,loadsDto, axialForce);
        
        double k = calculateKValue(wf, d, fcu, mCr);
        double kDash = 0.156;
        
        
        double z = calculateLeverArm(wf, fcu, d, mCr);
        double x = calculateNutralAxisDepth(d, z);
        double dDash = rfCal.dDash();
        
        if (k > kDash){
            return ((k - kDash)*fcu*wf*d*d)/(0.95*fy*(d - dDash));
        }else {
            return 0;
        }
    }
    
    @Override
    public double tensionReinforcementRequirement(SoilPropertiesNewDto soilDto, ReinforcementDto rfDto, FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator,UltimateLoadsDto loadsDto, ReinforcementCalculatorUtil rfCal, double axialForce, double asDash){
        double wf = geometry.getSideLengthOfFooting(); 
        
        double fcu = rfDto.getGradeOfConcrete();
        double fy = rfDto.getYieldStrenghtOfReinforcement();
                
        double d = rfCal.calculateEffectiveDepth();
        double mCr = CriticalBendingMomentAboutTheFaceOfTheColumn(soilDto, geometry, soilCalculator,loadsDto, axialForce);
        
        double k = calculateKValue(wf, d, fcu, mCr);
        double kDash = 0.156;
        
        
        double z = calculateLeverArm(wf, fcu, d, mCr);
        
        
        if (k > kDash){
            return ((kDash*fcu*wf*d*d)/(0.95*fy*z) + asDash);
        }else {
            return (mCr*1000000)/(0.95*fy*z);
        }
    }
    
    public double maximumSpacingOfReinforcement(double effectiveDepth){
        return Math.min(750, (3 * effectiveDepth));
    }
    
    @Override
    public String TensionOrCompressionReinforcement(double asRequired, ReinforcementDto rfDto, FoundationGeometryDto geometry, ReinforcementCalculatorUtil rfUtil){
        double diameter = rfDto.getBarDiameter();
        double footingLength = 1000 * geometry.getSideLengthOfFooting();
        double footingHeight = 1000 * geometry.getHeightOfFooting();
        double d = rfUtil.calculateEffectiveDepth();
        int spacing = (int) (maximumSpacingOfReinforcement(d) / 25) * 25;
        
        while (spacing >= 100){
            int noOfBars = (int) Math.ceil(footingLength / spacing);
            double as = ((Math.PI)/4)*(diameter * diameter)*noOfBars;
            double minimumAs = 0.13*(footingLength*footingHeight)/100;
            if (as < minimumAs){
                as = minimumAs;
            }
            
            if (as >= asRequired){
                break;
            }
            
            spacing -= 25;
  
        }
        
        if (spacing < 100){
            return "Bar diameter needs to be increased. Required spacing is below 100mm.";
        }
        
        // otherwise, spacing is valid
        return "T" + (int)diameter+ "@" + spacing + "C/C";
        
    } 
    
    @Override
    public double getAsProvided(
            double asRequired, 
            ReinforcementDto rfDto, 
            FoundationGeometryDto geometry, 
            ReinforcementCalculatorUtil rfUtil){
        if (asRequired == 0) return 0;
        double diameter = rfDto.getBarDiameter();//in mm
        double footingLength = 1000 * geometry.getSideLengthOfFooting();//mm
        double footingHeight = 1000 * geometry.getHeightOfFooting();//mm
        double d = rfUtil.calculateEffectiveDepth();//mm
        
        // Round spacing down to nearest 25 mm
        int spacing = (int) (maximumSpacingOfReinforcement(d) / 25) * 25;
        
        double asProvided;
        while (spacing >= 100){
            int noOfBars = (int) Math.ceil(footingLength / spacing);
            asProvided = ((Math.PI)/4)*(diameter * diameter)*noOfBars;
            
            double minimumAs = 0.13*(footingLength*footingHeight)/100;//mm2
            
            if (asProvided < minimumAs){
                asProvided = minimumAs;
            }
            
            if (asProvided >= asRequired){
                return asProvided;
            }
            
            spacing -= 25;// reduce spacing and try again
  
        }
        
        // If loop exits without returning, spacing dropped below 100 mm
        throw new IllegalArgumentException("Bar diameter needs to be increased. Required spacing is below 100 mm.");
        
    } 
    
}
