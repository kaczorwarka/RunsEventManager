package controller;

import Model.User;
import com.kuba.runmanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.SingInService;

import java.io.IOException;

public class SignInController {

    @FXML
    private AnchorPane signIn;
    @FXML
    private TextField name;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextField password;

    private Parent root;
    private Scene scene;
    private Stage stage;

    private final SingInService singInService;

    public SignInController() {
        this.singInService = new SingInService();
    }

    public void createAccount(ActionEvent event) throws IOException {
        User user = new User(name.getText(), lastName.getText(),email.getText(),
                dateOfBirth.getValue(),password.getText());
        singInService.addUser(user);

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        root = loader.load();

        stage = (Stage)signIn.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
