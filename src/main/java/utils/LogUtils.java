package utils;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LogUtils {
    static boolean wasConfigured = false;
    final static Logger logger = Logger.getLogger(LogUtils.class);
    {
        configure();
    }

    public static void configure() {
        wasConfigured=true;
        System.out.println("Loading Properties");
        Properties p = new Properties();
        p = new Properties();
        try {
            InputStream fis ;
            File f = new File("resources/log4j.properties");
            if (f.exists()) {
                System.out.println("loading resources/log4j.properties");
                fis = new FileInputStream("resources/log4j.properties");
            } else {
                System.out.println("loading /resources/log4j.properties from jar");
                fis=LogUtils.class.getResourceAsStream("/log4j.properties");
            }
            p.load(fis);
            fis.close();
            PropertyConfigurator.configure(p);
            System.out.println("loaded " + "/resources/log4j.properties");
            LogManager.getRootLogger().setLevel(Level.ALL);
        } catch (IOException e) {
            System.out.println("failed to load log4j.properties");
            e.printStackTrace();
        }
    }

    public static Logger getLogger(Class cls) {
        if (!wasConfigured) {
            configure();
        }
        return Logger.getLogger(cls);
    }
}