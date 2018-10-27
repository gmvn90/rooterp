/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dao.PortMasterDao;
import com.swcommodities.wsmill.hibernate.dto.PortMaster;

/**
 *
 * @author duhc
 */
public class PortMasterService {
    private PortMasterDao portMasterDao;

    public void setPortMasterDao(PortMasterDao portMasterDao) {
        this.portMasterDao = portMasterDao;
    }
    
    public ArrayList<PortMaster> getAllPort() {
        return portMasterDao.getAllPort();
    }
    
    public PortMaster getPortById(int id) {
        return portMasterDao.getPortById(id);
    }
}
