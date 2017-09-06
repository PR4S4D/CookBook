package com.slp.cookbook.data;

/**
 * Created by Lakshmiprasad on 06-09-2017.
 */

public class Recipe {

    private Ingredients[] ingredients;
    private String id;
    private String servings;
    private String name;
    private String image;
    private Steps[] steps;

    public Ingredients[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Steps[] getSteps() {
        return steps;
    }

    public void setSteps(Steps[] steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "ClassPojo [ingredients = " + ingredients + ", id = " + id + ", servings = " + servings + ", name = " + name + ", image = " + image + ", steps = " + steps + "]";
    }
}
