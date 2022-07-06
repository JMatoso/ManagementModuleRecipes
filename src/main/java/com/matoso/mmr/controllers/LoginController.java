package com.matoso.mmr.controllers;

import com.jfoenix.controls.JFXButton;
import com.matoso.mmr.data.DataCollection;
import com.matoso.mmr.data.UserContext;
import com.matoso.mmr.helpers.MessageBox;
import com.matoso.mmr.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;

public class LoginController {
    @FXML
    public Label lblPwdError;
    @FXML
    public Label lblEmailError;
    @FXML
    public JFXButton btnLogin;
    @FXML
    private TextField txtBoxEmail;
    @FXML
    private PasswordField txtBoxPwd;

    @FXML
    public void onKeyPressed(@NotNull KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            btnLogin.setOnAction(this::login);
        }
    }

    @FXML
    public void btnLoginClicked(ActionEvent event) {
        login(event);
    }

    private void login(ActionEvent event) {
        if(!validateEmpty()) {
            var user = UserContext.login(txtBoxEmail.getText().trim(), txtBoxPwd.getText().trim());

            if (user != null) {
                DataCollection.ACTUAL_USER = user;

                switch (user.getRole()) {
                    case Worker -> SceneManager.changeScene(event, "views/main-view.fxml", "Management | Logged as " + user.getName() + " - " + user.getEmail());
                    case Client -> SceneManager.changeScene(event, "views/client-view.fxml", "Client | Logged as " + user.getName() + " - " + user.getEmail());
                }

                return;
            }

            MessageBox.showAlert(Alert.AlertType.ERROR, "Login Error", "Wrong Credentials.", "Unable to login due to incorrect credentials.");
        }
    }

    private boolean validateEmpty() {
        boolean hasError = false;

        lblPwdError.setVisible(false);
        lblEmailError.setVisible(false);

        if(txtBoxEmail.getText().isBlank()) {
            lblEmailError.setVisible(true);
            hasError = true;
        }

        if(txtBoxPwd.getText().isBlank()) {
            lblPwdError.setVisible(true);
            hasError = true;
        }

        return hasError;
    }
}