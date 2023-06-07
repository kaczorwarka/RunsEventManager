package controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import model.Run;
import service.EventsService;
import java.time.Duration;
import java.time.LocalDateTime;

public class Counter extends Thread{

    EventsService eventsService;
    BorderPane borderPane;
    Label timer;
    Button searchButton;
    Run run;
    long days;
    long hours;
    long minutes;
    long seconds;

    public Counter(EventsService eventsService, BorderPane borderPane, Label timer, Button searchButton, Run run){
        this.eventsService = eventsService;
        this.borderPane = borderPane;
        this.timer = timer;
        this.searchButton = searchButton;
        this.run = run;
    }

    @Override
    public void run(){
        LocalDateTime dateOfRun = run.getDate().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, dateOfRun);

        while (duration.isPositive()){
            days = duration.toDays();
            hours = duration.toHours() % 24;
            minutes = duration.toMinutes() % 60;
            seconds = duration.toSeconds() % 60;
            Platform.runLater(()->{
                timer.setText(days + ":" + hours + ":" + minutes + ":" + seconds);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

                return;
            } catch (RuntimeException e){
                break;
            }
            now = LocalDateTime.now();
            duration = Duration.between(now, dateOfRun);
        }
        eventsService.deleteApiRun(run);
        searchButton.fire();

    }
}
