# Introduction
WeatherApp - This application will return current weather data from OpenWeatherMap.org, based on a zipcode of a city chosen by the user.

# Source of Data
City detail using zipcode is read from http://smartystreets.com using API https://us-zipcode.api.smartystreets.com/lookup?auth-id={authid}&auth-token={tokenid}&zipcode={zipcode}
Weather data is read from http://openweathermap.org/ using API http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}
This API has used API keys and IDs personal to me. So I request everybody not to use or distribute unless otherwise permitted.
# How to run
This app has embedded tomcat server. In order to run this website execute below maven command
mvn spring-boot:run

It will start the embedded tomcat server on port 8080

# How to Use
- Access the website using http://localhost:8080/
- Enter a City or Zipcode
- Hit Submit button
- It will display Weather Summary Page
- Hit back button to go back to pervious page.

# Technologies used
- Spring Core
- Spring MVC
- RestTemplate
- Spring Boot
- Maven
- Html/thymeleaf
- Mockito
- Junit
- Embedded Tomcat Server
- MockMvc
- TDD
