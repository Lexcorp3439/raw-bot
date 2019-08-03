package com.eqorius.rawstory.bot.requests;


import org.junit.Test;

import com.egorius.rawstory.bot.connect.Properties;
import com.egorius.rawstory.bot.connect.RequestRunner;
import com.egorius.rawstory.bot.pojos.Post;

import static junit.framework.TestCase.assertEquals;

public class RequestsTest {

    private static final String testURL = "http://192.168.1.251:8080/blog/add";
    private static final String id = "AgADAgADy6sxG1hhMUqy6Ek7QYUtw-MAAbgPAAQBAAMCAAN5AAMd2gEAARYE";
    private static final String resource ="L:\\IdeaProjects\\raw-story\\src\\main\\resources\\static\\";


    @Test
    public void doPost() throws Exception {
        Post post = new Post("Test name5", "Test description5", new String[]{id});
        Post ans = RequestRunner.Instance.doPost(testURL, post);
        assertEquals(ans.getPaths()[0], resource.concat(post.getPaths()[0] + ".jpg"));
    }



    @Test
    public void doPostWithProxy() throws Exception {
        Properties.setProxy();
        Properties.removeProxy();

        Post post = new Post("Test name5", "Test description5", new String[]{id});
        Post ans = RequestRunner.Instance.doPost(testURL, post);
        assertEquals(ans.getPaths()[0], resource.concat(post.getPaths()[0] + ".jpg"));

        System.getProperties().put( "proxySet", "true" );
    }
}
