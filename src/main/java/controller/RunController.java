package controller;

import com.kuba.runmanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.RunService;

import java.io.IOException;

public class RunController {
    @FXML
    Label runName;
    @FXML
    Label runDistance;
    @FXML
    Label runDate;
    @FXML
    Label runLocation;
    @FXML
    Label runWeb;
    @FXML
    AnchorPane runInfo;

    private final RunService runService = new RunService();
    private Parent root;
    private Scene scene;
    private Stage stage;
    public void getRun(String runId, int userId){
        runService.getRunDB(runId);
        runService.setUserId(userId);
        runName.setText(runService.getRun().getName());
        runDistance.setText(runService.getRun().getDistance() + " km");
        runDate.setText(runService.getRun().getDate().toString());
        runLocation.setText(runService.getRun().getLocation());
        runName.setAlignment(Pos.CENTER);
    }

    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Events.fxml"));
        root = loader.load();

        EventsController eventsController = loader.getController();
        eventsController.getUser(runService.getUserId());

        stage = (Stage) runInfo.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void delete(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("You're about to delete your run from data base !");
        alert.setContentText("Are you sure?");
        if (alert.showAndWait().get() == ButtonType.OK){
            runService.deleteRunDB();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Events.fxml"));
            root = loader.load();

            EventsController eventsController = loader.getController();
            eventsController.getUser(runService.getUserId());

            stage = (Stage)runInfo.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
