package controller;

import model.User;
import com.kuba.runmanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private TextField updateName = new TextField();
    private TextField updateLastName = new TextField();
    private TextField updateEmail = new TextField();
    private DatePicker updateDateOfBirth = new DatePicker();
    private TextField updatePassword = new TextField();
    private Parent root;
    private Scene scene;
    private Stage stage;
    private UserService userService;
    private User user;

    public UserController() {
        userService = new UserService();
    }

    public void getUser(User user) {
        this.user = user;
        nameText.setText(user.getName());
        lastNameText.setText(user.getLastName());
        emailText.setText(user.getEmail());
        dateOfBirthText.setText(user.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        passwordText.setText(user.getPassword());
        acceptButton.setText("Accept");
        acceptButton.setOnAction(this::acceptUpdate);
    }

    public void updateUser(ActionEvent event){
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
        userScene.getChildren().remove(acceptButton);
        userScene.getChildren().remove(updateName);
        userScene.getChildren().remove(updateLastName);
        userScene.getChildren().remove(updateEmail);
        userScene.getChildren().remove(updateDateOfBirth);
        userScene.getChildren().remove(updatePassword);

        nameText.setText(user.getName());
        lastNameText.setText(user.getLastName());
        emailText.setText(user.getEmail());
        dateOfBirthText.setText(user.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        passwordText.setText(user.getPassword());
        changeButton.setText("Change");
        changeButton.setDisable(false);

    }

    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Events.fxml"));
        root = loader.load();

        EventsController eventsController = loader.getController();
        eventsController.getUser(user);

        stage = (Stage)userScene.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
