/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.sedceng.padfoundation.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ProjectDetailDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SlidingDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.UltimateLoadsDto;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Sanduni Navoda
 */
public class ReportFrame extends javax.swing.JFrame {

    /**
     * Creates new form ReportFrame
     *
     * @param geometryDto
     * @param soilCalculator
     * @param slidingResultDto
     * @param soilDto
     * @param serviceabilityLoadsDto
     * @param ultimateLoadsDto
     * @param bearingResutDto
     * @param uprootingResultDto
     * @param slidingDto
     */
    public ReportFrame(ProjectDetailDto projectDetailDto, FoundationGeometryDto geometryDto, SoilPressureCalculatorUtil soilCalculator, SoilPropertiesNewDto soilDto, ServiceabilityLoadsDto serviceabilityLoadsDto, UltimateLoadsDto ultimateLoadsDto, ResultDto bearingResutDto, ResultDto uprootingResultDto, ResultDto slidingResultDto, ResultDto overturnigResultDto) {
        initComponents();

        setTitle("Report");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//        reportArea.setEditable(false);
//        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
//
//        StringBuilder report = new StringBuilder();
//        report.append("----- Foundation Report -----\n");
//
//        for (String line : geometryDto.getReportLines()) {
//            report.append(line).append("\n");
//        }
//
//        reportArea.setText(report.toString());
//        
//         DefaultTableModel model = new DefaultTableModel(
//                new Object[][]{
//                    {"Footing Side Length", geometryDto.getSideLengthOfFooting() + " m"},
//                    {"Footing Depth", geometryDto.getHeightOfFooting() + " m"}
//                },
//                new String[]{"Parameter", "Value"}
//        );
        List<String[]> data = new ArrayList<>();

// Add a centered title
        data.add(new String[]{"", "=== FOUNDATION REPORT ===", ""});

// Add a 2-column section
        data.add(new String[]{"Project Name", "", ""});
        data.add(new String[]{"Location", "", ""});

// Add a full row
        
        List<String[]> projectDetails = projectDetailDto.getReportLines();
        List<String[]> soilReportData = soilDto.getReportLines();
        List<String[]> serviceabilityLoadData = serviceabilityLoadsDto.getReportLines();
        List<String[]> ultimateLoadsDtoData = ultimateLoadsDto.getReportLines();
        List<String[]> foundationGeometryDtoData = geometryDto.getReportLines();
        List<String[]> bearingResultDtoData = bearingResutDto.getData();
        List<String[]> uprootingResultDtoData = uprootingResultDto.getData();
        List<String[]> slidingResultDtoData = slidingResultDto.getData();
        List<String[]> overturningResultDtoData = overturnigResultDto.getData();
        //List<String[]> soilCalculatorUtilData = soilCalculator.getResult().getData(); 
        DefaultTableModel model = new DefaultTableModel(new String[]{"Parameter", "", "Value"}, 0);

        for (String[] row : projectDetails) {
            data.add(row);
        }
        for (String[] row : soilReportData) {
            data.add(row);
        }
        for (String[] row : serviceabilityLoadData) {
            data.add(row);
        }
        for (String[] row : ultimateLoadsDtoData) {
            data.add(row);
        }
        for (String[] row : foundationGeometryDtoData) {
            data.add(row);
        }
        for (String[] row : bearingResultDtoData) {
            data.add(row);
        }
        for (String[] row : uprootingResultDtoData) {
            data.add(row);
        }
        for (String[] row : slidingResultDtoData) {
            data.add(row);
        }
        for (String[] row : overturningResultDtoData) {
            data.add(row);
        }

//        for (String[] row : data) {
//            // Ensure row always has exactly 3 elements
//            if (row.length == 3) {
//                model.addRow(row);
//            } else if (row.length == 2) {
//                model.addRow(new String[]{row[0], row[1], ""});
//            } else if (row.length == 1) {
//                model.addRow(new String[]{"", row[0], ""});
//            }
//        }
        
        // Add rows to the model. Assume each 'row' String[] has at least 3 elements.
        // If a row has fewer than 3, ensure you handle it to avoid ArrayIndexOutOfBoundsException
        for (String[] row : soilReportData) {
            Object[] actualRow = new Object[3];
            actualRow[0] = row.length > 0 ? row[0] : "";
            actualRow[1] = row.length > 1 ? row[1] : ""; // Assumed Unit or empty string
            actualRow[2] = row.length > 2 ? row[2] : ""; // Assumed Value or empty string
            model.addRow(actualRow);
            System.out.println("Added from SoilDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
        }
        System.out.println("Model row count after SoilDto: " + model.getRowCount());

        for (String[] row : serviceabilityLoadData) {
            Object[] actualRow = new Object[3];
            actualRow[0] = row.length > 0 ? row[0] : "";
            actualRow[1] = row.length > 1 ? row[1] : "";
            actualRow[2] = row.length > 2 ? row[2] : "";
            model.addRow(actualRow);
            System.out.println("Added from ServiceabilityLoadsDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
        }
        System.out.println("Model row count after ServiceabilityLoadsDto: " + model.getRowCount());

        for (String[] row : ultimateLoadsDtoData) {
            Object[] actualRow = new Object[3];
            actualRow[0] = row.length > 0 ? row[0] : "";
            actualRow[1] = row.length > 1 ? row[1] : "";
            actualRow[2] = row.length > 2 ? row[2] : "";
            model.addRow(actualRow);
            System.out.println("Added from UltimateLoadsDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
        }
        System.out.println("Model row count after UltimateLoadsDto: " + model.getRowCount());

        for (String[] row : foundationGeometryDtoData) {
            Object[] actualRow = new Object[3];
            actualRow[0] = row.length > 0 ? row[0] : "";
            actualRow[1] = row.length > 1 ? row[1] : "";
            actualRow[2] = row.length > 2 ? row[2] : "";
            model.addRow(actualRow);
            System.out.println("Added from GeometryDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
        }
        System.out.println("Model row count after GeometryDto: " + model.getRowCount());

        for (String[] row : bearingResultDtoData) {
            Object[] actualRow = new Object[3];
            actualRow[0] = row.length > 0 ? row[0] : "";
            actualRow[1] = row.length > 1 ? row[1] : "";
            actualRow[2] = row.length > 2 ? row[2] : "";
            model.addRow(actualRow);
            System.out.println("Added from BearingResultDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
        }
        System.out.println("Model row count after BearingResultDto: " + model.getRowCount());

        for (String[] row : uprootingResultDtoData) {
            Object[] actualRow = new Object[3];
            actualRow[0] = row.length > 0 ? row[0] : "";
            actualRow[1] = row.length > 1 ? row[1] : "";
            actualRow[2] = row.length > 2 ? row[2] : "";
            model.addRow(actualRow);
            System.out.println("Added from UprootingResultDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
        }
        System.out.println("Model row count after UprootingResultDto: " + model.getRowCount());

        for (String[] row : slidingResultDtoData) {
            Object[] actualRow = new Object[3];
            actualRow[0] = row.length > 0 ? row[0] : "";
            actualRow[1] = row.length > 1 ? row[1] : "";
            actualRow[2] = row.length > 2 ? row[2] : "";
            model.addRow(actualRow);
            System.out.println("Added from SlidingResultDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
        }
        System.out.println("Model row count after SlidingResultDto: " + model.getRowCount());
        
      
//        for (String[] row : soilCalculatorUtilData) {
//            model.addRow(row);
//        }
        reportTable.setModel(model);
        reportTable.setDefaultRenderer(Object.class, new CustomRenderer());

//        JButton saveButton = new JButton("Save Report");
//        saveButton.addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setDialogTitle("Save Report");
//            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
//                File file = fileChooser.getSelectedFile();
//                try (PrintWriter writer = new PrintWriter(file)) {
//                    writer.write(reportArea.getText());
//                    JOptionPane.showMessageDialog(this, "Report saved successfully.");
//                } catch (IOException ex) {
//                    JOptionPane.showMessageDialog(this, "Error saving file.");
//                }
//            }
//        });
//
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
//        panel.add(saveButton, BorderLayout.SOUTH);
//
//        add(panel);
    }

    private class CustomRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(LEFT); // Default

            Object col0 = table.getValueAt(row, 0);
            Object col1 = table.getValueAt(row, 1);
            Object col2 = table.getValueAt(row, 2);

            // Style for 1-column centered rows (middle column has text only)
            if ("".equals(col0) && !"".equals(col1) && "".equals(col2)) {
                if (column == 1) {
                    setHorizontalAlignment(CENTER);
                    setFont(getFont().deriveFont(Font.BOLD));
                    setForeground(Color.BLUE);
                } else {
                    setText(""); // hide col 0 and 2
                }
            } // Style for 2-column rows (only col 0 and 2 used)
            else if (!"".equals(col0) && "".equals(col1) && "".equals(col2)) {
                if (column == 1 || column == 2) {
                    setText(""); // blank unused columns
                }
            }

            // Optional: you can style headers or other special rows too here
            return c;
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "", "", ""
            }
        ));
        jScrollPane2.setViewportView(reportTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Action listener for the PDF export button
    private void exportPdfButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        exportToPdf(reportTable);
    }                                            

    // --- START: PDF Export Method (adjusted for 3 columns) ---
    private void exportToPdf(JTable reportTable) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report as PDF");
        fileChooser.setSelectedFile(new File("PadFoundationReport.pdf"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.getAbsolutePath().toLowerCase().endsWith(".pdf")) { 
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }

            try {
                Document document = new Document(PageSize.A4); 
                PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                document.open();

                Paragraph title = new Paragraph("Pad Foundation Design Report",
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, com.itextpdf.text.Font.BOLD));
                title.setAlignment(Paragraph.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph("\n"));

                TableModel model = reportTable.getModel(); 
                
                // Changed to support 3 columns dynamically from the model
                PdfPTable pdfTable = new PdfPTable(model.getColumnCount()); 
                pdfTable.setWidthPercentage(100); 
                pdfTable.setSpacingBefore(10f); 
                pdfTable.setSpacingAfter(10f);  

                // Adjust column widths for 3 columns if the count matches
                // You might need to fine-tune these percentages based on your data content
                if (model.getColumnCount() == 3) { 
                    float[] columnWidths = {0.4f, 0.2f, 0.4f}; // Example: Parameter (40%), Unit (20%), Value (40%)
                    pdfTable.setWidths(columnWidths);
                } else if (model.getColumnCount() == 2) {
                    float[] columnWidths = {0.6f, 0.4f};
                    pdfTable.setWidths(columnWidths);
                }


                // Add Table Headers
                com.itextpdf.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, com.itextpdf.text.Font.BOLD); 
                for (int i = 0; i < model.getColumnCount(); i++) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(model.getColumnName(i), headerFont));
                    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    headerCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    headerCell.setPadding(5);
                    pdfTable.addCell(headerCell);
                }

                // Add Table Data
                com.itextpdf.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10); 
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        Object value = model.getValueAt(row, col);
                        PdfPCell dataCell = new PdfPCell(new Phrase(value != null ? value.toString() : "", dataFont));
                        dataCell.setPadding(5);
                        // Adjust alignment based on column (e.g., align value column to right)
                        if (model.getColumnCount() == 3 && col == 2) { // Assuming Value is the 3rd column (index 2)
                            dataCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        } else if (model.getColumnCount() == 2 && col == 1) { // For 2-column scenario, Value is 2nd column (index 1)
                            dataCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        }
                        else { 
                            dataCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                        }
                        pdfTable.addCell(dataCell);
                    }
                }

                document.add(pdfTable);
                document.close();

                JOptionPane.showMessageDialog(this,
                        "Report exported successfully to:\n" + fileToSave.getAbsolutePath(),
                        "PDF Export Complete", JOptionPane.INFORMATION_MESSAGE);

            } catch (DocumentException | IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error exporting report to PDF:\n" + ex.getMessage(),
                        "PDF Export Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
            }
        }
    }
    // --- END: PDF Export Method ---
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
//            java.util.logging.Logger.getLogger(ReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ReportFrame().setVisible(true);
//            }
//        });
//    }
    private javax.swing.JButton exportPdfButton;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable reportTable;
    // End of variables declaration//GEN-END:variables
}
