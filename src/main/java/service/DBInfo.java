package service;

import com.kuba.runmanager.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBInfo{

    public static Properties properties = new Properties();
    public static String DATABASE_URL;
    public static String USERNAME;
    public static String PASSWORD;

    public static void DBInitialize (){
        try (InputStream inputStream = DBInfo.class.getResourceAsStream("/config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DATABASE_URL = properties.getProperty("DB.URL");
        USERNAME = properties.getProperty("DB.USERNAME");
        PASSWORD = properties.getProperty("DB.PASSWORD");
    }

}