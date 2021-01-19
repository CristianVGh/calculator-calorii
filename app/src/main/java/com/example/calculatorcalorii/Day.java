package com.example.calculatorcalorii;

public class Day {
    private long id;
    private String date;
    private int steps;
    private float weight;
    private float calories;

    public Day(long id, String date, int steps, float weight, float calories) {
        this.id = id;
        this.date = date;
        this.steps = steps;
        this.weight = weight;
        this.calories = calories;
    }

    public long getId(){
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getSteps() {
        return steps;
    }

    public float getWeight() {
        return weight;
    }

    public float getCalories() {
        return calories;
    }
}
