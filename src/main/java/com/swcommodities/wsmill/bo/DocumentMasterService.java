/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dao.DocumentMasterDao;
import com.swcommodities.wsmill.hibernate.dto.DocumentMaster;

/**
 *
 * @author duhc
 */
public class DocumentMasterService {
    private DocumentMasterDao documentMasterDao;

    public void setDocumentMasterDao(DocumentMasterDao documentMasterDao) {
        this.documentMasterDao = documentMasterDao;
    }
    
    public ArrayList<DocumentMaster> getAllDocument(String type) {
        return documentMasterDao.getAllDocumentByType(type);
    }
    
    public DocumentMaster getDocumentMasterById(int id){
        return documentMasterDao.getDocumentMasterById(id);
    }
}
