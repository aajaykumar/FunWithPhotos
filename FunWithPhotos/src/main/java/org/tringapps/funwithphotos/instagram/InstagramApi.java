package org.tringapps.funwithphotos.instagram;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.tringapps.funwithphotos.MyAppEnvironment;

/**
 * Created by tring-ajay on 7/30/14
 */
public class InstagramApi {

    private static final String CLIENT_ID = "22e4d9657feb4c74acdc281e91b4935a";

    //private static final String CLIENT_SECRET = "8f1cc70cc4ec418189c615a96186acb3";

    public static final String REDIRECT_URL = "http://www.tringapps.com/";

    // ---
    private static final String AUTHENTICATION_URL = "https://instagram.com/oauth/authorize/";

    public static final String API_URL = "https://api.instagram.com/v1";

    // ---

    private String mAccessToken;

    public String getAuthenticationUrl() {
        return AUTHENTICATION_URL +
                "?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URL +
                "&response_type=token";
    }

    public String getTagSearchUrl(String tag) {
        return API_URL + "/tags/" + tag + "/media/recent?access_token=" + mAccessToken;
    }

    public void fetchImagesWithTag(String tag, Runnable onSuccess, Runnable onFailure) {
        fetchImagesWithUrl(getTagSearchUrl(tag), onSuccess, onFailure);
    }

    public void fetchImagesWithUrl(final String url, final Runnable onSuccess,
            final Runnable onFailure) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyAppEnvironment.getTagResult().parseAndUpdate(response);

                        if (onSuccess != null) {
                            onSuccess.run();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (onFailure != null) {
                            onFailure.run();
                        }
                    }
                }
        );

        MyAppEnvironment.getVolleyManager().addToRequestQueue(stringRequest);
    }

    public void setAccessToken(String requestToken) {
        mAccessToken = requestToken;
    }

}
