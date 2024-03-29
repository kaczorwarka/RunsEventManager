package service;

import com.google.gson.JsonObject;
import model.Run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ApiConnection {
    private HttpURLConnection urlConnection;

    public List<Run> getApiRunEvents(){
        List<JsonObject> jsonList = getConnection();
        List<Run> runEvents = new ArrayList<>();
        for(JsonObject run : jsonList){
            String dateS = run.get("Date").getAsString();
            LocalDateTime localDateTime = LocalDateTime.parse(dateS.substring(0, dateS.length() - 1));
            runEvents.add(new Run(run.get("Name").getAsString(),
                    run.get("Distance").getAsDouble(),
                    localDateTime.toLocalDate(),
                    run.get("Website").getAsString(),
                    run.get("Location").getAsString()));
        }
        return runEvents;
    }

    private List<JsonObject> getConnection(){

        Properties properties = new Properties();

        try (InputStream inputStream = DBInfo.class.getResourceAsStream("/config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String link = properties.getProperty("API.LINK");
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
