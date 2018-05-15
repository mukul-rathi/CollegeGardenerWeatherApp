package backend;

import java.util.List;

/*
This interface provides a set of methods the design team will call to set and get attributes about a particular
weather data point.
 */
public interface IWeatherData {
    /*
    Set internal state (this is done in settings of app)
     */
    void setLocation(String address); //using String for now, until suitable input format identified
    void setScale(boolean Imperial); //true if Celsius, false if Fahrenheit
    /*
    Getter methods
     */
    WeatherType getCurrentWeatherType();
    List<WeatherType> get24HourWeatherType();
    List<WeatherType> getDailyWeatherType();

    int getCurrentTemperature();
    List<Integer> get24HourTemperature();
    List<Integer> getDailyTemperature();

    double getCurrentChanceOfRain(); //returns value between 0 and 1
    List<Double> get24HourChanceOfRain();
    List<Double> getDailyChanceOfRain();

    double getCurrentWindSpeed(); //returns value between 0 and 1
    List<Double> get24HourWindSpeed();
    List<Double> getDailyWindSpeed();

    WindDirection getCurrentWindDirection(); //returns Compass Direction of Wind
    List<WindDirection> get24HourWindDirection();
    List<WindDirection> getDailyWindDirection();

    double getCurrentHumidity(); //returns value between 0 and 1
    List<Double> get24HourHumidity();
    List<Double> getDailyHumidity();

    String getCurrentSummary(); //used for 10 day forecast
    List<String> get24HourSummary();
    List<String> getDailySummary();

    void getAlerts();

}
