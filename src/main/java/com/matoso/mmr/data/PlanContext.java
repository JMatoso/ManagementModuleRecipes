package com.matoso.mmr.data;

import com.matoso.mmr.entities.Plan;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanContext {
    private final static ArrayList<Plan> _plans = new ArrayList<>();

    public static List<Plan> getAll() {
        return _plans;
    }

    public static int count() {
        return _plans.size();
    }

    public static void add(Plan @NotNull ... plans) {
        Collections.addAll(_plans, plans);
    }

    public static @Nullable Plan get(int id) {
        return getAll().stream()
                .filter(plan -> plan.getId() == id)
                .findFirst().orElse(null);

    }

    public static @NotNull List<Plan> get(String key) {
        var plans = new ArrayList<Plan>();

        for (var plan : getAll()) {
            if(plan.getPlanName().contains(key) ||
                    String.valueOf(plan.getMonths()).equals(key) ||
                    String.valueOf(plan.getPercentage()).equals(key) ||
                    String.valueOf(plan.getTimesBonus()).equals(key))    {
                plans.add(plan);
            }
        }

        return plans;
    }

    public static boolean update(@NotNull Plan plan) {
        return remove(plan.getId()) && _plans.add(plan);
    }

    public static boolean remove(int id) {
        var plan = get(id);
        return plan != null && _plans.remove(plan);
    }
}
