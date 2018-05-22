package backend;
import javafx.scene.image.Image;

import java.io.File;

public enum WeatherType {
    CLEAR_DAY, CLEAR_NIGHT, PARTLY_CLOUDY_DAY, PARTLY_CLOUDY_NIGHT, CLOUDY, RAIN, SLEET, SNOW, WIND, FOG;
    public static Image geticon(WeatherType weather){

        //default to cloudy
        File file = new File("src/frontend/icons/weather-fog.png");

        //select path to weather icon based on the current weather
        switch(weather){
            case CLEAR_DAY:
                file = new File("src/frontend/icons/weather-clear.png");
                break;
            case CLEAR_NIGHT:
                file = new File("src/frontend/icons/weather-clear-night.png");
                break;
            case PARTLY_CLOUDY_DAY:
                file = new File("src/frontend/icons/weather-few-clouds.png");
                break;
            case PARTLY_CLOUDY_NIGHT:
                file = new File("src/frontend/icons/weather-few-clouds-night.png");
                break;
            case CLOUDY:
                file = new File("src/frontend/icons/weather-clouds.png");
                break;
            case RAIN:
                file = new File("src/frontend/icons/weather-rain-day.png");
                break;
            case SLEET:
                file = new File("src/frontend/icons/weather-storm.png");
                break;
            case SNOW:
                file = new File("src/frontend/icons/weather-snow.png");
                break;
            case WIND:
                file = new File("src/frontend/icons/weather-wind.png");
                break;
            case FOG:
                file = new File("src/frontend/icons/weather-fog.png");
                break;
        }
        //build image relative to current weather and return it
        return new Image(file.toURI().toString());
    }

    public static String converttoString(WeatherType weather) {
        switch(weather){
            case CLEAR_DAY:
                return "Clear day";
            case CLEAR_NIGHT:
                return "Clear night";
            case PARTLY_CLOUDY_DAY:
                return "Partly cloudy day";
            case PARTLY_CLOUDY_NIGHT:
                return "Partly cloudy night";
            case CLOUDY:
                return "Cloudy";
            case RAIN:
                return "Rain";
            case SLEET:
                return "Sleet";
            case SNOW:
                return "Snow";
            case WIND:
                return "Wind";
            case FOG:
                return "Fog";
        }
        return "Not valid";
    }
}
