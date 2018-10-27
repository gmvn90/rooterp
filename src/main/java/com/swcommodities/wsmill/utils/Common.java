/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.lowagie.text.DocumentException;
import com.swcommodities.wsmill.hibernate.dto.Menu;

/**
 *
 * @author kiendn
 */
public class Common {

    public static final String time_format = "hh:mm:ss";
    public static final String date_format = "dd-MMM-yy hh:mm a";
    public static final String date_format_a = "dd-MMM-yy";
    public static final String date_format_MMM_yyyy = "MMM-yyyy";
    public static final String date_format_MM_yyyy = "MM/yyyy";
    public static final String date_format_full = "dd MMMM yyyy";
    public static final String date_format_full_dash = "dd-MMMM-yyyy";
    public static final String date_format_month_year = "MMMM yyyy";
    public static final String time_format_a = "hh:mm a";
    public static final String date_format_slash = "dd/MM/yy hh:mm a";
    public static final String date_format_slash_a = "dd/MM/yy";
    public static final String date_format_code = "yyMMdd";
    public static final String date_format_ddMMyyyy_dash = "dd-MM-yyyy";
    public static final String date_format_yyyyMMdd = "yyyy-MM-dd";
    public static final String datetime_format_yyyyMMdd = "yyyy-MM-dd hh:mm:ss";
    public static final String date_js_format_dMMMy = "d-MMM-y";
    public static final String[] strMonths = new String[]{"Jan", "Feb", "Mar", "Apr", "May",
        "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static void main(String[] args)
            throws IOException, DocumentException, MessagingException {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.vnn.vn");
        //sender.setHost("smtp.gmail.com");
        sender.setPort(25);
        sender.setUsername("swcommovn");
        sender.setPassword("swc2368142536");

        Properties prop = sender.getJavaMailProperties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        //prop.put("mail.debug", "true");

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(new InternetAddress("swcommovn@vnn.vn"));
        String[] addressTo = {"nhatminh.sw@gmail.com", "swcvn@vnn.vn"};
        helper.setTo(new InternetAddress("swcvn@vnn.vn"));
        String[] addressCc = {"nhatminh1601@gmail.com", "mr.daudinh@gmail.com"};
        //helper.setCc(new InternetAddress("vinh@swcvn.com.vn"));
        helper.setSubject("Test mail");
        helper.setText("Test content");

        new File("/Users/gmvn/Project/wsmill/ShippingAdvice/3c62aba5-fdbc-405a-ae13-bcbed851942c.pdf");

        helper.addAttachment("ShippingAdvice.pdf", new File("/Users/gmvn/Project/wsmill/ShippingAdvice/3c62aba5-fdbc-405a-ae13-bcbed851942c.pdf"));
        sender.send(message);
    }

    public static Date convertStringToDate(String str, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        System.out.println(str);
        Date date = dateFormat.parse(str);
        return date;
    }

    public static ArrayList<Menu> sortMenu(ArrayList<Menu> list) {
        list.sort((a, b) -> a.getId().compareTo(b.getId()));
        return list;
    }

    public static int getDateCode(Date date) {
        return Integer.parseInt(getDateFromDatabase(date, Common.date_format_code));
    }

    /**
     *
     * @param mode : 1:full, 2:2 numbers
     */
    public static String getCurrentYear(int mode) {
        Calendar cal = Calendar.getInstance();
        String base = cal.get(Calendar.YEAR) + "";
        if (mode == 2) {
            base = base.substring(2);
        }
        return base;
    }

    public static int compareDateWithMonth(String str, String format) {
        try {
            Date date = Common.convertStringToDate(str, format);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            int lengthOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            return (dayOfMonth < lengthOfMonth) ? dayOfMonth : lengthOfMonth;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Calendar getCalendarFromDate(String str, String format) {
        try {
            Date date = Common.convertStringToDate(str, format);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int compareCurrentDateWithMonths() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int lengthOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return (dayOfMonth < lengthOfMonth) ? dayOfMonth : lengthOfMonth;
    }

    public static Calendar getCalendarFromCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return cal;
    }

    public static String getNewRefNumber(String current_ref, String prefix) {
        if (current_ref != null) {
            int last_index = current_ref.lastIndexOf("-");
            if (last_index > 0) {
                String cur_year = current_ref.substring(last_index - 2, last_index);
                int new_ref = Integer.parseInt(current_ref.substring(last_index + 1)) + 1;
                if (!getCurrentYear(2).equals(cur_year)) {
                    cur_year = getCurrentYear(2);
                    new_ref = 1;
                }
                return prefix + cur_year + "-" + getRefNumberString(new_ref);
            }
        } else {
            return prefix + getCurrentYear(2) + "-00001";
        }
        return "";
    }

    public static StringBuilder getRefNumberString(int number) {
        StringBuilder result = new StringBuilder();
        if (number < 10) {
            for (int i = 0; i < 4; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 9 && number <= 99) {
            for (int i = 0; i < 3; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 99 && number <= 999) {
            for (int i = 0; i < 2; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 999 && number <= 9999) {
            for (int i = 0; i < 1; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 9999 && number <= 99999) {
            result.append(number);
        }
        return result;
    }

    public static StringBuilder getPalletRefNumber(long number) {
        StringBuilder result = new StringBuilder();
        if (number < 10) {
            for (int i = 0; i < 8; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 9 && number <= 99) {
            for (int i = 0; i < 7; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 99 && number <= 999) {
            for (int i = 0; i < 6; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 999 && number <= 9999) {
            for (int i = 0; i < 5; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 9999 && number <= 99999) {
            for (int i = 0; i < 4; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 99999 && number <= 999999) {
            for (int i = 0; i < 3; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 999999 && number <= 9999999) {
            for (int i = 0; i < 2; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 9999999 && number <= 99999999) {
            for (int i = 0; i < 1; i++) {
                result.append("0");
            }
            result.append(number);
        } else if (number > 99999999 && number <= 999999999) {
            result.append(number);
        }
        return result;
    }

    public static String getNewPallet(String current_ref, String prefix) {
        if (current_ref != null || !current_ref.equals("")) {
            String[] split_str = current_ref.split("-");
            long current_number = Long.parseLong(split_str[split_str.length - 1]);
            return prefix + "-" + getPalletRefNumber(current_number + 1);
        }
        return prefix + "-000000000";
    }

    public static String getDateFromDatabase(Date date, String format) {
        String currentDate = null;
        try {
            if (date != null) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                currentDate = sdf.format(date);
                // currentDate = formatDate(currentDate, isGetTime);
                return currentDate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMillType(byte type) {
        switch (type) {
            case 1:
                return Constants.IM;
            case 2:
                return Constants.IP;
            case 3:
                return Constants.XP;
            case 4:
                return Constants.EX;
            case 5:
                return Constants.MOVEMENT;
            default:
                return "";
        }
    }

    public static String getInsName(String type) {
        switch (type) {
            case "IM":
                return Constants.DI;
            case "IP":
            case "XP":
                return Constants.PI;
            case "EX":
                return Constants.SI;
            default:
                return "";

        }
    }

    public static String convertIntToAlphabet(int number) {
        switch (number) {
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "D";
            case 5:
                return "E";
            case 6:
                return "F";
            case 7:
                return "G";
            case 8:
                return "H";
            case 9:
                return "I";
            case 10:
                return "J";
            case 11:
                return "K";
            case 12:
                return "L";
            case 13:
                return "M";
            case 14:
                return "N";
            case 15:
                return "O";
            case 16:
                return "P";
            case 17:
                return "Q";
            case 18:
                return "R";
            case 19:
                return "S";
            case 20:
                return "T";
            case 21:
                return "U";
            case 22:
                return "V";
            case 23:
                return "W";
            case 24:
                return "X";
            case 25:
                return "Y";
            case 26:
                return "Z";
            default:
                return "";
        }
    }

    // the condition is the length of both arr must be the same
    public static String generateJsonString(String[] arr1, String[] arr2) {
        String json = "{";
        for (int i = 0; i < arr2.length; i++) {
            if (i == 0) {
                json += "'" + arr1[i] + "':'" + arr2[i];
            } else {
                json += ",'" + arr1[i] + "':'" + arr2[i] + "'";
            }
        }
        json += "}";
        return json;
    }

    public static String getAbsolutePath(HttpServletRequest request) {
        String rootPath = "";
        String realPath = request.getRequestURL().toString()
                .replace(request.getRequestURI().substring(1), request.getContextPath())
                + "/";
        String[] pathArray = realPath.split("/");
        for (String pathArray1 : pathArray) {
            if (!pathArray1.equals("")) {
                if (pathArray1.equals("http:")) {
                    rootPath += pathArray1 + "//";
                } else {
                    rootPath += pathArray1 + "/";
                }
            }
        }
        return rootPath;
    }

    public static String convertToJson(Object... objs) {
        Map m = new HashMap();
        String[][] new_obj = (String[][]) objs[0];
        for (String[] new_obj1 : new_obj) {
            String[] new_str = new_obj1;
            m.put(new_str[0], new_str[1]);
        }
        JSONObject json = new JSONObject(m);
        return json.toString();
    }

    public static Integer getPermissionId(String key, ServletContext context)
            throws FileNotFoundException, IOException {
        Resource resource = new ClassPathResource("/WEB-INF/permission.properties");
        FileReader reader = new FileReader(resource.getFile());
        Properties properties = new Properties();
        properties.load(reader);
        Integer permission_id = Integer.parseInt(properties.getProperty(key));
        return permission_id;
    }

    public static String readProperties(String key, ServletContext context)
            throws FileNotFoundException, IOException {
        Resource resource = new ClassPathResource("/WEB-INF/permission.properties");
        FileReader reader = new FileReader(resource.getFile());
        Properties properties = new Properties();
        properties.load(reader);
        return properties.getProperty(key);
    }

    public static HashMap<String, String> readOwnedCompanyInfo(ServletContext context)
            throws IOException {
        HashMap<String, String> ownedCompanyInfo = new HashMap();
        FileReader reader = new FileReader(context.getRealPath("/")
                + "WEB-INF/ownedCompany.properties");
        Properties properties = new Properties();
        properties.load(reader);
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            ownedCompanyInfo.put(key, value);
        }

        return ownedCompanyInfo;
    }

    public static String convertIdIntoCode(String map_name, int ordinateX, int ordinateY,
            String wallVer, String wallHor, int width, int height) {
        try {
            int first_before = ordinateX;
            String[] wall_ver = wallVer.split(",");
            String[] check = new String[width];
            int count = 1;
            for (int i = 1; i <= width; i++) {
                boolean flag = false;
                for (String wall_ver1 : wall_ver) {
                    if (i == Integer.parseInt(wall_ver1)) {
                        flag = true;
                    }
                }
                if (flag == true) {
                    check[i - 1] = "0";
                } else {
                    check[i - 1] = convertIntToAlphabet(count);
                    count++;
                }
            }
            String first_after = check[first_before - 1]; // first char

            int second_before = ordinateY;
            String[] wall_hor = wallHor.split(",");
            int[] check_second = new int[height];
            int count_second = 1;
            for (int i = 1; i <= height; i++) {
                boolean flag = false;
                for (String wall_hor1 : wall_hor) {
                    if (i == Integer.parseInt(wall_hor1)) {
                        flag = true;
                    }
                }
                if (flag == true) {
                    check_second[i - 1] = 0;
                } else {
                    check_second[i - 1] = count_second;
                    count_second++;
                }
            }
            int second_after = check_second[second_before - 1]; // second int

            return map_name + "-" + first_after + second_after;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getUrlPath(HttpServletRequest request) {
        String rootPath = "";
        String realPath = request.getRequestURL().toString()
                .replace(request.getRequestURI().substring(1), request.getContextPath())
                + "/";
        String[] pathArray = realPath.split("/");
        for (int i = 0; i < pathArray.length; i++) {
            if (!pathArray[i].equals("")) {
                if (pathArray[i].equals("http:")) {
                    rootPath += pathArray[i] + "//";
                } else {
                    rootPath += pathArray[i] + "/";
                }
            }
        }
        return rootPath;
    }

    public static String getShorterString(String str, Integer maxLength) {
        str = str != null ? str : "";
        if (str.length() > maxLength) {
            str = str.substring(0, maxLength) + "...";
        }

        return str;
    }

    public static String getStringValue(Object obj) {
        return (obj != null) ? obj.toString() : "";
    }

    public static String getDateValue(Object obj, String format) {
        return (obj != null) ? getDateFromDatabase((Date) obj, format) : "";
    }

    public static Float getFloatValue(Object obj) {
        if (obj != null) {
            String str = obj.toString();
            if (str.matches("-?(\\d+),*(\\d+).?(\\d*)")) {
                str = str.replace(",", "");
            }
            return Float.valueOf(str);
        }
        return (float) 0;
    }

    public static Double getDoubleValue(Object obj) {
        if (obj != null) {
            String str = obj.toString();
            if (str.matches("-?(\\d+),*(\\d+).?(\\d*)")) {
                str = str.replace(",", "");
            }
            return Double.valueOf(str);
        }
        return (double) 0;
    }

    public static Integer getIntegerValue(Object obj) {
        if (obj != null) {
            String str = obj.toString();
            if (str.matches("-?(\\d+),*(\\d+).?(\\d*)")) {
                str = str.replace(",", "");
            }
            return Integer.valueOf(str);
        }
        return 0;
    }

    public static String checkIntegerFloat(String str) {
        if (str.matches("-?\\d+(\\.\\d+)")) {
            return "float";
        } else if (str.matches("-?\\d+")) {
            return "int";
        } else {
            return "string";
        }
    }

    public static HashMap toHashMap(JSONObject json) {
        Iterator key = json.keys();
        HashMap map = new HashMap();
        while (key.hasNext()) {
            try {
                String k = key.next().toString();
                Object obj = json.get(k);
                if (obj instanceof JSONArray) {
                    ArrayList<HashMap> list = new ArrayList<>();
                    JSONArray jArr = (JSONArray) obj;
                    for (int i = 0; i < jArr.length(); i++) {
                        list.add(toHashMap(jArr.getJSONObject(i)));
                    }
                    map.put(k, list);
                } else if (obj instanceof JSONObject) {
                    map.put(k, toHashMap((JSONObject) obj));
                } else {
                    String str = obj.toString();
                    switch (Common.checkIntegerFloat(str)) {
                        case "float":
                            map.put(k, Float.valueOf(str));
                            break;
                        case "int":
                            map.put(k, Integer.valueOf(str));
                            break;
                        case "string":
                            map.put(k, str);
                    }
                }
            } catch (JSONException ex) {
                Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return map;
    }
}
