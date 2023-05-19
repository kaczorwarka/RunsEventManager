package controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import model.Run;
import service.EventsService;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

public class Counter extends Thread{

    EventsService eventsService;
    BorderPane borderPane;
    Label timer;
    Button searchButton;
    Run run;
    long days;
    int hours;
    int minutes;
    int seconds;

    public Counter(EventsService eventsService, BorderPane borderPane, Label timer, Button searchButton, Run run){
        this.eventsService = eventsService;
        this.borderPane = borderPane;
        this.timer = timer;
        this.searchButton = searchButton;
        this.run = run;
    }

    @Override
    public void run(){
        days = DAYS.between(LocalDate.now(), run.getDate());
        hours = 0;
        minutes = 0;
        seconds = 0;

        while (days > -1 && !Thread.interrupted()){
            Platform.runLater(()->{
                timer.setText(days + ":" + hours + ":" + minutes + ":" + seconds);
            });
            if(seconds == 0){
                seconds = 60;
                if(minutes == 0){
                    minutes = 60;
                    if(hours == 0){
                        hours = 24;
                        days --;
                    }
                    hours --;
                }
                minutes--;
            }
            seconds --;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        if(days == -1){
            eventsService.deleteApiRun(run);
            searchButton.fire();
        }
    }
}
