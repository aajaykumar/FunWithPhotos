package org.tringapps.funwithphotos.views;

import org.tringapps.funwithphotos.instagram.InstagramApi;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by tring-ajay on 7/30/14
 */
public class MyWebView extends WebView {

    @SuppressWarnings("UnusedDeclaration")
    public MyWebView(Context context) {
        super(context);
    }

    @SuppressWarnings("UnusedDeclaration")
    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressWarnings("UnusedDeclaration")
    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    {
        setWebViewClient(new MyWebViewClient());

        //TODO Is this needed
        //getSettings().setJavaScriptEnabled(true);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.startsWith(InstagramApi.REDIRECT_URL)) {
                String[] arguments = url.split("=");

                if (arguments != null && arguments.length > 0) {
                    if (mOnAccessTokenReceivedListener != null) {
                        mOnAccessTokenReceivedListener.onTokenReceived(arguments[1]);

                        loadUrl(url);

                        return true;
                    }
                }
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    // ---

    public static interface OnAccessTokenReceivedListener {

        public void onTokenReceived(String accessToken);
    }

    private OnAccessTokenReceivedListener mOnAccessTokenReceivedListener;

    public void setOnAccessTokenReceived(OnAccessTokenReceivedListener listener) {
        mOnAccessTokenReceivedListener = listener;
    }
}
