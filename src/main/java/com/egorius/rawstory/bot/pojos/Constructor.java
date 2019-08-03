package com.egorius.rawstory.bot.pojos;

import java.util.ArrayList;
import java.util.List;

public class Constructor {
    private Type type;
    private String name = "";
    private String description = "";
    private List<String> images = new ArrayList<>();

    public Constructor(Type type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addImages(String img) {
        images.add(img);
    }

    public void dropImages() {
        images = new ArrayList<>();
    }


    public String save() {
        switch (type) {
            case Post:
                Post post = new Post(name, description, images.toArray(new String[0]));
                System.out.println(post);
                break;
            case Product:
                Product product = new Product(name, description, images.toArray(new String[0]));
                System.out.println(product);
                break;
        }
        return "";
    }

    public String check() {
        if (type == Type.Post) {
            return new Post(name, description, images.toArray(new String[0])).toString();
        } else {
            return new Product(name, description, images.toArray(new String[0])).toString();
        }
    }

    public enum Type{
        Post, Product, Null;
    }
}
