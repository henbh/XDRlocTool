package config;

import java.io.*;
import java.util.Properties;

//Singleton class
public class ConfigurationManager {
    public String locationsFilesDir;
    public String filesFolderName;
    public String doneFolderName;
    public String csvFolderName;


    private static ConfigurationManager instance = null;

    public ConfigurationManager() throws IOException {
        initConfiguration();
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            try {
                instance = new ConfigurationManager();
            } catch (Exception e) {
                System.out.println("Exiting duo to absence of configuration file");
                System.exit(1);
            }
        }
        return instance;
    }

    private void initConfiguration() throws IOException {
        InputStream inputStream = null;

        try {
            Properties prop = new Properties();
            String propFileName = "resources/config.properties";

            InputStream fis;
            File f = new File(propFileName);
            if (f.exists()) {
                System.out.println("loading resources/config.properties");
                fis = new FileInputStream(propFileName);
            } else {
                System.out.println("loading resources/config.properties from jar");
                fis = ConfigurationManager.class.getResourceAsStream("/config.properties");
            }

            if (fis != null) {
                prop.load(fis);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // set properties values
            locationsFilesDir = prop.getProperty("locations_files_dir");
            filesFolderName = prop.getProperty("files_folder_name");
            doneFolderName = prop.getProperty("done_folder_name");
            csvFolderName = prop.getProperty("csv_folder_name");

            fis.close();
            System.out.println("loaded " + propFileName);
        } catch (Exception e) {
            //logger.error(e.getMessage());
        }
    }
}
