package service;

import com.google.gson.JsonObject;
import model.Run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ApiConnection {

    private String  link = "https://6464e6e6228bd07b353c5f76.mockapi.io/RunsEvents";
    private HttpURLConnection urlConnection;


    public List<Run> getApiRunEvents(){
        //connecting to DB
        List<JsonObject> jsonList = getConnection();
        System.out.println(jsonList.toString());

        List<Run> runEvents = new ArrayList<>();

        for(JsonObject run : jsonList){
            String dateS = run.get("Date").getAsString();
            LocalDateTime localDateTime = LocalDateTime.parse(dateS.substring(0, dateS.length() - 1));
            System.out.println(localDateTime.toLocalDate());
            runEvents.add(new Run(run.get("Name").getAsString(),
                    run.get("Distance").getAsDouble(),
                    localDateTime.toLocalDate(),
                    run.get("Website").getAsString(),
                    run.get("Location").getAsString()));
        }
        return runEvents;
    }

    private List<JsonObject> getConnection(){
        List<JsonObject> jsonList = null;
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            URL url = new URL(link);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");

            int status = urlConnection.getResponseCode();
            if(status < 300){
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
                Type listType = new TypeToken<List<JsonObject>>(){}.getType();
                jsonList = new Gson().fromJson(responseContent.toString(), listType);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            urlConnection.disconnect();
        }
        return jsonList;
    }
}
