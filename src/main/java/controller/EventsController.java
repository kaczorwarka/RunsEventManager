package controller;

import controller.EventBus.APIRunEvent;
import controller.EventBus.EventListener;
import controller.EventBus.MyRunEvent;
import javafx.geometry.Pos;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;

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
    @FXML
    private Label myRunText;

    private Parent root;
    private Scene scene;
    private Stage stage;
    private final EventsService eventsService;
    private boolean myRunsTurn;
    private final List<Counter> counters = new ArrayList<>();

    private EventBus eventBus = new EventBus();
    private APIRunEvent apiRunEvent;
    private MyRunEvent myRunEvent;
    private EventListener eventListener;

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

    public void runSearch(ActionEvent event){
        for(Counter counter : counters){
            counter.interrupt();
        }
        counters.clear();
        runList.getChildren().clear();
        double space = 15.0;
        double layoutX = space;
        double layoutY = 25;
        double height = 70;
        double width = runList.getWidth() - 2 * layoutY;
        List<Run> searchRun;
        if(eventListener.isMyRunTurns()){
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
            if(run.getDate().isAfter(LocalDate.now())){
                BorderPane borderPane =  putRun(run, space, height, width);
                runList.getChildren().add(borderPane);
                if(!eventListener.isMyRunTurns()){
                    borderPane.setOnMouseClicked(e -> addRun(borderPane.getId()));
                }else{
                    borderPane.setOnMouseClicked(e -> {
                        try {
                            runInfo(borderPane.getId());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                }
                AnchorPane.setTopAnchor(borderPane,layoutX);
                AnchorPane.setLeftAnchor(borderPane,layoutY);
                layoutX += height + space;
            }
        }
    }

    public void addRun(String id){
        eventsService.addToMyRunDB(id);
        searchButton.fire();
    }

    public void runInfo(String id) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Run.fxml"));
        root = loader.load();

        RunController runController = loader.getController();
        runController.getRun(id, eventsService.getUser().getId());

        stage = (Stage) events.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized !");
        myRunsTurn = false;
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000);
        searchDistance.setValueFactory(valueFactory);

        apiRunEvent = new APIRunEvent(myRuns, apiRuns, myRunText, runList);
        myRunEvent = new MyRunEvent(myRuns, apiRuns, myRunText, runList);
        eventListener = new EventListener();
        eventBus.register(eventListener);
        myRuns.setOnAction(event -> {
            eventBus.post(myRunEvent);
        });
        apiRuns.setOnAction(event -> {
            eventBus.post(apiRunEvent);
        });

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
        Label location = new Label();
        Label timer = new Label();
        name.setText(run.getName());
        name.setFont(Font.font("myFont", FontWeight.BOLD, 12));
        distance.setText(run.getDistance()+" km");
        date.setText(run.getDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        location.setText(run.getLocation());
        timer.setText("timer");

        date.setPrefWidth(5*space);
        date.setAlignment(Pos.CENTER_RIGHT);
        distance.setPrefWidth(5*space);

        borderPane.setLeft(distance);
        borderPane.setCenter(name);
        borderPane.setRight(date);
        borderPane.setTop(location);
        borderPane.setBottom(timer);

        BorderPane.setMargin(distance, new Insets(0, 0, 0, space));
        BorderPane.setAlignment(distance, Pos.CENTER_LEFT);
        BorderPane.setMargin(date, new Insets(0, space, 0, 0));
        BorderPane.setAlignment(date, Pos.CENTER_RIGHT);
        BorderPane.setAlignment(location, Pos.TOP_CENTER);
        BorderPane.setAlignment(timer, Pos.CENTER);
        counters.add(new Counter(eventsService, borderPane, timer, searchButton, run));
        counters.get(counters.size() - 1).start();
        return borderPane;
    }

}
