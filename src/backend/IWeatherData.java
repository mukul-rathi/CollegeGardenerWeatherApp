package backend;
/*
This interface provides a set of methods the design team will call to set and get attributes about a particular
weather data point.
 */
public interface IWeatherData {
    /*
    Set internal state (this is done in settings of app)
     */
    void setLocation(String location); //using String for now, until suitable input format identified
    void setTempScale(boolean Celsius); //true if Celsius, false if Fahrenheit
    void setSpeedScale(boolean mph); //true if miles per hour, false if km per hour
    void setTimeOfData(String time); //using String for now, until suitable input format identified
    /*
    Getter methods
     */
    WeatherType getWeatherType();
    int getTemperature();
    double getChanceOfRain(); //returns value between 0 and 1
    double getWindSpeed();
    WindDirection getWindDirection(); //returns Compass Direction of Wind
    double getHumidity(); //returns value between 0 and 1

    String getSummary(); //used for 10 day forecast


}
