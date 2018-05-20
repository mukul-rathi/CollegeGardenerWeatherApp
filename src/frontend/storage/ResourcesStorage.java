package frontend.storage;


import backend.WeatherType;
import frontend.AlertsContext;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

@XmlRootElement
public class ResourcesStorage {
    int id;

    // attributes from settings page
    private String userLocation;  // the location for the API
    private boolean tempScale; // true is centigrade, false is fahrenheit
    private boolean speedScale; // true is mph, false is kph
    private String language; // the language the user wants
    private boolean[] alertable;
    private boolean[] priority;

    // Weather type used for alerts
    final WeatherType[] alertTypes = new WeatherType[]{WeatherType.RAIN, WeatherType.SLEET, WeatherType.SNOW,
            WeatherType.WIND, WeatherType.FOG};


    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }


    public String getUserLocation() {
        return userLocation;
    }

    @XmlElement
    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public boolean isTempScale() {
        return tempScale;
    }

    @XmlElement
    public void setTempScale(boolean tempScale) {
        this.tempScale = tempScale;
    }

    public boolean isSpeedScale() {
        return speedScale;
    }

    @XmlElement
    public void setSpeedScale(boolean speedScale) {
        this.speedScale = speedScale;
    }

    public String getLanguage() {
        return language;
    }

    @XmlElement
    public void setLanguage(String language) {
        this.language = language;
    }

    /*
    public AlertsContext getAlerts() {

        HashMap<WeatherType, Boolean> altertable = new HashMap<>();
        HashMap<WeatherType, Boolean> priority = new HashMap<>();

        for(int i = 0; i < getPriority().length; i++){
            altertable.put(alertTypes[i], getAlertable()[i]);
            priority.put(alertTypes[i], getPriority()[i]);
        }

        AlertsContext alerts = new AlertsContext(altertable,priority);

        return alerts;
    }
    */


    public void setAlerts(AlertsContext alerts) {
        HashMap<WeatherType, Boolean> userAlertable = alerts.getAltertable();
        HashMap<WeatherType, Boolean> userPriority = alerts.getPriority();
        //Setup arrays with the correct values
        for(int i = 0; i < alertTypes.length; i++){
            WeatherType w = alertTypes[i];
            alertable[i] = userAlertable.get(w);
            priority[i] = userPriority.get(w);
        }

    }


    public boolean[] getAlertable() {

        return alertable;
    }

    @XmlElement
    public void setAlertable(boolean[] alertable) {
        this.alertable = alertable;
    }


    public boolean[] getPriority() {
        return priority;
    }

    @XmlElement
    public void setPriority(boolean[] priority) {
        this.priority = priority;
    }
}
