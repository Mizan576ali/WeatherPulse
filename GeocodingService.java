package demo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class GeocodingService {
    Coordinates findCoordinates(String city,String country)  throws IOException,InterruptedException,WeatherException{
        String name = city + "," + country;
        String encodedName = URLEncoder.encode(
                name, StandardCharsets.UTF_8
        );
        URI uri = URI.create(
                "https://geocoding-api.open-meteo.com/v1/search" +
                        "?name=" + encodedName
                        + "&count=5&language=en&format=json"

        );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() != 200)  throw new WeatherException("No matching-location found");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.body());
            JsonNode results = root.get("results");
            if (results == null || results.isEmpty()) {
                throw new WeatherException("Location not found");
            }

            JsonNode firstLocation = results.get(0);
             Coordinates coordinates= new Coordinates(

                    firstLocation.get("latitude").asDouble(),
                    firstLocation.get("longitude").asDouble()
            );
        return coordinates;

    }
}

class Coordinates{
    double latitude,longitude;
    Coordinates(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;

    }
    double getLatitude(){
        return latitude;
    }
    double getLongitude(){
        return longitude;
    }

}
