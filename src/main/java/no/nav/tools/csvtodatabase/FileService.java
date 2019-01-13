package no.nav.tools.csvtodatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

public class FileService {

    private final Properties configuration;
    private DatabaseService databaseService;

    public FileService(Properties configuration) {
        this.configuration = configuration;
        databaseService = new DatabaseService(this.configuration);
    }

    public void readAndStore() {
        try {
            databaseService.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (
            BufferedReader reader = Files.newBufferedReader(Paths.get(configuration.getProperty("csv-file")))) {
                reader.lines().forEach(i -> {
                    String[] row = i.split(",");
                    databaseService.insert(row);
            });

            databaseService.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DatabaseService.disconnect();
        }
    }
}
