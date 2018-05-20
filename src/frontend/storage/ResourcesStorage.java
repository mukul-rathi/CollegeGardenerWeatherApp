package frontend.storage;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResourcesStorage {
    int id;

    // attributes from settings page
    private String userLocation;  // the location for the API
    private boolean tempScale; // true is centigrade, false is fahrenheit
    private boolean speedScale; // true is mph, false is kph
    private String language; // the language the user wants


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
}
