package controller;

import com.kuba.runmanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.UserService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class UserController {

    @FXML
    private Label nameText;
    @FXML
    private Label lastNameText;
    @FXML
    private Label emailText;
    @FXML
    private Label dateOfBirthText;
    @FXML
    private Label passwordText;
    @FXML
    private AnchorPane userScene;
    @FXML
    private Button changeButton;

    private final Button acceptButton = new Button();
    private final TextField updateName = new TextField();
    private final TextField updateLastName = new TextField();
    private final TextField updateEmail = new TextField();
    private final DatePicker updateDateOfBirth = new DatePicker();
    private final TextField updatePassword = new TextField();
    private Parent root;
    private Scene scene;
    private Stage stage;
    private final UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public void getUser(int id) {
        userService.getUserDB(id);
        nameText.setText(userService.getUser().getName());
        lastNameText.setText(userService.getUser().getLastName());
        emailText.setText(userService.getUser().getEmail());
        dateOfBirthText.setText(userService.getUser().getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        passwordText.setText(userService.getUser().getPassword());
        acceptButton.setText("Accept");
        acceptButton.setOnAction(this::acceptUpdate);
    }

    public void updateUser(){
        nameText.setText("");
        lastNameText.setText("");
        emailText.setText("");
        dateOfBirthText.setText("");
        passwordText.setText("");
        changeButton.setText("");
        changeButton.setDisable(true);

        userScene.getChildren().add(updateName);
        AnchorPane.setTopAnchor(updateName, 82.0);
        AnchorPane.setLeftAnchor(updateName, 273.0);

        userScene.getChildren().add(updateLastName);
        AnchorPane.setTopAnchor(updateLastName, 136.0);
        AnchorPane.setLeftAnchor(updateLastName, 273.0);

        userScene.getChildren().add(updateEmail);
        AnchorPane.setTopAnchor(updateEmail, 177.0);
        AnchorPane.setLeftAnchor(updateEmail, 273.0);

        userScene.getChildren().add(updateDateOfBirth);
        AnchorPane.setTopAnchor(updateDateOfBirth, 219.0);
        AnchorPane.setLeftAnchor(updateDateOfBirth, 273.0);

        userScene.getChildren().add(updatePassword);
        AnchorPane.setTopAnchor(updatePassword, 263.0);
        AnchorPane.setLeftAnchor(updatePassword, 273.0);

        userScene.getChildren().add(acceptButton);
        AnchorPane.setTopAnchor(acceptButton, 337.0);
        AnchorPane.setLeftAnchor(acceptButton, 274.0);
    }

    public void acceptUpdate(ActionEvent event){
        userService.updateUserDB(updateName.getText(), updateLastName.getText(), updateEmail.getText(),
                updateDateOfBirth.getValue(), updatePassword.getText());

        userScene.getChildren().remove(acceptButton);
        userScene.getChildren().remove(updateName);
        userScene.getChildren().remove(updateLastName);
        userScene.getChildren().remove(updateEmail);
        userScene.getChildren().remove(updateDateOfBirth);
        userScene.getChildren().remove(updatePassword);

        nameText.setText(userService.getUser().getName());
        lastNameText.setText(userService.getUser().getLastName());
        emailText.setText(userService.getUser().getEmail());
        dateOfBirthText.setText(userService.getUser().getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        passwordText.setText(userService.getUser().getPassword());
        changeButton.setText("Change");
        changeButton.setDisable(false);

    }

    public void delete() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("You're about to delete your account !");
        alert.setContentText("Are you sure?");
        if (alert.showAndWait().get() == ButtonType.OK){
            userService.deleteUserDB();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login.fxml"));
            root = loader.load();
            stage = (Stage)userScene.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Events.fxml"));
        root = loader.load();

        EventsController eventsController = loader.getController();
        eventsController.getUser(userService.getUser().getId());

        stage = (Stage)userScene.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
