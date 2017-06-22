package tk.cbouthoorn.loyalty.loyaltyreturn;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static RequestQueue requestQueue;

    public static String LOG_TAG = "loyaltyreturn.log";

    // putExtra
    static String root = "tk.cbouthoorn.loyalty.loyaltyreturn";

    public static String LOGIN_OK = root + ".login_ok";
    public static String LOGIN_USERNAME = root + ".login_username";

    public static String PRIVATE_PREFS = "PRIVATE_PREFS";
    public static String SESSION_ID = PRIVATE_PREFS + ".session_id";


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        requestQueue = Volley.newRequestQueue(this);
    }

    public static Context getContext() {
        return context;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
