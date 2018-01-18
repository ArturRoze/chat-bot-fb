package hello.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class HttpSender {

    public void sendPost(String url, String body) throws Exception {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setDoOutput(true);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
        bw.write(body);
        bw.flush();
        bw.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        InputStream is;

        if (con.getResponseCode() < HttpsURLConnection.HTTP_BAD_REQUEST){
            is = con.getInputStream();
        } else {
            is = con.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(is));

        String inputLine;

        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
    }

    public HttpAnswer sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setDoOutput(true);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        InputStream is;

        if (con.getResponseCode() < HttpsURLConnection.HTTP_BAD_REQUEST){
            is = con.getInputStream();
        } else {
            is = con.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(is));

        String inputLine;

        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
        return (new HttpAnswer(responseCode, response.toString()));
    }
}
