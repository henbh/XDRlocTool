package watcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.ConfigurationManager;
import datamodule.Location;
import utils.FileUtils;
import utils.KafkaInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Spooler {

    public static void main(String[] args) {
        Spooler spooler = new Spooler();
        spooler.execute();
    }

    private ArrayList<String> locDrArr = new ArrayList<String>();

    public void execute() {
        File folder = new File(ConfigurationManager.getInstance().locationsFilesDir + ConfigurationManager.getInstance().filesFolderName);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File Found ::::::::" + listOfFiles[i].getName());
                readLocationsFile(listOfFiles[i].getPath());
                Send();
            }
        }
    }

    private boolean readLocationsFile(String file_path) {
        boolean result = true;
        ObjectMapper mapper = new ObjectMapper();
        String fileContent = FileUtils.readContentFile(file_path);
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(fileContent.split(";")));

        for (String item : arrayList) {
            String[] locArr = item.split(",");
            Location location = new Location(Integer.parseInt(locArr[1]),  Integer.parseInt(locArr[2]),Integer.parseInt(locArr[3]),Integer.parseInt(locArr[4]), Long.parseLong(locArr[0]),
                    locArr[5], locArr[6], locArr[7]);
            try {
                locDrArr.add(mapper.writeValueAsString(location));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private void Send(){
        for (String item : locDrArr) {
            KafkaInterface.SendKafkaRecordToTopic(item, "enrich-1", ConfigurationManager.getInstance().kafkaHost);
        }
    }

}
