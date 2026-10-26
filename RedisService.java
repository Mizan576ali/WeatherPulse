package demo;

import redis.clients.jedis.RedisClient;
public class RedisService {
    RedisClient myCloud=RedisClient.create("myCloud://localhost:6379");
     void settingWeatherData(String city,String country,double temp,double windSpeed,double humidity){
        String key=city+":"+country;
        myCloud.hset(key,"Temperature:",Double.toString(temp));
        myCloud.hset(key,"WindSpeed:",Double.toString(windSpeed));
        myCloud.hset(key,"Humidity:",Double.toString(humidity));
        myCloud.expire(key,180);
    }
     boolean getCachedWeather(String city,String country){
        String key=city+":"+country;
        try{
            if(myCloud.exists(key)){
                System.out.println(myCloud.hgetAll(key));
                return true;
            }

        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        return false;
    }
}
