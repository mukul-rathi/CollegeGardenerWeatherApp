package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Geocoder {

    private static String[] coords(String url) throws IOException {

        String[] res = new String[2];

        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setInstanceFollowRedirects(false);
        con.connect();
        con.getInputStream();

        if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
            String redirectUrl = con.getHeaderField("Location");
            return coords(redirectUrl);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            // if it contains this String, then the search has found at least one location corresponding to the address
            if (inputLine.contains("latLng")){

                res[0] = res[1] = "";

                int index = inputLine.indexOf("latLng");

                // add 15 to get to the start of the number representing the latitude
                index += 15;

                // parse the number
                while (('0' <= inputLine.charAt(index) && inputLine.charAt(index) <= '9') || inputLine.charAt(index) == '.' || inputLine.charAt(index) == '-'){
                    res[0] += inputLine.charAt(index++);
                }

                // add 7 to get to the start of the number representing the longitude
                index += 7;

                //parse the number
                while (('0' <= inputLine.charAt(index) && inputLine.charAt(index) <= '9') || inputLine.charAt(index) == '.' || inputLine.charAt(index) == '-'){
                    res[1] += inputLine.charAt(index++);
                }

                break;
            }
        }

        in.close();

        return res;
    }

    public static String[] getCoords(String address){
        String query = "http://open.mapquestapi.com/geocoding/v1/address?key=yZsrHlePEA3ystEsVq6ySkyjci2JdAAG&location=";

        //String regex = "\\s[,]*\\s*";

        //Split the address by whitespaces
        String regex = "\\s+";
        String[] querySplit = address.split(regex);

        //concatenate the link and the address to get the final URL
        for (String token : querySplit){
            query += token + "%20";
        }

        try {
            return coords(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
