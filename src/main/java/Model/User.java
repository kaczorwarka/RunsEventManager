package Model;

import java.time.LocalDate;

public class User {
    static int idGlobal = 0;
    private int id;
    private String Name;
    private String LastName;
    private String email;
    private LocalDate dateOfBirth;
    private String password;

    public User(String name, String lastName, String email, LocalDate dateOfBirth, String password) {
        Name = name;
        LastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.id = idGlobal;
        idGlobal ++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getLastName() {
        return LastName;
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
