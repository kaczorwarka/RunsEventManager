package controller;

import javafx.scene.control.Label;
import model.User;
import com.kuba.runmanager.Main;
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
    @FXML
    private Label wrongEmail;
    @FXML
    private Label noValue;

    private final SingInService singInService;

    public SignInController() {
        this.singInService = new SingInService();
    }

    public void createAccount() throws IOException {

        if(name.getText() == null || lastName.getText() == null || email.getText() == null ||
        dateOfBirth.getValue() == null || password.getText() == null){
            noValue.setText("Fill all empty spots !");
        }else{
            User user = new User(name.getText(), lastName.getText(),email.getText(),
                    dateOfBirth.getValue(),password.getText());
            int answer = singInService.add(user);
            if(answer == 0){
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) signIn.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else if(answer == 1){
                wrongEmail.setText("This email already exist in data base !");
            }else if(answer == 2){
                wrongEmail.setText("This email is wrong !");
            }
        }
    }
}
