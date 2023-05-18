package controller;

import model.Run;
import com.kuba.runmanager.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import service.EventsService;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ResourceBundle;

public class EventsController implements Initializable{
    @FXML
    public AnchorPane events;
    @FXML
    public AnchorPane runList;
    @FXML
    public Button searchButton;
    @FXML
    public MenuItem apiRuns;
    @FXML
    public MenuItem myRuns;
    @FXML
    private Label userNameLabel;

    @FXML
    private TextField searchName;
    @FXML
    private Spinner<Integer> searchDistance;
    @FXML
    private DatePicker searchDate;
    @FXML
    private TextField searchLocation;

    private Parent root;
    private Scene scene;
    private Stage stage;
    private final EventsService eventsService;
    private List<Run> runEventsAPI;
    private boolean myRunsTurn = false;

    public EventsController() {
        this.eventsService = new EventsService();
    }
    public void getUser(int id) {
        eventsService.getUserDB(id);
        userNameLabel.setText("User: " + eventsService.getUser().getName());
    }

    public void userAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("User.fxml"));
        root = loader.load();

        UserController userController = loader.getController();
        userController.getUser(eventsService.getUser().getId());

        stage = (Stage) events.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void userLogout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout");
        alert.setContentText("Are you sure");
        if (alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login.fxml"));
            root = loader.load();
            stage = (Stage) events.getScene().getWindow();
            System.out.println("You just logout");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void myRunsClicked(ActionEvent event){
        myRuns.setDisable(true);
        apiRuns.setDisable(false);
        myRunsTurn = true;
        runList.getChildren().clear();
    }

    public void apiRunsClicked(ActionEvent event){
        myRuns.setDisable(false);
        apiRuns.setDisable(true);
        myRunsTurn = false;
        runList.getChildren().clear();
    }

    public void runSearch(ActionEvent event){
        double space = 15.0;
        double height = 50;
        double width = 300;
        double layoutX = space;
        double layoutY = 25;
        List<Run> searchRun;
        if(myRunsTurn){
            searchRun = eventsService.getMyRuns(searchName.getText(), searchDistance.getValue(),
                    searchDate.getValue(),searchLocation.getText());
            runList.getChildren().clear();
        }else{
            searchRun = eventsService.getApiRuns(searchName.getText(), searchDistance.getValue(),
                    searchDate.getValue(),searchLocation.getText());
        }
        for(Run run : searchRun){
            if(layoutX + height >= runList.getHeight()){
                break;
            }
            BorderPane borderPane =  putRun(run, space, height, width);
            runList.getChildren().add(borderPane);
            if(!myRunsTurn){
                borderPane.setOnMouseClicked(e -> addRun(borderPane.getId()));
            }
            AnchorPane.setTopAnchor(borderPane,layoutX);
            AnchorPane.setLeftAnchor(borderPane,layoutY);
            layoutX += height + space;
        }
    }

    public void addRun(String id){
        runList.getChildren().clear();
        eventsService.addToMyRunDB(id);
//        System.out.println(id);
//        for(Run run : eventsService.getApiRunsList()){
//            if(run.getId() == Double.parseDouble(id)){
//                eventsService.addRun(run);
//                try {
//                    eventsService.getApiRunsList().remove(run);
//                }catch (ConcurrentModificationException e){
//                    System.out.println("Bieg usuniety");
//                }
//                runList.getChildren().clear();
//                searchButton.fire();
//            }
//        }
        searchButton.fire();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized !");
        // runEventsAPI = eventsService.getRunEvents();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000);
        searchDistance.setValueFactory(valueFactory);
    }

    public BorderPane putRun(Run run, double space, double height, double width){
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #89faa7;" +
                "-fx-border-color: black;");
        borderPane.setPrefSize(width,height);
        borderPane.setId(String.valueOf(run.getId()));

        Label name = new Label();
        Label distance = new Label();
        Label date = new Label();
        name.setText(run.getName());
        name.setFont(Font.font("myFont", FontWeight.BOLD, 12));
        distance.setText(run.getDistance()+" km");
        date.setText(run.getDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));

        borderPane.setLeft(distance);
        borderPane.setCenter(name);
        borderPane.setRight(date);

        BorderPane.setMargin(distance, new Insets(space, 0, 0, space));
        BorderPane.setMargin(date, new Insets(space, space, 0, 0));

        return borderPane;
    }

}
