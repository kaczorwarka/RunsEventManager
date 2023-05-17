package model;

import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String password;

    public User(String name, String lastName, String email, LocalDate dateOfBirth, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public User(String name, String lastName, String email, LocalDate dateOfBirth, String password, int id) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
    }
}
