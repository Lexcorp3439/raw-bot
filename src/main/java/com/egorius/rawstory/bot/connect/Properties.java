package com.egorius.rawstory.bot.connect;

public class Properties {
    private static final String PROXY_PORT = "1080";
    private static final String PROXY_ADDRESS = "178.197.248.213";

    private static final String SET_KEY = "proxySet";
    private static final String HOST_KEY = "socksProxyHost";
    private static final String PORT_KEY = "socksProxyPort";
    private static final String SET_VALUE = "true";

    public static void setProxy() {
        System.getProperties().put(SET_KEY, SET_VALUE);
        System.getProperties().put(HOST_KEY, PROXY_ADDRESS);
        System.getProperties().put(PORT_KEY, PROXY_PORT);
    }

    public static void removeProxy() {
        System.getProperties().remove(SET_KEY);
        System.getProperties().remove(HOST_KEY);
        System.getProperties().remove(PORT_KEY);
    }
}
