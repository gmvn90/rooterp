package com.swcommodities.wsmill.exels;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.swcommodities.wsmill.hibernate.dto.BankAccount;
import com.swcommodities.wsmill.hibernate.dto.Invoice;
import com.swcommodities.wsmill.hibernate.dto.OtherTransaction;
import com.swcommodities.wsmill.hibernate.dto.query.result.TransactionCardViewResult;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * Created by macOS on 5/3/17.
 */
public class InvoiceInExcel extends ExcelFile {
    private Invoice invoice;
    private List<TransactionCardViewResult> dis;
    private List<TransactionCardViewResult> pis;
    private List<TransactionCardViewResult> sis;
    private List<OtherTransaction> others;
    private BankAccount bankAccount;

    public InvoiceInExcel(String work_path) {
        super("invoice.xls", 0, work_path);
    }

    public InvoiceInExcel(String work_path, Invoice invoice,List<TransactionCardViewResult> dis, 
        List<TransactionCardViewResult> pis, List<TransactionCardViewResult> sis, 
        List<OtherTransaction> others, BankAccount bankAccount) {
        super("invoice.xls", 0, work_path);
        this.invoice = invoice;
        this.dis = dis;
        this.pis = pis;
        this.sis = sis;
        this.others = others;
        this.bankAccount = bankAccount;
    }

    public String generateInvoice(String webPath) {
        Map beans = new HashMap();
        beans.put("invoice", invoice);
        beans.put("dis", dis);
        beans.put("pis", pis);
        beans.put("sis", sis);
        beans.put("others", others);
        beans.put("bankAccount", bankAccount);
        String fileName = "invoice" + invoice.getRefNumber();
        String destFileName = this.getAsbPath(fileName) + ".xls";
        XLSTransformer transformer = new XLSTransformer();
        try {
            transformer.transformXLS(getAsbPath("reports", "invoice") + ".xls", beans, destFileName);
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
