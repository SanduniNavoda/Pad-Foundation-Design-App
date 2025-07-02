/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.sedceng.padfoundation.view;

import com.sedceng.padfoundation.controller.UserInputController;
import com.sedceng.padfoundation.controller.UserInputControllerForReinforcementDesign;
import com.sedceng.padfoundation.dto.BearingDto;
import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ProjectDetailDto;
import com.sedceng.padfoundation.dto.ReinforcementDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SlidingDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.UltimateLoadsDto;
import com.sedceng.padfoundation.dto.UprootingDto;
import com.sedceng.padfoundation.util.ReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.ShearReinforcementCalculatorUtil;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;
import com.sedceng.padfoundation.util.ValidationUtil;
import java.awt.BorderLayout;
import java.awt.Image;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Sanduni Navoda
 */
public class SoilReportView extends javax.swing.JFrame {
    
    private UserInputController inputController;
    private UserInputControllerForReinforcementDesign reinforcementInputController;
    private ProjectDetailDto projectDetailDto;
    private SoilPropertiesNewDto soilDto;
    private FoundationGeometryDto geometryDto;
    private ServiceabilityLoadsDto serviceabilityLoadsDto;
    private UltimateLoadsDto ultimateLoadsDto;
    private ReinforcementDto reinforcementDto;
    private ResultDto slidingResultDto;
    private ResultDto bearingResultDto;
    private ResultDto uprootingResultDto;
    private ResultDto overturningResultDto;
    private ResultDto soilCalculatorResultDto;
    private ResultDto reinforcementDesignResultDto;
    private ResultDto designForCompInSaggingResultDto;
    private ResultDto designForCompInHoggingResultDto;
    private ResultDto designForTensInSaggingResultDto;
    private ResultDto designForTenseInHoggingResultDto;
    private ResultDto designForShearResultDto;
    private SoilPressureCalculatorUtil soilCalculator;
    /**
     * Creates new form GuestView
     */
    public SoilReportView() {
        inputController = new UserInputController();
        reinforcementInputController = new UserInputControllerForReinforcementDesign();
        soilCalculatorResultDto = new ResultDto();
        initComponents();
        
        
        FoundationDrawingPanel foundationPanel = new FoundationDrawingPanel();
        foundationPanel.setPreferredSize(ImagePanel.getSize());
        ImagePanel.setLayout(new BorderLayout());
        ImagePanel.add(foundationPanel, BorderLayout.CENTER);
        ImagePanel.revalidate();
        ImagePanel.repaint();
        
        addInputListeners();
        
        ValidationUtil.applyDoubleFilterToAllTextFields(this);
        setupTabChangeListener();
        
        rbNo.addActionListener(e
                -> {
            depthOfWaterTable.setVisible(false);
            tfWaterTableDepth.setVisible(false);
            jLabel36.setVisible(false);

            SaturatedUnitWeightOfSoil.setVisible(false);
            tfSaturatedUnitWeight.setVisible(false);
            jLabel37.setVisible(false);

            UnitWeightOfWater.setVisible(false);
            gammaWat.setVisible(false);
            jLabel38.setVisible(false);

            tfWaterTableDepth.setText(String.valueOf(Integer.MAX_VALUE));
        }
        );

        rbYes.addActionListener(e
                -> {
            depthOfWaterTable.setVisible(true);
            tfWaterTableDepth.setVisible(true);
            jLabel36.setVisible(true);

            SaturatedUnitWeightOfSoil.setVisible(true);
            tfSaturatedUnitWeight.setVisible(true);
            jLabel37.setVisible(true);

            UnitWeightOfWater.setVisible(true);
            gammaWat.setVisible(true);
            jLabel38.setVisible(true);

            tfWaterTableDepth.setText(""); // Clear or reset as needed
        }
        );
        
        rbNo.addActionListener(e -> {
            foundationPanel.setShowGroundWaterLine(false); // hides the line
            dwgWaterTableDepth2.setVisible(false);
            dwgWaterTableDepth.setVisible(false);
        });
        
        rbYes.addActionListener(e -> {
            foundationPanel.setShowGroundWaterLine(true); // shows the line
            dwgWaterTableDepth2.setVisible(true);
            dwgWaterTableDepth.setVisible(true);
            
        });
        
        btnGetReport.addActionListener(e -> {
            setProjectDetails();
            ReportFrame1 report = new ReportFrame1(projectDetailDto,geometryDto,soilCalculator, soilDto, serviceabilityLoadsDto, ultimateLoadsDto, bearingResultDto, uprootingResultDto, slidingResultDto, overturningResultDto, reinforcementDto);
            report.setVisible(true);
        });
    }
    
