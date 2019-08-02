package com.egorius.rawstory.bot.pojos;

import java.util.Arrays;
import java.util.Objects;

public class Product {

    private long id = -1;

    private String name;

    private String description;

    private String[] paths = new String[]{};

    public Product() {
    }

    public Product(String name, String description, String[] paths) {
        this.name = name;
        this.description = description;
        this.paths = paths;
    }

    public Product(int id, String name, String description, String[] paths) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return id == product.id &&
                name.equals(product.name) &&
                description.equals(product.description) &&
                Arrays.equals(paths, product.paths);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description);
        result = 31 * result + Arrays.hashCode(paths);
        return result;
    }

    @Override
    public String toString() {
        return "Продукт"+ '\n' +
                "Название продукта: " + name + '\n' +
                "Описание продукта: " + description +  '\n' +
                "Картинки: " + Arrays.toString(paths) + '\n';
    }
}
