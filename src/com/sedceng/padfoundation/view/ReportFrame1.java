/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.sedceng.padfoundation.view;

// --- iText/OpenPDF Imports ---
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font; // Explicitly import iText's Font class here
// --- End iText/OpenPDF Imports ---

import com.sedceng.padfoundation.dto.FoundationGeometryDto;
import com.sedceng.padfoundation.dto.ProjectDetailDto;
import com.sedceng.padfoundation.dto.ReinforcementDto;
import com.sedceng.padfoundation.dto.ResultDto;
import com.sedceng.padfoundation.dto.ServiceabilityLoadsDto;
import com.sedceng.padfoundation.dto.SoilPropertiesNewDto;
import com.sedceng.padfoundation.dto.UltimateLoadsDto;
import com.sedceng.padfoundation.util.SoilPressureCalculatorUtil;
import java.awt.Color;
import java.awt.Component;
import static java.awt.SystemColor.text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List; // Import List for DTO report data
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.LEFT;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Sanduni Navoda
 */
public class ReportFrame1 extends javax.swing.JFrame {

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
     * @param overturningResultDto
     * @param reinforcementDto
     * @param reinforcementDesignResultDto
     * @param designForCompInSaggingResultDto
     * @param designForCompInHoggingResultDto
     * @param designForTensInSaggingResultDto
     * @param designForTenseInHoggingResultDto
     * @param designForShearResultDto
     */
    public ReportFrame1(ProjectDetailDto projectDetailDto, 
            FoundationGeometryDto geometryDto, 
            SoilPressureCalculatorUtil soilCalculator, 
            SoilPropertiesNewDto soilDto, 
            ServiceabilityLoadsDto serviceabilityLoadsDto, 
            UltimateLoadsDto ultimateLoadsDto, 
            ResultDto bearingResutDto, 
            ResultDto uprootingResultDto, 
            ResultDto slidingResultDto, 
            ResultDto overturningResultDto, 
            ReinforcementDto reinforcementDto,
            ResultDto reinforcementDesignResultDto, 
            ResultDto designForCompInSaggingResultDto,
            ResultDto designForCompInHoggingResultDto,
            ResultDto designForTensInSaggingResultDto,
            ResultDto designForTenseInHoggingResultDto,
            ResultDto designForShearResultDto) {
        initComponents();

        setTitle("Report");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize DefaultTableModel with 3 columns as per your original code
        DefaultTableModel model = new DefaultTableModel(new String[]{"Parameter", "Unit", "Value"}, 0);

        System.out.println("Populating Report Table Model..."); // Debugging print

        // Get data from DTOs (using List<String[]> as per your previous code)
        List<String[]> projectDetailDtoData = projectDetailDto.getReportLines();
        List<String[]> soilReportData = soilDto.getReportLines();
        List<String[]> serviceabilityLoadData = serviceabilityLoadsDto.getReportLines();
        List<String[]> ultimateLoadsDtoData = ultimateLoadsDto.getReportLines();
        List<String[]> foundationGeometryDtoData = geometryDto.getReportLines();
        List<String[]> bearingResultDtoData = bearingResutDto.getData();
        List<String[]> uprootingResultDtoData = uprootingResultDto.getData();
        List<String[]> slidingResultDtoData = slidingResultDto.getData();
        List<String[]> overturningResultDtoData = overturningResultDto.getData();
        List<String[]> reinforcementDtoData = reinforcementDto.getReportLines();
        List<String[]> reinforcementDesignResultDtoData = reinforcementDesignResultDto.getData();
        List<String[]> designForCompInSaggingResultDtoData = designForCompInSaggingResultDto.getData();
        List<String[]> designForCompInHoggingResultDtoData = designForCompInHoggingResultDto.getData();
        List<String[]> designForTensInSaggingResultDtoData = designForTensInSaggingResultDto.getData();
        List<String[]> designForTenseInHoggingResultDtoData = designForTenseInHoggingResultDto.getData();
        List<String[]> designForShearResultDtoData = designForShearResultDto.getData();
        
        
        

        // Add rows to the model. Assume each 'row' String[] has at least 3 elements.
        // If a row has fewer than 3, ensure you handle it to avoid ArrayIndexOutOfBoundsException
//        for (String[] row : soilReportData) {
//            Object[] actualRow = new Object[3];
//            actualRow[0] = row.length > 0 ? row[0] : "";
//            actualRow[1] = row.length > 1 ? row[1] : ""; // Assumed Unit or empty string
//            actualRow[2] = row.length > 2 ? row[2] : ""; // Assumed Value or empty string
//            model.addRow(actualRow);
//            System.out.println("Added from SoilDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
//        }
//        System.out.println("Model row count after SoilDto: " + model.getRowCount());
//
//        for (String[] row : serviceabilityLoadData) {
//            Object[] actualRow = new Object[3];
//            actualRow[0] = row.length > 0 ? row[0] : "";
//            actualRow[1] = row.length > 1 ? row[1] : "";
//            actualRow[2] = row.length > 2 ? row[2] : "";
//            model.addRow(actualRow);
//            System.out.println("Added from ServiceabilityLoadsDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
//        }
//        System.out.println("Model row count after ServiceabilityLoadsDto: " + model.getRowCount());
//
//        for (String[] row : ultimateLoadsDtoData) {
//            Object[] actualRow = new Object[3];
//            actualRow[0] = row.length > 0 ? row[0] : "";
//            actualRow[1] = row.length > 1 ? row[1] : "";
//            actualRow[2] = row.length > 2 ? row[2] : "";
//            model.addRow(actualRow);
//            System.out.println("Added from UltimateLoadsDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
//        }
//        System.out.println("Model row count after UltimateLoadsDto: " + model.getRowCount());
//
//        for (String[] row : foundationGeometryDtoData) {
//            Object[] actualRow = new Object[3];
//            actualRow[0] = row.length > 0 ? row[0] : "";
//            actualRow[1] = row.length > 1 ? row[1] : "";
//            actualRow[2] = row.length > 2 ? row[2] : "";
//            model.addRow(actualRow);
//            System.out.println("Added from GeometryDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
//        }
//        System.out.println("Model row count after GeometryDto: " + model.getRowCount());
//
//        for (String[] row : bearingResultDtoData) {
//            Object[] actualRow = new Object[3];
//            actualRow[0] = row.length > 0 ? row[0] : "";
//            actualRow[1] = row.length > 1 ? row[1] : "";
//            actualRow[2] = row.length > 2 ? row[2] : "";
//            model.addRow(actualRow);
//            System.out.println("Added from BearingResultDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
//        }
//        System.out.println("Model row count after BearingResultDto: " + model.getRowCount());
//
//        for (String[] row : uprootingResultDtoData) {
//            Object[] actualRow = new Object[3];
//            actualRow[0] = row.length > 0 ? row[0] : "";
//            actualRow[1] = row.length > 1 ? row[1] : "";
//            actualRow[2] = row.length > 2 ? row[2] : "";
//            model.addRow(actualRow);
//            System.out.println("Added from UprootingResultDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
//        }
//        System.out.println("Model row count after UprootingResultDto: " + model.getRowCount());
//
//        for (String[] row : slidingResultDtoData) {
//            Object[] actualRow = new Object[3];
//            actualRow[0] = row.length > 0 ? row[0] : "";
//            actualRow[1] = row.length > 1 ? row[1] : "";
//            actualRow[2] = row.length > 2 ? row[2] : "";
//            model.addRow(actualRow);
//            System.out.println("Added from SlidingResultDto: " + actualRow[0] + " | " + actualRow[1] + " | " + actualRow[2]);
//        }
//        System.out.println("Model row count after SlidingResultDto: " + model.getRowCount());
//        // ---End of table format 1-------
        //---Table format 2
        List<String[]> data = new ArrayList<>();

// Add a full row
        for (String[] row : projectDetailDtoData) {
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
        for (String[] row : reinforcementDtoData) {
            data.add(row);
        }
        for (String[] row : reinforcementDesignResultDtoData) {
            data.add(row);
        }
        for (String[] row : designForCompInSaggingResultDtoData) {
            data.add(row);
        }
        for (String[] row : designForCompInHoggingResultDtoData) {
            data.add(row);
        }
        for (String[] row : designForTensInSaggingResultDtoData) {
            data.add(row);
        }
        for (String[] row : designForTenseInHoggingResultDtoData) {
            data.add(row);
        }
        for (String[] row : designForShearResultDtoData) {
            data.add(row);
        }
        

        for (String[] row : data) {
            // Ensure row always has exactly 3 elements
            if (row.length == 3) {
                model.addRow(row);
            } else if (row.length == 2) {
                model.addRow(new String[]{row[0], row[1], ""});
            } else if (row.length == 1) {
                model.addRow(new String[]{row[0], "", ""});
            }
        }
        //-----------

        reportTable.setModel(model); // Set the populated model to the JTable
        reportTable.setDefaultRenderer(Object.class, new CustomRenderer());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     *
     * This section has been manually adjusted to include the exportPdfButton
     * and correctly integrate it into the GroupLayout.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        exportPdfButton = new javax.swing.JButton(); // Declared and initialized here

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // IMPORTANT: The column headers and initial data for reportTable
        // should match what your DTOs provide (e.g., 3 columns: Parameter, Unit, Value)
        reportTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null}, // Changed to 3 columns
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String[]{
                    "Parameter", "Unit", "Value" // Changed to 3 column headers
                }
        ));
        jScrollPane2.setViewportView(reportTable);

        // Configure the new button and its action
        exportPdfButton.setText("Export to PDF");
        exportPdfButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportPdfButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(exportPdfButton)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exportPdfButton)
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

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
                    setFont(getFont().deriveFont(java.awt.Font.BOLD));
                    //setForeground(Color.BLUE);
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

                // Extract the first 7 rows from the table model to make a 2-column summary table
                TableModel model = reportTable.getModel();
                
                Paragraph title = new Paragraph("Pad Foundation Design Report",
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, com.itextpdf.text.Font.BOLD));
                title.setAlignment(Paragraph.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph("\n"));

                PdfPTable infoTable = new PdfPTable(2); // 2 columns: Label, Value
                infoTable.setWidthPercentage(100);
                infoTable.setSpacingAfter(10f);
                infoTable.setWidths(new float[]{1f, 3f}); // 1:3 ratio (Label:Value)

                Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
                Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

                int infoRowCount = Math.min(8, model.getRowCount()); // In case there are fewer than 7

                for (int i = 0; i < infoRowCount; i++) {
                    Object key = model.getValueAt(i, 0); // Label
                    Object value = model.getValueAt(i, 1); // Value (middle column might be just "=" or empty)

                    PdfPCell keyCell = new PdfPCell(new Phrase(key != null ? key.toString() : "", labelFont));
                    PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value.toString() : "", valueFont));

                    keyCell.setPadding(5);
                    valueCell.setPadding(5);

                    infoTable.addCell(keyCell);
                    infoTable.addCell(valueCell);
                }

