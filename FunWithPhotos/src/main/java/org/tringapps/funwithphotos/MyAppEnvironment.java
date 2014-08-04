package org.tringapps.funwithphotos;

import org.tringapps.funwithphotos.model.TagResult;
import org.tringapps.funwithphotos.server.VolleyManager;

import android.app.Application;
import android.content.Context;

/**
 * Created by tring-ajay on 7/30/14.
 *
 * <br/>
 *
 * Dependency injunction needs to be used but since this is just a POC choosing the next best
 * alternative approach
 */
public class MyAppEnvironment extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        mManager = new VolleyManager(mContext);
        mManager.clearCache();

        mTagResult.clear();
    }

    public static Context getContext() {
        return mContext;
    }

    private static VolleyManager mManager;

    public static VolleyManager getVolleyManager() {
        return mManager;
    }

    private static final TagResult mTagResult = new TagResult();

    public static TagResult getTagResult() {
        return mTagResult;
    }
}
