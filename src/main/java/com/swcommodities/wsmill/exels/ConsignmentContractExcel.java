package com.swcommodities.wsmill.exels;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.swcommodities.wsmill.hibernate.dto.FinanceContract;
import com.swcommodities.wsmill.hibernate.dto.GradeMaster;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * Created by macOS on 5/23/17.
 */
public class ConsignmentContractExcel extends ExcelFile {
    private FinanceContract financeContract;

    public ConsignmentContractExcel(String work_path) {
        super("consignment_contract.xls", 0, work_path);
    }

    public ConsignmentContractExcel(String work_path, FinanceContract financeContract) {
        super("consignment_contract.xls", 0, work_path);
        this.financeContract = financeContract;
    }

    public  String generateConsignmentContract(String webPath, double instoreCost) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        Map beans = new HashMap();
        beans.put("financeContract", financeContract);
        beans.put("instoreCost", formatter.format(instoreCost));
        GradeMaster grade = financeContract.getGrade();
        beans.put("commodity", "Vietnam " + grade.getQualityMaster().getName() + " Coffee " + grade.getReportDescription());
        beans.put("commodity_vn", "Cà phê nhân " + grade.getQualityMaster().getName() + " " + grade.getReportDescriptionVn());
        String fileName = "consignment_contract" + financeContract.getRefNumber();
        String destFileName = this.getAsbPath(fileName) + ".xls";
        XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getAsbPath("reports", "consignment_contract") + ".xls", beans, destFileName);
        } catch (ParsePropertyException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(StockReportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return webPath + "temp" + "/" + fileName + ".xls";
    }
}
