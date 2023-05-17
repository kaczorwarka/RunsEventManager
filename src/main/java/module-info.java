module com.kuba.runmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;


    opens com.kuba.runmanager to javafx.fxml;
    exports com.kuba.runmanager;
    exports controller;
    opens controller to javafx.fxml;
}
