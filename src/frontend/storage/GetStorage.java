package frontend.storage;

import java.io.File;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class GetStorage {


    //don't actually run this, just for testing
    public static void main(String[] args) {

        try {

            File file = new File("storage.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ResourcesStorage.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ResourcesStorage resources = (ResourcesStorage) jaxbUnmarshaller.unmarshal(file);
            System.out.println(resources);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }


    protected ResourcesStorage getData(){

        // This try block will try to load the file storage.xml
        try {

            File file = new File("storage.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ResourcesStorage.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ResourcesStorage resources = (ResourcesStorage) jaxbUnmarshaller.unmarshal(file);
            System.out.println(resources);

            //Return the resources object retrieved.
            return resources;

        } catch (javax.xml.bind.UnmarshalException e) {

            //In this case, the file is not there so it needs to be generated first
            return setupStorage();


        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static ResourcesStorage setupStorage(){
        StorageCreation storageCreator = new StorageCreation();

        return storageCreator.setup();
    }


}
