package xdrloc_spooler;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.ConfigurationManager;
import datamodule.Location;
import org.json.JSONObject;
import utils.CSVUtils;
import utils.FileUtils;
import utils.KafkaInterface;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class Spooler {

    public static void main(String[] args) {
        Spooler spooler = new Spooler();
        spooler.execute();
    }

    private ArrayList<String[]> locDrArr = new ArrayList<String[]>();

    public void execute() {
        File folder = new File(ConfigurationManager.getInstance().locationsFilesDir + ConfigurationManager.getInstance().filesFolderName);
        System.out.println("Spooler Starting ........ " + ConfigurationManager.getInstance().locationsFilesDir + ConfigurationManager.getInstance().filesFolderName);

        while (true) {
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("File Found ::::::::" + listOfFiles[i].getName());
                    readLocationsFile(listOfFiles[i].getPath());
                    convert();
                    moveFile(listOfFiles[i].getPath());
                }
            }
        }
    }

    private boolean readLocationsFile(String file_path) {
        boolean result = true;

        String fileContent = FileUtils.readContentFile(file_path);
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(fileContent.split(";")));

        for (String item : arrayList) {
            String[] locArr = item.split(",");
            locDrArr.add(locArr);
        }

        return result;
    }

    private void moveFile(String file_path) {
        try {
            File file = new File(file_path);
            String newFilePath = ConfigurationManager.getInstance().locationsFilesDir + ConfigurationManager.getInstance().doneFolderName + "\\" + file.getName();
            if (file.renameTo(new File(newFilePath))) {
                System.out.println("File is moved successful!");
            } else {
                System.out.println("File is failed to move!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void convert() {
        try {
            String fileName = new SimpleDateFormat("yyyyMMddHHmm'-xdrloc.csv'").format(new Date());
            String csvFile = ConfigurationManager.getInstance().locationsFilesDir + ConfigurationManager.getInstance().csvFolderName + "\\" + fileName;
            FileWriter writer = new FileWriter(csvFile);

            for (String[] item : locDrArr) {
                CSVUtils.writeLine(writer, Arrays.asList(item));
            }

            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
