package backend;

import com.github.dvdme.ForecastIOLib.*;
import frontend.storage.ResourcesStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import frontend.AlertsContext;
import frontend.storage.StorageHandler;

public class WeatherData implements IWeatherData {

    // Data is initialized and fetched by the ForecastIO class

    //these are protected so that they can be accessed by the Unit Test class
    //exceeded the number of requests for first 2 API keys
    //protected static ForecastIO fio = new ForecastIO("ef9f0749b95d6503c14c61bb45e8cb41");
    //protected static ForecastIO fio = new ForecastIO("796023b547a71e8f7cd533ecaf5a284d");

    protected static ForecastIO fio = new ForecastIO("e277033986917c85bafb81f5eed89e35");

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
    protected List<String> getDailyData(String str){
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

        // the getCurrentDatA("icon") will return strings in an undesirable format, such as "clear-day" instead of clear_day
        // so we need to remove the first and the last character, namely the quotation marks
        s = s.substring(1, s.length() - 1);

        switch (s){
            case "clear-day": return WeatherType.CLEAR_DAY;
            case "clear-night": return WeatherType.CLEAR_NIGHT;
            case "partly-cloudy-day": return WeatherType.PARTLY_CLOUDY_DAY;
            case "partly-cloudy-night": return WeatherType.PARTLY_CLOUDY_NIGHT;
            case "cloudy": return WeatherType.CLOUDY;
            case "rain": return WeatherType.RAIN;
            case "sleet": return WeatherType.SLEET;
            case "snow": return WeatherType.SNOW;
            case "wind": return WeatherType.WIND;
            case "fog": return WeatherType.FOG;
        }

        return null;
    }

    @Override
    public WeatherType getCurrentWeatherType() {


        return StringToWeatherType(getCurrentData("icon"));
    }

    @Override
    public List<WeatherType> get24HourWeatherType() {


        List<WeatherType> weatherType24h = new ArrayList<WeatherType>();

        for (String hourData : getHourlyData("icon")){
            weatherType24h.add(StringToWeatherType(hourData));
        }

        return weatherType24h;
    }

    @Override
    public List<WeatherType> getDailyWeatherType() {


        List<WeatherType> res = new ArrayList<>();

        for (String dailyData : getDailyData("icon")){
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
            temp24h.add((int) Double.parseDouble(hourData));
        }
        return temp24h;
    }

    @Override
    public List<Integer> getDailyTemperature() {


        List<Integer> temp7day = new ArrayList<>();

        for(String dailyData : getDailyData("temperatureHigh")){
            temp7day.add((int) Double.parseDouble(dailyData));
        }
        return temp7day;
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

        for (String dailyData : getDailyData("precipProbability")){
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

        for (String dailyData : getDailyData("windSpeed")){
            res.add(Double.parseDouble(dailyData));
        }

        return res;
    }

    @Override
    public WindDirection getCurrentWindDirection() {


        return computeWindDirection(Double.parseDouble(getCurrentData("windBearing"))) ;
    }

    @Override
    public List<WindDirection> get24HourWindDirection() {


        List<WindDirection> windDirection24h = new ArrayList<WindDirection>();
        for(String hourData : getHourlyData("windBearing")){
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


        return getDailyData("summary");
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

        for (String dailyData : getDailyData("windBearing")){
            res.add(computeWindDirection(Double.parseDouble(dailyData)));
        }

        return res;
    }


    @Override
    public List<Double> getDailyHumidity() {


        List<Double> res = new ArrayList<>();

        for (String dailyData : getDailyData("humidity")){
            res.add(Double.parseDouble(dailyData));
        }

        return res;
    }

    @Override
    public String getAlerts(){

        //read the weather in the next 24h, if there will be an alertable weather signal it
        List<WeatherType> weather24=this.get24HourWeatherType();

        StorageHandler handler = new StorageHandler(); // setup Handler
        ResourcesStorage storage = handler.returnStorage(); // read
        //weather type for wich we may want an alert
        WeatherType[] alertTypes = new WeatherType[]{WeatherType.RAIN, WeatherType.SLEET, WeatherType.SNOW,
                WeatherType.WIND, WeatherType.FOG};
        //alertable and priority for every weather type in alertTypes, set by user
        boolean[] alertable = storage.getAlertable();
        boolean[] priority = storage.getPriority();

        //if in the next 24h there will be one of that event put an alert
        int numberOfAlerts=0;
        //string representing the alerts concatenate with a comma
        String res = "";
        //iterate over weather types to see if there will be a corespondence in the next 24h
        for(int i=0;i<alertable.length;i++){
            for(WeatherType weather: weather24)
            {
                if((alertable[i] || priority[i]) && alertTypes[i]==weather){
                    res += WeatherType.converttoString(alertTypes[i]) + ",";
                    numberOfAlerts++;
                    break;
                }
            }
        }

        //if there is no alert return message, otherwise return string containing alert(s)
        if(numberOfAlerts == 0){
            return "No alerts for this location.";
        }else {
            return res;
        }
    }

    public boolean getPriority(){

        //read the weather in the next 24h, if there will be an alertable weather signal it
        List<WeatherType> weather24=this.get24HourWeatherType();

        StorageHandler handler = new StorageHandler(); // setup Handler
        ResourcesStorage storage = handler.returnStorage(); // read
        //weather type for wich we may want an alert
        WeatherType[] alertTypes = new WeatherType[]{WeatherType.RAIN, WeatherType.SLEET, WeatherType.SNOW,
                WeatherType.WIND, WeatherType.FOG};
        //alertable and priority for every weather type in alertTypes, set by user
        boolean[] alertable = storage.getAlertable();
        boolean[] priority = storage.getPriority();

        //if in the next 24h there will be one of that event put an alert
        int numberOfPriority=0;

        //iterate over weather types to see if there will be a corespondence in the next 24h
        for(int i=0;i<alertable.length;i++){
            for(WeatherType weather: weather24)
            {
                if(priority[i] && alertTypes[i]==weather){
                    numberOfPriority++;
                    break;
                }
            }
        }
        //if there is no alert with priority return false, otherwise return true
        if(numberOfPriority == 0){
            return false;
        }else {
            return true;
        }
    }



    public static void main(String[] args) {
        WeatherData obj = new WeatherData();
        System.out.println(obj.getCurrentWeatherType());
    }
}
