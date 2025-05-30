/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sedceng.padfoundation.service;

import com.sedceng.padfoundation.service.custom.impl.BearingCheckServiceImpl;
import com.sedceng.padfoundation.service.custom.impl.OverturningCheckServiceImpl;
import com.sedceng.padfoundation.service.custom.impl.SlidingCheckServiceImpl;
import com.sedceng.padfoundation.service.custom.impl.UprootingCheckServiceImpl;
//import edu.ijse.layered.service.custom.impl.OrderServiceImpl;
/**
 *
 * @author Sanduni Navoda
 */
public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    
    private ServiceFactory(){}
    
    public static ServiceFactory getInstance(){
        if(serviceFactory == null){
            serviceFactory = new ServiceFactory();
        }
        
        return serviceFactory;
    }
    
        public SuperService getService(ServiceType type){
        switch (type) {
            
            case UPROOTING:
                return new UprootingCheckServiceImpl();
            case BEARING:
                return new BearingCheckServiceImpl();
            case SLIDING:
                return new SlidingCheckServiceImpl();
            case OVERTURNING:
                return new OverturningCheckServiceImpl();
            default:
                return null;
        }
    }
    
    public enum ServiceType{
        UPROOTING, BEARING, SLIDING, OVERTURNING;
    }
}
