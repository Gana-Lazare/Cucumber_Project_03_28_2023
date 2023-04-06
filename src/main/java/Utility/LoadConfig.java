package Utility;

public class LoadConfig extends ConnectToPostgresDB{

    public String loadConfiguration(String propertie){
        String pr=null;
        try {
            pr = loadProperties().getProperty(propertie);
        }
        catch (Exception e){}

        return pr;
    }

//    public static void main(String[] args) {
//        ConnectToPostgresDB connectToPostgresDB = new ConnectToPostgresDB() ;
//        System.out.println(connectToPostgresDB.loadProperties().getProperty("useCloudEnv"));
//    }
}
