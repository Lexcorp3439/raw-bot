package com.egorius.rawstory.bot.connect;

import com.egorius.rawstory.bot.pojos.Post;
import com.egorius.rawstory.bot.pojos.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class Connector {
    private static final String postURL = "";
    //    private static final String testURL = "http://192.168.1.251:8080/blog/add";
//    private static final String testURLPutChatID = "http://192.168.1.251:8080/reg/";
    private static final String testURLtoPost = "http://localhost:8080/blog/add";
    private static final String testURLtoProduct = "http://localhost:8080/products/add";
    private static final String testURLPutChatID = "http://localhost:8080/reg/";

    private static final String resourcePath = "D:\\Projects\\IdeaProjects\\tests\\raw-bot\\src\\main\\resources\\";


    public static Request sendPost(Post post) {
        Properties.removeProxy();
        Post ans;
        try {
            ans = (Post) RequestRunner.Instance.doPost(testURLtoPost, post, Post.class);
        } catch (Exception e) {
            return new Request.Failed(e.getMessage());
        }
        Properties.setProxy();
        return new Request.Success(ans);
    }

    public static Request sendProduct(Product product) {
        Properties.removeProxy();
        Product ans;

        try {
            ans = (Product) RequestRunner.Instance.doPost(testURLtoProduct, product, Product.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new Request.Failed(e.getMessage());
        }

        Properties.setProxy();
        return new Request.Success(ans);
    }


    @SuppressWarnings("UnusedReturnValue") // Выходной параметр нужен в дальнейшем!
    public static Request putChatId(Long chatId) {
        Properties.removeProxy();
        try {
            String ans = RequestRunner.Instance.doPut(testURLPutChatID.concat(String.valueOf(chatId)));
            if (!ans.equals(Long.toString(chatId))) {
                return new Request.Failed("Переданный ID - не число");
            }
        } catch (Exception e) {
            return new Request.Failed(e.getMessage());
        }
        Properties.setProxy();
        return new Request.Success(chatId);
    }
}
