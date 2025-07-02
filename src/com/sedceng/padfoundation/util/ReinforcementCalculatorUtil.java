/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.util;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ReinforcementDto;
import com.sedceng.padfoundation.dto.ResultDto;

/**
 *
 * @author Sanduni Navoda
 */
public class ReinforcementCalculatorUtil {
    
    private ReinforcementDto rfDto;
    private FoundationGeometryDto geometry;

    public ReinforcementCalculatorUtil(ReinforcementDto rfDto, FoundationGeometryDto geometry) {
        this.rfDto = rfDto;
        this.geometry = geometry;
    }
    
    
    public double calculateEffectiveDepth(ResultDto result){
        double hf = geometry.getHeightOfFooting();
        double cover = rfDto.getClearCover();
        double barDia = rfDto.getBarDiameter();
        
        double d = (hf * 1000) - cover - (0.5 * barDia);
        result.addReportLine(String.format("Effective Depth (d) = (%.2f x 1000)-%.2f-(0.5 x %.2f);", hf, cover, barDia), "=", String.format("%.2f", d));
        return d;
    }
    
    
    public double dDash(ResultDto result){
        double cover = rfDto.getClearCover();
        double barDia = rfDto.getBarDiameter();
        
        double dDash = cover + 0.5 * barDia;
        result.addReportLine(String.format("d Dash = %.2f+0.5 x %.2f;", cover, barDia), "=", String.format("%.2f", dDash));
        return dDash;
    }


    
    
    
    
    
    
   
}
