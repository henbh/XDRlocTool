package utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;

public class CellApiRest {
    public static Tuple getCellLocationRest(String token, String mcc, String mnc, String lac, String cid) {
        Tuple result = null;
        try {
            HttpResponse<String> response = Unirest.post("https://us1.unwiredlabs.com/v2/process.php")
                    .body(String.format("{\"token\": \"%s\",\"radio\": \"gsm\",\"mcc\": %s,\"mnc\": %s,\"cells\": [{\"lac\": %s,\"cid\": %s}],\"address\": 1}", token, mcc, mnc, lac, cid))
                    .asString();
            System.out.println(response.getBody().toString());
            JSONObject jsonObj = new JSONObject(response.getBody().toString());

            result = new Tuple(jsonObj.get("lon").toString(), jsonObj.get("lat").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
