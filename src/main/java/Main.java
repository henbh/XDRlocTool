import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) {

        BufferedReader br = null;
        FileReader fr = null;

        try {

            try {

                fr = new FileReader(FILENAME);
                br = new BufferedReader(fr);

                String sCurrentLine;

                br = new BufferedReader(new FileReader(FILENAME));

                while ((sCurrentLine = br.readLine()) != null) {
                    System.out.println(sCurrentLine);
                }

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                try {

                    if (br != null)
                        br.close();

                    if (fr != null)
                        fr.close();

                } catch (IOException ex) {

                    ex.printStackTrace();

                }

            }

            HttpResponse<String> response = Unirest.post("https://us1.unwiredlabs.com/v2/process.php")
                    .body("{\"token\": \"95c59fe063cbaa\",\"radio\": \"gsm\",\"mcc\": 310,\"mnc\": 410,\"cells\": [{\"lac\": 7033,\"cid\": 17811}],\"address\": 1}")
                    .asString();
            System.out.println(response.getBody().toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
