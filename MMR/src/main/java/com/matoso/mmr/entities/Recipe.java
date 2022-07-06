package com.matoso.mmr.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter

public class Recipe {
    private int id;
    private int planId;
    private int userId;
    private int workerId;
    private double salary;
    private double investment;
    private double totalDebit;
    private double finalBalance;
    private LocalDateTime created;

    public Recipe() {
        this.id = new Random().nextInt(1000);
        this.created = LocalDateTime.now();
    }

    public Recipe(int planId, int userId, int workerId, double salary, double investment, double totalDebit, double finalBalance) {
        this.id = new Random().nextInt(1000);
        this.planId = planId;
        this.userId = userId;
        this.workerId = workerId;
        this.salary = salary;
        this.totalDebit = totalDebit;
        this.investment = investment;
        this.finalBalance = finalBalance;
        this.created = LocalDateTime.now();
    }
}