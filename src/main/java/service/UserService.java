package service;

import model.User;

import java.sql.*;
import java.time.LocalDate;

public class UserService {

    private final Statement statement;
    private User user;
    private final String userTable = "user";

    public UserService() {
        try {
            Connection connection = DriverManager.getConnection(DBInfo.DATABASE_URL, DBInfo.USERNAME, DBInfo.PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void updateUserDB(String name, String lastName, String email, LocalDate dateOfBirth, String password){
        if(!name.equals("")){
            user.setName(name);
        }
        if(!lastName.equals("")){
            user.setLastName(lastName);
        }
        if(!email.equals("")){
            user.setEmail(email);
        }
        if(dateOfBirth != null){
            user.setDateOfBirth(dateOfBirth);
        }
        if(!password.equals("")){
            user.setPassword(password);
        }
        try {
            statement.executeUpdate("update " + userTable + " set " +
                    "name = '" + user.getName() + "', " +
                    "lastName = '" + user.getLastName() + "', " +
                    "email = '" + user.getEmail() + "', " +
                    "dateOfBirth = '" + user.getDateOfBirth() + "', " +
                    "password = '" + user.getPassword() + "' " +
                    "where idUser = " + user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("user " + user.getName() + " updated");
    }

    public void deleteUserDB(){
        try {
            statement.executeUpdate("delete from " + userTable +
                    " where idUser = " + user.getId() + ";");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser() {
        return user;
    }
}
