package service;

import model.User;

import java.sql.*;


public class LoginService {

    private final Statement statement;


    public LoginService() {
        try {
            Connection connection = DriverManager.getConnection(DBInfo.DATABASE_URL, DBInfo.USERNAME, DBInfo.PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String email, String password){
        ResultSet resultSet;
        try {
            String userTable = "user";
            resultSet = statement.executeQuery("select * from " + userTable +
                    " where email = '" + email +
                    "' and password = '" + password + "';");
            if(!resultSet.next()){
                return null;
            }else{
                return new User(
                        resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getString("email"),resultSet.getDate("dateOfBirth").toLocalDate(),
                        resultSet.getString("password"), resultSet.getInt("idUSer"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