    private void setupTabChangeListener() {

        jTabbedPane1.addChangeListener(e -> {
            
            SoilPropertiesNewDto dto = new SoilPropertiesNewDto();
            dto.setBearingCapacity(Double.parseDouble(tfBearignCapacity.getText()));
            //dto.setBreadthOfColumn(Double.parseDouble(tfBearignCapacity.getText()));
            dto.setBulkUnitWeight(Double.parseDouble(tfUnitWeightOfSoil.getText()));
            dto.setCohesion(Double.parseDouble(tfCohesion.getText()));
            dto.setFrictionAngleWithFoundation(Double.parseDouble(tfFrictionAngleFoundationAndSoil.getText()));
            dto.setInternalFrictionAngle(Double.parseDouble(tfInternalFrictionAngle.getText()));
            dto.setSaturatedUnitWeight(Double.parseDouble(tfSaturatedUnitWeight.getText()));
            dto.setWaterUnitWeight(Double.parseDouble(gammaWat.getText()));
            dto.setWaterTableDepth(Double.parseDouble(tfWaterTableDepth.getText()));
            System.out.println(dto.toString());
            this.soilDto = dto;

            FoundationGeometryDto geometryDto = new FoundationGeometryDto();
            geometryDto.setColumnHeightAboveGround(Double.parseDouble(columnHeightAboveGround.getText()));
            geometryDto.setColumnHeightBelowGround(Double.parseDouble(columnHeightBelowGround.getText()));
            geometryDto.setFoundationDepth(Double.parseDouble(foundationDepth.getText()));
            geometryDto.setHeightOfFooting(Double.parseDouble(heightOfFooting.getText()));
            geometryDto.setSideLenghtOfColumn(Double.parseDouble(sideLenghtOfColumn.getText()));
            geometryDto.setSideLengthOfFooting(Double.parseDouble(sideLenghtOfFooting.getText()));
            geometryDto.setUnitWeightOfConcrete(Double.parseDouble(unitWeightOfConcrete.getText()));
            geometryDto.setWeightOfFoundation();
            System.out.println(geometryDto.toString());
            this.geometryDto = geometryDto;

            ServiceabilityLoadsDto loadsDto = new ServiceabilityLoadsDto();
            loadsDto.setCompressiveForce(Double.parseDouble(txtSlsCompressiveForce.getText()));
            loadsDto.setTensileForce(Double.parseDouble(txtSlsTensileForce.getText()));
            loadsDto.setHorizontalLongitudinalForce(Double.parseDouble(txtSlsHorizontalLongitudinalForce.getText()));
            loadsDto.setHorizontalTransverseForce(Double.parseDouble(txtSlsHorizontalTransverseForce.getText()));
            System.out.println(loadsDto.toString());
            this.serviceabilityLoadsDto = loadsDto;
            
            
            this.soilCalculator = new SoilPressureCalculatorUtil(dto, geometryDto);
            
            int selectedIndex = jTabbedPane1.getSelectedIndex();
            String selectedTitle = jTabbedPane1.getTitleAt(selectedIndex);

            if ("Check Stability".equals(selectedTitle)) {
                if (!ValidationUtil.validateRequiredFields(this)) {
                    return; // Skip further processing
                }
                performStabilityCheck();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        bearingCapacity = new javax.swing.JLabel();
        unitWeightOfSoil = new javax.swing.JLabel();
        internalFrictionAngle = new javax.swing.JLabel();
        frictionAngleConcreteAndSoil = new javax.swing.JLabel();
        cohesion = new javax.swing.JLabel();
        waterTableLocation = new javax.swing.JLabel();
        tfBearignCapacity = new javax.swing.JTextField();
        tfUnitWeightOfSoil = new javax.swing.JTextField();
        tfInternalFrictionAngle = new javax.swing.JTextField();
        tfFrictionAngleFoundationAndSoil = new javax.swing.JTextField();
        tfCohesion = new javax.swing.JTextField();
        rbYes = new javax.swing.JRadioButton();
        rbNo = new javax.swing.JRadioButton();
        depthOfWaterTable = new javax.swing.JLabel();
        UnitWeightOfWater = new javax.swing.JLabel();
        gammaWat = new javax.swing.JLabel();
        SaturatedUnitWeightOfSoil = new javax.swing.JLabel();
        tfWaterTableDepth = new javax.swing.JTextField();
        tfSaturatedUnitWeight = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        foundationDepth = new javax.swing.JTextField();
        sideLenghtOfFooting = new javax.swing.JTextField();
        heightOfFooting = new javax.swing.JTextField();
        sideLenghtOfColumn = new javax.swing.JTextField();
        columnHeightAboveGround = new javax.swing.JTextField();
        columnHeightBelowGround = new javax.swing.JTextField();
        unitWeightOfConcrete = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        ImagePanel = new javax.swing.JPanel();
        dwgWaterTableDepth = new javax.swing.JLabel();
        dwgFootingWidth = new javax.swing.JLabel();
        dwgColWidth = new javax.swing.JLabel();
        dwgFootingHeight = new javax.swing.JLabel();
        dwgColHeightBelowGround = new javax.swing.JLabel();
        dwgColHeightAboveGround = new javax.swing.JLabel();
        dwgWaterTableDepth1 = new javax.swing.JLabel();
        dwgWaterTableDepth2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtSlsCompressiveForce = new javax.swing.JTextField();
        txtSlsTensileForce = new javax.swing.JTextField();
        txtSlsHorizontalTransverseForce = new javax.swing.JTextField();
        txtSlsHorizontalLongitudinalForce = new javax.swing.JTextField();
        txtUlsCompressiveForce = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtUlsTensileForce = new javax.swing.JTextField();
        txtUlsHorizontalTransverseForce = new javax.swing.JTextField();
        txtUlsHorizntalLongitudinalForce = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblFosBearing = new javax.swing.JLabel();
        lblFosUprooting = new javax.swing.JLabel();
        lblFosSliding = new javax.swing.JLabel();
        lblFosOverturning = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtFy = new javax.swing.JTextField();
        txtFcu = new javax.swing.JTextField();
        txtCover = new javax.swing.JTextField();
        txtBarDia = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtShearLinksDia = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        btnRfDesign = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        lblAsReqComForSagging = new javax.swing.JLabel();
        lblAsProvComForSagging = new javax.swing.JLabel();
        lblProvComRfConfigForSagging = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        lblAsProvComForHogging = new javax.swing.JLabel();
        lblProvComRfConfigForHogging = new javax.swing.JLabel();
        lblAsReqComForHogging = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        lblAsReqTensForSagging = new javax.swing.JLabel();
        lblProvTensRfConfigForSagging = new javax.swing.JLabel();
        lblAsProvTensForSagging = new javax.swing.JLabel();
        lblAsReqTensForHogging = new javax.swing.JLabel();
        lblProvTensRfConfigForHogging = new javax.swing.JLabel();
        lblAsProvTensForHogging = new javax.swing.JLabel();
        lblAsvReq = new javax.swing.JLabel();
        lblShearRefConfig = new javax.swing.JLabel();
        txtNoOfLegs = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        btnGetReport = new javax.swing.JButton();
        txtSite = new javax.swing.JTextField();
        txtClient = new javax.swing.JTextField();
        txtVender = new javax.swing.JTextField();
        txtLocation = new javax.swing.JTextField();
        txtDesignedBy = new javax.swing.JTextField();
        txtCheckedBy = new javax.swing.JTextField();
        txtTowerHeight = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bearingCapacity.setText("Safe bearing capacity of soil : ");

        unitWeightOfSoil.setText("Bulk unit weight of soil : ");

        internalFrictionAngle.setText("Effective soil Angle of Internal Friction (Ø’)  : ");

        frictionAngleConcreteAndSoil.setText("Friction angle between concrete foundation and soil (δ)  : ");

        cohesion.setText("Effective soil Cohesion/ Adhesion (c’)  : ");

        waterTableLocation.setText("Water Table is Above the Footing ? :");

        tfBearignCapacity.setText("250");

        tfUnitWeightOfSoil.setText("17.7");

        tfInternalFrictionAngle.setText("32");

        tfFrictionAngleFoundationAndSoil.setText("24");
        tfFrictionAngleFoundationAndSoil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFrictionAngleFoundationAndSoilActionPerformed(evt);
            }
        });

        tfCohesion.setText("8");

        buttonGroup1.add(rbYes);
        rbYes.setText("Yes");
        rbYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbYesActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbNo);
        rbNo.setText("No");

