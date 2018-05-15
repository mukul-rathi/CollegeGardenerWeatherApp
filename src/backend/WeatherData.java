package backend;

import com.github.dvdme.ForecastIOLib.*;

import java.util.HashMap;
import java.util.Map;

public class WeatherData implements IWeatherData {

    // Data is initialized and fetched by the ForecastIO class
    private static ForecastIO fio = new ForecastIO("ef9f0749b95d6503c14c61bb45e8cb41");
    private static FIOCurrently currently; // to be instantiated
    private static FIOMinutely minutely;
    private static FIOHourly hourly;
    private static FIODaily daily;

    public WeatherData() {

        //sets the units as SI - optional
        fio.setUnits(ForecastIO.UNITS_AUTO); // automatically select units based on geographic location

        //excluded the minutely and hourly reports from the reply
        fio.setExcludeURL("hourly,minutely");

        //sets the language to English
        fio.setLang(ForecastIO.LANG_ENGLISH);


        /* !!!!!!!!!!!!!
          sets the latitude and longitude - not optional
          it will fail to get forecast if it is not set
          this method should be called after the options were set
        */
        setLocation("52.2053", "0.1218");

        currently = new FIOCurrently(fio);
        minutely = new FIOMinutely(fio);
        hourly = new FIOHourly(fio);
        daily = new FIODaily(fio);


        /* how to print all the weather data fields
        String[] fields = currently.get().getFieldsArray();
        for (String f : fields){ System.out.println(f); }
        */
    }

    @Override
    public void setLocation(String Latitude, String Longitude) {
        fio.getForecast(Latitude, Longitude);
    }

    @Override
    public void setScale(boolean Imperial) {
        if (!Imperial)
            fio.setUnits(ForecastIO.UNITS_SI);
        else
            fio.setUnits(ForecastIO.UNITS_CA);
    }

    @Override
    public void setTimeOfData(String time) {

    }

    @Override
    public WeatherType getWeatherType() {
        switch (currently.get().getByKey("icon")){
            case "CLEAR_DAY": return WeatherType.CLEAR_DAY;
            case "CLEAR_NIGHT": return WeatherType.CLEAR_NIGHT;
            case "PARTLY_CLOUDY_DAY": return WeatherType.PARTLY_CLOUDY_DAY;
            case "PARTLY_CLOUDY_NIGHT": return WeatherType.PARTLY_CLOUDY_NIGHT;
            case "CLOUDY": return WeatherType.CLOUDY;
            case "RAIN": return WeatherType.RAIN;
            case "SLEET": return WeatherType.SLEET;
            case "SdNOW": return WeatherType.SNOW;
            case "WIND": return WeatherType.WIND;
            case "FOG": return WeatherType.FOG;
        }

        return null;
    }

    @Override
    public int getTemperature() {
        return (int)Double.parseDouble(currently.get().getByKey("temperature"));
    }

    @Override
    public double getChanceOfRain() {
        return Double.parseDouble(currently.get().getByKey("precipProbability"));
    }

    @Override
    public double getWindSpeed() {
        return Double.parseDouble(currently.get().getByKey("windSpeed"));
    }

    @Override
    public WindDirection getWindDirection() {

        Double angle = Double.parseDouble(currently.get().getByKey("windSpeed"));

        if ((angle < 22.5 && angle >= 0) || (angle >= 337.5 && angle < 360))
            return WindDirection.EAST;

        if (angle >= 22.5 && angle < 67.5)
            return WindDirection.NORTH_EAST;

        if (angle >= 67.5 && angle < 112.5)
            return WindDirection.NORTH;

        if (angle >= 112.5 && angle < 157.5)
            return WindDirection.NORTH_WEST;

        if (angle >= 157.5 && angle < 202.5)
            return WindDirection.WEST;

        if (angle >= 202.5 && angle < 247.5)
            return WindDirection.SOUTH_WEST;

        if (angle >= 247.5 && angle < 292.5)
            return WindDirection.SOUTH;

        return WindDirection.SOUTH_EAST;
    }

    @Override
    public double getHumidity() {
        return Double.parseDouble(currently.get().getByKey("humidity"));
    }

    @Override
    public String getSummary() {
        return currently.get().getByKey("summary");
    }

    public static void main(String[] args) {
        WeatherData obj = new WeatherData();

        System.out.println(obj.getTemperature());
        System.out.println(obj.getWindSpeed());
    }
}
