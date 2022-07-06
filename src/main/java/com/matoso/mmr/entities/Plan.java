package com.matoso.mmr.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter

public class Plan {
    private int id;
    private int months;
    private int percentage;
    private int timesBonus;
    private String planName;

    public Plan(int id, int months, int percentage, int timesBonus, String planName) {
        this.id = id;
        this.months = months;
        this.percentage = percentage;
        this.timesBonus = timesBonus;
        this.planName = planName;
    }

    public Plan(int months, int percentage, int timesBonus, String planName) {
        this.id = new Random().nextInt(1000);
        this.months = months;
        this.percentage = percentage;
        this.timesBonus = timesBonus;
        this.planName = planName;
    }
}