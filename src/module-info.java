module workshop.javafx.jdbc {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens sample;
    opens gui;
    opens model.entities;
}