package backend;

import com.github.dvdme.ForecastIOLib.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private WeatherType StringToWeatherType(String s){
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
    public WeatherType getCurrentWeatherType() {
        return StringToWeatherType(currently.get().getByKey("icon"));
    }

    @Override
    public List<WeatherType> get24HourWeatherType() {
        return null;
    }

    @Override
    public List<WeatherType> getDailyWeatherType() {
        List<WeatherType> res = new ArrayList<>();

        for (int i=1; i <= 10; i++){
            res.add(StringToWeatherType(daily.getDay(i).getByKey("icon")));
        }

        return res;
    }

    @Override
    public int getCurrentTemperature() {
        return (int)Double.parseDouble(currently.get().getByKey("temperature"));

    }

    @Override
    public List<Integer> get24HourTemperature() {

        List<Integer> temp24h = new ArrayList<Integer>();
        for(int i=0; i<24; i++){
          temp24h.add((int)Double.parseDouble(hourly.getHour(i).getByKey("temperature")));
        }
        return temp24h;
    }

    @Override
    public List<Integer> getDailyTemperature() {
        List<Integer> res = new ArrayList<>();

        for (int i=1; i <= 10; i++){
            res.add(Integer.parseInt(daily.getDay(i).getByKey("temperature")));
        }

        return res;

    }

    @Override
    public double getCurrentChanceOfRain() {
        return Double.parseDouble(currently.get().getByKey("precipProbability"));
    }

    @Override
    public List<Double> get24HourChanceOfRain() {

        List<Double> rain24h = new ArrayList<Double>();
        for(int i=0; i<24; i++){
            rain24h.add(Double.parseDouble(hourly.getHour(i).getByKey("precipProbability")));
        }
        return rain24h;
    }

    @Override
    public List<Double> getDailyChanceOfRain() {
        List<Double> res = new ArrayList<>();

        for (int i=1; i <= 10; i++){
            res.add(Double.parseDouble(daily.getDay(i).getByKey("precipProbability")));
        }

        return res;
    }

    @Override
    public double getCurrentWindSpeed() {
        return Double.parseDouble(currently.get().getByKey("windSpeed"));
    }


    @Override
    public List<Double> getDailyWindSpeed() {
        List<Double> res = new ArrayList<>();

        for (int i=1; i <= 10; i++){
            res.add(Double.parseDouble(daily.getDay(i).getByKey("windSpeed")));
        }

        return res;
    }



    @Override
    public List<Double> get24HourWindSpeed() {
        List<Double> wind24h = new ArrayList<Double>();
        for(int i=0; i<24; i++){
            wind24h.add(Double.parseDouble(hourly.getHour(i).getByKey("windSpeed")));
        }
        return wind24h;
    }

   

    @Override
    public WindDirection getCurrentWindDirection() {
        return computeWindDirection(Double.parseDouble(currently.get().getByKey("windDirection"))) ;
    }

    @Override
    public List<WindDirection> get24HourWindDirection() {
        List<WindDirection> windDirection24h = new ArrayList<WindDirection>();
        for(int i=0; i<24; i++){
            windDirection24h.add(computeWindDirection(Double.parseDouble(hourly.getHour(i).getByKey("windDirection"))));
        }
        return windDirection24h;
    }

    @Override
    public double getCurrentHumidity() {
        return Double.parseDouble(currently.get().getByKey("humidity"));
    }

    @Override
    public List<Double> get24HourHumidity() {
        List<Double> humidity24h = new ArrayList<Double>();
        for(int i=0; i<24; i++){
            humidity24h.add(Double.parseDouble(hourly.getHour(i).getByKey("humidity")));
        }
        return humidity24h;
    }


    @Override
    public String getCurrentSummary() {
        return currently.get().getByKey("summary");
    }

    @Override
    public List<String> get24HourSummary() {
        List<String> summary24h = new ArrayList<String>();
        for(int i=0; i<24; i++){
            summary24h.add(hourly.getHour(i).getByKey("summary"));
        }
        return summary24h;
    }

    @Override
    public List<String> getDailySummary() {
        List<String> res = new ArrayList<>();

        for (int i=1; i <= 10; i++){
            res.add(daily.getDay(i).getByKey("summary"));
        }

        return res;
    }


    private WindDirection computeWindDirection(double angle) {
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
    public List<WindDirection> getDailyWindDirection() {
        List<WindDirection> res = new ArrayList<WindDirection>();

        for (int i=1; i <= 10; i++){
            res.add(computeWindDirection(Double.parseDouble(daily.getDay(i).getByKey("windDirection"))));
        }

        return res;
    }


    @Override
    public List<Double> getDailyHumidity() {
        List<Double> res = new ArrayList<>();

        for (int i=1; i <= 10; i++){
            res.add(Double.parseDouble(daily.getDay(i).getByKey("humidity")));
        }

        return res;
    }



    public static void main(String[] args) {
        WeatherData obj = new WeatherData();

    }
}
