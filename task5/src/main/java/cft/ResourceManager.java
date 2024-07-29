package cft;

public final class ResourceManager {
    private static int id;

    public static Resource createNewResource(){
        return new Resource(incAndGetId());
    }

    private ResourceManager(){
    }

    private static synchronized int incAndGetId(){
        ++id;
        return id;
    }
}
