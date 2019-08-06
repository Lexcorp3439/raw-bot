package com.egorius.rawstory.bot.pojos;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("unused")
public class Product {

    private long id = -1;

    private String name;

    private String description;

    private String[] paths;

    private BigDecimal cost;

    private int cal;

    private int squirrels;

    private int fats;

    private int carbohydrates;

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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getSquirrels() {
        return squirrels;
    }

    public void setSquirrels(int squirrels) {
        this.squirrels = squirrels;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getCal() {
        return cal;
    }

    public void setCal(int cal) {
        this.cal = cal;
    }

    public Product(String name, String description, String[] paths, BigDecimal cost, int cal, int squirrels, int fats, int carbohydrates) {
        this.name = name;
        this.description = description;
        this.paths = paths;
        this.cost = cost;
        this.cal = cal;
        this.squirrels = squirrels;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return cal == product.cal &&
                squirrels == product.squirrels &&
                fats == product.fats &&
                carbohydrates == product.carbohydrates &&
                name.equals(product.name) &&
                description.equals(product.description) &&
                Arrays.equals(paths, product.paths) &&
                cost.equals(product.cost);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, description, cost, cal, squirrels, fats, carbohydrates);
        result = 31 * result + Arrays.hashCode(paths);
        return result;
    }

    @Override
    public String toString() {
        return "Продукт" +
                "Название продукта:'" + name + '\'' +
                "Описание продукта'" + description + '\'' +
                "Цена:" + cost +
                "Калории: " + cal +
                "Белки: " + squirrels +
                "Жиры: " + fats +
                "Углеводы" + carbohydrates;
    }
}
