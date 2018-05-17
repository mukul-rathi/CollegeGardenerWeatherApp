package backend.unittests;

import org.junit.Test;
import backend.WeatherData;

import static junit.framework.TestCase.assertTrue;

public class WeatherDataTest extends WeatherData{
    WeatherData testObj = new WeatherData();


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




}
