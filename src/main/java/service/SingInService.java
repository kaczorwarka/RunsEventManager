package service;

import Model.User;

public class SingInService {

    public void addUser(User user){
        //connecting to DB
        System.out.println("User " + user.getName() + " added");
    }
}
