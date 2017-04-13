/*
 * Created by Brett Brist April 2017
 * https://github.com/3brettb/android_design_framework
 */

/*
    This is a mature class. Do not alter the contents of this class.
    If its necessary to create different functionality, extend this class.
 */

package domain.appname.facades.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import domain.appname.callbacks.OnRequestComplete;
import domain.appname.preferences.ApplicationSettings;

public class RequestSender extends AsyncTask<String, Void, HttpResponse> {

    private HttpRequest request;

    private OnRequestComplete listener;

    private static final String USER_AGENT = ApplicationSettings.USER_AGENT;

    public RequestSender(HttpRequest request, OnRequestComplete listener){
        this.request = request;
        this.listener = listener;
    }

    @Override
    public void onPreExecute(){

    }

    @Override
    protected HttpResponse doInBackground(String... params){
        try{
            return send(this.request);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(HttpResponse response){
        super.onPostExecute(response);
        if(this.listener != null){
            this.listener.onComplete(response);
        }
    }

    private HttpResponse send(HttpRequest request){
        HttpURLConnection connection = null;
        HttpResponse response;
        switch (request.getMethod()){
            case "GET":
            case "Get":
            case "get":
            default:
                connection = connect(request.getMethod(), request.getURI()+"?"+formatParams(request.getParams()), request.getHeaders());
                response = send_get(connection);
                break;
            case "POST":
            case "Post":
            case "post":
                connection = connect(request.getMethod(), request.getURI(), request.getHeaders());
                response = send_post(connection, formatParams(request.getParams()));
                break;
        }
        return response;
    }

    private HttpResponse send_post(HttpURLConnection connection, String params){
        try{
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            HttpResponse response = new HttpResponse(connection);
            connection.disconnect();
            return response;
        } catch(UnknownHostException e){
            e.printStackTrace();
            Log.d("UnknownHostException", e.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
        connection.disconnect();
        return null;
    }

    private HttpResponse send_get(HttpURLConnection connection){
        HttpResponse response = new HttpResponse(connection);
        connection.disconnect();
        return response;
    }

    private HttpURLConnection connect(String method, String uri, RequestHeader[] headers){
        HttpURLConnection connection = null;
        try{
            URL url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);

            switch(method){
                case "GET":
                    connection.setDoOutput(false);
                    break;
                case "POST":
                    connection.setReadTimeout(30000);
                    connection.setDoOutput(true);
                    break;
            }

            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            connection.setConnectTimeout(45000);

            if (headers != null){
                for(RequestHeader h : headers){
                    connection.setRequestProperty(h.getKey(), h.getValue());
                }
            }

            connection.connect();

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch(UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return connection;
    }

    private String formatParams(RequestParameter[] params){
        String paramString = "";
        boolean first = true;
        for (RequestParameter p : params){
            paramString += (first) ? "" : "&";
            paramString += p.getKey() + "=" + p.getValue();
            first = false;
        }
        return paramString;
    }

}