// Add the info section to the document
                document.add(infoTable);

// Optionally, add a center-aligned paragraph
                Paragraph sectionTitle = new Paragraph("Design Calculation for 40 m 4 Legged Tower Foundation",
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                sectionTitle.setAlignment(Paragraph.ALIGN_CENTER);
                sectionTitle.setSpacingAfter(10f);
                document.add(sectionTitle);

                //--End of the hedding render

                // Changed to support 3 columns dynamically from the model
                PdfPTable pdfTable = new PdfPTable(model.getColumnCount());
                pdfTable.setWidthPercentage(100);
                pdfTable.setSpacingBefore(10f);
                pdfTable.setSpacingAfter(10f);

                // Adjust column widths for 3 columns if the count matches
                // You might need to fine-tune these percentages based on your data content
                if (model.getColumnCount() == 3) {
                    float[] columnWidths = {5f, 0.3f, 1f}; // Left and right wider, middle very narrow
                    pdfTable.setWidths(columnWidths);
                } else if (model.getColumnCount() == 2) {
                    float[] columnWidths = {0.6f, 0.4f};
                    pdfTable.setWidths(columnWidths);
                }

                // Add Table Headers
//                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, com.itextpdf.text.Font.BOLD);
//                for (int i = 0; i < model.getColumnCount(); i++) {
//                    PdfPCell headerCell = new PdfPCell(new Phrase(model.getColumnName(i), headerFont));
//                    headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//                    headerCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
//                    headerCell.setPadding(5);
//                    headerCell.setBorder(PdfPCell.NO_BORDER); // Hide header borders
//                    pdfTable.addCell(headerCell);
//                }

                // Fonts
                Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
                Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

                // Add Table Data
                for (int row = 9; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        Object value = model.getValueAt(row, col);

                        String text = value != null ? value.toString() : "";

                        Font usedFont = normalFont;

                        // Basic bold detection by special marker
                        if (text.startsWith("**") && text.endsWith("**")) {
                            text = text.substring(2, text.length() - 2); // Remove **
                            usedFont = boldFont;
                        }
                        PdfPCell dataCell = new PdfPCell(new Phrase(text, usedFont));
                        dataCell.setPadding(5);
                        dataCell.setBorder(PdfPCell.NO_BORDER);// Hide data cell borders
                        // Adjust alignment based on column (e.g., align value column to right)
                        if (model.getColumnCount() == 3 && col == 2) { // Assuming Value is the 3rd column (index 2)
                            dataCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        } else if (model.getColumnCount() == 2 && col == 1) { // For 2-column scenario, Value is 2nd column (index 1)
                            dataCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        } else {
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

    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable reportTable;
    private javax.swing.JButton exportPdfButton; // Declared here for initComponents
    // End of variables declaration                   
}
