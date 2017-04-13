/*
 * Created by Brett Brist April 2017
 * https://github.com/3brettb/android_design_framework
 */

/*
    This is a mature class. Do not alter the contents of this class.
    If its necessary to create different functionality, extend this class.
 */

package domain.appname.facades.http;

public class RequestParameter
{

    private String _key;

    private String _value;

    public RequestParameter(String key, String value){
        _key = key;
        _value = value;
    }

    public String getKey(){
        return _key;
    }

    public  String getValue(){
        return _value;
    }

}