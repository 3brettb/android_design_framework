/*
 * Created by Brett Brist April 2017
 * https://github.com/3brettb/android_design_framework
 */

/*
    This is a mature class. Do not alter the contents of this class.
    If its necessary to create different functionality, extend this class.
 */

package domain.appname.facades.http;

import java.util.Stack;

import domain.appname.clients.HttpClient;
import domain.appname.callbacks.OnRequestComplete;

public class HttpRequest
{
    private static final String _default_method = "GET";

    private String _method = "GET";

    private String _uri = "";

    private Stack<RequestHeader> _headers = new Stack<>();

    private Stack<RequestParameter> _params = new Stack<>();

    private static HttpClient http = HttpClient.getInstance();

    public HttpRequest(){}

    public HttpRequest(String uri){
        this(_default_method, uri);
    }

    public HttpRequest(String method, String uri){
        this(method, uri, null, null, null);
    }

    public HttpRequest(String uri, RequestHeader[] headers){
        this(_default_method, uri, headers, null, null);
    }

    public HttpRequest(String uri, RequestParameter[] params){
        this(_default_method, uri, null, params);
    }

    public HttpRequest(String method, String uri, RequestHeader[] headers){
        this(method, uri, headers, null, null);
    }

    public HttpRequest(String method, String uri, RequestParameter[] params){
        this(method, uri, null, params);
    }

    public HttpRequest(String uri, RequestHeader[] headers, RequestParameter[] params){
        this(_default_method, uri, headers, params);
    }

    public HttpRequest(String method, String uri, RequestHeader[] headers, RequestParameter[] params){
        _method = method;
        _uri = uri;
        mapParams(params);
        mapHeaders(headers);
    }

    public void setMethod(String method){
        _method = method;
    }

    public void setURI(String uri){
        _uri = uri;
    }

    public void addParam(RequestParameter param){
        _params.push(param);
    }

    public void addParam(String key, String value){
        addParam(new RequestParameter(key, value));
    }

    public void addHeader(RequestHeader header){
        _headers.push(header);
    }

    public void addHeader(String key, String value){
        addHeader(new RequestHeader(key, value));
    }

    public String getMethod(){
        return _method;
    }

    public String getURI(){
        return _uri;
    }

    public RequestHeader[] getHeaders(){
        RequestHeader[] list = new RequestHeader[_headers.size()];
        int i = 0;
        for(RequestHeader h : _headers){
            list[i] = h;
            i++;
        }
        return list;
    }

    public RequestParameter[] getParams(){
        RequestParameter[] list = new RequestParameter[_params.size()];
        int i = 0;
        for(RequestParameter p : _params){
            list[i] = p;
            i++;
        }
        return list;
    }

    public void send(OnRequestComplete listener){
        http.send(this, listener);
    }

    private void mapHeaders(RequestHeader[] headers){
        if(headers != null) {
            for (RequestHeader h : headers) {
                _headers.push(h);
            }
        }
    }

    private void mapParams(RequestParameter[] params) {
        if (params != null){
            for (RequestParameter p : params) {
                _params.push(p);
            }
        }
    }

}