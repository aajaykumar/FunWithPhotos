package org.tringapps.funwithphotos.activities;

import com.squareup.picasso.Picasso;

import org.tringapps.funwithphotos.R;
import org.tringapps.funwithphotos.support.AppConstants;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * Created by tring-ajay on 7/30/14
 */
public class ImageDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_detail);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String urlToLoad = bundle.getString(AppConstants.KEY_URL_TO_LOAD);

            if (!TextUtils.isEmpty(urlToLoad)) {
                ImageView imageView = (ImageView) findViewById(R.id.image_view);

                Picasso.with(getApplicationContext())
                        .load(urlToLoad)
                        .config(Bitmap.Config.RGB_565)
                        .error(R.drawable.ic_launcher)
                        .into(imageView);
            }
        } else {
            finish();
        }
    }
}
