/**
 * Exame de Programação 2 | Grupo 8
 */

package com.matoso.mmr;

import com.matoso.mmr.data.PlanContext;
import com.matoso.mmr.data.RecipeContext;
import com.matoso.mmr.data.UserContext;
import com.matoso.mmr.entities.Plan;
import com.matoso.mmr.entities.Recipe;
import com.matoso.mmr.entities.User;
import com.matoso.mmr.models.Genre;
import com.matoso.mmr.models.Role;
import com.matoso.mmr.services.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class MMRApplication extends Application {
    @Override
    public void start(@NotNull Stage stage) {
        SceneManager.changeScene(stage, "views/login-view.fxml", "Login");
        seedData();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void seedData() {
        UserContext.add(
                new User(100, "José Matoso", "matoso@email.com", "matoso", Genre.Male, "Bairro Popular", Role.Worker),
                new User(200, "Bernarda Gonçalves", "bernarda@email.com", "bernarda", Genre.Female, "São Paulo", Role.Worker),
                new User(300, "Jeremias Dinzinga", "jeremias@email.com", "jeremias", Genre.Male, "Unknown", Role.Worker),
                new User(400, "Sara Samuel", "sara@email.com", "sara", Genre.Female, "Talatona", Role.Worker),
                new User(500, "Joaquim José", "joaquim@email.com", "joaquim", Genre.Male, "Viana", Role.Client),
                new User(600, "Irene Teresa", "irene@email.com", "irene", Genre.Female, "Maianga", Role.Client));

        PlanContext.add(
                new Plan(201, 10, 15, 2, "Base Partner"),
                new Plan(202, 12, 35, 3, "Complementary Partner"),
                new Plan(203, 18, 45, 4, "VIP Partner"));

        RecipeContext.add(
                new Recipe(203, 600, 100, 350000f, 157500f, 250000f, 1000000),
                new Recipe(203, 500, 100, 350000f, 157500f, 250000f, 1000000));
    }
}