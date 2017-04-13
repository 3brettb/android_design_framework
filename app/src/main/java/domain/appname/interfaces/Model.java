/*
 * Created by Brett Brist April 2017
 * https://github.com/3brettb/android_design_framework
 */

/*
    This is a mature interface. Do not alter the contents of this interface.
 */

package domain.appname.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

public interface Model {

    Model map(JSONObject reference) throws JSONException;

}
