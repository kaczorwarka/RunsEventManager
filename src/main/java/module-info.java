module com.kuba.runmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires com.google.common;
    requires commons.validator;

    opens com.kuba.runmanager to javafx.fxml;
    exports com.kuba.runmanager;
    exports controller;
    exports controller.EventBus;
    opens controller to javafx.fxml;
}
