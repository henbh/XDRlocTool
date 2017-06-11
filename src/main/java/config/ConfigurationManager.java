package config;

import java.io.*;
import java.util.Properties;

//Singleton class
public class ConfigurationManager
{
    public String filesDir;
    public String cassandraIp;

    private static ConfigurationManager instance = null;

    public ConfigurationManager() throws IOException {
        initConfiguration();
    }

    public static ConfigurationManager getInstance() {
        if(instance == null) {
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

            InputStream fis ;
            File f = new File(propFileName);
            if (f.exists()) {
                System.out.println("loading resources/config.properties");
                fis = new FileInputStream(propFileName);
            } else {
                System.out.println("loading resources/config.properties from jar");
                fis = ConfigurationManager.class.getResourceAsStream("/config.properties");
            }

            if (fis != null)
            {
                prop.load(fis);
            }
            else
            {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // set properties values
            filesDir = prop.getProperty("files_dir");
            cassandraIp = prop.getProperty("cassandra_ip");

            fis.close();
            System.out.println("loaded " + propFileName);
        }
        catch (Exception e)
        {
            //logger.error(e.getMessage());
        }
    }
}
