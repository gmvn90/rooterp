/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.infrastructure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author trung
 */

@Service
public class PdfRenderService {
    
    @Autowired PathFinderService pathFinderService;
    
    public void render(String absoluteFileName, String html) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(new ByteArrayInputStream(html.getBytes("UTF-8")));
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(doc, null);
            renderer.layout();
            String outputFile =  absoluteFileName;
            OutputStream os = new FileOutputStream(outputFile);
            renderer.createPDF(os);
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public byte[] renderAsByte(String xhtml) {
        byte[] pdf = null;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(new ByteArrayInputStream(xhtml.getBytes("UTF-8")));
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(doc, null);
            renderer.layout();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            renderer.createPDF(byteArrayOutputStream);
            pdf = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdf;
    }
    
    public String renderAsTraceableFile(String subFolder, String fileNameWithExtension, String xhtml) throws IOException {
        String fullPathWithoutFileName = pathFinderService.getBaseDir();
        FileUtils.writeByteArrayToFile(new File(fullPathWithoutFileName + "/" + subFolder + "/" + fileNameWithExtension), 
            renderAsByte(xhtml));
        return subFolder + "/" + fileNameWithExtension;
    }
    
    public String renderAsTraceableUrl(String subFolder, String fileNameWithExtension, String xhtml) throws IOException {
        String uriWithoutSplash = renderAsTraceableFile(subFolder, fileNameWithExtension, xhtml);
        return pathFinderService.getStaticUrlFromUri(uriWithoutSplash);
    }
    
    
}
