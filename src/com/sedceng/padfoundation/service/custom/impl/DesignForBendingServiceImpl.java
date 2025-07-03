/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ReinforcementDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.UltimateLoadsDto;
import com.sedceng.padfoundation.service.custom.DesignForBendingService;
import com.sedceng.padfoundation.util.ReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;

/**
 *
 * @author Sanduni Navoda
 */
public class DesignForBendingServiceImpl implements DesignForBendingService {

    @Override
    public double CriticalBendingMomentAboutTheFaceOfTheColumn(SoilPropertiesNewDto soilDto,
            FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator, UltimateLoadsDto loadsDto,
            double axialForce, ResultDto result) {
        result.addReportLine(" ");
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
        if (d > h) {
            sigmaC = axialForce / (wf * wf);
            result.addReportLine(String.format("Pressure due to Axial Compression (Sigma C) = %.2f/(%.2f x %.2f)",
                    axialForce, wf, wf), "=", String.format("%.2f kN/m²", sigmaC));
        } else {
            sigmaC = (axialForce + u) / (wf * wf);
            result.addReportLine(
                    String.format("Pressure due to Axial Compression (Sigma C) = (%.2f+%.2f)/(%.2f x %.2f)", axialForce,
                            u, wf, wf),
                    "=", String.format("%.2f kN/m²", sigmaC));
        }

        double sigmaCDashVl = (12 * vl * (hc_ag + hc_bg)) / (2 * wf * wf * wf);
        double sigmaCDashVt = (12 * vt * (hc_ag + hc_bg)) / (2 * wf * wf * wf);
        result.addReportLine(String.format(
                "Pressure due to Longitudinal Shear (Sigma CDash Vl) = (12 x %.2f x (%.2f+%.2f))/(2 x %.2f x %.2f x %.2f)",
                vl, hc_ag, hc_bg, wf, wf, wf), "=", String.format("%.2f kN/m²", sigmaCDashVl));
        result.addReportLine(String.format(
                "Pressure due to Transverse Shear (Sigma CDash Vt) = (12 x %.2f x (%.2f+%.2f))/(2 x %.2f x %.2f x %.2f)",
                vt, hc_ag, hc_bg, wf, wf, wf), "=", String.format("%.2f kN/m²", sigmaCDashVt));

        double sigmaMin = sigmaC - sigmaCDashVl - sigmaCDashVt;
        double sigmaMax = sigmaC + sigmaCDashVl + sigmaCDashVt;
        result.addReportLine(String.format("Minimum Pressure Under Base (Sigma Min) = %.2f-%.2f-%.2f", sigmaC,
                sigmaCDashVl, sigmaCDashVt), "=", String.format("%.2f kN/m²", sigmaMin));
        result.addReportLine(String.format("Maximum Pressure Under Base (Sigma Max) = %.2f+%.2f+%.2f", sigmaC,
                sigmaCDashVl, sigmaCDashVt), "=", String.format("%.2f kN/m²", sigmaMax));

        double y = (wf - wc) * 0.5;
        double sigmaCr = sigmaMin + (sigmaMax - sigmaMin) * (wf - y) / wf;
        result.addReportLine(String.format("y = (%.2f-%.2f) x 0.5;", wf, wc), "=", String.format("%.2f m", y));
        result.addReportLine(String.format("Sigma Cr = %.2f+(%.2f-%.2f) x (%.2f-%.2f)/%.2f", sigmaMin, sigmaMax,
                sigmaMin, wf, y, wf), "=", String.format("%.2f kN/m²", sigmaCr));

        double forceAtCriticalPortion = 0.5 * (sigmaCr + sigmaMax) * y * wf;
        double distanceFromFaceOfColumn = (2 * y / 3) * ((2 * sigmaCr + sigmaMax) / (sigmaCr + sigmaMax));
        result.addReportLine(String.format("Force At Critical Portion(Fcr) = 0.5 x (%.2f+%.2f) x %.2f x %.2f", sigmaCr,
                sigmaMax, y, wf), "=", String.format("%.2f kN", forceAtCriticalPortion));
        result.addReportLine(
                String.format("Distance From Face Of Column = (2 x %.2f/3) x ((2 x %.2f+%.2f)/(%.2f+%.2f))", y, sigmaCr,
                        sigmaMax, sigmaCr, sigmaMax),
                "=", String.format("%.2f m", distanceFromFaceOfColumn));

        double mCr = forceAtCriticalPortion * distanceFromFaceOfColumn;
        result.addReportLine(String.format("CriticalBendingMomentAboutTheFaceOfTheColumn (Mcr) = %.2f x %.2f",
                forceAtCriticalPortion, distanceFromFaceOfColumn), "=", String.format("%.2f kNm", mCr));
        return mCr;
    }

