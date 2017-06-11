import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import config.ConfigurationManager;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class XdrLocEngine {
    private File mDoneFolder;

    public static void main(String[] args) throws IOException {
        XdrLocEngine xdrLocEngine = new XdrLocEngine();
        xdrLocEngine.run();
    }

    private void run() throws IOException {
        File folder = new File(ConfigurationManager.getInstance().filesDir);
        mDoneFolder = new File(ConfigurationManager.getInstance().filesDir + "done");

        if (!mDoneFolder.exists()) {
            if (mDoneFolder.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }

        while (true) {
            File[] listOfFiles = folder.listFiles();
            for (File item : listOfFiles) {
                if (!item.isDirectory()) {
                    readLocationsFile(item);
                    File dest = new File(folder.getPath() + item.getName());
                    FileUtils.copyDirectory(item, dest);
                    FileUtils.forceDelete(item);
                }
            }
        }
    }

    private boolean readLocationsFile(File file) {
        boolean result = true;

        BufferedReader br = null;
        FileReader fr = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String sCurrentLine;

            br = new BufferedReader(new FileReader(file));

            while ((sCurrentLine = br.readLine()) != null) {
                stringBuilder.append(sCurrentLine);
            }
            getCellIdFromFileData(stringBuilder.toString());
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (Exception ex) {
                result = false;
                ex.printStackTrace();
            }
        }

        return result;
    }

    private boolean getCellIdFromFileData(String data) {
        boolean result = true;
        String[] lines = data.split(";");
        for (String item : lines) {
            String[] drs = item.split(",");
            try {
                HttpResponse<String> response = Unirest.post("https://us1.unwiredlabs.com/v2/process.php")
                        .body("{\"token\": \"95c59fe063cbaa\",\"radio\": \"gsm\",\"mcc\": " + drs[4] + ",\"mnc\": " + drs[5] + ",\"cells\": [{\"lac\": " + drs[3] + ",\"cid\": " + drs[2] + "}],\"address\": 1}")
                        .asString();
                System.out.println(response.getBody().toString());
                result = insertDB(response.getBody());
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            }
        }

        return result;
    }

    private boolean insertDB(String response) {
        boolean result = true;



        return result;
    }

}
