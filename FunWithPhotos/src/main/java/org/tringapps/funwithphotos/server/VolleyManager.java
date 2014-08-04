package org.tringapps.funwithphotos.server;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.content.Context;

/**
 * Created by tring-ajay on 7/30/14
 */
public class VolleyManager {

    private final RequestQueue mRequestQueue;

    public VolleyManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void addToRequestQueue(Request<?> request) {
        mRequestQueue.add(request);
    }

    public void clearCache() {
        mRequestQueue.getCache().clear();
    }
}
