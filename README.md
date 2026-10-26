# WeatherPulse 🌦️

WeatherPulse is a Java-based weather application that retrieves current weather information using the Open-Meteo API and uses Redis for caching frequently requested weather data.

## 🚀 Features

- Search weather information using a city and country.
- Convert a location into latitude and longitude using geocoding.
- Retrieve current:
  - Temperature
  - Relative humidity
  - Wind speed
- Cache weather data using Redis.
- Reduce repeated API requests by checking Redis before calling the weather API.
- Store weather information using a city-country based cache key.

## 🛠️ Technologies Used

- **Java**
- **Open-Meteo API**
- **Jackson** — JSON processing
- **Redis** — caching
- **Jedis** — Java Redis client
- **Maven** — dependency management

## 🔄 How It Works

```text
User
 ↓
Enter city and country
 ↓
Geocoding API
 ↓
Latitude + Longitude
 ↓
Check Redis
 ↓
 ┌───────────────┐
 │ Data in Redis?│
 └───────┬───────┘
     Yes │   │ No
         ↓   ↓
      Redis  Weather API
         │       │
         │       ↓
         │    Get weather
         │       │
         │       ↓
         │    Store in Redis
         │       │
         └───┬───┘
             ↓
       Return weather data
```

## 💾 Redis Caching

The application checks Redis before making a request to the weather API.

If the requested city's weather data is already cached:

```text
User Request
     ↓
Redis
     ↓
Cache Hit
     ↓
Return cached data
```

If the data is not available:

```text
User Request
     ↓
Redis
     ↓
Cache Miss
     ↓
Weather API
     ↓
Store result in Redis
     ↓
Return weather data
```

This helps reduce unnecessary API requests and improves response time for repeated requests.

## 📁 Project Structure

```text
WeatherPulse/
├── src/
│   └── ...
├── pom.xml
└── README.md
```

## ⚙️ Requirements

Before running the project, make sure you have:

- Java installed
- Maven installed
- Redis running locally
- Internet connection for API requests

## ▶️ Running the Project

1. Clone the repository.

```bash
git clone <repository-url>
```

2. Open the project in IntelliJ IDEA or another Java IDE.

3. Make sure Redis is running.

4. Build the Maven project.

```bash
mvn clean install
```

5. Run the Java application.



## Contact me
For any query(s) & discussion, kindly free to drop me a message in following email_id.

- Name: Mizan Ali (Owner)
- email_id: mak8083000@gmail.com

