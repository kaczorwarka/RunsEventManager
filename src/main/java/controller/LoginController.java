package controller;

import com.kuba.runmanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.LoginService;

import java.io.IOException;

public class LoginController {
    @FXML
    private AnchorPane login;
    @FXML
    private TextField userLoginField;
    @FXML
    private PasswordField userPasswordField;
    @FXML
    private Label errorField;
    private Parent root;
    private Scene scene;
    private Stage stage;
    private final LoginService loginService;

    public LoginController() {

        this.loginService = new LoginService();
    }

    public void userLogin(ActionEvent event) throws IOException {

        String userLogin = userLoginField.getText();
        String userPassword = userPasswordField.getText();
        Integer userId = loginService.getUserDB(userLogin, userPassword);

        if (userId == null){
            errorField.setText("Your login or password are incorrect");
        }else{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Events.fxml"));
            root = loader.load();

            EventsController eventsController = loader.getController();
            eventsController.getUser(userId);

            stage = (Stage)login.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void userSingIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("SignIn.fxml"));
        root = loader.load();

        stage = (Stage)login.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
