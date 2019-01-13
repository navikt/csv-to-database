# CSV To Database
This application will read a CSV file of your choice and write each row to any database.
It comes precompiled with the Oracle 12.2 driver - feel free to recompile it with a database driver of your choice. Just replace the Oracle driver dependency with the driver of your choice and run mvn clean install.

The application supports single-column CSVs and multi-column CSVs - just make sure you adjust the SQL from the configuration file accordingly.
In the SQL statement, the columns and question marks in VALUES _must match_ the columns of the CSV.

Do not add headers to your CSV as these will be inserted into your database like any other row in your file. The column delimiter of your CSV file _must be_ one comma (,) without pre/post-spacing.

## Requirements
- To run: Java JRE 1.8 or newer.
- To recompile: Maven and any database driver library you wish to use.

## How to use
### Step 1: Configure
The application will read from a properties file. You will find an empty properties file in the resources-folder which you can either edit or copy to any given path.
The configuration file _must have_ the following fields properly populated:

| Configuration name | Description | Example |
|--------------------|--------------------------------|---------|
| csv-file           | Full qualified path to the CSV-file you wish to store in a database. | C:/csvtodatabase/csvtodatabase.properties |
| batch-size         | Amount of rows you wish to commit per database transaction | 1000 |
| database-uri       | URI to your database, including port and service name (if relevant) | jdbc:oracle:thin:localhost:5121:service_name |
| database-username  | Your database username (must have INSERT privilege | myusername |
| database-password  | Your database user's password | secretpassword123 |
| sql                | The SQL you wish to use as a template for insertion | INSERT INTO database (column_one, column_2) VALUES (?, ?) |

### Step 2: Run the application
Run the application and specify the path to the configuration file as a command-line argument. 

Example: java -jar csvtodatabase.jar "c:/csvtodatabase/csvtodatabase.properties"

## Support requests, feedback and changes are welcome!
You are welcome to make changes to this code repository. Create a branch and provide a pull request - we'll pick them up and merge them to master in a forward-rolling fashion.

Do you need help or have any feedback? Feel free to open an issue in our GitHub repository or contact bjorn.frode.kvernstuen@nav.no.

## To-do list
1. Write tests
2. Automatically generate SQL statement based on new configuration entries like table-name and amount-of-rows
3. Improve error handling (e.g. null-checks/value checks on values from the configuration file)
4. Customize CSV column delimiter as a configuration parameter.