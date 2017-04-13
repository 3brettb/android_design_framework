/*
 * Created by Brett Brist April 2017
 * https://github.com/3brettb/android_design_framework
 */

/*
    This is a mature interface. Do not alter the contents of this interface.
 */

package domain.appname.callbacks;

import domain.appname.facades.http.HttpResponse;

public interface OnRequestComplete {
    void onComplete(HttpResponse response);
}
