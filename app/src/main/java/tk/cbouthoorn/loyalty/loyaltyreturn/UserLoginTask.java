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
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<Void, Void, JSONObject> {
    @SuppressLint("StaticFieldLeak")
    private Context loginContext;
    private final String email;
    private final String password;

    UserLoginTask(Context loginContext, String email, String password) {
        this.loginContext = loginContext;
        this.email = email;
        this.password = password;
    }

    @Override
    protected JSONObject doInBackground(Void... _) {
        try {
            String baseURL = App.getContext().getString(R.string.base_url);

            JSONObject params = new JSONObject();
            params.put("username", this.email);
            params.put("password", this.password);

            RequestFuture<JSONObject> future = RequestFuture.newFuture();

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    baseURL + "/login",
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
                Log.i(App.LOG_TAG, "Logged in successfully!");
                Intent intent = new Intent(loginContext, MainActivity.class);

                SharedPreferences sharedPreferences = loginContext.getSharedPreferences(App.PRIVATE_PREFS, Context.MODE_PRIVATE);

                // Save username
                sharedPreferences
                        .edit()
                        .putBoolean(App.LOGIN_OK, true)
                        .putString(App.LOGIN_USERNAME, this.email)
                        .apply();

                loginContext.startActivity(intent);

            } else {
                String error = data.getJSONObject("err").getString("message");
                Log.e(App.LOG_TAG, "Failed to log in: " + error);

                new AlertBuilder(loginContext).errorAlert("Failed to log in", error);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
