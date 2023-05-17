package model;

import java.time.LocalDate;

public class Run {
    static int idGlobal = 0;
    private int id;
    private String name;
    private double distance;
    private LocalDate date;
    private String website;

    public Run(String name, double distance, LocalDate date, String website) {
        this.name = name;
        this.distance = distance;
        this.date = date;
        this.website = website;
        this.id = idGlobal;
        idGlobal += 1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getWebsite() {
        return website;
    }
}
