package com.egorius.rawstory.bot.pojos;

import com.egorius.rawstory.bot.connect.Connector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Constructor {
    private Type type;
    private String name = "";
    private String description = "";
    private List<String> images = new ArrayList<>();

    private BigDecimal cost = new BigDecimal(-1);
    private int cal = -1;
    private int squirrels = -1;
    private int fats = -1;
    private int carbohydrates = -1;

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

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setCal(int cal) {
        this.cal = cal;
    }

    public void setSquirrels(int squirrels) {
        this.squirrels = squirrels;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public boolean isProduct() {
        return type == Type.Product;
    }

    public String save() {
        switch (type) {
            case Post:
                Post post = new Post(name, description, images.toArray(new String[0]));
                Connector.sendPost(post);
                break;
            case Product:
                Product product = new Product(name, description, images.toArray(new String[0]),
                        cost, cal, squirrels, fats, carbohydrates);
                Connector.sendProduct(product);
                break;
        }
        return "";
    }

    public String check() {
        if (type == Type.Post) {
            return new Post(name, description, images.toArray(new String[0])).toString();
        } else {
            return new Product(name, description, images.toArray(new String[0]),
                    cost, cal, squirrels, fats, carbohydrates).toString();
        }
    }

    public void clear() {
        type = Type.Null;
        name = "";
        description = "";
        images = new ArrayList<>();
    }


    public enum Type{
        Post, Product, Null
    }
}
