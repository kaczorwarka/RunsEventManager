package service;

import model.Run;
import java.sql.*;

public class RunService {

    private Run run;
    private final Statement statement;
    private String runTable = "myrun";
    private int userId;
    public RunService() {
        try {
            Connection connection = DriverManager.getConnection(DBInfo.DATABASE_URL, DBInfo.USERNAME, DBInfo.PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getRunDB(String id) {
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery("select * from " + runTable +
                    " where idRun = " + id + ";");
            resultSet.next();
            run = new Run(
                    resultSet.getInt("idRun"), resultSet.getString("name"),
                    resultSet.getDouble("distance"), resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("website"), resultSet.getString("location"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRunDB(){
        try {
            statement.executeUpdate("delete from " + runTable +
                    " where idRun = " + run.getId() + ";");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public Run getRun() {
        return run;
    }
}
