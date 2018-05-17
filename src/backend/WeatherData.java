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
    private static FIOHourly hourly;
    private static FIODaily daily;
    private static FIOAlerts alerts;
    private static String address;

    public WeatherData() {

        //sets the units as SI - optional
        fio.setUnits(ForecastIO.UNITS_AUTO); // automatically select units based on geographic location

        //sets the language to English
        fio.setLang(ForecastIO.LANG_ENGLISH);

        setLocation("Cambridge"); // by default
    }


    private void updateWeather(){
        setLocation(address);
        currently = new FIOCurrently(fio);
        hourly = new FIOHourly(fio);
        daily = new FIODaily(fio);

    }

    @Override
    public void setLocation(String addr) {

        address = addr;

        String[] latLng = Geocoder.getCoords(address);
        try {
            fio.getForecast(latLng[0], latLng[1]);
        }
        catch (NullPointerException e){
            System.out.println("Couldn't process your request, here's the weather in Cambridge instead.");
            fio.getForecast("52.2053", "0.1218");
        }
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
        updateWeather();
        return StringToWeatherType(currently.get().getByKey("icon"));
    }

    @Override
    public List<WeatherType> get24HourWeatherType() {
        updateWeather();

        List<WeatherType> weatherType24h = new ArrayList<WeatherType>();

        for (int i=0; i<24; i++){
            weatherType24h.add(StringToWeatherType(hourly.getHour(i).getByKey("icon")));
        }

        return weatherType24h;
    }

    @Override
    public List<WeatherType> getDailyWeatherType() {
        updateWeather();

        List<WeatherType> res = new ArrayList<>();

        for (int i=1; i <= 7; i++){
            res.add(StringToWeatherType(daily.getDay(i).getByKey("icon")));
        }

        return res;
    }

    @Override
    public int getCurrentTemperature() {
        updateWeather();

        return (int)Double.parseDouble(currently.get().getByKey("temperature"));

    }

    @Override
        public List<Integer> get24HourTemperature() {
        updateWeather();


        List<Integer> temp24h = new ArrayList<Integer>();
        for(int i=0; i<24; i++){
          temp24h.add((int)Double.parseDouble(hourly.getHour(i).getByKey("temperature")));
        }
        return temp24h;
    }

    @Override
    public List<Integer> getDailyTemperature() {
        updateWeather();

        List<Integer> res = new ArrayList<>();

        for (int i=1; i <= 7; i++){
            res.add(Integer.parseInt(daily.getDay(i).getByKey("temperature")));
        }

        return res;

    }

    @Override
    public double getCurrentChanceOfRain() {
        updateWeather();

        return Double.parseDouble(currently.get().getByKey("precipProbability"));
    }

    @Override
    public List<Double> get24HourChanceOfRain() {
        updateWeather();


        List<Double> rain24h = new ArrayList<Double>();
        for(int i=0; i<24; i++){
            rain24h.add(Double.parseDouble(hourly.getHour(i).getByKey("precipProbability")));
        }
        return rain24h;
    }

    @Override
    public List<Double> getDailyChanceOfRain() {
        updateWeather();

        List<Double> res = new ArrayList<>();

        for (int i=1; i <= 7; i++){
            res.add(Double.parseDouble(daily.getDay(i).getByKey("precipProbability")));
        }

        return res;
    }

    @Override
    public double getCurrentWindSpeed() {
        updateWeather();

        return Double.parseDouble(currently.get().getByKey("windSpeed"));
    }


    @Override
    public List<Double> getDailyWindSpeed() {
        updateWeather();

        List<Double> res = new ArrayList<>();

        for (int i=1; i <= 7; i++){
            res.add(Double.parseDouble(daily.getDay(i).getByKey("windSpeed")));
        }

        return res;
    }



    @Override
    public List<Double> get24HourWindSpeed() {
        updateWeather();

        List<Double> wind24h = new ArrayList<Double>();
        for(int i=0; i<24; i++){
            wind24h.add(Double.parseDouble(hourly.getHour(i).getByKey("windSpeed")));
        }
        return wind24h;
    }

   

    @Override
    public WindDirection getCurrentWindDirection() {
        updateWeather();

        return computeWindDirection(Double.parseDouble(currently.get().getByKey("windDirection"))) ;
    }

    @Override
    public List<WindDirection> get24HourWindDirection() {
        updateWeather();

        List<WindDirection> windDirection24h = new ArrayList<WindDirection>();
        for(int i=0; i<24; i++){
            windDirection24h.add(computeWindDirection(Double.parseDouble(hourly.getHour(i).getByKey("windDirection"))));
        }
        return windDirection24h;
    }

    @Override
    public double getCurrentHumidity() {
        updateWeather();

        return Double.parseDouble(currently.get().getByKey("humidity"));
    }

    @Override
    public List<Double> get24HourHumidity() {
        updateWeather();

        List<Double> humidity24h = new ArrayList<Double>();
        for(int i=0; i<24; i++){
            humidity24h.add(Double.parseDouble(hourly.getHour(i).getByKey("humidity")));
        }
        return humidity24h;
    }


    @Override
    public String getCurrentSummary() {
        updateWeather();

        return currently.get().getByKey("summary");
    }

    @Override
    public List<String> get24HourSummary() {
        updateWeather();

        List<String> summary24h = new ArrayList<String>();
        for(int i=0; i<24; i++){
            summary24h.add(hourly.getHour(i).getByKey("summary"));
        }
        return summary24h;
    }

    @Override
    public List<String> getDailySummary() {

        updateWeather();

        List<String> res = new ArrayList<>();

        for (int i=1; i <= 7; i++){
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
        updateWeather();

        List<WindDirection> res = new ArrayList<WindDirection>();

        for (int i=1; i <= 7; i++){
            res.add(computeWindDirection(Double.parseDouble(daily.getDay(i).getByKey("windDirection"))));
        }

        return res;
    }


    @Override
    public List<Double> getDailyHumidity() {
        updateWeather();

        List<Double> res = new ArrayList<>();

        for (int i=1; i <= 7; i++){
            res.add(Double.parseDouble(daily.getDay(i).getByKey("humidity")));
        }

        return res;
    }

    @Override
    public String getAlerts(){

        alerts = new FIOAlerts(fio);
        //Check if there are alerts
        if(alerts.NumberOfAlerts() <= 0){
            return "No alerts for this location.";
        }
        //if there are alerts, print them.
        else {
            String res = "";

            System.out.println("Alerts");
            for(int i=0; i<alerts.NumberOfAlerts(); i++)
                res += alerts.getAlert(i) + " ";

            return res;
        }
    }


    public static void main(String[] args) {
        WeatherData obj = new WeatherData();
    }
}
