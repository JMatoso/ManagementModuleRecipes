package com.matoso.mmr.controllers;

import com.jfoenix.controls.JFXButton;
import com.matoso.mmr.data.DataCollection;
import com.matoso.mmr.data.PlanContext;
import com.matoso.mmr.data.RecipeContext;
import com.matoso.mmr.data.UserContext;
import com.matoso.mmr.entities.Plan;
import com.matoso.mmr.entities.Recipe;
import com.matoso.mmr.entities.User;
import com.matoso.mmr.helpers.MessageBox;
import com.matoso.mmr.models.Genre;
import com.matoso.mmr.models.Role;
import com.matoso.mmr.services.SceneManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public TabPane tabPane;
    @FXML
    public TextField txtSearch;
    @FXML
    public TableView<User> usersTable = new TableView<>();
    @FXML
    public Label usersLblRegisteredUsers;
    @FXML
    public Label recipesLblRegisteredRecipes;
    @FXML
    public TableColumn<User, Integer> usersIdColumn;
    @FXML
    public TableColumn<User, String> usersNameColumn;
    @FXML
    public TableColumn<User, String> usersEmailColumn;
    @FXML
    public TableColumn<User, Role> usersRoleColumn;
    @FXML
    public TableColumn<User, LocalDateTime> usersCreatedColumn;
    @FXML
    public TableColumn<User, Genre> usersGenreColumn;
    @FXML
    public TableColumn<User, String> usersAddressColumn;
    @FXML
    public Tab tabPlans;
    @FXML
    public Label plansLblRegisteredPlans;
    @FXML
    public TableView<Plan> plansTable = new TableView<>();
    @FXML
    public TableColumn<Plan, Integer> plansIdColumn;
    @FXML
    public TableColumn<Plan, String> plansNameColumn;
    @FXML
    public TableColumn<Plan, Integer> plansPercentageColumn;
    @FXML
    public TableColumn<Plan, Integer> plansMonthsColumn;
    @FXML
    public TableColumn<Plan, Integer> plansBonusColumn;
    @FXML
    public TableView<Recipe> recipesTable = new TableView<>();
    @FXML
    public TableColumn<Recipe, Integer> recipeIdColumn;
    @FXML
    public TableColumn<Recipe, Integer> recipePlanIdColumn;
    @FXML
    public TableColumn<Recipe, Integer> recipeUserIdColumn;
    @FXML
    public TableColumn<Recipe, Double> recipeSalaryColumn;
    @FXML
    public TableColumn<Recipe, Double> recipeInvestmentColumn;
    @FXML
    public TableColumn<Recipe, Double> recipeFinalBalanceColumn;
    @FXML
    public TableColumn<Recipe, Double> recipeTotalDebitColumn;
    @FXML
    public TableColumn<Recipe, Integer> recipeWorkerIdColumn;
    @FXML
    public TableColumn<Recipe, LocalDateTime> recipeCreatedColumn;
    @FXML
    public StackPane stackPane;
    @FXML
    public TextField txtUserName;
    @FXML
    public TextField txtUserEmail;
    @FXML
    public PasswordField txtPassword;
    @FXML
    public ComboBox<String> cboUsersRole = new ComboBox<>();
    @FXML
    public JFXButton btnUsersSave;
    @FXML
    public JFXButton btnUsersRemove;
    @FXML
    public TextField txtUserAddress;
    @FXML
    public ComboBox<String> cboUsersGenre = new ComboBox<>();
    @FXML
    public Button btnUser;
    @FXML
    public TextField recipeTxtWorkerId;
    @FXML
    public TextField recipeTxtWorkerName;
    @FXML
    public TextField recipeTxtPlanId;
    @FXML
    public TextField recipeTxtUserId;
    @FXML
    public TextField recipeTxtSalary;
    @FXML
    public TextField recipeTxtFinalRecipe;
    @FXML
    public TextField recipeTxtInvestment;
    @FXML
    public JFXButton btnRecipeSave;
    @FXML
    public JFXButton btnRecipeRemove;
    @FXML
    public TextField recipeTxtTotalDebit;
    @FXML
    public ComboBox<String> recipeCboPlan = new ComboBox<>();
    @FXML
    public ComboBox<String> recipeCboUser = new ComboBox<>();
    @FXML
    public Label lblPlanName;
    @FXML
    public Label lblPlanPercentage;
    @FXML
    public Label lblPlanMonths;
    @FXML
    public Label lblPlanBonus;
    @FXML
    private Tab tabRecipes;
    @FXML
    private Tab tabUsers;
    @FXML
    private Tab tabManageRecipes;
    @FXML
    private Tab tabManageUsers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> changeInputsValues());

        btnUser.setText(DataCollection.ACTUAL_USER.getName());

        plansTable.setItems(FXCollections.observableArrayList(PlanContext.getAll()));

        setPlanColumnsPropertyValues();
        setUserColumnsPropertyValues();
        setRecipeColumnsPropertyValues();
    }

    @FXML
    public void onBtnManageRecipesClick() {
        changeTab(tabManageRecipes);

        btnRecipeRemove.setVisible(false);

        recipeCboUser.getItems().removeAll();

        for (var user : UserContext.getAll()) {
            var item = user.getId() + " - " + user.getName();
            if(!recipeCboUser.getItems().contains(item)) {
                recipeCboUser.getItems().add(item);
            }
        }

        if(DataCollection.SELECTED_RECIPE != null) {
            btnRecipeRemove.setVisible(true);

            try {
                var user = UserContext.get(DataCollection.SELECTED_RECIPE.getUserId());
                var plan = PlanContext.get(DataCollection.SELECTED_RECIPE.getPlanId());
                var worker = UserContext.get(DataCollection.SELECTED_RECIPE.getWorkerId());

                recipeTxtWorkerId.setText(String.valueOf(Objects.requireNonNull(worker).getId()));
                recipeTxtWorkerName.setText(worker.getName());
                recipeTxtSalary.setText(String.valueOf(DataCollection.SELECTED_RECIPE.getSalary()));
                recipeTxtInvestment.setText(String.valueOf(DataCollection.SELECTED_RECIPE.getInvestment()));
                recipeTxtUserId.setText(String.valueOf(Objects.requireNonNull(user).getId()));
                recipeTxtPlanId.setText(String.valueOf(Objects.requireNonNull(plan).getId()));
                recipeTxtTotalDebit.setText(String.valueOf(DataCollection.SELECTED_RECIPE.getTotalDebit()));
                recipeTxtFinalRecipe.setText(String.valueOf(DataCollection.SELECTED_RECIPE.getFinalBalance()));

                recipeCboPlan.getSelectionModel().select(plan.getId() + " - " + plan.getPlanName());
                recipeCboUser.getSelectionModel().select(user.getId() + " - " + user.getName());
            } catch (NullPointerException ignored){}

            return;
        }

        recipeTxtWorkerName.setText(DataCollection.ACTUAL_USER.getName());
        recipeTxtWorkerId.setText(String.valueOf(DataCollection.ACTUAL_USER.getId()));
    }

    @FXML
    public void onBtnManageUsersClick() {
        changeTab(tabManageUsers);

        btnUsersRemove.setVisible(false);

        if(DataCollection.SELECTED_USER != null) {
            if(DataCollection.SELECTED_USER.getId() != DataCollection.ACTUAL_USER.getId()) {
                btnUsersRemove.setVisible(true);
            }

            txtUserName.setText(DataCollection.SELECTED_USER.getName());
            txtUserEmail.setText(DataCollection.SELECTED_USER.getEmail());
            txtPassword.setText(DataCollection.SELECTED_USER.getPassword());
            txtUserAddress.setText(DataCollection.SELECTED_USER.getAddress());

            cboUsersRole.getSelectionModel().select(DataCollection.SELECTED_USER.getRole().name());
            cboUsersGenre.getSelectionModel().select(DataCollection.SELECTED_USER.getGenre().name());
        }
    }

    @FXML
    public void onBtnUsersClick() {
        txtSearch.setPromptText("Search users...");

        usersLblRegisteredUsers.setText("Registered Users (" + UserContext.count() + ")");

        usersTable.setItems(FXCollections.observableArrayList(UserContext.getAll()));

        changeTab(tabUsers);
    }

    @FXML
    public void onBtnRecipesClick() {
        txtSearch.setPromptText("Search recipes...");

        recipesLblRegisteredRecipes.setText("Registered Recipes (" + RecipeContext.count() + ")");

        recipesTable.setItems(FXCollections.observableArrayList(RecipeContext.getAll()));

        changeTab(tabRecipes);
    }

    @FXML
    public void onBtnPlansClick() {
        plansLblRegisteredPlans.setText("Registered Plans (" + PlanContext.count() + ")");

        plansTable.setItems(FXCollections.observableArrayList(PlanContext.getAll()));

        txtSearch.setPromptText("Search plans...");

        tabPane.getSelectionModel().select(tabPlans);
    }

    @FXML
    public void onTxtFieldEnterPressed(@NotNull KeyEvent keyEvent) {
        if(!txtSearch.getText().isBlank()) {
            if(keyEvent.getCode() == KeyCode.ENTER) {
                var selectedTab = tabPane.getSelectionModel().getSelectedItem();

                if (selectedTab.equals(tabPlans)) {
                    plansTable.getItems().removeAll();
                    plansTable.setItems(FXCollections.observableArrayList(PlanContext.get(txtSearch.getText())));
                    return;
                }

                if (selectedTab.equals(tabRecipes)) {
                    recipesTable.getItems().removeAll();
                    recipesTable.setItems(FXCollections.observableArrayList(RecipeContext.get(txtSearch.getText())));
                    return;
                }

                if (selectedTab.equals(tabUsers)) {
                    usersTable.getItems().removeAll();
                    usersTable.setItems(FXCollections.observableArrayList(UserContext.get(txtSearch.getText())));
                }
            }
        }
    }

    @FXML
    public void onBtnUserClick() {
        DataCollection.SELECTED_USER = DataCollection.ACTUAL_USER;
        onBtnManageUsersClick();
    }

    @FXML
    public void onBtnUsersRemove() {
        if(DataCollection.SELECTED_USER != null) {
            UserContext.remove(DataCollection.SELECTED_USER.getId());
            MessageBox.showToast(stackPane, DataCollection.SELECTED_USER.getName() + " was removed successfully!");
            DataCollection.SELECTED_USER = null;
        }
    }

    @FXML
    public void onBtnUsersSave() {
        if(checkFields(txtUserEmail, txtUserName, txtPassword, txtUserAddress) && checkComboBoxesValues(cboUsersRole, cboUsersGenre)) {
            if(DataCollection.SELECTED_USER != null) {
                DataCollection.SELECTED_USER.setName(txtUserName.getText());
                DataCollection.SELECTED_USER.setEmail(txtUserEmail.getText());
                DataCollection.SELECTED_USER.setAddress(txtUserAddress.getText());
                DataCollection.SELECTED_USER.setPassword(txtPassword.getText());
                DataCollection.SELECTED_USER.setRole(Role.valueOf(cboUsersRole.getSelectionModel().getSelectedItem()));
                DataCollection.SELECTED_USER.setGenre(Genre.valueOf(cboUsersGenre.getSelectionModel().getSelectedItem()));

                UserContext.update(DataCollection.SELECTED_USER);
                MessageBox.showToast(stackPane, DataCollection.SELECTED_USER.getName() + " was updated successfully!");
                return;
            }

            var newUser = new User(txtUserName.getText(), txtUserEmail.getText(), txtPassword.getText(), Genre.valueOf(cboUsersGenre.getSelectionModel().getSelectedItem()), txtUserAddress.getText(), Role.valueOf(cboUsersRole.getSelectionModel().getSelectedItem()));
            UserContext.add(newUser);

            MessageBox.showToast(stackPane, newUser.getName() + " was added successfully!");
            clearTextFields(txtPassword, txtUserName, txtUserEmail, txtUserAddress);
        }
    }

    @FXML
    public void onBtnRecipeSave() {
        if(checkFields(recipeTxtPlanId, recipeTxtUserId, recipeTxtWorkerId, recipeTxtSalary, recipeTxtInvestment)) {
            var plan = PlanContext.get(Integer.parseInt(recipeTxtPlanId.getText()));

            if(plan == null) {
                MessageBox.showToast(stackPane, "Plan not found.");
                return;
            }

            if(DataCollection.SELECTED_RECIPE != null) {
                DataCollection.SELECTED_RECIPE.setPlanId(Integer.parseInt(recipeTxtPlanId.getText()));
                DataCollection.SELECTED_RECIPE.setUserId(Integer.parseInt(recipeTxtUserId.getText()));
                DataCollection.SELECTED_RECIPE.setWorkerId(Integer.parseInt(recipeTxtWorkerId.getText()));
                DataCollection.SELECTED_RECIPE.setSalary(Double.parseDouble(recipeTxtSalary.getText()));
                DataCollection.SELECTED_RECIPE.setInvestment(Double.parseDouble(recipeTxtInvestment.getText()));
                DataCollection.SELECTED_RECIPE.setFinalBalance((DataCollection.SELECTED_RECIPE.getSalary() * plan.getPercentage()) / 100);
                DataCollection.SELECTED_RECIPE.setTotalDebit(DataCollection.SELECTED_RECIPE.getInvestment() * plan.getTimesBonus());

                RecipeContext.update(DataCollection.SELECTED_RECIPE);
                MessageBox.showToast(stackPane, "Recipe has been updated successfully!");
                return;
            }

            var recipe = new Recipe();

            recipe.setPlanId(Integer.parseInt(recipeTxtPlanId.getText()));
            recipe.setUserId(Integer.parseInt(recipeTxtUserId.getText()));
            recipe.setWorkerId(Integer.parseInt(recipeTxtWorkerId.getText()));
            recipe.setSalary(Double.parseDouble(recipeTxtSalary.getText()));
            recipe.setInvestment(Double.parseDouble(recipeTxtInvestment.getText()));
            recipe.setFinalBalance((recipe.getSalary() * plan.getPercentage()) / 100);
            recipe.setTotalDebit(recipe.getInvestment() * plan.getTimesBonus());

            RecipeContext.add(recipe);
            MessageBox.showToast(stackPane, "Recipe has been added successfully.");
            clearTextFields(recipeTxtSalary, recipeTxtInvestment);
        }
    }

    @FXML
    public void onBtnRecipeRemove() {
        if(DataCollection.SELECTED_RECIPE != null) {
            RecipeContext.remove(DataCollection.SELECTED_RECIPE.getId());
            MessageBox.showToast(stackPane, DataCollection.SELECTED_RECIPE.getId() + " has been removed successfully!");
            DataCollection.SELECTED_RECIPE = null;
        }
    }

    @FXML
    public void onBtnUsersClear() {
        clearTextFields(txtUserName, txtUserAddress, txtUserEmail, txtPassword);
    }

    @FXML
    public void onBtnRecipesClear() {
        clearTextFields(recipeTxtInvestment, recipeTxtSalary);
    }

    @FXML
    public void onBtnRecipeCalculate() {
        if(checkFields(recipeTxtSalary, recipeTxtInvestment, recipeTxtPlanId)) {
            try {
                var salary = Double.parseDouble(recipeTxtSalary.getText());
                var investment = Double.parseDouble(recipeTxtInvestment.getText());

                var plan = PlanContext.get(Integer.parseInt(recipeTxtPlanId.getText()));

                if(plan != null) {
                    var debitValue = (salary * plan.getPercentage()) / 100;
                    var finalRecipe = investment * plan.getTimesBonus();

                    recipeTxtTotalDebit.setText(debitValue + " AKZ");
                    recipeTxtFinalRecipe.setText(finalRecipe + " AKZ");
                    return;
                }

                MessageBox.showToast(stackPane, "Plan not found.");
            } catch (Exception ex) {
                MessageBox.showToast(stackPane, "Invalid numerics values.");
            }
        }
    }

    @FXML
    public void onBtnExitClick() {
        var alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("You are closing the application. Are you sure?");

        var result = alert.showAndWait();

        result.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                Platform.exit();
                System.exit(1);
            }
        });
    }

    @FXML
    public void onBtnLogoutClick(ActionEvent event) {
        var alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("You are closing your actual session. Are you sure?");

        var result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
            SceneManager.changeScene(event, "views/login-view.fxml", "Login");
    }

    private void setRecipeColumnsPropertyValues() {
        recipeIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        recipeFinalBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("finalBalance"));
        recipeCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("created"));
        recipeInvestmentColumn.setCellValueFactory(new PropertyValueFactory<>("investment"));
        recipeSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        recipeTotalDebitColumn.setCellValueFactory(new PropertyValueFactory<>("totalDebit"));
        recipePlanIdColumn.setCellValueFactory(new PropertyValueFactory<>("planId"));
        recipeWorkerIdColumn.setCellValueFactory(new PropertyValueFactory<>("workerId"));
        recipeUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        recipesTable
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        var detailsButton = new ButtonType("Details", ButtonBar.ButtonData.OK_DONE);
                        var cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                        var alert = new Alert(Alert.AlertType.NONE, "Selected recipe details: \n\nID: " + newValue.getId() + "\n", detailsButton, cancelButton);

                        alert.getButtonTypes().addAll();

                        alert.setTitle("Select an action...");

                        var result = alert.showAndWait();

                        if(result.isPresent() && result.get() == detailsButton) {
                            DataCollection.SELECTED_RECIPE = newValue;
                            onBtnManageRecipesClick();
                        }
                    }
                });

        for (var user : UserContext.getAll()) recipeCboUser.getItems().add(user.getId() + " - " + user.getName());
        for (var plan : PlanContext.getAll()) recipeCboPlan.getItems().add(plan.getId() + " - " + plan.getPlanName());

        recipeCboPlan.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(!recipeCboPlan.getSelectionModel().isEmpty()) {
                        var pid = Integer.parseInt(newValue.split("-")[0].trim());
                        var plan = PlanContext.get(pid);

                        if(plan != null) {
                            recipeTxtPlanId.setText(String.valueOf(plan.getId()));

                            lblPlanName.setText("Plan Name: " + plan.getPlanName());
                            lblPlanMonths.setText("Months: " + plan.getMonths());
                            lblPlanPercentage.setText("Percentage: " + plan.getPercentage() + "%");
                            lblPlanBonus.setText("Bonus: " + plan.getTimesBonus() + "x");
                            return;
                        }

                        MessageBox.showToast(stackPane, "Plan not found.");
                    }
                });

        recipeCboUser.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(!recipeCboUser.getSelectionModel().isEmpty()) {
                        var pid = Integer.parseInt(newValue.split("-")[0].trim());
                        recipeTxtUserId.setText(String.valueOf(Objects.requireNonNull(UserContext.get(pid)).getId()));
                    }
                });
    }

    private void setUserColumnsPropertyValues() {
        usersIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usersNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usersEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        usersGenreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        usersAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        usersRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        usersCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("created"));

        usersTable
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue != null) {

                        var detailsButton = new ButtonType("Details", ButtonBar.ButtonData.OK_DONE);
                        var cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                        var alert = new Alert(Alert.AlertType.NONE, "Selected user details: \n\nID: " + newValue.getId() + "\nRole: " + newValue.getRole() + "\nName: " + newValue.getName() + "\n");

                        alert.getButtonTypes().addAll(detailsButton, cancelButton);

                        alert.setTitle("Select an action...");

                        var result = alert.showAndWait();

                        if(result.isPresent() && result.get() == detailsButton) {
                            DataCollection.SELECTED_USER = newValue;
                            onBtnManageUsersClick();
                        }
                    }
                });

        cboUsersRole.getItems().addAll(Role.Worker.name(), Role.Client.name());
        cboUsersGenre.getItems().addAll(Genre.Male.name(), Genre.Female.name());

        cboUsersRole.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            if(!cboUsersRole.getSelectionModel().isEmpty()) {
                if(newValue.equals(Role.Client.name())) {
                    txtPassword.setDisable(true);
                    txtPassword.setText("12345");

                    return;
                }
            }

            txtPassword.setDisable(false);
            txtPassword.clear();
        });
    }

    private void setPlanColumnsPropertyValues() {
        plansIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        plansMonthsColumn.setCellValueFactory(new PropertyValueFactory<>("months"));
        plansBonusColumn.setCellValueFactory(new PropertyValueFactory<>("timesBonus"));
        plansNameColumn.setCellValueFactory(new PropertyValueFactory<>("planName"));
        plansPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));
    }

    private void changeInputsValues() {
        var selectedTab = tabPane.getSelectionModel().getSelectedItem();

        plansTable.getSelectionModel().clearSelection();
        recipesTable.getSelectionModel().clearSelection();

        if(selectedTab.equals(tabPlans) || selectedTab.equals(tabRecipes) || selectedTab.equals(tabUsers)) {
            txtSearch.setDisable(false);
            txtSearch.setVisible(true);
            return;
        }

        if(!selectedTab.equals(tabManageRecipes) && !selectedTab.equals(tabManageUsers)) {
            DataCollection.SELECTED_RECIPE = null;
            DataCollection.SELECTED_USER = null;
            return;
        }

        txtSearch.setDisable(true);
        txtSearch.setVisible(false);
    }

    private void changeTab(Tab tab) {
        tabPane.getSelectionModel().select(tab);
    }

    private boolean checkFields(TextField @NotNull ... textFields) {
        boolean isValid = Arrays.stream(textFields).noneMatch(textField -> textField.getText().isBlank());

        if(!isValid) MessageBox.showToast(stackPane, "Fill all required fields.");
        return isValid;
    }

    private void clearTextFields(TextField @NotNull ... textFields) {
        Arrays.stream(textFields).forEach(TextInputControl::clear);
    }

    @SafeVarargs
    private boolean checkComboBoxesValues(ComboBox<String> @NotNull ... comboBoxes) {
        boolean isValid = Arrays.stream(comboBoxes).noneMatch(comboBox -> comboBox.getSelectionModel().isEmpty());

        if(!isValid) MessageBox.showToast(stackPane, "Select all boxes values.");
        return isValid;
    }
}