package backend.unittests;

import org.junit.Test;
import backend.WeatherData;

import static junit.framework.TestCase.assertTrue;

public class WeatherDataTest {
    WeatherData testObj = new WeatherData();


    @Test
    public void testHumidity(){
        assertTrue(testObj.getCurrentHumidity()>0);
        assertTrue(testObj.getCurrentHumidity()>0);
    }


}
