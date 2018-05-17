package backend;

import com.github.dvdme.ForecastIOLib.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherData implements IWeatherData {

    // Data is initialized and fetched by the ForecastIO class
  
    //these are protected so that they can be accessed by the Unit Test class
    protected static ForecastIO fio = new ForecastIO("ef9f0749b95d6503c14c61bb45e8cb41");
    protected static FIOCurrently currently; // to be instantiated
    protected static FIOMinutely minutely;
    protected static FIOHourly hourly;
    protected static FIODaily daily;
    protected static FIOAlerts alerts;
    protected static String address;


    public WeatherData() {

        //sets the units as SI - optional
        fio.setUnits(ForecastIO.UNITS_AUTO); // automatically select units based on geographic location

        //sets the language to English
        fio.setLang(ForecastIO.LANG_ENGLISH);

        setLocation("Cambridge"); // by default
    }


    protected void updateWeather(){
        setLocation(address);
        currently = new FIOCurrently(fio);
        hourly = new FIOHourly(fio);
        daily = new FIODaily(fio);

    }

    /*
        Separate the Data request calls from the other code
     */
    protected String getCurrentData(String str){
        updateWeather();

        return currently.get().getByKey(str);
    }
    protected List<String> getHourlyData(String str){
        updateWeather();

        List<String> data24hr = new ArrayList<String>();
        for(int i=0; i<24;i++) {
            data24hr.add(hourly.getHour(i).getByKey(str));
        }
        return data24hr;
    }
    protected List<String> getWeeklyData(String str){
        updateWeather();

        List<String> data7day = new ArrayList<String>();
        for(int i=1; i<=7;i++) {
            data7day.add(daily.getDay(i).getByKey(str));
        }
        return data7day;
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
        switch (getCurrentData("icon")){
            case "CLEAR_DAY": return WeatherType.CLEAR_DAY;
            case "CLEAR_NIGHT": return WeatherType.CLEAR_NIGHT;
            case "PARTLY_CLOUDY_DAY": return WeatherType.PARTLY_CLOUDY_DAY;
            case "PARTLY_CLOUDY_NIGHT": return WeatherType.PARTLY_CLOUDY_NIGHT;
            case "CLOUDY": return WeatherType.CLOUDY;
            case "RAIN": return WeatherType.RAIN;
            case "SLEET": return WeatherType.SLEET;
            case "SNOW": return WeatherType.SNOW;
            case "WIND": return WeatherType.WIND;
            case "FOG": return WeatherType.FOG;
        }

        return null;
    }

    @Override
    public WeatherType getCurrentWeatherType() {

        return StringToWeatherType(getCurrentData("icon"));
    }

    @Override
    public List<WeatherType> get24HourWeatherType() {
        updateWeather();

        List<WeatherType> weatherType24h = new ArrayList<WeatherType>();

        for (String hourData : getHourlyData("icon")){
            weatherType24h.add(StringToWeatherType(hourData));
        }

        return weatherType24h;
    }

    @Override
    public List<WeatherType> getDailyWeatherType() {
        updateWeather();

        List<WeatherType> res = new ArrayList<>();

        for (String dailyData : getWeeklyData("icon")){
            res.add(StringToWeatherType(dailyData));
        }

        return res;
    }

    @Override
    public int getCurrentTemperature() {

        return (int) Double.parseDouble(getCurrentData("temperature"));

    }

    @Override
        public List<Integer> get24HourTemperature() {



        List<Integer> temp24h = new ArrayList<Integer>();
        for(String hourData : getHourlyData("temperature")){
          temp24h.add((int)Double.parseDouble(hourData));
        }
        return temp24h;
    }

    @Override
    public List<Integer> getDailyTemperature() {


        List<Integer> res = new ArrayList<>();

        for (String dailyData : getWeeklyData("temperature")){
            res.add(Integer.parseInt(dailyData));
        }

        return res;

    }

    @Override
    public double getCurrentChanceOfRain() {


        return Double.parseDouble(getCurrentData("precipProbability"));
    }

    @Override
    public List<Double> get24HourChanceOfRain() {



        List<Double> rain24h = new ArrayList<Double>();
        for(String hourData : getHourlyData("precipProbability")){
            rain24h.add(Double.parseDouble(hourData));
        }
        return rain24h;
    }

    @Override
    public List<Double> getDailyChanceOfRain() {


        List<Double> res = new ArrayList<>();

        for (String dailyData : getWeeklyData("precipProbability")){
            res.add(Double.parseDouble(dailyData));
        }

        return res;
    }

    @Override
    public double getCurrentWindSpeed() {


        return Double.parseDouble(getCurrentData("windSpeed"));
    }


    @Override
    public List<Double> get24HourWindSpeed() {


        List<Double> wind24h = new ArrayList<Double>();
        for(String hourData : getHourlyData("windSpeed")){
            wind24h.add(Double.parseDouble(hourData));
        }
        return wind24h;
    }


    @Override
    public List<Double> getDailyWindSpeed() {


        List<Double> res = new ArrayList<>();

        for (String dailyData : getWeeklyData("windSpeed")){
            res.add(Double.parseDouble(dailyData));
        }

        return res;
    }




   

    @Override
    public WindDirection getCurrentWindDirection() {


        return computeWindDirection(Double.parseDouble(getCurrentData("windDirection"))) ;
    }

    @Override
    public List<WindDirection> get24HourWindDirection() {


        List<WindDirection> windDirection24h = new ArrayList<WindDirection>();
        for(String hourData : getHourlyData("windDirection")){
            windDirection24h.add(computeWindDirection(Double.parseDouble(hourData)));
        }
        return windDirection24h;
    }

    @Override
    public double getCurrentHumidity() {


        return Double.parseDouble(getCurrentData("humidity"));
    }

    @Override
    public List<Double> get24HourHumidity() {


        List<Double> humidity24h = new ArrayList<Double>();
        for(String hourData : getHourlyData("humidity")){
            humidity24h.add(Double.parseDouble(hourData));
        }
        return humidity24h;
    }


    @Override
    public String getCurrentSummary() {


        return getCurrentData("summary");
    }

    @Override
    public List<String> get24HourSummary() {
        return getHourlyData("summary");
    }

    @Override
    public List<String> getDailySummary() {

       return getWeeklyData("summary");
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

        for (String dailyData : getWeeklyData("windDirection")){
            res.add(computeWindDirection(Double.parseDouble(dailyData)));
        }

        return res;
    }


    @Override
    public List<Double> getDailyHumidity() {


        List<Double> res = new ArrayList<>();

        for (String dailyData : getWeeklyData("humidity")){
            res.add(Double.parseDouble(dailyData));
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