    @Override
    public double calculateKValue(double sideLengthOfFooting, double effectiveDepth, double concreteGrade, double mCr,
            ResultDto result) {
        double wf = sideLengthOfFooting;
        double d = effectiveDepth;
        double fcu = concreteGrade;

        double k = mCr * 1000 / (wf * d * d * fcu);
        result.addReportLine(String.format("k = %.2f x 1000/(%.2f x %.2f x %.2f x %.2f)", mCr, wf, d, d, fcu), "=",
                String.format("%.2f", k));

        return k;
    }

    public double calculateLeverArm(double sideLenghtOfFooting, double concreteGrade, double effectiveDepth, double mCr,
            double k, ResultDto result) {

        double wf = sideLenghtOfFooting;
        double fcu = concreteGrade;

        double d = effectiveDepth;

        double kDash = 0.156;
        double z;

        if (k > kDash) {
            z = d * (0.5 + Math.sqrt(0.25 - (kDash) / 0.9));
            result.addReportLine(String.format("Leaver arm (z) = %.2f x (0.5 + sqrt(0.25-(%.2f)/0.9));", d, kDash), "=", String.format("%.2f m", z));
        } else {
            z = d * (0.5 + Math.sqrt(0.25 - (k / 0.9)));
            result.addReportLine(String.format("Leaver arm (z) = %.2f x (0.5 + sqrt(0.25-(%.2f/0.9)));", d, k), "=", String.format("%.2f m", z));
            if (z > (0.95 * d)) {
                z = 0.95 * d;
                result.addReportLine(String.format("Leaver arm (z) = 0.95 x %.2f;", d), "=", String.format("%.2f m", z));
            }
        }

        return z;
    }

    public double calculateNutralAxisDepth(double effectiveDepth, double z) {
        double d = effectiveDepth;

        return (d - z) / 0.45;
    }

    @Override
    public double compressionReinforcementRequirement(SoilPropertiesNewDto soilDto, ReinforcementDto rfDto,
            FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator, UltimateLoadsDto loadsDto,
            ReinforcementCalculatorUtil rfCal, double axialForce, double mCr, double d, double k, ResultDto result) {
        double wf = geometry.getSideLengthOfFooting();

        double fcu = rfDto.getGradeOfConcrete();
        double fy = rfDto.getYieldStrenghtOfReinforcement();

        double kDash = 0.156;
        result.addReportLine("K Dash", "=", String.format("%.2f", kDash));

        double z = calculateLeverArm(wf, fcu, d, mCr, k, result);
        double x = calculateNutralAxisDepth(d, z);
        double dDash = rfCal.dDash(result);

        if (k > kDash) {
            double aDashS = ((k - kDash) * fcu * wf * d * d) / (0.95 * fy * (d - dDash));
            result.addReportLine(String.format(
                    "Compressive r/f Requirement (A's)= ((%.2f-%.2f) x %.2f x %.2f x %.2f x %.2f)/(0.95 x %.2f x (%.2f-%.2f));",
                    k, kDash, fcu, wf, d, d, fy, d, dDash), "=", String.format("%.2f mm²", aDashS));
            return aDashS;
        } else {
            double aDashS = 0;
            result.addReportLine(String.format("Compressive r/f Requirement (A's)"), "=",
                    String.format("%.2f mm²", aDashS));
            return aDashS;
        }
    }

    @Override
    public double tensionReinforcementRequirement(SoilPropertiesNewDto soilDto, ReinforcementDto rfDto,
            FoundationGeometryDto geometry, SoilPressureCalculatorUtil soilCalculator, UltimateLoadsDto loadsDto,
            ReinforcementCalculatorUtil rfCal, double axialForce, double asDash, double d, double mCr, double k, ResultDto result) {
        double wf = geometry.getSideLengthOfFooting();

        double fcu = rfDto.getGradeOfConcrete();
        double fy = rfDto.getYieldStrenghtOfReinforcement();

        //double d = rfCal.calculateEffectiveDepth();
        //double mCr = CriticalBendingMomentAboutTheFaceOfTheColumn(soilDto, geometry, soilCalculator, loadsDto,
                //axialForce);

        //double k = calculateKValue(wf, d, fcu, mCr);
        double kDash = 0.156;

        double z = calculateLeverArm(wf, fcu, d, mCr, k, result);
        
        double asReq = 0;
        if (k > kDash) {
            asReq = ((kDash * fcu * wf * d * d) / (0.95 * fy * z) + asDash);
            result.addReportLine(String.format("As Req = ((%.2f x %.2f x %.2f x %.2f x %.2f)/(0.95 x %.2f x %.2f)+%.2f);", kDash, fcu, wf, d, d, fy, z, asDash), "=", String.format("%.2f mm²", asReq));

            return asReq;
        } else {
            asReq = (mCr * 1000000) / (0.95 * fy * z);
            result.addReportLine(String.format("As Req = (%.2f x 1000000)/(0.95 x %.2f x %.2f);", mCr, fy, z), "=", String.format("%.2f mm²", asReq));
            return asReq;
        }
    }

