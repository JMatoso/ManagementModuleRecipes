module com.matoso.mmr {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.jetbrains.annotations;
    requires lombok;
    requires com.jfoenix;

    opens com.matoso.mmr to javafx.fxml;
    exports com.matoso.mmr;
    exports com.matoso.mmr.controllers;
    exports com.matoso.mmr.entities;
    exports com.matoso.mmr.data;
    exports com.matoso.mmr.helpers;
    exports com.matoso.mmr.models;
    exports com.matoso.mmr.services;
    opens com.matoso.mmr.controllers to javafx.fxml;
}