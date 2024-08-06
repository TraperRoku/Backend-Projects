package com.WeatherApp.WeatherApp.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {

    private HttpClient client;
    private static final String API_URL =  "MKY6X49GQRP8V4S45MGPVMVED";
    private static final String BASE_URL =  "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    public WeatherService(){
        this.client= HttpClient.newHttpClient();
    }

    public String getWeather(String location){
        String url = BASE_URL + location + "?unitGroup=metric&elements=datetime%2Ctempmax%2Ctempmin%2Ctemp%2Cwindspeedmean&include=days%2Cobs%2Cstats%2Cfcst%2Cstatsfcst&key=" + API_URL + "&contentType=json";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (Exception e){
            e.printStackTrace();
            return "Error downloading weather data";
        }
    }

}
