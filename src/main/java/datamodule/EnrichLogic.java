package datamodule;

import com.fasterxml.jackson.databind.ObjectMapper;
import components.IgniteManager;
import config.ConfigurationManager;
import org.apache.ignite.IgniteCache;
import utils.CellApiRest;
import utils.Tuple;

public class EnrichLogic {

    public Location EnrichData(String data) {
        Location location = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            location = mapper.readValue(data, Location.class);

            IgniteCache loc_cachae = IgniteManager.getInstance().getOrCreateCache(ConfigurationManager.getInstance().cellsCache);

            if (loc_cachae != null && location != null) {
                if (loc_cachae.containsKey(location.mCellId)) {
                    Tuple cell_location = (Tuple) loc_cachae.get(location.mCellId);
                    location.mLatitude = Float.parseFloat(cell_location.latitude.toString());
                    location.mLongitude = Float.parseFloat(cell_location.longitude.toString());
                } else if (ConfigurationManager.getInstance().isCellApiEnabled) {
                    Tuple loc_tuple = CellApiRest.getCellLocationRest(
                            ConfigurationManager.getInstance().apiCellToken,
                            String.valueOf(location.mMCC),
                            String.valueOf(location.mMNC),
                            String.valueOf(location.mLac),
                            String.valueOf(location.mCellId));
                    loc_cachae.put(location.mCellId, loc_tuple);
                    location.mLatitude = Float.parseFloat(loc_tuple.latitude.toString());
                    location.mLongitude = Float.parseFloat(loc_tuple.longitude.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            location = null;
        }

        return location;
    }


//    private boolean insertDB(String response, String[] data) throws SQLException {
//        boolean result = true;
//        JSONObject jsonObj = new JSONObject(response);
//        Connection con = DriverManager.getConnection("jdbc:cassandra://" + ConfigurationManager.getInstance().cassandraIp + ":9160/loc");
//        PreparedStatement queryStatement = con.prepareStatement(
//                "INSERT INTO loc.xdr_cellid_locations(" +
//                        "visit_time, " +
//                        "cell_id," +
//                        "msisdn," +
//                        " imei," +
//                        " imsi," +
//                        " latitude," +
//                        " longitude," +
//                        " mnc," +
//                        " mcc " +
//                        ") VALUES (?,?,?,?,?,?,?,?,?)");
//        queryStatement.setLong(1, Long.parseLong(data[0]));
//        queryStatement.setLong(2, Long.parseLong(data[1]));
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

//    private void exportToKMLfile() {
//        Kml kml = new Kml();
//
//        Document document = kml.createAndSetDocument().withName("MyMarkers");
//
//        for (Location item : mLocationsHash) {
//            document.createAndAddPlacemark()
//                    .withName(item.CellId + "--" + item.Msisdn + "--" + item.Address).withOpen(Boolean.TRUE)
//                    .createAndSetPoint().addToCoordinates(Double.parseDouble(item.longtitude), Double.parseDouble(item.Latitude));
//        }
//
//        try {
//            kml.marshal(new File(ConfigurationManager.getInstance().filesDir + "HelloKml.kml"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
////    }
//    }
}
