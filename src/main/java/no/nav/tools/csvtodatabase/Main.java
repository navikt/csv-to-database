package no.nav.tools.csvtodatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    /*
    * CsvToDatabase
    *
    * Start this application by providing a property file path as the command line argument. It will default to csvtodatabase.properties located on the classpath.
    * The application will read its configurations and immediately initiate the ETL process.
    *
    * Example: java -jar CsvToDatabase.jar "c:/csvtodatabase.properties"
    *
    * */

    public static void main(String[] args) throws IOException {
        final String configurationFile = (args.length == 0) ? Thread.currentThread().getContextClassLoader().getResource("").getPath() + "csvtodatabase.properties" : args[0];
        Properties configuration = new Properties();
        configuration.load(new FileInputStream(configurationFile));

        System.out.println("Reading " + configuration.getProperty("csv-file"));

        FileService fileService = new FileService(configuration);
        fileService.readAndStore();
    }

}