    public double maximumSpacingOfReinforcement(double effectiveDepth, ResultDto result) {
        double maxSpacing = Math.min(750, (3 * effectiveDepth));
        result.addReportLine(String.format("Maximum Spacing = min of (750 , 3 x %.2f)", effectiveDepth), "=",
                String.format("%.2f mm", maxSpacing));
        return maxSpacing;
    }

    @Override
    public String TensionOrCompressionReinforcement(double asRequired, ReinforcementDto rfDto,
            FoundationGeometryDto geometry, ReinforcementCalculatorUtil rfUtil, double d, ResultDto result) {
        double diameter = rfDto.getBarDiameter();
        double footingLength = 1000 * geometry.getSideLengthOfFooting();
        double footingHeight = 1000 * geometry.getHeightOfFooting();

        // Calculate minimum reinforcement required
        double minimumAs = 0.13 * (footingLength * footingHeight) / 100;

        // Use the larger of required reinforcement or minimum reinforcement
        double finalAsRequired = Math.max(asRequired, minimumAs);

        // Start with maximum spacing and work downwards
        int spacing = (int) (maximumSpacingOfReinforcement(d, result) / 25) * 25;

        while (spacing >= 100) {
            // Calculate number of bars for current spacing
            int noOfBars = (int) Math.ceil(footingLength / spacing);

            // Calculate actual reinforcement area provided
            double as = (Math.PI / 4) * (diameter * diameter) * noOfBars;

            String rfDesign = "";
            // Check if provided reinforcement is sufficient
            if (as >= finalAsRequired) {
                // Found adequate spacing
                rfDesign = "T" + (int) diameter + "@" + spacing + "C/C";
                result.addReportLine("","","**"+rfDesign+"**");
                return rfDesign;
            }

            // Reduce spacing by 25mm and try again
            spacing -= 25;
            
        }

        // If we reach here, even minimum spacing (100mm) is not sufficient
        return "Bar diameter needs to be increased. Required spacing is below 100mm.";

    }

    @Override
    public double getAsProvided(
            double asRequired,
            ReinforcementDto rfDto,
            FoundationGeometryDto geometry,
            ReinforcementCalculatorUtil rfUtil,
            double d,
            ResultDto result) {
        if (asRequired == 0) {
            return 0;
        }
        double diameter = rfDto.getBarDiameter();// in mm
        double footingLength = 1000 * geometry.getSideLengthOfFooting();// mm
        double footingHeight = 1000 * geometry.getHeightOfFooting();// mm

        // Calculate minimum reinforcement required
        double minimumAs = 0.13 * (footingLength * footingHeight) / 100;
        result.addReportLine(String.format("Minimum As = 0.13 x (%.2f x %.2f)/100;", footingLength, footingHeight), "=",
                String.format("%.2f mm²", minimumAs));

        // Use the larger of required reinforcement or minimum reinforcement
        double finalAsRequired = Math.max(asRequired, minimumAs);
        result.addReportLine(String.format("As Required = max of (%.2f , %.2f)", asRequired, minimumAs), "=",
                String.format("%.2f mm²", finalAsRequired));
        // Round spacing down to nearest 25 mm
        int spacing = (int) (maximumSpacingOfReinforcement(d, result) / 25) * 25;

        double asProvided = 0;
        double noOfBars = 0;
        while (spacing >= 100) {
            noOfBars = (int) Math.ceil(footingLength / spacing);
            result.addReportLine("No of Bars", "=", noOfBars + "");
            asProvided = ((Math.PI) / 4) * (diameter * diameter) * noOfBars;

            if (asProvided >= finalAsRequired) {
                result.addReportLine(
                        String.format("As Provided = (phi/4) x (%.2f x %.2f) x %.2f;", diameter, diameter, noOfBars),
                        "=", String.format("%.2f mm²", asProvided));
                return asProvided;
            }

            spacing -= 25;// reduce spacing and try again
            int size = result.getData().size();
            if (size >= 1) {
                result.getData().remove(size - 1); // Removes the most recently added item
                 // Removes the one added before that
            }

        }

        // If loop exits without returning, spacing dropped below 100 mm
        throw new IllegalArgumentException("Bar diameter needs to be increased. Required spacing is below 100 mm.");

    }

}
