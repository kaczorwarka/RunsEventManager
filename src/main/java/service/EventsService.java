package service;

import Model.Run;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventsService {

    private List<Run> myRunsList = new ArrayList<>();
    private List<Run> apiRunsList = new ArrayList<>();


    public List<Run> getRunEvents(){
        //connecting to DB
        List<Run> runEvents = new ArrayList<>();

        runEvents.add(new Run("Warsaw marathon", 42.195, LocalDate.of(2023,10,2), "web1"));
        runEvents.add(new Run("Warsaw half marathon", 21.1, LocalDate.of(2023,3,12), "web2"));
        runEvents.add(new Run("Bieg dzika", 125, LocalDate.of(2023,7,3), "web3"));

        apiRunsList = runEvents;

        return runEvents;
    }

    public List<Run> getMyRuns(String name, Integer distance, LocalDate date, String location){
        List<Run> searchRun = new ArrayList<>();
        int counter = 0;
        for(Run run : myRunsList){
            if(name == null || run.getName().equals(name)) {
                counter++;
            }
            if(distance == null || (int)run.getDistance() == distance) {
                counter++;
            }
            if(date == null || run.getDate().isAfter(date)) {
                counter++;
            }

            if(counter == 3){
                searchRun.add(run);
            }
        }
        return searchRun;
    }

    public List<Run> getApiRuns(String name, Integer distance, LocalDate date, String location) {
        List<Run> searchRun = new ArrayList<>();
        int counter;
        for (Run run : apiRunsList) {
            counter = 0;
            if (Objects.equals(name, "") || run.getName().equalsIgnoreCase(name)) {
                System.out.println("git N");
                counter++;
            }
            if (distance == 0 || (int) run.getDistance() == distance) {
                System.out.println((int) run.getDistance());
                System.out.println("git D");
                counter++;
            }
            if (date == null || run.getDate().isAfter(date)) {
                System.out.println("git Da");
                counter++;
            }

            if (counter == 3) {
                searchRun.add(run);
            }
        }
        return searchRun;
    }

    public void addRun(Run run){
        myRunsList.add(run);
    }
}
