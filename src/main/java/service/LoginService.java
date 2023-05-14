package service;

import Model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoginService {

    private List<User> users = new ArrayList<>(List.of(
            new User("Kuba", "Kaczmarski", "kuba.kaczmarski@gmail.com",
                    LocalDate.of(2001,10,2), "123")
    ));

    public LoginService() {

    }

    public User getUser(String email, String password){
        //connecting DB
        //-----------------------------------
        for(User user : users){
            if(user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
        //-----------------------------------
    }

}
