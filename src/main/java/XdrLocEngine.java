import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import config.ConfigurationManager;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class XdrLocEngine {
    private File mDoneFolder;
    private HashMap mLocationHash = new HashMap();

    public static void main(String[] args) throws IOException {
        XdrLocEngine xdrLocEngine = new XdrLocEngine();
        xdrLocEngine.run();
    }

    private void run() throws IOException {
        File folder = new File(ConfigurationManager.getInstance().filesDir);
        String doneDirPath = ConfigurationManager.getInstance().filesDir + "done\\";
        mDoneFolder = new File(doneDirPath);

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
                        .body("{\"token\": \"95c59fe063cbaa\",\"radio\": \"gsm\",\"mcc\": " + drs[3] + ",\"mnc\": " + drs[4] + ",\"cells\": [{\"lac\": " + drs[2] + ",\"cid\": " + drs[1] + "}],\"address\": 1}")
                        .asString();
                System.out.println(response.getBody().toString());
                result = insertDB(response.getBody(), drs);
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            }
        }

        return result;
    }

    private boolean insertDB(String response, String[] data) throws SQLException {
        boolean result = true;
        JSONObject jsonObj = new JSONObject(response);
        Connection con = DriverManager.getConnection("jdbc:cassandra://"+ConfigurationManager.getInstance().cassandraIp+":9160/loc");
        PreparedStatement queryStatement = con.prepareStatement(
                    "INSERT INTO loc.xdr_cellid_locations(" +
                            "visit_time, " +
                            "cell_id," +
                            "msisdn," +
                            " imei," +
                            " imsi," +
                            " latitude," +
                            " longitude," +
                            " mnc," +
                            " mcc " +
                            ") VALUES (?,?,?,?,?,?,?,?,?)");
        queryStatement.setLong(1,Long.parseLong(data[0]));
        queryStatement.setLong(2,Long.parseLong(data[1]));
        queryStatement.setLong(3, Long.parseLong(data[7]));
        queryStatement.setLong(4, Long.parseLong(data[5]));
        queryStatement.setLong(5, Long.parseLong(data[6]));
        queryStatement.setFloat(6, Float.parseFloat(jsonObj.get("lat").toString()));
        queryStatement.setFloat(7, Float.parseFloat(jsonObj.get("lon").toString()));
        queryStatement.setLong(8, Integer.parseInt(data[4]));
        queryStatement.setLong(9, Integer.parseInt(data[3]));

//        mLocationHash.put(drs[2], new Pair<String,String>())

        queryStatement.execute();
        return result;
    }

}
