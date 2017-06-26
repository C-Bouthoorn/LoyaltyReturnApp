package tk.cbouthoorn.loyalty.loyaltyreturn;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Represents an asynchronous registration task
 */
public class UserRegisterTask extends AsyncTask<Void, Void, JSONObject> {
    @SuppressLint("StaticFieldLeak")
    private Context registerContext;
    private final String email;
    private final String password;

    UserRegisterTask(Context registerContext, String email, String password) {
        this.registerContext = registerContext;
        this.email = email;
        this.password = password;
    }

    @Override
    protected JSONObject doInBackground(Void... _void) {
        try {
            String baseURL = App.getContext().getString(R.string.base_url);

            JSONObject params = new JSONObject();
            params.put("username", this.email);
            params.put("password", this.password);

            RequestFuture<JSONObject> future = RequestFuture.newFuture();

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    baseURL + "/register",
                    params,
                    future,
                    future
            );

            App.getRequestQueue().add(request);

            return future.get(10, TimeUnit.SECONDS);

        } catch(JSONException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(final JSONObject data) {
        try {
            boolean ok = data.getBoolean("ok");


            if ( ok ) {
                Log.i(App.LOG_TAG, "Registered successfully!");
                Intent intent = new Intent(registerContext, MainActivity.class);

                SharedPreferences sharedPreferences = registerContext.getSharedPreferences(App.PRIVATE_PREFS, Context.MODE_PRIVATE);

                // Save username
                sharedPreferences
                        .edit()
                        .putBoolean(App.LOGIN_OK, true)
                        .putString(App.LOGIN_USERNAME, this.email)
                        .apply();

                registerContext.startActivity(intent);

            } else {
                String error = data.getJSONObject("err").getString("message");
                Log.e(App.LOG_TAG, "Failed to register: " + error);

                new AlertBuilder(registerContext).errorAlert("Failed to register", error);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}