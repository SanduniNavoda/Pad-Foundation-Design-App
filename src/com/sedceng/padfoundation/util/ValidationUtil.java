/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.util;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Sanduni Navoda
 */
public class ValidationUtil {
    
    
    public static void applyDoubleFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DoubleFilter());
    }
    
    private static class DoubleFilter extends DocumentFilter{

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.replace(offset, offset + length, text);
            if (isValidDouble(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.insert(offset, string);
            if (isValidDouble(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            }
        }
        
        private boolean isValidDouble(String text) {
            if (text.isEmpty() || text.equals("-") || text.equals(".")) return true;
            try {
                Double.parseDouble(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        
    }
    
    public static void applyDoubleFilterToAllTextFields(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                applyDoubleFilter((JTextField) component);
            } else if (component instanceof Container) {
                applyDoubleFilterToAllTextFields((Container) component); // Recursive call
            }
        }
    }
    
    public static boolean validateRequiredFields(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (field.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(container, "Please fill in all required fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    field.requestFocus();
                    return false;
                }
            } else if (comp instanceof Container) {
                // Recursively validate nested containers (e.g., JPanel inside JFrame)
                if (!validateRequiredFields((Container) comp)) {
                    return false;
                }
            }
        }
        return true;
    }
}
