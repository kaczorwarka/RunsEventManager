package model;

import java.time.LocalDate;

public class Run {
    private static int idGlobal = 0;
    private final int id;
    private final String name;
    private final double distance;
    private final LocalDate date;
    private final String website;
    private final String Location;

    public Run(String name, double distance, LocalDate date, String website, String Location) {
        this.name = name;
        this.distance = distance;
        this.date = date;
        this.website = website;
        this.Location = Location;
        this.id = idGlobal;
        idGlobal += 1;
    }
    public Run(int id,String name, double distance, LocalDate date, String website, String Location) {
        this.name = name;
        this.distance = distance;
        this.date = date;
        this.website = website;
        this.Location = Location;
        this.id = id;
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

    public String getLocation() {
        return Location;
    }

    public static int getIdGlobal() {
        return idGlobal;
    }

    public static void setIdGlobal(int idGlobal) {
        Run.idGlobal = idGlobal;
    }
}
