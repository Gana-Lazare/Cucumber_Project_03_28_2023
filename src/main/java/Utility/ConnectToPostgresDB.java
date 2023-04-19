package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectToPostgresDB {

//First Load the properties from db properties file and read that
    public  Connection connection =null;
    public  Properties properties;
    public  Statement statement = null;
    public  PreparedStatement ps = null;
    public  ResultSet resultSet = null;


    public   Properties loadProperties()  {
        properties = new Properties();
        try {
            File file = new File(System.getProperty("user.dir")+"\\src\\main\\java\\Utility\\config.properties");
            FileInputStream fis = new FileInputStream(file);
            properties.load(fis);
        }
       catch (Exception e){}

        return properties;
    }



    public Connection connecttoPostgresDb() throws ClassNotFoundException, SQLException, IOException {
        Properties properties = loadProperties();
        String driverClass= properties.getProperty("POSTGRESQLJDBC.driver");
        String url = properties.getProperty("POSTGRESQJDBC.url");
        String user = properties.getProperty("POSTGRESQJDBC.userName");
        String password = properties.getProperty("POSTGRESQJDBC.password");

        Class.forName(driverClass);

        connection = DriverManager.getConnection(url,user,password);
        if(connection!=null) System.out.println("Connection to POSTGRES DB SUCCESSFULL");
        else System.out.println("COnnection Not Established");
        return connection;
    }



    public  List<String> readFromDB(String tableName, String columnName) {
        List<String> data = new ArrayList<String>();
        try{
            connection =connecttoPostgresDb();
            //ps = connection.prepareStatement("select "+columnName+" from "+tableName);
            Statement statement = connection.createStatement();
            System.out.println("statement vlue : " + statement.toString());
             resultSet = statement.executeQuery("select "+columnName+" from "+tableName);
            System.out.println("resultset vlue : " + resultSet.toString());
            //resultSet = ps.executeQuery();
           // System.out.println("result et next :" + resultSet.next());

            data = getResultSetData(resultSet, columnName);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    return data;
    }
    private List<String> getResultSetData(ResultSet resultSet, String columnName) throws SQLException {
        List<String> dataList = new ArrayList();

        while(resultSet.next()){
            String itemName = resultSet.getString(columnName);
            System.out.println("column name : "+ itemName);
            dataList.add(itemName);
        }
        return dataList;
    }




}
