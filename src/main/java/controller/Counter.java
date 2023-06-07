package controller;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Run;
import service.EventsService;
import java.time.Duration;
import java.time.LocalDateTime;

public class Counter extends Thread{

    private final EventsService eventsService;
    private final Label timer;
    private final Button searchButton;
    private final Run run;
    private long days;
    private long hours;
    private long minutes;
    private long seconds;

    public Counter(EventsService eventsService, Label timer, Button searchButton, Run run){
        this.eventsService = eventsService;
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
