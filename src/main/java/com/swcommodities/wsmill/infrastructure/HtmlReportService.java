/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.infrastructure;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 *
 * @author macOS
 */

@Service
public class HtmlReportService {
    
    public String toHtmlString(String templateNameWithExtension, Map<String, Object> data) throws IOException, TemplateException {
        // template will be in resources/web-inf/templates
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "/");
        Template template = cfg.getTemplate("WEB-INF/templates/" + templateNameWithExtension);
        Writer out = new StringWriter();
        template.process(data, out);
        out.flush();
        return out.toString();
    }
    
}
