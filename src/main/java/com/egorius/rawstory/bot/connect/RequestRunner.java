package com.egorius.rawstory.bot.connect;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum RequestRunner {
    Instance;

    private static final int CONNECTION_TIMEOUT = 0;

    public Object doPost(String u, Object object, Class<?> clazz) throws Exception {
        URL url = new URL(u);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(CONNECTION_TIMEOUT);
        con.setRequestProperty("Content-Type", "application/json");

        // collect parameters
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(object);

        con.setDoOutput(true);
        final DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(body);
        out.flush();
        out.close();

        Object ans = mapper.readValue(con.getInputStream(), clazz);
        con.disconnect();

        return ans;
    }

    public String doPut(String u) throws Exception {
        URL url = new URL(u);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(CONNECTION_TIMEOUT);

        ObjectMapper mapper = new ObjectMapper();
        String ans = mapper.readValue(con.getInputStream(), String.class);
        con.disconnect();
        return ans;
    }
}
