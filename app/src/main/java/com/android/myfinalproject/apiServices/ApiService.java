package com.android.myfinalproject.apiServices;

import android.content.Context;

import com.android.myfinalproject.app.G;
import com.android.myfinalproject.models.Translate;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;

public class ApiService {

    public static RequestQueue requestQueue;
    public static String BASE = "http://api.vajehyab.com/v3/word?token=%s&title=%s&db=%s&num=%s";
    public static String TOKEN = "68351.vRuDn14gQWBWBlhjNLMXLUL7eS5N3cCjDfM8slG8";
    public static String DB = "en2fa";
    public static String TITLE = "test";
    public static String NUM = "1";


    public ApiService() {
    }

    public static void addRequest(Request request){
        if (requestQueue==null)
            requestQueue=Volley.newRequestQueue(G.context);
        requestQueue.cancelAll(request.getTag());
        requestQueue.add(request);
    }


    public static Request getTranslate(HashMap<String, String> postBody, Response.Listener<Translate> listener, Response.ErrorListener errorListener) {
        String URL=String.format(BASE,
                TOKEN,
                postBody.get(TITLE),
                postBody.get(DB),
                NUM
        );
        GsonRequest request=new GsonRequest(URL, Request.Method.GET,postBody,new TypeToken<Translate>(){}.getType(),listener,errorListener);
         return request;
    }

}
