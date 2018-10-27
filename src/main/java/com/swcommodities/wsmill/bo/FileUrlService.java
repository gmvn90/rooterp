/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.bo;

/**
 *
 * @author trung
 */
public class FileUrlService {
    private String baseStaticHost;
    private String subDirectory = "fileUploads";
    
    public FileUrlService() {
    
    }
    
    public FileUrlService(String baseStaticHost) {
        this.baseStaticHost = baseStaticHost;
    }
    
    public String getUrlByFilename(String fileName) {
        String res = baseStaticHost;
        if(! res.endsWith("/")) {
            res += "/";
        }
        res += subDirectory + "/" + fileName;
        return res;
    }

    public String getBaseStaticHost() {
        return baseStaticHost;
    }

    public FileUrlService setBaseStaticHost(String baseStaticHost) {
        this.baseStaticHost = baseStaticHost;
        return this;
    }

    public String getSubDirectory() {
        return subDirectory;
    }

    public FileUrlService setSubDirectoryWithoutSplash(String subDirectory) {
        this.subDirectory = subDirectory;
        return this;
    }
    
    
    
    
}
