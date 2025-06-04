/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.util;

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ReinforcementDto;

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
    
    
    public double calculateEffectiveDepth(){
        double hf = geometry.getHeightOfFooting();
        double cover = rfDto.getClearCover();
        double barDia = rfDto.getBarDiameter();
        
        return (hf * 1000) - cover - (0.5 * barDia);
    }
    
    
    public double dDash(){
        double cover = rfDto.getClearCover();
        double barDia = rfDto.getBarDiameter();
        return cover - 0.5 * barDia;
    }
    
    
    
    
   
}
