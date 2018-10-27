/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

import java.util.ArrayList;

import com.swcommodities.wsmill.hibernate.dao.DocumentsDao;
import com.swcommodities.wsmill.hibernate.dto.DocumentMaster;
import com.swcommodities.wsmill.hibernate.dto.Documents;

/**
 *
 * @author duhc
 */
public class DocumentsService {
    private DocumentsDao documentsDao;

    public void setDocumentsDao(DocumentsDao documentsDao) {
        this.documentsDao = documentsDao;
    }
    
    public ArrayList<Documents> getDocumentsByInsId(int id, String type) {
        return documentsDao.getDocumentsByInsId(id, type);
    }
    
    public Documents getDocumentById(int id) {
        return documentsDao.getDocumentById(id);
    }
    
     public ArrayList<Documents> getDocumentsByInsIdAndDocumentId(int id, DocumentMaster document_id, String type) {
        return documentsDao.getDocumentsByInsIdAndDocumentId(id, document_id, type);
    }
    
    public void updateDocuments(Documents doc){
        documentsDao.updateDocuments(doc);
    }
}
