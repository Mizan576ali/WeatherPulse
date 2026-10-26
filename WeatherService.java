package demo;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.*;
import java.util.Scanner;

public class WeatherService
{
    public static void main(String[] args)  {

        try {
            GeocodingService geocoding=new GeocodingService();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter city:");
            String city = sc.nextLine().toLowerCase().trim();
            System.out.println("Country:");
            String country = sc.nextLine().toLowerCase().trim();

            RedisService redisService=new RedisService();
            boolean isCached=redisService.getCachedWeather(city,country);
            if(isCached)return;

           Coordinates coordinates=geocoding.findCoordinates(city,country);
            double latitude =coordinates.getLatitude();
            double longitude =coordinates.getLongitude();
            URI uri=URI.create(
                    "https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&current=temperature_2m,relative_humidity_2m,wind_speed_10m"

            );

            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
            if(response.statusCode()!=200) throw new WeatherException("Location not found");

            ObjectMapper objectMapper=new ObjectMapper();
            JsonNode root=objectMapper.readTree(response.body());

            JsonNode current=root.get("current");
            if(current==null || current.isEmpty()) throw new WeatherException("Temperature not found at this moment");

            JsonNode currentUnits=root.get("current_units");
            double temperature=current.get("temperature_2m").asDouble();
           String unitTemperature=currentUnits.get("temperature_2m").asText();

           double windSpeed=current.get("wind_speed_10m").asDouble();
           String windUnit=currentUnits.get("wind_speed_10m").asText();
           double humidity=current.get("relative_humidity_2m").asDouble();
           String humidityUnit=currentUnits.get("relative_humidity_2m").asText();
            System.out.println("Weather detail");
            System.out.println("---------------");
           System.out.println("Temperature:"+temperature+unitTemperature);
            System.out.println("Wind speed:"+windSpeed+windUnit);
            System.out.println("Humidity: "+humidity+humidityUnit);
            redisService.settingWeatherData(city,country,temperature,windSpeed,humidity);

        }catch(IOException | InterruptedException  e){
            System.out.println(e.getMessage());

        }
    }





}
