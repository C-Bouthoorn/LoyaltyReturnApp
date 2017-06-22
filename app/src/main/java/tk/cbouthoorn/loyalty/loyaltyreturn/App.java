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
    public static String LOGIN_OK = "tk.cbouthoorn.loyalty.loyaltyreturn.login_ok";


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
