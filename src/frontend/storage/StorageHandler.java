package frontend.storage;

import java.io.File;

public class StorageHandler {

    /**
     *
     * The method return Storage is for getting the object stored in memory
     *
     * The method write to storage is for writing your updated storage file to memory
     */

    //this is for testing only please do not run
    public static void main(String[] args){
        StorageHandler storageHandler = new StorageHandler();
        ResourcesStorage storage = storageHandler.returnStorage();

        //test, should read out Cambridge, false, false if no file is present at start
        System.out.println(storage.getUserLocation());
        System.out.println(storage.getPriority()[0]);
        System.out.println(storage.getPriority()[1]);

        storage.setUserLocation("London");
        boolean[] priority = storage.getPriority();
        priority[0] = false;
        priority[1] = true;
        storage.setPriority(priority);

        StorageCreation storageCreator = new StorageCreation();
        storageCreator.writeToStorage(storage);

        ResourcesStorage storage2 = storageHandler.returnStorage();

        //test, should read out London, false, true
        System.out.println(storage2.getUserLocation());
        System.out.println(storage.getPriority()[0]);
        System.out.println(storage.getPriority()[1]);
    }

    public ResourcesStorage returnStorage(){

        GetStorage storageGetter = new GetStorage();

        //this returns an object of type ResourcesStorage
        return storageGetter.getData();

    }

    public void writeToStorage(ResourcesStorage storage){

        // this gets a Storage creation object which then writes your data to memory
        // in the file storage.xml

        StorageCreation storageCreator = new StorageCreation();

        storageCreator.writeToStorage(storage);
    }



}
