package com.android.myfinalproject.apiServices;

import android.util.Log;

import com.android.myfinalproject.R;
import com.android.myfinalproject.app.G;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;


public class GsonRequest<T> extends Request<T> {

    private static final String TAG = "GsonRequest";

    Gson gson = new Gson();
    Type type;
    Response.Listener<T> listener;
    HashMap<String, String> body;

    public GsonRequest(String url, int method, HashMap<String, String> postBody, Type type, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.type = type;
        this.listener = listener;
        this.body = postBody;
        this.setTag(TAG);
        setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApiService.addRequest(this);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {

        Log.i(TAG, "parseNetworkResponse: " + networkResponse.data);
        try {
            String responseString = new String(networkResponse.data, UTF_8);
            Log.i(TAG, "parseNetworkResponse: " + responseString);
            T result = gson.fromJson(responseString, type);
            return Response.success(result, null);
        } catch (Exception e) {
            return Response.error(new VolleyError(networkResponse));
        }
    }

    @Override
    protected void deliverResponse(T t) {
        listener.onResponse(t);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        Log.i(TAG, "getBody: " + (body == null ? "null" : new JSONObject(body).toString()));
        return body == null ? null : new JSONObject(body).toString().getBytes();
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        Map<String, String> map = new HashMap<>();
//        Log.i(TAG, "getHeaders: " + map.toString());
//        return map;
//    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        Log.i(TAG, "parseNetworkError: " + volleyError.getMessage());
        try {
            String response = new String(volleyError.networkResponse.data, UTF_8);
            Log.i(TAG, "parseNetworkError: " + response);
            JSONObject responseObject = new JSONObject(response);
            return super.parseNetworkError(new VolleyError(responseObject.getJSONArray("message").getString(0)));
        } catch (Exception e) {
            try {
                return super.parseNetworkError(new VolleyError(volleyError.networkResponse.statusCode + "\n"+"Error in receive data!"));
            } catch (Exception e1) {
                return super.parseNetworkError(new VolleyError("Connection Error"));
            }
        }

    }
}