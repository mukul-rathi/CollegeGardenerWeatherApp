package frontend;

import backend.WeatherType;

import java.util.ArrayList;
import java.util.HashMap;

public class AlertsContext {

    private HashMap<WeatherType, Boolean> altertable;
    private HashMap<WeatherType, Boolean> priority;

    public AlertsContext(HashMap<WeatherType, Boolean> altertable, HashMap<WeatherType, Boolean> priority){
        this.altertable = altertable;
        this.priority = priority;
    }

}
