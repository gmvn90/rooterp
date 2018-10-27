/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.exels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author kiendn
 */
public class ExcelFile {

    private boolean zero_blank = true;
    private static final String file_separator = System.getProperty("file.separator");
    private String work_path = null;
    private String template = null;
    private FileInputStream fin = null;
    private POIFSFileSystem fsys = null;
    private HSSFWorkbook workbook = null;
    private HSSFSheet sheet = null;
    private Hashtable cellStyles = new Hashtable();
    private int colCount = 0;
    private int dy = 0;
    //
    public static final short BORDER_THIN = HSSFCellStyle.BORDER_THIN;
    public static final short BORDER_MEDIUM = HSSFCellStyle.BORDER_MEDIUM;
    public static final short BORDER_THICK = HSSFCellStyle.BORDER_THICK;
    public static final short BORDER_NONE = HSSFCellStyle.BORDER_NONE;
    //
    public static final short VERTICAL_TOP = HSSFCellStyle.VERTICAL_TOP;
    public static final short VERTICAL_BOTTOM = HSSFCellStyle.VERTICAL_BOTTOM;
    public static final short VERTICAL_CENTER = HSSFCellStyle.VERTICAL_CENTER;
    private static final Hashtable color_table = new Hashtable();
    
    public ExcelFile(){
        
    }

