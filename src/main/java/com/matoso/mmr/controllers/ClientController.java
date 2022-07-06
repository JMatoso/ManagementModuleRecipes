package com.matoso.mmr.controllers;

import com.matoso.mmr.data.DataCollection;
import com.matoso.mmr.data.PlanContext;
import com.matoso.mmr.data.RecipeContext;
import com.matoso.mmr.data.UserContext;
import com.matoso.mmr.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    public TextField txtClientId;
    @FXML
    public TextField txtClientName;
    @FXML
    public TextField txtClientEmail;
    @FXML
    public TextField txtClientGenre;
    @FXML
    public TextField txtClientAddress;
    @FXML
    public TextField txtClientRole;
    @FXML
    public TextField txtRecipeSalary;
    @FXML
    public TextField txtRecipeInvestment;
    @FXML
    public TextField txtRecipeTotalDebit;
    @FXML
    public TextField txtRecipeFinal;
    @FXML
    public Label lblPlanName;
    @FXML
    public Label lblPlanMonths;
    @FXML
    public Label lblPlanPercentage;
    @FXML
    public Label lblPlanBonus;
    @FXML
    public Label lblRegisterInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtClientId.setText(String.valueOf(DataCollection.ACTUAL_USER.getId()));
        txtClientName.setText(DataCollection.ACTUAL_USER.getName());
        txtClientEmail.setText(DataCollection.ACTUAL_USER.getEmail());
        txtClientGenre.setText(DataCollection.ACTUAL_USER.getGenre().name());
        txtClientAddress.setText(DataCollection.ACTUAL_USER.getAddress());
        txtClientRole.setText(DataCollection.ACTUAL_USER.getRole().name());

        var recipe = RecipeContext.get(DataCollection.ACTUAL_USER.getId());

        if(recipe != null) {
            txtRecipeSalary.setText(String.valueOf(recipe.getSalary()));
            txtRecipeInvestment.setText(String.valueOf(recipe.getInvestment()));
            txtRecipeTotalDebit.setText(String.valueOf(recipe.getTotalDebit()));
            txtRecipeFinal.setText(String.valueOf(recipe.getFinalBalance()));

            var plan = PlanContext.get(recipe.getPlanId());

            if(plan != null) {
                lblPlanName.setText("Plan Name: " + plan.getPlanName());
                lblPlanMonths.setText("Plan Months: " + plan.getMonths());
                lblPlanPercentage.setText("Percentage: " + plan.getPercentage() + "%");
                lblPlanBonus.setText("Bonus: " + plan.getTimesBonus() + "x");

                var worker = UserContext.get(recipe.getWorkerId());

                if(worker != null) {
                    var date = recipe.getCreated();

                    var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.ENGLISH);
                    var timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);

                    lblRegisterInfo.setText("Added by " + worker.getName() + " on " + date.format(dateFormatter) + " at " + date.format(timeFormatter) + ".");
                }
            }
        }
    }

    @FXML
    public void onBtnExitClick(ActionEvent event) {
        var alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("You are closing your actual session. Are you sure?");

        var result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
            SceneManager.changeScene(event, "views/login-view.fxml", "Login");
    }
}
