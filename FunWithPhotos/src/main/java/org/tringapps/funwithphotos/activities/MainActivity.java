package org.tringapps.funwithphotos.activities;

import org.tringapps.funwithphotos.MyAppEnvironment;
import org.tringapps.funwithphotos.R;
import org.tringapps.funwithphotos.adapter.CustomSortListAdapter;
import org.tringapps.funwithphotos.fragment.MyDialogFragment;
import org.tringapps.funwithphotos.instagram.InstagramApi;
import org.tringapps.funwithphotos.model.TagImageData;
import org.tringapps.funwithphotos.model.TagResult;
import org.tringapps.funwithphotos.support.AppConstants;
import org.tringapps.funwithphotos.utils.SharedPrefUtils;
import org.tringapps.funwithphotos.views.MyWebView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by tring-ajay on 7/30/14
 */
public class MainActivity extends Activity {

    private InstagramApi mInstagramApi = new InstagramApi();

    private CustomSortListAdapter mCustomSortListAdapter = new CustomSortListAdapter();

    private ListView mListView;

    private MyWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mWebView = (MyWebView) findViewById(R.id.login_web_view);

        mListView = (ListView) findViewById(R.id.photos_list_view);
        mListView.setAdapter(mCustomSortListAdapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int lastVisiblePositionOfList = absListView.getLastVisiblePosition()
                            + 1; // +1 for loading more images view

                    if (lastVisiblePositionOfList == mCustomSortListAdapter.getCount()) {

                        if (mInstagramApi != null) {
                            mInstagramApi.fetchImagesWithUrl(
                                    MyAppEnvironment.getTagResult().mNextUrl,
                                    mOnDataFetchSuccess, mOnDataFetchSuccess);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TagImageData imageData = MyAppEnvironment.getTagResult().mTagImageDatas.get(i);

                if (imageData != null) {
                    Intent intent = new Intent(getApplicationContext(), ImageDetailActivity.class);
                    intent.putExtra(AppConstants.KEY_URL_TO_LOAD,
                            imageData.getImageUrl(TagResult.ImageSize.STD_RES));

                    startActivity(intent);
                }
            }
        });

        String accessToken = SharedPrefUtils.getString(getApplicationContext(),
                AppConstants.KEY_ACCESS_TOKEN);

        if (TextUtils.isEmpty(accessToken)) {
            loadLoginPage();
        } else {
            mInstagramApi.setAccessToken(accessToken);
            mInstagramApi.fetchImagesWithTag(AppConstants.TAG_SELFIE,
                    mOnDataFetchSuccess, mOnDataFetchFailure);

            showFeaturesImplemented();
        }
    }

    private void loadLoginPage() {
        mWebView.setOnAccessTokenReceived(new MyWebView.OnAccessTokenReceivedListener() {
            @Override
            public void onTokenReceived(String accessToken) {
                if (mInstagramApi != null && mWebView != null && mListView != null) {
                    mInstagramApi.setAccessToken(accessToken);

                    SharedPrefUtils.updateSharedPref(getApplicationContext(),
                            AppConstants.KEY_ACCESS_TOKEN,
                            accessToken);

                    mWebView.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);

                    mInstagramApi
                            .fetchImagesWithTag(AppConstants.TAG_SELFIE, mOnDataFetchSuccess, mOnDataFetchFailure);

                    showFeaturesImplemented();
                }
            }
        });

        mWebView.loadUrl(mInstagramApi.getAuthenticationUrl());
    }

    private void showFeaturesImplemented() {
        if (isFinishing()) {
            return;
        }

        MyDialogFragment myDialogFragment = MyDialogFragment.getDialogFragment(
                MyDialogFragment.DialogType.DIALOG_TYPE_DETAIL);

        myDialogFragment.show(getFragmentManager(), null);
    }

    private Runnable mOnDataFetchSuccess = new Runnable() {
        @Override
        public void run() {
            if (mListView != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mCustomSortListAdapter != null) {
                            mCustomSortListAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }
    };

    private Runnable mOnDataFetchFailure = new Runnable() {
        @Override
        public void run() {
            if (mListView != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getApplicationContext() != null) {
                            Toast.makeText(getApplicationContext(), "Error retrieving data",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    };

}
