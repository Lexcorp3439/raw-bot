package com.eqorius.rawstory.bot.requests;


import com.egorius.rawstory.bot.connect.Connector;
import com.egorius.rawstory.bot.connect.Request;
import com.egorius.rawstory.bot.pojos.Product;
import org.junit.Test;

import com.egorius.rawstory.bot.connect.Properties;
import com.egorius.rawstory.bot.connect.RequestRunner;
import com.egorius.rawstory.bot.pojos.Post;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestsTest {

    private static final String id = "AgADAgADy6sxG1hhMUqy6Ek7QYUtw-MAAbgPAAQBAAMCAAN5AAMd2gEAARYE";
    private static final String resource ="L:\\IdeaProjects\\raw-story\\src\\main\\resources\\static\\";


    @Test
    public void sendPost() throws Exception {
        Post post = new Post("name5", "description5", new String[]{id});
        Request request = Connector.sendPost(post);
        assertTrue(request instanceof Request.Success);

        System.out.println(((Post)((Request.Success) request).getObject()).toString());

    }

    @Test
    public void sendProduct() throws Exception {
        Product product = new Product("Test name5", "Test description5", new String[]{id}, new BigDecimal(124),
                150, 50, 50, 50);
        Request request = Connector.sendProduct(product);
        assertTrue(request instanceof Request.Success);

        System.out.println(((Product)((Request.Success) request).getObject()).toString());
    }


//    @Test
//    public void doPostWithProxy() throws Exception {
//        Properties.setProxy();
//        Properties.removeProxy();
//
//        Post post = new Post("Test name5", "Test description5", new String[]{id});
//        Post ans = RequestRunner.Instance.doPost(testURL, post);
//        assertEquals(ans.getPaths()[0], resource.concat(post.getPaths()[0] + ".jpg"));
//
//        System.getProperties().put( "proxySet", "true" );
//    }
}
