package com.swcommodities.wsmill.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.json.JSONObject;

/**
 * Created by dunguyen on 8/24/16.
 */
public class Request {

    private String baseUrl;

    public Request(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getContent(String url) throws Exception {
        // url: start with /
        return getContentFromFullUrl(baseUrl + url);
    }

    public String putContent(String url, Map<String,Object> params) throws Exception {
        return putContentFromFullUrl(baseUrl + url, params);
    }

    public String getContentFromFullUrl(String url) throws Exception {
        // url start with http:
        URL target = new URL(url);
        URLConnection yc = target.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));

        StringBuilder builder = new StringBuilder();
        String aux = "";

        while ((aux = in.readLine()) != null) {
            builder.append(aux);
        }

        String text = builder.toString();
        return text;
    }

    public String putContentFromFullUrl(String url, Map<String,Object> params) throws Exception {
        URL urlObj = new URL(url);
        JSONObject postData = new JSONObject();
        for(Map.Entry<String, Object> entry: params.entrySet()) {
            postData.put(entry.getKey(), entry.getValue().toString());
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        HttpURLConnection conn = (HttpURLConnection)urlObj.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        String response = sb.toString();
        return response;
    }
}
