import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import config.ConfigurationManager;
import datamodule.Location;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class XdrLocEngine {
    private File mDoneFolder;
    private ArrayList<Location> mLocationsHash = new ArrayList();

//    public static void main(String[] args) throws IOException {
//        XdrLocEngine xdrLocEngine = new XdrLocEngine();
//        xdrLocEngine.run();
//    }
//
//    private void run() throws IOException {
//        File folder = new File(ConfigurationManager.getInstance().filesDir);
//        String doneDirPath = ConfigurationManager.getInstance().filesDir + "done\\";
//        mDoneFolder = new File(doneDirPath);
//
//        if (!mDoneFolder.exists()) {
////            if (mDoneFolder.mkdir()) {
////                System.out.println("Directory is created!");
////            } else {
////                System.out.println("Failed to create directory!");
////            }
//        }
//
//        while (true) {
//            File[] listOfFiles = folder.listFiles();
//            for (File item : listOfFiles) {
//                if (!item.isDirectory() && item.getName().contains("xdr")) {
//                    readLocationsFile(item);
//                    File dest = new File(folder.getPath() + item.getName());
//                    FileUtils.copyDirectory(item, dest);
//                    FileUtils.forceDelete(item);
//                }
//            }
//        }
//    }
//
//    private boolean getCellIdFromFileData(String data) {
//        boolean result = true;
//        String[] lines = data.split(";");
//        for (String item : lines) {
//            String[] drs = item.split(",");
//            try {
//                HttpResponse<String> response = Unirest.post("https://us1.unwiredlabs.com/v2/process.php")
//                        .body("{\"token\": \"95c59fe063cbaa\",\"radio\": \"gsm\",\"mcc\": " + drs[3] + ",\"mnc\": " + drs[4] + ",\"cells\": [{\"lac\": " + drs[2] + ",\"cid\": " + drs[1] + "}],\"address\": 1}")
//                        .asString();
//                System.out.println(response.getBody().toString());
//                JSONObject jsonObj = new JSONObject(response.getBody().toString());
//                Location location = new Location(drs[0], jsonObj.get("lon").toString(), jsonObj.get("lat").toString(), jsonObj.get("address").toString(), drs[1], drs[7]);
//                mLocationsHash.add(location);
////                result = insertDB(response.getBody(), drs);
//            } catch (Exception e) {
//                e.printStackTrace();
//                result = false;
//            }
//        }
//
//        return result;
//    }
//
//    private boolean insertDB(String response, String[] data) throws SQLException {
//        boolean result = true;
//        JSONObject jsonObj = new JSONObject(response);
//        Connection con = DriverManager.getConnection("jdbc:cassandra://"+ConfigurationManager.getInstance().cassandraIp+":9160/loc");
//        PreparedStatement queryStatement = con.prepareStatement(
//                    "INSERT INTO loc.xdr_cellid_locations(" +
//                            "visit_time, " +
//                            "cell_id," +
//                            "msisdn," +
//                            " imei," +
//                            " imsi," +
//                            " latitude," +
//                            " longitude," +
//                            " mnc," +
//                            " mcc " +
//                            ") VALUES (?,?,?,?,?,?,?,?,?)");
//        queryStatement.setLong(1,Long.parseLong(data[0]));
//        queryStatement.setLong(2,Long.parseLong(data[1]));
//        queryStatement.setLong(3, Long.parseLong(data[7]));
//        queryStatement.setLong(4, Long.parseLong(data[5]));
//        queryStatement.setLong(5, Long.parseLong(data[6]));
//        queryStatement.setFloat(6, Float.parseFloat(jsonObj.get("lat").toString()));
//        queryStatement.setFloat(7, Float.parseFloat(jsonObj.get("lon").toString()));
//        queryStatement.setLong(8, Integer.parseInt(data[4]));
//        queryStatement.setLong(9, Integer.parseInt(data[3]));
//
////        mLocationHash.put(drs[2], new Pair<String,String>())
//
//        queryStatement.execute();
//        return result;
//    }
//
//    private void exportToKMLfile(){
//        Kml kml = new Kml();
//
//        Document document = kml.createAndSetDocument().withName("MyMarkers");
//
//        for (Location item : mLocationsHash) {
//            document.createAndAddPlacemark()
//                    .withName(item.CellId +"--"+item.Msisdn+"--"+item.Address).withOpen(Boolean.TRUE)
//                    .createAndSetPoint().addToCoordinates(Double.parseDouble(item.longtitude),Double.parseDouble(item.Latitude));
//        }
//
//        try {
//            kml.marshal(new File(ConfigurationManager.getInstance().filesDir+"HelloKml.kml"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

}
