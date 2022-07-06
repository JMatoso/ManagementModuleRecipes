package com.matoso.mmr.helpers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.matoso.mmr.MMRApplication;
import javafx.scene.control.Alert;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MessageBox {
    private static JFXSnackbar _snackBar;
    private static final DropShadow _dropShadowEffect = new DropShadow();
    private static final JFXButton _snackBarCloseButton = new JFXButton("Close");

    public static void showAlert(Alert.AlertType alertType, @NotNull String title, String headerText, @NotNull String contentText) {
        var _alert = new Alert(alertType);

        _alert.setTitle(title);
        _alert.setHeaderText(headerText);

        if(!contentText.isBlank()) _alert.setContentText(contentText);

        _alert.showAndWait();
    }

    public static void showToast(StackPane stackPane, @NotNull String message) {
        try {
            _snackBar = new JFXSnackbar(stackPane);
            _snackBarCloseButton.setOnAction(action -> _snackBar.close());

            _dropShadowEffect.blurTypeProperty().set(BlurType.THREE_PASS_BOX);
            _dropShadowEffect.radiusProperty().set(14);
            _dropShadowEffect.setColor(Color.color(0, 0,0, 0.16f));

            var _snackBarLayout = new JFXSnackbarLayout(message, "Close", action -> _snackBar.close());

            _snackBarLayout.setPrefWidth(300);
            _snackBarLayout.setOpacity(0.9f);
            _snackBarLayout.setEffect(_dropShadowEffect);
            _snackBarLayout.autosize();

            _snackBarLayout.setStyle("-fx-background-radius: 14; -fx-background-color: black; -fx-border-radius: 14;");

            var styleSheets = Objects.requireNonNull(MMRApplication.class
                    .getResource("css/snackbar.css")).toExternalForm();

            _snackBarLayout.getStylesheets().add(styleSheets);
            _snackBar.enqueue(new JFXSnackbar.SnackbarEvent(_snackBarLayout, Duration.seconds(5)));
            _snackBar.close();
        } catch (NullPointerException ignored) {}
    }
}
