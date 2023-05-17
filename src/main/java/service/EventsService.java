package service;

import model.Run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventsService {

    private List<Run> myRunsList = new ArrayList<>();
    private final List<Run> apiRunsList;

    private final Statement statement;

    public EventsService() {
        try {
            Connection connection = DriverManager.getConnection(DBInfo.DATABASE_URL, DBInfo.USERNAME, DBInfo.PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ApiConnection apiConnection = new ApiConnection();
        apiRunsList = apiConnection.getApiRunEvents();
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
                //System.out.println("git N");
                counter++;
            }
            if (distance == 0 || (int) run.getDistance() == distance) {
                System.out.println((int) run.getDistance());
                //System.out.println("git D");
                counter++;
            }
            if (date == null || run.getDate().isAfter(date)) {
                //System.out.println("git Da");
                counter++;
            }

            if (counter == 3) {
                searchRun.add(run);
            }
        }
        return searchRun;
    }

    public void addToMyRuns(String id){
        Run newRun = null;
        for (Run run : apiRunsList){
            if (run.getId() == Integer.parseInt(id)){
                newRun = run;
                break;
            }
        }
        if(newRun != null){
            apiRunsList.remove(newRun);

        }

    }

    public void addRun(Run run){
        myRunsList.add(run);
    }

    public List<Run> getApiRunsList() {
        return apiRunsList;
    }
}
