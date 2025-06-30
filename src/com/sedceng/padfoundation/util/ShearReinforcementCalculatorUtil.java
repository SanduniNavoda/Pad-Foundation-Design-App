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
public class ShearReinforcementCalculatorUtil {
    
    private final double[] AS_OVER_BVD = {0.15, 0.25, 0.50, 0.75, 1.00, 1.50, 2.00, 3.00};
    private final int[] DEPTHS = {125, 150, 175, 200, 225, 250, 300, 400};
    private ResultDto result;

    private final double[][] VC_VALUES = {
        {0.45, 0.43, 0.41, 0.40, 0.39, 0.38, 0.36, 0.34},
        {0.53, 0.51, 0.49, 0.47, 0.46, 0.45, 0.43, 0.40},
        {0.67, 0.64, 0.62, 0.60, 0.58, 0.56, 0.54, 0.50},
        {0.77, 0.73, 0.71, 0.68, 0.66, 0.65, 0.62, 0.57},
        {0.84, 0.81, 0.78, 0.75, 0.73, 0.71, 0.68, 0.63},
        {0.97, 0.92, 0.89, 0.86, 0.83, 0.81, 0.78, 0.72},
        {1.06, 1.02, 0.98, 0.95, 0.92, 0.89, 0.86, 0.80},
        {1.22, 1.16, 1.12, 1.08, 1.05, 1.02, 0.98, 0.91}
    };

    public double getVc(double asInput, double depthInput) {
        int lastCol = DEPTHS.length - 1;

        // Handle asInput ≤ 0.15 (use first row only)
        if (asInput <= AS_OVER_BVD[0]) {
            // Clamp depth between two columns
            int leftCol = 0;
            while (leftCol < lastCol && DEPTHS[leftCol + 1] < depthInput) {
                leftCol++;
            }

            int rightCol = Math.min(leftCol + 1, lastCol);
            double y1 = DEPTHS[leftCol];
            double y2 = DEPTHS[rightCol];

            double Q1 = VC_VALUES[0][leftCol];
            double Q2 = VC_VALUES[0][rightCol];

            if (y2 == y1) {
                return Q1;
            }
            return Q1 + (Q2 - Q1) * ((depthInput - y1) / (y2 - y1));
        }

        // Find row indices
        int lowerRow = 0;
        while (lowerRow + 1 < AS_OVER_BVD.length && AS_OVER_BVD[lowerRow + 1] < asInput) {
            lowerRow++;
        }
        int upperRow = Math.min(lowerRow + 1, AS_OVER_BVD.length - 1);

        // For depth > 400: use last column, vertical interpolation only
        if (depthInput >= DEPTHS[lastCol]) {
            double x1 = AS_OVER_BVD[lowerRow];
            double x2 = AS_OVER_BVD[upperRow];

            double Q1 = VC_VALUES[lowerRow][lastCol];
            double Q2 = VC_VALUES[upperRow][lastCol];

            if (x2 == x1) {
                return Q1;
            }
            return Q1 + (Q2 - Q1) * ((asInput - x1) / (x2 - x1));
        }

        // Otherwise, do bilinear interpolation
        int leftCol = 0;
        while (leftCol + 1 < DEPTHS.length && DEPTHS[leftCol + 1] < depthInput) {
            leftCol++;
        }
        int rightCol = leftCol + 1;

        double x1 = AS_OVER_BVD[lowerRow];
        double x2 = AS_OVER_BVD[upperRow];
        double y1 = DEPTHS[leftCol];
        double y2 = DEPTHS[rightCol];

        double Q11 = VC_VALUES[lowerRow][leftCol];
        double Q12 = VC_VALUES[lowerRow][rightCol];
        double Q21 = VC_VALUES[upperRow][leftCol];
        double Q22 = VC_VALUES[upperRow][rightCol];

        double denom = (x2 - x1) * (y2 - y1);
        if (denom == 0) {
            return Q11; // avoid division by zero
        }
        return 1.0 / denom * (Q11 * (x2 - asInput) * (y2 - depthInput)
                + Q21 * (asInput - x1) * (y2 - depthInput)
                + Q12 * (x2 - asInput) * (depthInput - y1)
                + Q22 * (asInput - x1) * (depthInput - y1));
    }

    // Interpolate only across depth row (used for ≤0.15 or ≥3.00)
    private double interpolateDepthOnly(int row, double depthInput) {
        if (depthInput <= DEPTHS[0]) return VC_VALUES[row][0];
        if (depthInput >= DEPTHS[DEPTHS.length - 1]) return VC_VALUES[row][DEPTHS.length - 1];

        int leftCol = 0;
        while (DEPTHS[leftCol + 1] < depthInput) {
            leftCol++;
        }
        int rightCol = leftCol + 1;

        double x1 = DEPTHS[leftCol];
        double x2 = DEPTHS[rightCol];
        double y1 = VC_VALUES[row][leftCol];
        double y2 = VC_VALUES[row][rightCol];

        return y1 + ((depthInput - x1) / (x2 - x1)) * (y2 - y1);
    }
    
    public String getShearReinforcementConfig(double asvPerMm, int numLegs, double barDiameter) {
        // Calculate area of a single bar (πd²/4)
        double areaOfOneBar = Math.PI * Math.pow(barDiameter, 2) / 4.0; // mm²

        // Calculate spacing (mm)
        double spacing = (areaOfOneBar * numLegs) / asvPerMm;

        // Round spacing to nearest 25 mm step (common practice)
        int roundedSpacing = (int) (Math.round(spacing / 25.0) * 25);

        // Limit spacing (you may want to validate this against code limits)
        if (roundedSpacing > 300) roundedSpacing = 300;
        if (roundedSpacing < 75) roundedSpacing = 75;

        return numLegs + "T" + (int)barDiameter + "@" + roundedSpacing;
    }

    /**
     * @return the result
     */
    public ResultDto getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(ResultDto result) {
        this.result = result;
    }
    
    
}