    public ExcelFile(String template, int sheet_no, String work_path) {
        color_table.put("RED", new HSSFColor.RED());
        color_table.put("BLUE", new HSSFColor.BLUE());
        color_table.put("WHITE", new HSSFColor.WHITE());
        color_table.put("GREEN", new HSSFColor.GREEN());
        color_table.put("YELLOW", new HSSFColor.YELLOW());
        color_table.put("BROWN", new HSSFColor.BROWN());
        color_table.put("BLACK", new HSSFColor.BLACK());

        this.work_path = work_path;

        this.template = template;
        String filename = getAsbPath("reports", template);
        try {
            fin = new java.io.FileInputStream(filename);
            fsys = new POIFSFileSystem(fin);
            workbook = new HSSFWorkbook(fsys);
            setSheet(sheet_no);
        } catch (java.io.IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String getTemplate() {
        return template;
    }

    public String getAsbPath(String path, String filename) {
        if (path == null) {
            return filename;
        } else {
            return work_path + file_separator + path + file_separator + filename;
        }
    }

    public String getAsbPath(String filename) {
        return work_path + file_separator + "temp" + file_separator + filename;
    }

    public HSSFSheet getSheet(int no) {
        HSSFSheet sheet = workbook.getSheetAt(no);
        if (sheet == null) {
            sheet = workbook.createSheet();
        }
        return sheet;
    }

    public int getFirstRow(String sign) {
        cellStyles.clear();
        int dy = 0;
        for (int i = 0; i < 50; i++) {
            HSSFCell cell = getCell(0, i);
            if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                if (cell.getStringCellValue().equals(sign)) {
                    cell.setCellValue("");
                    dy = i;
                    break;
                }
            }
        }

        if (dy > 0) {
            colCount = getRow(dy).getPhysicalNumberOfCells();
        }

        for (int i = 0; i < colCount; i++) {
            HSSFCell cell = getCell(i, dy);
            cellStyles.put("template" + i, cell.getCellStyle());
            cell.setCellValue("");
        }
        return dy;
    }

    public void setSheet(int sheet_no) {
        this.sheet = getSheet(sheet_no);
        dy = getFirstRow("***");
        /*
         cellStyles.clear();

         this.sheet = getSheet(sheet_no);

         for (int i = 0; i < 50; i++) {
         HSSFCell cell = getCell(0,i);
         if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
         if (cell.getStringCellValue().equals("***")) {
         cell.setCellValue("");
         dy = i;
         break;
         }
         }
         }

         if (dy > 0) colCount  = getRow(dy).getPhysicalNumberOfCells();

         for (int i = 0; i < colCount; i++) {
         HSSFCell cell = getCell(i,dy);
         cellStyles.put("template"+i,cell.getCellStyle());
         cell.setCellValue("");
         } */
    }

    public HSSFRow getRow(int y) {
        HSSFRow row = sheet.getRow(y);
        if (row == null) {
            row = sheet.createRow(y);
        }
        return row;
    }

    public HSSFCell getCell(int x, int y) {
        HSSFCell cell = getRow(y).getCell((short) x);
        if (cell == null) {
            cell = getRow(y).createCell((short) x);
        }
        return cell;
    }

    public String export(String filename, String path) {
        String file_tmp = getAsbPath(path, filename);
        if (workbook == null) {
            return null;
        }
        try {
            FileOutputStream file = new FileOutputStream(file_tmp);
            workbook.write(file);
            file.close();
            fin.close();
        } catch (java.io.IOException ex) {
            //System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        if (path == null) {
            return filename;
        } else {
            return path + "/" + filename;
        }
    }

    public String export(String filename) {
        return export(filename, "temp");
    }

    private HSSFFont getFont(HSSFCellStyle style) {
        return workbook.getFontAt(style.getFontIndex());
    }

    public void setCellTextColor(int x, int y, String color) {
        color = color.toUpperCase();
        HSSFColor cl = (HSSFColor) color_table.get(color);
        if (cl == null) {
            return;
        }

        HSSFCell cell = getCell(x, y);

        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        style.setFont(font);

        copyStyleFromCell(style, x, y);

        font.setColor(cl.getIndex());//HSSFColor.RED.index);
        cell.setCellStyle(style);
    }

    public void setRowTextColor(int y, String color) {
        color = color.toUpperCase();
        HSSFColor cl = (HSSFColor) color_table.get(color);
        if (cl == null) {
            return;
        }

        for (int x = 0; x < colCount; x++) {
            HSSFCellStyle style = this.getCellStyle("row_color" + x);
            copyStyle(style, this.getCellStyle(x));
            this.getFont(style).setColor(cl.getIndex());
            HSSFCell cell = getCell(x, y);
            cell.setCellStyle(style);
        }
    }

    private void copyStyle(HSSFCellStyle style, HSSFCellStyle st) {
        style.setDataFormat(st.getDataFormat());
        getFont(style).setBoldweight(getFont(st).getBoldweight());
        getFont(style).setItalic(getFont(st).getItalic());
        getFont(style).setFontHeight(getFont(st).getFontHeight());
        style.setAlignment(st.getAlignment());
        style.setBorderBottom(st.getBorderBottom());
        style.setBorderLeft(st.getBorderLeft());
        style.setBorderRight(st.getBorderRight());
        style.setBorderTop(st.getBorderTop());
        style.setWrapText(st.getWrapText());
    }

    private void copyStyleFromCell(HSSFCellStyle style, int x, int y) {
        copyStyle(style, getCell(x, y).getCellStyle());
    }

    public HSSFCellStyle setCellStyle(int x, int y, String at) {
        HSSFCellStyle style = getCellStyle(at + x);
        copyStyleFromCell(style, x, y);
        if (at == null) {
            at = "";
        }
        if (at.indexOf('B') >= 0) {
            getCellFont(style).setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        if (at.indexOf('I') >= 0) {
            getCellFont(style).setItalic(true);
        }
        int idx = at.indexOf('=');
        if (idx >= 0) {
            short size = Short.parseShort(at.substring(idx + 1));
            getCellFont(style).setFontHeightInPoints(size);
        }
        getCell(x, y).setCellStyle(style);
        return style;
    }

    private HSSFFont getCellFont(HSSFCellStyle style) {
        return workbook.getFontAt(style.getFontIndex());
    }

    public HSSFFont getCellFont(int x, int y) {
        return getCellFont(getCellStyle(x, y));
    }

    public HSSFCellStyle getCellStyle(int x, int y) {
        return getCell(x, y).getCellStyle();
    }

    public HSSFCellStyle getCellStyle(String name) {
        HSSFCellStyle style = (HSSFCellStyle) cellStyles.get(name);
        if (style == null) {
            style = workbook.createCellStyle();
            style.setFont(workbook.createFont());
            cellStyles.put(name, style);
        }
        return style;
    }

    public HSSFCellStyle getCellStyle(int x) {
        return getCellStyle("template" + x);
    }

    /*-------------------------------------------------*/
    public void setCellStyleFrom(int x, int y, int fx, int fy) {
        HSSFCellStyle st = this.getCell(fx, fy).getCellStyle();
        this.getCell(x, y).setCellStyle(st);
    }

    public void setCellStyleFrom(int x, int y, int from) {
        setCellStyleFrom(x, y, x, from);
    }

    public void setRowStyleFrom(int y, int from) {
        for (int i = 0; i < colCount; i++) {
            setCellStyleFrom(i, y, from);
        }
    }

    public void copyRow(int from, int to) {
        for (int i = 0; i < colCount; i++) {
            this.set_CellValue(i, to, this.getCell(i, from).getStringCellValue());
        }
        this.getRow(to);
        setRowStyleFrom(to, from);
    }
    /*-------------------------------------------------*/

    //----------------------------------------------------------
    public void set_CellValue(int x, int y, String value) {
        //setCellStyle(x,y);
        HSSFCell cell = getCell(x, y);
        if (value != null) {
            cell.setCellValue(value);
        } else {
            cell.setCellValue("");
        }
    }
}
