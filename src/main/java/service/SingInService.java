package service;

import model.User;

import java.sql.*;

public class SingInService {

    private final Statement statement;

    public SingInService() {

        try {
            Connection connection = DriverManager.getConnection(DBInfo.DATABASE_URL, DBInfo.USERNAME, DBInfo.PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(User user){
        int id;
        String email;
        ResultSet resultSet;
        try {
            String userTable = "user";
            resultSet = statement.executeQuery("select idUser, email from " + userTable + " order by idUser desc;");
            resultSet.next();
            id = resultSet.getInt("idUser");
            do{
                email = resultSet.getString("email");
                if(email.equals(user.getEmail()))
                    return 1;
            }while(resultSet.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        user.setId(id+1);
        try {
            statement.executeUpdate(
                    "INSERT INTO `runs-jdbc`.`user`\n" +
                    "(`idUser`,\n" +
                    "`name`,\n" +
                    "`lastName`,\n" +
                    "`email`,\n" +
                    "`dateOfBirth`,\n" +
                    "`password`)\n" +
                    "VALUES\n" +
                    "(" +
                    user.getId() + ",\n'" +
                    user.getName() + "',\n'" +
                    user.getLastName() + "',\n'" +
                    user.getEmail() + "',\n'" +
                    user.getDateOfBirth() + "',\n" +
                    user.getPassword() +
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User " + user.getName() + " added");
        return 0;
    }
}
