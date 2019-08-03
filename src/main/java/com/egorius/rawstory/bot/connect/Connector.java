package com.egorius.rawstory.bot.connect;

import com.egorius.rawstory.bot.pojos.Post;

public class Connector {
    private static final String postURL = "";
    private static final String testURL = "http://192.168.1.251:8080/blog/add";
    private static final String resourcePath = "D:\\Projects\\IdeaProjects\\tests\\raw-bot\\src\\main\\resources\\";

    public static Request putPost(Post post) {
        try {
            RequestRunner.Instance.doPost(testURL, post);
        } catch (Exception e) {
            return new Request.Failed(e.getMessage());
        }
        return new Request.Success(post);
    }
}
