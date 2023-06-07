package service;

import model.Run;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class EventsService {

    private final List<Run> apiRunsList;
    private final String runTable = "myrun";
    private final  String userTable = "user";
    private final Statement statement;
    private User user;

    public EventsService() {

        System.out.println(DBInfo.DATABASE_URL);
        try {
            Connection connection = DriverManager.getConnection(DBInfo.DATABASE_URL, DBInfo.USERNAME, DBInfo.PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        System.out.println(Run.getIdGlobal());

        ApiConnection apiConnection = new ApiConnection();
        apiRunsList = apiConnection.getApiRunEvents();

        apiRunsList.sort(Comparator.comparing(Run::getDate));
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

            int i = 0;
            Run run;
            while (i < apiRunsList.size()){
                run = apiRunsList.get(i);
                resultSet = statement.executeQuery("select * from " + runTable +
                        "\nwhere idUser = " + id +
                        "\nand name = \"" + run.getName() +
                        "\"\nand distance = " + run.getDistance() +
                        "\nand date = '" + run.getDate() +
                        "'\nand location = \"" + run.getLocation() +
                        "\";");
                if(resultSet.next()) apiRunsList.remove(run);
                else i ++;
            }
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
                        "where idUser = " + user.getId() +
                        " order by date asc;");
            }else{
                if(date == null) date = LocalDate.parse("2001-02-16");
                resultSet = statement.executeQuery("select * from myrun " +
                        "where (name = \"" + name +
                        "\" or distance = " + distance +
                        " or date = '" + date +
                        "' or location = \"" + location +
                        "\") and idUser = " + user.getId() + "\n" +
                        "order by date asc;");
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
                System.out.println(newRun.getId());
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
                                newRun.getId() + ",\n\"" +
                                newRun.getName() + "\",\n" +
                                newRun.getDistance() + ",\n'" +
                                newRun.getDate() + "',\n'" +
                                newRun.getWebsite() + "',\n\"" +
                                newRun.getLocation() + "\",\n" +
                                user.getId() +
                                ");");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void deleteApiRun(Run run){
        apiRunsList.remove(run);
    }
    public User getUser() {
        return user;
    }
}
