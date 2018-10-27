/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dao.CupTestDao;
import com.swcommodities.wsmill.hibernate.dto.CupTest;

/**
 *
 * @author duhc
 */
public class CupTestService {
    private CupTestDao cupTestDao;

    public void setCupTestDao(CupTestDao cupTestDao) {
        this.cupTestDao = cupTestDao;
    }
    
    public ArrayList<CupTest> getCupTestByQrId(int id) {
        return cupTestDao.getCupTestByQrId(id);
    }
    
    public void deleteOldCupTest(ArrayList<CupTest> cts) {
        cupTestDao.deleteOldCupTest(cts);
    }
    
    public void updateCupTest(CupTest ct) {
        cupTestDao.updateCupTest(ct);
    }
}
