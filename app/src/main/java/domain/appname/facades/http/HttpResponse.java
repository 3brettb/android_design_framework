/*
 * Created by Brett Brist April 2017
 * https://github.com/3brettb/android_design_framework
 */

/*
    This is a mature class. Do not alter the contents of this class.
    If its necessary to create different functionality, extend this class.
 */

package domain.appname.facades.http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpResponse
{

    private String _response = "";

    private int _response_code = 0;

    public boolean success = false;

    public JSONObject content = null;

    public HttpResponse(HttpURLConnection connection){
        parseConnectionResponse(connection);
    }

    public String getContent(){
        return _response;
    }

    public JSONObject getJSON(){
        try {
            return new JSONObject(_response);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getCode(){
        return _response_code;
    }

    private void parseConnectionResponse(HttpURLConnection connection){
        _response = getResponse(connection);
        _response_code = getResponseCode(connection);
    }

    private String getResponse(HttpURLConnection connection){
        String response_string = "";
        InputStream in = null;
        boolean partial_success = false;
        try {
            in = new DataInputStream(connection.getInputStream());
            partial_success = true;
        } catch (IOException e) {
            in = new DataInputStream(connection.getErrorStream());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String inputLine;
        StringBuffer response = new StringBuffer();

        try {
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            response_string = response.toString();

            this.success = partial_success;

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.content = new JSONObject(response_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response_string;
    }

    private int getResponseCode(HttpURLConnection connection){
        int responseCode = 0;
        try {
            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode;
    }

}