        depthOfWaterTable.setText("Depth of Water Table (m)  : ");

        UnitWeightOfWater.setText("Unit Weight of Water (kN/m3)  : ");

        gammaWat.setText("9.81");

        SaturatedUnitWeightOfSoil.setText("Saturated Unit Weight of Soil (kN/m3)  : ");

        tfWaterTableDepth.setText("10");

        tfSaturatedUnitWeight.setText("19");

        jLabel33.setText("kN/m2");

        jLabel34.setText("kN/m3");

        jLabel35.setText("kN/m2");

        jLabel36.setText("m");

        jLabel37.setText("kN/m3");

        jLabel38.setText("kN/m3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SaturatedUnitWeightOfSoil)
                    .addComponent(depthOfWaterTable)
                    .addComponent(waterTableLocation)
                    .addComponent(cohesion)
                    .addComponent(bearingCapacity)
                    .addComponent(unitWeightOfSoil)
                    .addComponent(internalFrictionAngle, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(frictionAngleConcreteAndSoil)
                    .addComponent(UnitWeightOfWater))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfBearignCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel33))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfUnitWeightOfSoil, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34))
                    .addComponent(tfInternalFrictionAngle, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfFrictionAngleFoundationAndSoil, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfCohesion, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel35))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbYes)
                        .addGap(18, 18, 18)
                        .addComponent(rbNo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfWaterTableDepth, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfSaturatedUnitWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(gammaWat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel38)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bearingCapacity)
                    .addComponent(tfBearignCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitWeightOfSoil)
                    .addComponent(tfUnitWeightOfSoil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(internalFrictionAngle)
                    .addComponent(tfInternalFrictionAngle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(frictionAngleConcreteAndSoil)
                    .addComponent(tfFrictionAngleFoundationAndSoil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cohesion)
                    .addComponent(tfCohesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(waterTableLocation)
                    .addComponent(rbYes)
                    .addComponent(rbNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(depthOfWaterTable)
                    .addComponent(tfWaterTableDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SaturatedUnitWeightOfSoil)
                    .addComponent(tfSaturatedUnitWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UnitWeightOfWater)
                    .addComponent(gammaWat)
                    .addComponent(jLabel38))
                .addContainerGap(225, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Soil Report Details", jPanel1);

        jLabel1.setText("Foundation Depth");

        jLabel2.setText("Side Lenght of Footing");

        jLabel3.setText("Height of Footing");

        jLabel4.setText("Side Lenght of Column");

        jLabel5.setText("Column Height Above Ground");

        jLabel6.setText("Column Height Below Ground");

        jLabel7.setText("Unit Weight of Concrete");

        foundationDepth.setText("3");
        foundationDepth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foundationDepthActionPerformed(evt);
            }
        });

        sideLenghtOfFooting.setText("3.8");
        sideLenghtOfFooting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sideLenghtOfFootingActionPerformed(evt);
            }
        });

        heightOfFooting.setText("0.6");

        sideLenghtOfColumn.setText("0.6");

        columnHeightAboveGround.setText("0.3");

        columnHeightBelowGround.setText("2.4");
        columnHeightBelowGround.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                columnHeightBelowGroundActionPerformed(evt);
            }
        });

        unitWeightOfConcrete.setText("24");

        jLabel14.setText("m");

        jLabel15.setText("m");

        jLabel16.setText("m");

        jLabel17.setText("m");

        jLabel22.setText("m");

        jLabel23.setText("m");

        jLabel24.setText("kN/m3");

        dwgWaterTableDepth.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dwgWaterTableDepth.setText("jLabel59");

        dwgFootingWidth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dwgFootingWidth.setText("jLabel59");

        dwgColWidth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dwgColWidth.setText("jLabel59");

        dwgFootingHeight.setText("jLabel59");

        dwgColHeightBelowGround.setText("jLabel59");

        dwgColHeightAboveGround.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        dwgColHeightAboveGround.setText("jLabel59");

        dwgWaterTableDepth1.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        dwgWaterTableDepth1.setText("Ground Level");

        dwgWaterTableDepth2.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        dwgWaterTableDepth2.setText("G.W.T");

        javax.swing.GroupLayout ImagePanelLayout = new javax.swing.GroupLayout(ImagePanel);
        ImagePanel.setLayout(ImagePanelLayout);
        ImagePanelLayout.setHorizontalGroup(
            ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelLayout.createSequentialGroup()
                .addGroup(ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ImagePanelLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(dwgColHeightAboveGround))
                    .addGroup(ImagePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dwgFootingHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ImagePanelLayout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(dwgFootingWidth)))
                .addContainerGap(238, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ImagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ImagePanelLayout.createSequentialGroup()
                        .addComponent(dwgColHeightBelowGround)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dwgWaterTableDepth1)
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ImagePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ImagePanelLayout.createSequentialGroup()
                                .addComponent(dwgWaterTableDepth)
                                .addGap(14, 14, 14))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ImagePanelLayout.createSequentialGroup()
                                .addComponent(dwgColWidth)
                                .addGap(230, 230, 230))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ImagePanelLayout.createSequentialGroup()
                                .addComponent(dwgWaterTableDepth2)
                                .addGap(40, 40, 40))))))
        );
        ImagePanelLayout.setVerticalGroup(
            ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(dwgColWidth)
                .addGap(13, 13, 13)
                .addComponent(dwgColHeightAboveGround)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(dwgColHeightBelowGround)
                .addGap(105, 105, 105)
                .addComponent(dwgFootingHeight)
                .addGap(25, 25, 25)
                .addComponent(dwgFootingWidth)
                .addGap(37, 37, 37))
            .addGroup(ImagePanelLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(dwgWaterTableDepth1)
                .addGap(27, 27, 27)
                .addComponent(dwgWaterTableDepth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dwgWaterTableDepth2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(39, 39, 39)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)))
                        .addComponent(jLabel5)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(foundationDepth, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(sideLenghtOfColumn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(columnHeightAboveGround, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(unitWeightOfConcrete, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(columnHeightBelowGround, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(sideLenghtOfFooting, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(heightOfFooting, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
                .addComponent(ImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ImagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(foundationDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(sideLenghtOfFooting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(heightOfFooting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(sideLenghtOfColumn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(columnHeightAboveGround, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(columnHeightBelowGround, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(unitWeightOfConcrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))))
                .addContainerGap(127, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Foundation Geometry", jPanel2);

        jLabel8.setText("Serviceability Loads");

        jLabel9.setText("Ultimate Loads");

        jLabel10.setText("Compression Force (Fc)");

        jLabel11.setText("Tensile Force (Ft)");

        jLabel12.setText("Horizontal Transverse Force (Vt)");

        jLabel13.setText("Horizontal Longitudinal Force (Vl)");

        txtSlsCompressiveForce.setText("865");

        txtSlsTensileForce.setText("775");

        txtSlsHorizontalTransverseForce.setText("63");
        txtSlsHorizontalTransverseForce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSlsHorizontalTransverseForceActionPerformed(evt);
            }
        });

        txtSlsHorizontalLongitudinalForce.setText("63");

        txtUlsCompressiveForce.setText("1502");

        jLabel18.setText("Compression Force (Fc)");

        jLabel19.setText("Tensile Force (Ft)");

        jLabel20.setText("Horizontal Transverse Force (Vt)");

        jLabel21.setText("Horizontal Longitudinal Force (Vl)");

        txtUlsTensileForce.setText("1405");

        txtUlsHorizontalTransverseForce.setText("110");
        txtUlsHorizontalTransverseForce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUlsHorizontalTransverseForceActionPerformed(evt);
            }
        });

        txtUlsHorizntalLongitudinalForce.setText("110");

        jLabel25.setText("kN");

        jLabel26.setText("kN");

        jLabel27.setText("kN");

        jLabel28.setText("kN");

        jLabel29.setText("kN");

        jLabel30.setText("kN");

        jLabel31.setText("kN");

        jLabel32.setText("kN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUlsCompressiveForce, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUlsTensileForce, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUlsHorizontalTransverseForce, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUlsHorizntalLongitudinalForce, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jSeparator1)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSlsCompressiveForce, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSlsTensileForce, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSlsHorizontalTransverseForce, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSlsHorizontalLongitudinalForce, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addContainerGap(940, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSlsCompressiveForce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSlsTensileForce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSlsHorizontalTransverseForce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtSlsHorizontalLongitudinalForce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(32, 32, 32)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtUlsCompressiveForce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUlsTensileForce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel30)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtUlsHorizontalTransverseForce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtUlsHorizntalLongitudinalForce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addContainerGap(193, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Loads", jPanel3);

        lblFosBearing.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblFosBearing.setText("jLabel59");

        lblFosUprooting.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblFosUprooting.setText("jLabel59");

        lblFosSliding.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblFosSliding.setText("jLabel59");

        lblFosOverturning.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblFosOverturning.setText("jLabel59");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFosUprooting, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFosOverturning)
                            .addComponent(lblFosSliding)
                            .addComponent(lblFosBearing, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(lblFosBearing)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFosUprooting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFosSliding)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFosOverturning)
                .addContainerGap(359, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Check Stability", jPanel4);

        jLabel39.setText("Yield Strenght of Reinforcement (fy)");

        jLabel40.setText("Grade of Concrete");

        jLabel41.setText("Clear Cover to Reinforcement");

        jLabel42.setText("Bar Diameter (Main Reinforcement)");

        txtFy.setText("500");

        txtFcu.setText("25");
        txtFcu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFcuActionPerformed(evt);
            }
        });

        txtCover.setText("50");

        txtBarDia.setText("16");

        jLabel43.setText("mm");

        jLabel44.setText("MPa");

        jLabel45.setText("mm");

        jLabel46.setText("Bar Diameter (Shear Links)");

        txtShearLinksDia.setText("10");

        jLabel47.setText("mm");

        btnRfDesign.setText("Design");
        btnRfDesign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRfDesignActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel48.setText("Compressive Reinforcement");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel49.setText("Tensile Reinforcement");

        lblAsReqComForSagging.setText("--");

        lblAsProvComForSagging.setText("--");

        lblProvComRfConfigForSagging.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        lblProvComRfConfigForSagging.setText("--");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel51.setText("Sagging");

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel52.setText("Hogging");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel53.setText("Shear");

        jLabel54.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        jLabel54.setText("As Required");

        jLabel55.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        jLabel55.setText("As Provided");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        jLabel56.setText("As Required");

        jLabel57.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        jLabel57.setText("As Provided");

        lblAsProvComForHogging.setText("--");

        lblProvComRfConfigForHogging.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        lblProvComRfConfigForHogging.setText("--");

        lblAsReqComForHogging.setText("--");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        jLabel58.setText("Asv Required");

        lblAsReqTensForSagging.setText("--");

        lblProvTensRfConfigForSagging.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        lblProvTensRfConfigForSagging.setText("--");

        lblAsProvTensForSagging.setText("--");

        lblAsReqTensForHogging.setText("--");

        lblProvTensRfConfigForHogging.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        lblProvTensRfConfigForHogging.setText("--");

        lblAsProvTensForHogging.setText("--");

        lblAsvReq.setText("--");

        lblShearRefConfig.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        lblShearRefConfig.setText("--");

        txtNoOfLegs.setText("2");

        jLabel50.setText("Number of Legs");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel46)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel39)
                        .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel50))
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(txtShearLinksDia, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel47))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(txtFy, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel44))
                                .addComponent(txtFcu, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(txtCover, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel45))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(txtBarDia, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel43)))
                            .addGap(160, 160, 160)))
                    .addComponent(txtNoOfLegs, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel49)
                    .addComponent(jLabel48))
                .addGap(37, 37, 37)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblAsReqTensForSagging, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAsReqComForSagging, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAsProvComForSagging, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAsProvTensForSagging, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblProvTensRfConfigForSagging, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                            .addComponent(lblProvComRfConfigForSagging, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAsReqComForHogging, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel56)
                                    .addComponent(lblAsReqTensForHogging, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel57)
                                    .addComponent(lblProvComRfConfigForHogging, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblProvTensRfConfigForHogging, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(lblAsProvComForHogging, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblAsProvTensForHogging, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRfDesign, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jLabel51))
                .addGap(33, 33, 33)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblShearRefConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblAsvReq, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(113, 113, 113))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtFy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(txtFcu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txtCover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtBarDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jLabel46)
                    .addComponent(txtShearLinksDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(txtNoOfLegs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRfDesign))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(jLabel52)
                    .addComponent(jLabel53))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblAsReqComForHogging)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblProvComRfConfigForHogging))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel54)
                            .addComponent(jLabel55)
                            .addComponent(jLabel56)
                            .addComponent(jLabel57)
                            .addComponent(jLabel58))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel48)
                                    .addComponent(lblAsReqComForSagging)
                                    .addComponent(lblAsProvComForSagging))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblProvComRfConfigForSagging))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblAsProvComForHogging)
                                    .addComponent(lblAsvReq))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblShearRefConfig)))))
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAsReqTensForSagging)
                    .addComponent(jLabel49)
                    .addComponent(lblAsProvTensForSagging)
                    .addComponent(lblAsReqTensForHogging)
                    .addComponent(lblAsProvTensForHogging))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProvTensRfConfigForSagging)
                    .addComponent(lblProvTensRfConfigForHogging))
                .addContainerGap(192, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reinforcement Details", jPanel5);

        jLabel59.setText("Site");

        jLabel60.setText("Client");

        jLabel61.setText("Vendor");

        jLabel62.setText("Location");

        jLabel63.setText("Designed by");

        jLabel64.setText("Checked by");

        jLabel65.setText("Tower Height");

        jLabel66.setText("No. of Legs");

        btnGetReport.setText("Get Report");
        btnGetReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetReportActionPerformed(evt);
            }
        });

        txtSite.setText("30m Telecom Tower at Jayasumana Road");
        txtSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSiteActionPerformed(evt);
            }
        });

        txtClient.setText("Dialog Axiata PLC");

        txtVender.setText("Trailers");

        txtLocation.setText("Jayasumana Road");
        txtLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLocationActionPerformed(evt);
            }
        });

        txtDesignedBy.setText("SK");

        txtCheckedBy.setText("BK");

        txtTowerHeight.setText("30 ");

        jTextField8.setText("3");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel66)
                    .addComponent(jLabel64)
                    .addComponent(jLabel63)
                    .addComponent(jLabel62)
                    .addComponent(jLabel61)
                    .addComponent(jLabel60)
                    .addComponent(jLabel59)
                    .addComponent(jLabel65))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTowerHeight, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(jTextField8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 466, Short.MAX_VALUE)
                        .addComponent(btnGetReport, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(397, 397, 397))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSite, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                            .addComponent(txtClient)
                            .addComponent(txtVender)
                            .addComponent(txtLocation)
                            .addComponent(txtDesignedBy)
                            .addComponent(txtCheckedBy))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGetReport)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel59)
                            .addComponent(txtSite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(txtClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(txtVender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel62)
                            .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63)
                            .addComponent(txtDesignedBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel64)
                            .addComponent(txtCheckedBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65)
                            .addComponent(txtTowerHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(258, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Project Details", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUlsHorizontalTransverseForceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUlsHorizontalTransverseForceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUlsHorizontalTransverseForceActionPerformed

    private void txtSlsHorizontalTransverseForceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSlsHorizontalTransverseForceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSlsHorizontalTransverseForceActionPerformed

    private void columnHeightBelowGroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_columnHeightBelowGroundActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_columnHeightBelowGroundActionPerformed

    private void foundationDepthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foundationDepthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_foundationDepthActionPerformed

    private void rbYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbYesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbYesActionPerformed

    private void tfFrictionAngleFoundationAndSoilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFrictionAngleFoundationAndSoilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFrictionAngleFoundationAndSoilActionPerformed

    private void sideLenghtOfFootingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sideLenghtOfFootingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sideLenghtOfFootingActionPerformed

    private void txtFcuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFcuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFcuActionPerformed

    private void btnRfDesignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRfDesignActionPerformed
        reinforcementDesign();// TODO add your handling code here:
    }//GEN-LAST:event_btnRfDesignActionPerformed

    private void btnGetReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGetReportActionPerformed

    private void txtSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSiteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSiteActionPerformed

    private void txtLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLocationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocationActionPerformed

    
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GuestView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GuestView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GuestView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GuestView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SoilReportView().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ImagePanel;
    private javax.swing.JLabel SaturatedUnitWeightOfSoil;
    private javax.swing.JLabel UnitWeightOfWater;
    private javax.swing.JLabel bearingCapacity;
    private javax.swing.JButton btnGetReport;
    private javax.swing.JButton btnRfDesign;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel cohesion;
    private javax.swing.JTextField columnHeightAboveGround;
    private javax.swing.JTextField columnHeightBelowGround;
    private javax.swing.JLabel depthOfWaterTable;
    private javax.swing.JLabel dwgColHeightAboveGround;
    private javax.swing.JLabel dwgColHeightBelowGround;
    private javax.swing.JLabel dwgColWidth;
    private javax.swing.JLabel dwgFootingHeight;
    private javax.swing.JLabel dwgFootingWidth;
    private javax.swing.JLabel dwgWaterTableDepth;
    private javax.swing.JLabel dwgWaterTableDepth1;
    private javax.swing.JLabel dwgWaterTableDepth2;
    private javax.swing.JTextField foundationDepth;
    private javax.swing.JLabel frictionAngleConcreteAndSoil;
    private javax.swing.JLabel gammaWat;
    private javax.swing.JTextField heightOfFooting;
    private javax.swing.JLabel internalFrictionAngle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JLabel lblAsProvComForHogging;
    private javax.swing.JLabel lblAsProvComForSagging;
    private javax.swing.JLabel lblAsProvTensForHogging;
    private javax.swing.JLabel lblAsProvTensForSagging;
    private javax.swing.JLabel lblAsReqComForHogging;
    private javax.swing.JLabel lblAsReqComForSagging;
    private javax.swing.JLabel lblAsReqTensForHogging;
    private javax.swing.JLabel lblAsReqTensForSagging;
    private javax.swing.JLabel lblAsvReq;
    private javax.swing.JLabel lblFosBearing;
    private javax.swing.JLabel lblFosOverturning;
    private javax.swing.JLabel lblFosSliding;
    private javax.swing.JLabel lblFosUprooting;
    private javax.swing.JLabel lblProvComRfConfigForHogging;
    private javax.swing.JLabel lblProvComRfConfigForSagging;
    private javax.swing.JLabel lblProvTensRfConfigForHogging;
    private javax.swing.JLabel lblProvTensRfConfigForSagging;
    private javax.swing.JLabel lblShearRefConfig;
    private javax.swing.JRadioButton rbNo;
    private javax.swing.JRadioButton rbYes;
    private javax.swing.JTextField sideLenghtOfColumn;
    private javax.swing.JTextField sideLenghtOfFooting;
    private javax.swing.JTextField tfBearignCapacity;
    private javax.swing.JTextField tfCohesion;
    private javax.swing.JTextField tfFrictionAngleFoundationAndSoil;
    private javax.swing.JTextField tfInternalFrictionAngle;
    private javax.swing.JTextField tfSaturatedUnitWeight;
    private javax.swing.JTextField tfUnitWeightOfSoil;
    private javax.swing.JTextField tfWaterTableDepth;
    private javax.swing.JTextField txtBarDia;
    private javax.swing.JTextField txtCheckedBy;
    private javax.swing.JTextField txtClient;
    private javax.swing.JTextField txtCover;
    private javax.swing.JTextField txtDesignedBy;
    private javax.swing.JTextField txtFcu;
    private javax.swing.JTextField txtFy;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtNoOfLegs;
    private javax.swing.JTextField txtShearLinksDia;
    private javax.swing.JTextField txtSite;
    private javax.swing.JTextField txtSlsCompressiveForce;
    private javax.swing.JTextField txtSlsHorizontalLongitudinalForce;
    private javax.swing.JTextField txtSlsHorizontalTransverseForce;
    private javax.swing.JTextField txtSlsTensileForce;
    private javax.swing.JTextField txtTowerHeight;
    private javax.swing.JTextField txtUlsCompressiveForce;
    private javax.swing.JTextField txtUlsHorizntalLongitudinalForce;
    private javax.swing.JTextField txtUlsHorizontalTransverseForce;
    private javax.swing.JTextField txtUlsTensileForce;
    private javax.swing.JTextField txtVender;
    private javax.swing.JTextField unitWeightOfConcrete;
    private javax.swing.JLabel unitWeightOfSoil;
    private javax.swing.JLabel waterTableLocation;
    // End of variables declaration//GEN-END:variables
    
    private void setProjectDetails(){
        ProjectDetailDto projectDetailDto = new ProjectDetailDto();
        
        projectDetailDto.setSite(txtSite.getText());
        projectDetailDto.setClient(txtClient.getText());
        projectDetailDto.setVender(txtVender.getText());
        projectDetailDto.setLocation(txtLocation.getText());
        projectDetailDto.setDesignedBy(txtDesignedBy.getText());
        projectDetailDto.setCheckedBy(txtCheckedBy.getText());
        projectDetailDto.setTowerHeight(txtTowerHeight.getText());
        projectDetailDto.setNoOfLegs(txtNoOfLegs.getText());
        
        this.projectDetailDto = projectDetailDto;
    }
    
    private void setDrawingDimentions(){
        dwgColHeightAboveGround.setText(columnHeightAboveGround.getText()+"m");
        dwgColHeightBelowGround.setText(columnHeightBelowGround.getText()+"m");
        dwgColWidth.setText(sideLenghtOfColumn.getText()+"m");
        dwgFootingWidth.setText(sideLenghtOfFooting.getText()+"m");
        dwgWaterTableDepth.setText(tfWaterTableDepth.getText()+"m");
        dwgFootingHeight.setText(heightOfFooting.getText()+"m");
        
        
    }
    
    private void addInputListeners() {
        addDocumentListener(columnHeightAboveGround);
        addDocumentListener(columnHeightBelowGround);
        addDocumentListener(sideLenghtOfColumn);
        addDocumentListener(sideLenghtOfFooting);
        addDocumentListener(tfWaterTableDepth);
        addDocumentListener(heightOfFooting);
    }

    private void addDocumentListener(JTextField textField) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
           
            @Override
            public void insertUpdate(DocumentEvent e) {
                setDrawingDimentions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setDrawingDimentions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setDrawingDimentions();
            }
        });
    }
    
    
    private void performStabilityCheck() {
//        SoilPropertiesNewDto soilDto = new SoilPropertiesNewDto();
//        soilDto.setBearingCapacity(Double.parseDouble(tfBearignCapacity.getText()));
//        //dto.setBreadthOfColumn(Double.parseDouble(tfBearignCapacity.getText()));
//        soilDto.setBulkUnitWeight(Double.parseDouble(tfUnitWeightOfSoil.getText()));
//        soilDto.setCohesion(Double.parseDouble(tfCohesion.getText()));
//        soilDto.setFrictionAngleWithFoundation(Double.parseDouble(tfFrictionAngleFoundationAndSoil.getText()));
//        soilDto.setInternalFrictionAngle(Double.parseDouble(tfInternalFrictionAngle.getText()));
//        soilDto.setSaturatedUnitWeight(Double.parseDouble(tfSaturatedUnitWeight.getText()));
//        soilDto.setWaterUnitWeight(Double.parseDouble(gammaWat.getText()));
//        soilDto.setWaterTableDepth(Double.parseDouble(tfWaterTableDepth.getText()));
//        
//        System.out.println(soilDto.toString());
        
        
//        FoundationGeometryDto geometryDto = new FoundationGeometryDto();
//        geometryDto.setColumnHeightAboveGround(Double.parseDouble(columnHeightAboveGround.getText()));
//        geometryDto.setColumnHeightBelowGround(Double.parseDouble(columnHeightBelowGround.getText()));
//        geometryDto.setFoundationDepth(Double.parseDouble(foundationDepth.getText()));
//        geometryDto.setHeightOfFooting(Double.parseDouble(heightOfFooting.getText()));
//        geometryDto.setSideLenghtOfColumn(Double.parseDouble(sideLenghtOfColumn.getText()));
//        geometryDto.setSideLengthOfFooting(Double.parseDouble(sideLenghtOfFooting.getText()));
//        geometryDto.setUnitWeightOfConcrete(Double.parseDouble(unitWeightOfConcrete.getText()));
//        System.out.println(geometryDto.toString());
        
//        ServiceabilityLoadsDto serviceabilityLoadsDto = new ServiceabilityLoadsDto();
//        serviceabilityLoadsDto.setCompressiveForce(Double.parseDouble(txtSlsCompressiveForce.getText()));
//        serviceabilityLoadsDto.setTensileForce(Double.parseDouble(txtSlsTensileForce.getText()));
//        serviceabilityLoadsDto.setHorizontalLongitudinalForce(Double.parseDouble(txtSlsHorizontalLongitudinalForce.getText()));
//        serviceabilityLoadsDto.setHorizontalTransverseForce(Double.parseDouble(txtSlsHorizontalTransverseForce.getText()));
//        System.out.println(serviceabilityLoadsDto.toString());
        

        
        ResultDto weightCalculationResultDto = new ResultDto();
        soilCalculator.setResult(weightCalculationResultDto);
              
        
        try {
            double weightOfFoundation = geometryDto.getWeightOfFoundation();
            double weightOfRectangularSoilVolume = soilCalculator.calculateRectangularSoilWeight();
            double weightOfPyramidsoilFrustum = soilCalculator.calculatePyramidSoilWeight();
            
            
            ResultDto bearingResult = new ResultDto();
            soilCalculator.setResult(bearingResult);
            bearingResult = inputController.fosCheckForBearing(weightOfFoundation, weightOfRectangularSoilVolume, geometryDto, soilDto, serviceabilityLoadsDto, soilCalculator, bearingResult);
            this.bearingResultDto = bearingResult;
            boolean bearingCheckResult = bearingResult.isIsSatisfied();
            
            
            
            ResultDto uprootingResult = new ResultDto();
            soilCalculator.setResult(uprootingResult);
            uprootingResult = inputController.fosCheckForUprooting(weightOfFoundation, weightOfRectangularSoilVolume, weightOfPyramidsoilFrustum, geometryDto, soilDto, serviceabilityLoadsDto, soilCalculator, uprootingResult);
            this.uprootingResultDto = uprootingResult;
            boolean uprootingCheckResult = uprootingResult.isIsSatisfied();
            
            
            
            ResultDto slidingResult = new ResultDto();
            soilCalculator.setResult(slidingResult);
            slidingResult = inputController.fosCheckForSliding(weightOfFoundation, weightOfRectangularSoilVolume, geometryDto, soilDto, serviceabilityLoadsDto, soilCalculator, slidingResult);
            this.slidingResultDto = slidingResult;
            boolean slidingCheckResult = slidingResult.isIsSatisfied();
            
            
            
            ResultDto overturningResult = new ResultDto();
            soilCalculator.setResult(overturningResult);
            overturningResult = inputController.fosCheckForOverturning(weightOfFoundation, weightOfPyramidsoilFrustum, geometryDto, soilDto, serviceabilityLoadsDto, soilCalculator, overturningResult);
            this.overturningResultDto = overturningResult;
            boolean overturningCheckResult = overturningResult.isIsSatisfied();
            
            
            lblFosBearing.setText(bearingCheckResult
                    ? "<html>FOS - Bearing: PASS</html>"
                    : "<html>FOS - Bearing: <font color='red'>FAIL</font></html>");

            lblFosUprooting.setText(uprootingCheckResult
                    ? "<html>FOS - Uprooting: PASS</html>"
                    : "<html>FOS - Uprooting: <font color='red'>FAIL</font></html>");

            lblFosSliding.setText(slidingCheckResult
                    ? "<html>FOS - Sliding: PASS</html>"
                    : "<html>FOS - Sliding: <font color='red'>FAIL</font></html>");

            lblFosOverturning.setText(overturningCheckResult
                    ? "<html>FOS - Overturning: PASS</html>"
                    : "<html>FOS - Overturning: <font color='red'>FAIL</font></html>");
            
            
        } catch (Exception ex) {
            Logger.getLogger(SoilReportView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
     
     private void reinforcementDesign(){
        
        
        
//        FoundationGeometryDto geometryDto = new FoundationGeometryDto();
//        geometryDto.setColumnHeightAboveGround(Double.parseDouble(columnHeightAboveGround.getText()));
//        geometryDto.setColumnHeightBelowGround(Double.parseDouble(columnHeightBelowGround.getText()));
//        geometryDto.setFoundationDepth(Double.parseDouble(foundationDepth.getText()));
//        geometryDto.setHeightOfFooting(Double.parseDouble(heightOfFooting.getText()));
//        geometryDto.setSideLenghtOfColumn(Double.parseDouble(sideLenghtOfColumn.getText()));
//        geometryDto.setSideLengthOfFooting(Double.parseDouble(sideLenghtOfFooting.getText()));
//        geometryDto.setUnitWeightOfConcrete(Double.parseDouble(unitWeightOfConcrete.getText()));
//        System.out.println(geometryDto.toString());
//        this.geometryDto = geometryDto;
        
        UltimateLoadsDto loadsDto = new UltimateLoadsDto();
        loadsDto.setCompressiveForce(Double.parseDouble(txtUlsCompressiveForce.getText()));
        loadsDto.setTensileForce(Double.parseDouble(txtUlsTensileForce.getText()));
        loadsDto.setHorizontalLongitudinalForce(Double.parseDouble(txtUlsHorizntalLongitudinalForce.getText()));
        loadsDto.setHorizontalTransverseForce(Double.parseDouble(txtUlsHorizontalTransverseForce.getText()));
        System.out.println(loadsDto.toString());
        this.ultimateLoadsDto = loadsDto;
        
        
        ReinforcementDto rfDto = new ReinforcementDto();
        rfDto.setYieldStrenghtOfReinforcement(Double.parseDouble(txtFy.getText()));
        rfDto.setGradeOfConcrete(Double.parseDouble(txtFcu.getText()));
        rfDto.setBarDiameter(Double.parseDouble(txtBarDia.getText()));
        rfDto.setClearCover(Double.parseDouble(txtCover.getText()));
        rfDto.setShearLinksDiameter(Double.parseDouble(txtShearLinksDia.getText()));
        rfDto.setNoOfLegs(Integer.parseInt(txtNoOfLegs.getText()));
        System.out.println(rfDto.toString());
        this.reinforcementDto = rfDto;
        
        
        
        
        ReinforcementCalculatorUtil rfCalculator = new ReinforcementCalculatorUtil(rfDto, geometryDto);
        ShearReinforcementCalculatorUtil shearRfCalculator = new ShearReinforcementCalculatorUtil();
        

        try {
            ResultDto criticalBendingMomentResultDto = new ResultDto();
            double mCr = reinforcementInputController.CriticalBendingMomentAboutTheFaceOfTheColumn(soilDto, geometryDto, soilCalculator, loadsDto, criticalBendingMomentResultDto);
            double d = rfCalculator.calculateEffectiveDepth(criticalBendingMomentResultDto);
            double sideLenghtOfFooting = geometryDto.getSideLengthOfFooting();
            double fCu = reinforcementDto.getGradeOfConcrete();
            double k = reinforcementInputController.calculateKValue(sideLenghtOfFooting, d, fCu, mCr, criticalBendingMomentResultDto);
            this.reinforcementDesignResultDto = criticalBendingMomentResultDto;
            
            ResultDto asReqForCompInSaggingResultDto = new ResultDto();
            double asRequiredForCompressionInSagging = reinforcementInputController.CompressiveRfRequirementForSagging(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCalculator, mCr, d, k, asReqForCompInSaggingResultDto);
            double asProvidedForCompressionInSagging = reinforcementInputController.getAsProvided(asRequiredForCompressionInSagging, geometryDto, rfDto, rfCalculator, d,  asReqForCompInSaggingResultDto);
            String compressiveRfDesignForSagging = reinforcementInputController.compressiveRfDesignForSagging(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCalculator, asRequiredForCompressionInSagging, d, asReqForCompInSaggingResultDto);
            this.designForCompInSaggingResultDto = asReqForCompInSaggingResultDto;
            
            ResultDto asReqForCompInHoggingResultDto = new ResultDto();
            double asRequiredForCompressionInHogging = reinforcementInputController.compressiveRfRequirementForHogging(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCalculator,mCr, d, k, asReqForCompInHoggingResultDto);
            double asProvidedForCompressionInHogging = reinforcementInputController.getAsProvided(asRequiredForCompressionInHogging, geometryDto, rfDto, rfCalculator, d, asReqForCompInHoggingResultDto);
            String compressiveRfDesignForHogging = reinforcementInputController.compressiveRfDesignForHogging(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCalculator, asRequiredForCompressionInHogging, d, asReqForCompInHoggingResultDto);
            
            ResultDto asReqForTensInSaggingResultDto = new ResultDto(); 
            double asRequiredForTensileInSagging = reinforcementInputController.tensileRfRequirementForSagging(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCalculator, asRequiredForCompressionInSagging, d, mCr, k, asReqForTensInSaggingResultDto);
            double asProvidedForTensileInSagging = reinforcementInputController.getAsProvided(asRequiredForTensileInSagging, geometryDto, rfDto, rfCalculator, d, asReqForTensInSaggingResultDto);
            String tensileRfDesignForSagging = reinforcementInputController.tensileRfDesignForSagging(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCalculator, asRequiredForTensileInSagging, d, asReqForTensInSaggingResultDto);
            
            ResultDto asReqForTenseInHoggingResultDto = new ResultDto();
            double asRequiredForTensileInHogging = reinforcementInputController.tensileRfRequirementForHogging(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCalculator, asRequiredForCompressionInHogging, d, mCr, k, asReqForTenseInHoggingResultDto);
            double asProvidedForTensileInHogging = reinforcementInputController.getAsProvided(asRequiredForTensileInHogging, geometryDto, rfDto, rfCalculator, d, asReqForTenseInHoggingResultDto);
            String tensileRfDesignForHogging = reinforcementInputController.tensileRfDesignForHogging(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCalculator, asRequiredForTensileInHogging, d, asReqForTenseInHoggingResultDto);
            
            ResultDto asReqForShearResultDto = new ResultDto();
            double asv = reinforcementInputController.ShearReinforcementRequirement(geometryDto, soilDto, rfDto, loadsDto, soilCalculator, rfCalculator, shearRfCalculator, asProvidedForTensileInSagging, d, asReqForShearResultDto);
            double shearLinkDia = rfDto.getShearLinksDiameter();
            int noOfLegs = rfDto.getNoOfLegs();
            String asvDesign = reinforcementInputController.ShearReinforcementDesign(shearRfCalculator, asv, noOfLegs, shearLinkDia);
            
            
            lblAsReqComForSagging.setText(String.format("%.2f", asRequiredForCompressionInSagging));
            lblAsProvComForSagging.setText(String.format("%.2f", asProvidedForCompressionInSagging));
            lblProvComRfConfigForSagging.setText(compressiveRfDesignForSagging);
            
            lblAsReqComForHogging.setText(String.format("%.2f", asRequiredForCompressionInHogging));
            lblAsProvComForHogging.setText(String.format("%.2f", asProvidedForCompressionInHogging));
            lblProvComRfConfigForHogging.setText(compressiveRfDesignForHogging);
            
            lblAsReqTensForSagging.setText(String.format("%.2f", asRequiredForTensileInSagging));
            lblAsProvTensForSagging.setText(String.format("%.2f", asProvidedForTensileInSagging));
            lblProvTensRfConfigForSagging.setText(tensileRfDesignForSagging);
            
            lblAsReqTensForHogging.setText(String.format("%.2f", asRequiredForTensileInHogging));
            lblAsProvTensForHogging.setText(String.format("%.2f", asProvidedForTensileInHogging));
            lblProvTensRfConfigForHogging.setText(tensileRfDesignForHogging);
            
            lblAsvReq.setText(String.format("%.4f", asv));
            lblShearRefConfig.setText(asvDesign);
            
            
        } catch (Exception ex) {
            Logger.getLogger(SoilReportView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
     }
    
    private void clear() {
//        txtGuestId.setText("");
//        txtGuestName.setText("");
//        txtTitle.setText("");
//        txtNic.setText("");
//        txtEmail.setText("");
//        txtContact.setText("");
//        txtAddress.setText("");
    }

}
