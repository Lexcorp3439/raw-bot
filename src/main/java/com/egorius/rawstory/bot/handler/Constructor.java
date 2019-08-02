package com.egorius.rawstory.bot.handler;

import com.egorius.rawstory.bot.pojos.Post;
import com.egorius.rawstory.bot.pojos.Product;

public class Constructor {
    private Type type = Type.Null;
    private String name = "";
    private String description = "";
    private String[] images = new String[]{};

    public Constructor(Type type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String save() {
        switch (type) {
            case Post:
                Post post = new Post(name, description, images);
                System.out.println(post);
                break;
            case Product:
                Product product = new Product(name, description, images);
                System.out.println(product);
                break;
        }
        return "";
    }

    public String check() {
        if (type == Type.Post) {
            return new Post(name, description, images).toString();
        } else {
            return new Product(name, description, images).toString();
        }
    }

    enum Type{
        Post, Product, Null;
    }
}
