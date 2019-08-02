package com.eqorius.rawstory.bot.requests;

import org.junit.Test;

import com.egorius.rawstory.bot.connect.RequestRunner;
import com.egorius.rawstory.bot.pojos.Post;
import com.sun.org.glassfish.gmbal.Description;

import static junit.framework.TestCase.assertEquals;

public class RequestsTest {

    private static final String testURL = "http://192.168.1.251:8080/blog/add";
    private static final String id = "AgADAgADQasxG7njEUpU4y4X12ou9Xfuug8ABAEAAwIAA3gAAxxEAAIWBA";
    private static final String resource ="L:\\IdeaProjects\\raw-story\\src\\main\\resources\\static\\";

    private static final String PROXY_PORT = "1080";
    private static final String PROXY_ADDRESS = "178.197.248.213";


    @Test
    @Description("POST запрос на сервер с отправкой поста")
    public void doPost() throws Exception {
        Post post = new Post("Test name5", "Test description5", new String[]{id});
        Post ans = RequestRunner.Instance.doPost(testURL, post);
        assertEquals(ans.getPaths()[0], resource.concat(post + ".jpg"));
    }



    @Test
    @Description("POST запрос на сервер с отправкой поста через прокси")
    public void doPostWithProxy() throws Exception {
        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "socksProxyHost", PROXY_ADDRESS );
        System.getProperties().put( "socksProxyPort", PROXY_PORT );

        System.getProperties().put( "proxySet", "false" );
        Post post = new Post("Test name5", "Test description5", new String[]{id});
        Post ans = RequestRunner.Instance.doPost(testURL, post);
        assertEquals(ans, resource.concat(post + ".jpg"));

        System.getProperties().put( "proxySet", "true" );
    }
}
