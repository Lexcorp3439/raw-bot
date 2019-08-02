package com.egorius.rawstory.bot.connect;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.egorius.rawstory.bot.pojos.Post;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum  RequestRunner {
    Instance;

    private static final int CONNECTION_TIMEOUT = 0;

    public Post doPost(String u, Object object) throws Exception{
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

        Post ans = mapper.readValue(con.getInputStream(), Post.class);
        con.disconnect();

        System.out.println("Good");
        return ans;
    }

    public InputStream doGet(String u) throws Exception{
        URL url = new URL(u);
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(CONNECTION_TIMEOUT);
        con.setReadTimeout(CONNECTION_TIMEOUT);

        InputStream input = con.getInputStream();
        con.disconnect();

        return input;
    }
}
