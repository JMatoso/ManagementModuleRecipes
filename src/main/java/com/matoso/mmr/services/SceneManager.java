package com.matoso.mmr.services;

import com.matoso.mmr.MMRApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    public static void changeScene(@NotNull ActionEvent event, @NotNull String fxml, @NotNull String sceneTitle) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(MMRApplication.class.getResource(fxml)));

            var scene = new Scene(root, 1080, 720);
            var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            configure(scene, stage, sceneTitle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeScene(@NotNull Stage stage, @NotNull String fxml, @NotNull String sceneTitle) {
        try {
            var fxmlLoader = new FXMLLoader(MMRApplication.class.getResource(fxml));
            var scene = new Scene(fxmlLoader.load(), 1080, 720);

            configure(scene, stage, sceneTitle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void configure(@NotNull Scene scene, @NotNull Stage stage, @NotNull String sceneTitle) {
        scene.getStylesheets().add(String.valueOf(MMRApplication.class.getResource("css/style.css")));

        stage.setMinWidth(1080);
        stage.setMinHeight(720);
        stage.setTitle("BAI | Management Module Recipe | " + sceneTitle);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects
                .requireNonNull(MMRApplication.class.getResourceAsStream("img/logo.png"))));

        stage.show();
    }
}
