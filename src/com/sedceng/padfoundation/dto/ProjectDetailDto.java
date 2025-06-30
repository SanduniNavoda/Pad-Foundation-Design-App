/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Sanduni Navoda
 */
public class ProjectDetailDto {
    private String site;
    private String client;
    private String vender;
    private String location;
    private String designedBy;
    private String checkedBy;
    private String towerHeight;
    private String noOfLegs;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String today = LocalDate.now().format(formatter);

    public ProjectDetailDto() {
    }

    public ProjectDetailDto(String site, String client, String vender, String location, String designedBy, String checkedBy, String towerHeight, String noOfLegs) {
        this.site = site;
        this.client = client;
        this.vender = vender;
        this.location = location;
        this.designedBy = designedBy;
        this.checkedBy = checkedBy;
        this.towerHeight = towerHeight;
        this.noOfLegs = noOfLegs;
    }

    
    
    
    public List<String[]> getReportLines() {

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{""});
        data.add(new String[]{"Site", site });
        data.add(new String[]{"Client", client});
        data.add(new String[]{"Vender", vender});
        data.add(new String[]{"Location", location});
        data.add(new String[]{"Designed By", designedBy});
        data.add(new String[]{"Checked By", checkedBy});
        data.add(new String[]{"Date", today});
        data.add(new String[]{"Design Calculation for " + towerHeight + "m " + noOfLegs + " Legged Tower Foundation" });
        data.add(new String[]{""});
        
        return data;
    }

    /**
     * @return the site
     */
    public String getSite() {
        return site;
    }

    /**
     * @param site the site to set
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return the vender
     */
    public String getVender() {
        return vender;
    }

    /**
     * @param vender the vender to set
     */
    public void setVender(String vender) {
        this.vender = vender;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the designedBy
     */
    public String getDesignedBy() {
        return designedBy;
    }

    /**
     * @param designedBy the designedBy to set
     */
    public void setDesignedBy(String designedBy) {
        this.designedBy = designedBy;
    }

    /**
     * @return the checkedBy
     */
    public String getCheckedBy() {
        return checkedBy;
    }

    /**
     * @param checkedBy the checkedBy to set
     */
    public void setCheckedBy(String checkedBy) {
        this.checkedBy = checkedBy;
    }

    /**
     * @return the towerHeight
     */
    public String getTowerHeight() {
        return towerHeight;
    }

    /**
     * @param towerHeight the towerHeight to set
     */
    public void setTowerHeight(String towerHeight) {
        this.towerHeight = towerHeight;
    }

    /**
     * @return the noOfLegs
     */
    public String getNoOfLegs() {
        return noOfLegs;
    }

    /**
     * @param noOfLegs the noOfLegs to set
     */
    public void setNoOfLegs(String noOfLegs) {
        this.noOfLegs = noOfLegs;
    }

    
    
    
    
    
}
