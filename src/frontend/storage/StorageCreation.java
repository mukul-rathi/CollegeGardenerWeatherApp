package frontend.storage;

import backend.WeatherType;
import frontend.AlertsContext;

import java.io.File;
import java.util.HashMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class StorageCreation {



    // just for testing do not run please
    public static void main(String[] args) {


        //Default setup of values if the file is not there
        ResourcesStorage resources = new ResourcesStorage();
        resources.setUserLocation("Cambridge");
        resources.setTempScale(true);
        resources.setSpeedScale(true);

        try {

            File file = new File("storage.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ResourcesStorage.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(resources, file);
            jaxbMarshaller.marshal(resources, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    protected ResourcesStorage setup() {


        //Default setup of values if the file is not there
        ResourcesStorage resources = new ResourcesStorage();
        resources.setUserLocation("Cambridge");
        resources.setTempScale(true);
        resources.setSpeedScale(true);
        resources.setLanguage("English");
        resources.setAlertable(new boolean[5]);
        resources.setPriority(new boolean[5]);

        try {

            File file = new File("storage.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ResourcesStorage.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(resources, file);
            jaxbMarshaller.marshal(resources, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return resources;

    }

    /**
     *
     * @param resources : this should be the updated resources object
     */
    protected void writeToStorage(ResourcesStorage resources){

        try {
            File file = new File("storage.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ResourcesStorage.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(resources, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }


}
