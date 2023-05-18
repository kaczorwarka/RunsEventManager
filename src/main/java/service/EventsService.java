package service;

import model.Run;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventsService {

    private List<Run> myRunsList = new ArrayList<>();
    private final List<Run> apiRunsList;
    private final String runTable = "myrun";
    private final  String userTable = "user";
    private final Statement statement;
    private User user;

    public EventsService() {
        try {
            Connection connection = DriverManager.getConnection(DBInfo.DATABASE_URL, DBInfo.USERNAME, DBInfo.PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ApiConnection apiConnection = new ApiConnection();
        apiRunsList = apiConnection.getApiRunEvents();

        int id = -1;
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("select idRun from " + runTable + " order by idRun desc;");
            if (resultSet.next()){
                id = resultSet.getInt("idRun");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Run.setIdGlobal(id + 1);
    }

    public void getUserDB(int id){
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("select * from " + userTable +
                    " where idUser = '" + id + "';");
            resultSet.next();
            user = new User(
                    resultSet.getString("name"), resultSet.getString("lastName"),
                    resultSet.getString("email"), resultSet.getDate("dateOfBirth").toLocalDate(),
                    resultSet.getString("password"), resultSet.getInt("idUser"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Run> getMyRuns(String name, Integer distance, LocalDate date, String location){
        ResultSet resultSet;
        List<Run> searchRun = new ArrayList<>();
        try {
            if(name.equals("") && distance == 0 && date == null && location.equals("")){
                resultSet = statement.executeQuery("select * from myrun " +
                        "where idUser = " + user.getId() + ";");
            }else{
                if(date == null) date = LocalDate.parse("2001-02-16");
                resultSet = statement.executeQuery("select * from myrun " +
                        "where (name = '" + name +
                        "' or distance = " + distance +
                        " or date = '" + date +
                        "' or location = '" + location +
                        "') and idUser = " + user.getId() + ";");
            }
            while(resultSet.next()){
                searchRun.add(new Run(resultSet.getInt("idRun"), resultSet.getString("name"),
                        resultSet.getDouble("distance"), resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("website"), resultSet.getString("location")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return searchRun;
    }

    public List<Run> getApiRuns(String name, Integer distance, LocalDate date, String location) {
        List<Run> searchRun = new ArrayList<>();
        int counter;
        for (Run run : apiRunsList) {
            counter = 0;
            if (Objects.equals(name, "") || run.getName().equalsIgnoreCase(name)) {
                counter++;
            }
            if (distance == 0 || (int) run.getDistance() == distance) {
                System.out.println((int) run.getDistance());
                counter++;
            }
            if (date == null || run.getDate().isAfter(date)) {
                counter++;
            }
            if (counter == 3) {
                searchRun.add(run);
            }
        }
        return searchRun;
    }

    public void addToMyRunDB(String id){
        Run newRun = null;
        for (Run run : apiRunsList){
            if (run.getId() == Integer.parseInt(id)){
                newRun = run;
                break;
            }
        }
        if(newRun != null){
            apiRunsList.remove(newRun);
            try {
                statement.executeUpdate(
                        "INSERT INTO `runs-jdbc`.`" + runTable + "`\n" +
                                "(`idRun`,\n" +
                                "`name`,\n" +
                                "`distance`,\n" +
                                "`date`,\n" +
                                "`website`,\n" +
                                "`location`,\n" +
                                "`idUser`)\n" +
                                "VALUES\n" +
                                "(" +
                                newRun.getId() + ",\n'" +
                                newRun.getName() + "',\n" +
                                newRun.getDistance() + ",\n'" +
                                newRun.getDate() + "',\n'" +
                                newRun.getWebsite() + "',\n'" +
                                newRun.getLocation() + "',\n" +
                                user.getId() +
                                ");");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addRun(Run run){
        myRunsList.add(run);
    }

    public List<Run> getApiRunsList() {
        return apiRunsList;
    }

    public User getUser() {
        return user;
    }
}
