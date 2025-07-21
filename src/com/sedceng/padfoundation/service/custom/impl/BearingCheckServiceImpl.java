/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service.custom.impl;

import com.sedceng.padfoundation.dto.BearingDto;
import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.service.custom.BearingCheckService;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;

/**
 *
 * @author Sanduni Navoda
 */
public class BearingCheckServiceImpl implements BearingCheckService {

    @Override
    public ResultDto fosSatisfied(double foundationWeight, double rectangularSoilWeight, FoundationGeometryDto geometryDto, SoilPropertiesNewDto soilDto, SoilPressureCalculatorUtil soilCalculator, ServiceabilityLoadsDto serviceabilityLoadsDto, ResultDto result) throws Exception {
        soilCalculator.setResult(result);
        
        double sw = rectangularSoilWeight;
        double w = foundationWeight;
        double fc = serviceabilityLoadsDto.getCompressiveForce();
        result.addReportLine(" ");
        result.addReportLine("**Bearing Check**");
        result.addReportLine("Rectangular Soil Weight", "=", String.format("%.2f kN", sw));
        result.addReportLine("Foundation Weight", "=", String.format("%.2f kN", w));
        result.addReportLine("Compressive Force", "=", String.format("%.2f kN", fc));
        double u = soilCalculator.calculateUpthrustForce();
        double h = geometryDto.getHeightOfFooting();
        double d = soilDto.getWaterTableDepth();
        double sideLength = geometryDto.getSideLengthOfFooting();
        double vt = serviceabilityLoadsDto.getHorizontalTransverseForce();
        double vl = serviceabilityLoadsDto.getHorizontalLongitudinalForce();
        double hc_ag = geometryDto.getColumnHeightAboveGround();



        double sigmaC;
        if (h <= d) {
            //sigmaC = (sw + w + fc)/(wf*bf);
            sigmaC = (sw + w + fc) / (sideLength * sideLength);
        } else {
            //sigmaC = (sw + w + fc - u)/(wf*bf);
            sigmaC = (sw + w + fc - u) / (sideLength * sideLength);
        }



        double v;
        double x;
        double y;
        if (vt > vl) {
            v = vt;
            //x = bf;
            //y = wf;
        } else {
            v = vl;
            //x = wf;
            //y = bf;
        }

        //double sigmaCDash = (v * (h + hc_ag) * y * 12) / (2 * x * (y*y*y));
        double sigmaCDash = (v * (h + hc_ag) * sideLength * 12) / (2 * sideLength * (sideLength * sideLength * sideLength));

        double maximumPressureUnderBase = sigmaC + 2 * sigmaCDash;


        double bearingCapacity = soilDto.getBearingCapacity();

        double fos = (bearingCapacity / maximumPressureUnderBase);

        boolean isFosSatisfied = (soilDto.getBearingCapacity() / maximumPressureUnderBase) > 1.00;
        result.setResult(fos);
        result.setIsSatisfied(isFosSatisfied);
        
        result.addReportLine(String.format("Sigma C = (%.2f+%.2f+%.2f-%.2f)/(%.2f x %.2f);", sw, w, fc, u, sideLength, sideLength), "=", String.format("%.2f kN/m²", sigmaC));
        result.addReportLine(String.format("Sigma CDash = (%.2f x (%.2f+%.2f) x %.2f x 12)/(2 x %.2f x (%.2f x %.2f x %.2f));", v, h, hc_ag, sideLength, sideLength, sideLength, sideLength, sideLength), "=", String.format("%.2f kN/m²", sigmaCDash));
        result.addReportLine(String.format("Maximum Pressure Under Base = %.2f+2 x %.2f;", sigmaC, sigmaCDash), "=", String.format("%.2f kN/m²", maximumPressureUnderBase));
        result.addReportLine(String.format("Fos = %.2f/%.2f (>1.00) ", bearingCapacity, maximumPressureUnderBase), "=", String.format("%.2f", fos));
        result.addReportLine(isFosSatisfied? "Bearing Check Pass" : "Bearing Check Fail", "", "" );
        result.addReportLine(" ");

        return result;

    }

}
