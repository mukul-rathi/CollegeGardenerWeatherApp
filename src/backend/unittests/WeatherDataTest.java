package backend.unittests;

import org.junit.Before;
import org.junit.Test;
import backend.WeatherData;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class WeatherDataTest extends WeatherData{
    private WeatherData testObj;

    @Before
    public void setUp(){
         testObj = new WeatherData();
    }


    @Test
    public void testHumidityInRangeZeroToOne(){
        assertTrue(testObj.getCurrentHumidity()>=0);
        assertTrue(testObj.getCurrentHumidity()<=1);
    }
    @Test
    public void testChanceOfRainInRangeZeroToOne(){
        assertTrue(testObj.getCurrentChanceOfRain()>=0);
        assertTrue(testObj.getCurrentChanceOfRain()<=1);
    }
    @Test
    public void test24HoursOfDataAvailable(){
        assertTrue(WeatherData.hourly.hours()>=24);
    }

    @Test
    public void testWeatherAPIAccessible(){
        assertNotNull(WeatherData.fio);
    }
    @Test
    public void testNotNullChanceOfRain(){
        assertNotNull(getCurrentChanceOfRain());
        assertNotNull(get24HourChanceOfRain());
        assertNotNull(getDailyChanceOfRain());

    }
    @Test
    public void testNotNullHumidity(){
        assertNotNull(getCurrentHumidity());
        assertNotNull(get24HourHumidity());
        assertNotNull(getDailyHumidity());

    }
    @Test
    public void testNotNullSummary(){
        assertNotNull(getCurrentSummary());
        assertNotNull(get24HourSummary());
        assertNotNull(getDailySummary());

    }


    @Test
    public void testNotNullTemperature(){
        assertNotNull(getCurrentTemperature());
        assertNotNull(get24HourTemperature());
        assertNotNull(getDailyTemperature());

    }
    @Test
    public void testNotNullWeatherType(){
        assertNotNull(getCurrentWeatherType());
        assertNotNull(get24HourWeatherType());
        assertNotNull(getDailyWeatherType());

    }
    @Test
    public void testNotNullWindDirection(){
        assertNotNull(getCurrentWindDirection());
        assertNotNull(get24HourWindDirection());
        assertNotNull(getDailyWindDirection());

    }
    @Test
    public void testNotNullWindSpeed(){
        assertNotNull(getCurrentWindSpeed());
        assertNotNull(get24HourWindSpeed());
        assertNotNull(getDailyWindSpeed());

    }



    






}
