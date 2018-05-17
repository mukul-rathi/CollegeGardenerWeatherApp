package frontend;

public class SceneResource {


    /**
     * Put all data that needs to be transferred between scenes here
     */

    private String location;
    private boolean tempScale;
    private boolean speedScale;
    private AlertsContext alerts;


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
}
