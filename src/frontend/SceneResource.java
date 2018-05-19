package frontend;

import backend.WeatherType;

import java.util.HashMap;

public class SceneResource {


    /**
     * Put all data that needs to be transferred between scenes here
     */

    private String location;
    private boolean tempScale;
    private boolean speedScale;
    private AlertsContext alerts;
    private boolean alertsTab;
    private HashMap<WeatherType, Boolean> alertable;
    private HashMap<WeatherType, Boolean> priority;


    /**
     *
     * All the methods below should be getters and setters /
     * functions that represent data in the form that you want
     */

    protected String getLocation() {
        return location;
    }

    protected void setLocation(String location) {
        this.location = location;
    }

    protected boolean isTempScale() {
        return tempScale;
    }

    protected void setTempScale(boolean tempScale) {
        this.tempScale = tempScale;
    }

    public boolean isSpeedScale() {
        return speedScale;
    }

    public void setSpeedScale(boolean speedScale) {
        this.speedScale = speedScale;
    }

    public AlertsContext getAlerts() {
        return alerts;
    }

    public void setAlerts(AlertsContext alerts) {
        this.alerts = alerts;
    }

    public boolean isAlertsTab() {
        return alertsTab;
    }

    public void setAlertsTab(boolean alertsTab) {
        this.alertsTab = alertsTab;
    }

    public HashMap<WeatherType, Boolean> getAlertable() {
        return alertable;
    }

    public void setAlertable(HashMap<WeatherType, Boolean> alertable) {
        this.alertable = alertable;
    }

    public HashMap<WeatherType, Boolean> getPriority() {
        return priority;
    }

    public void setPriority(HashMap<WeatherType, Boolean> priority) {
        this.priority = priority;
    }
}
