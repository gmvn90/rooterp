/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.infrastructure;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;

import com.swcommodities.wsmill.exels.StockReportExcel;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author trung
 */
@Service
public class ExcelReportService {

    private static final String fileSeparator = "/";
    private static final String inputFolder = "reports";
    private static final String outputFolder = "temp";
    @Resource(name = "configConfigurer") private Properties configConfigurer;

    private String getFilePath(String webPath, String subFolder, String fileNameWithoutXlsExt) {
        return webPath + fileSeparator + subFolder + fileSeparator + fileNameWithoutXlsExt + ".xls";
    }

    private String getFilePathForInputFile(String webPath, String fileNameWithoutXlsExt) {
        return getFilePath(webPath, inputFolder, fileNameWithoutXlsExt);
    }

    private String getFilePathForOutputFile(String webPath, String fileNameWithoutXlsExt) {
        return getFilePath(webPath, outputFolder, fileNameWithoutXlsExt);
    }

    public String generateImportReport(String webPath, String excelInputFileName,
        Map<String, Object> data, String excelOutputFileName) {
        XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getFilePathForInputFile(webPath, excelInputFileName), data,
                getFilePathForOutputFile(webPath, excelOutputFileName));
        } catch (ParsePropertyException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return configConfigurer.getProperty("base_web_url") + fileSeparator + outputFolder + fileSeparator + excelOutputFileName + ".xls";
    }
    
    public String generateReportAndStoreInBaseDir(String excelInputFileNameWithExtension, Map<String, Object> data, 
            String excelOutputFileNameWithExtension) {
        XLSTransformer transformer = new XLSTransformer();
        String fullPathWithoutFileName = configConfigurer.getProperty("base_dir");
        try {
            transformer.transformXLS(Paths.get(configConfigurer.getProperty("excel_input_dir"), excelInputFileNameWithExtension).toString(), data,
                fullPathWithoutFileName + fileSeparator + excelOutputFileNameWithExtension);
        } catch (ParsePropertyException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return excelOutputFileNameWithExtension;
    }
}
