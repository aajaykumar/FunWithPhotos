package org.tringapps.funwithphotos.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tring-ajay on 7/30/14
 */
public class TagResult {

    public static enum ImageSize {
        LOW_RES,
        THUMBNAIL,
        STD_RES
    }

    /**
     * Next set of image data to fetch...
     */
    public String mNextUrl;

    public ArrayList<TagImageData> mTagImageDatas = new ArrayList<TagImageData>();

    // --- Parser Logic

    private static final String PAGINATION = "pagination";

    private static final String DATA = "data";

    private static final String NEXT_URL = "next_url";

    private static final String IMAGES = "images";

    private static final String THUMBNAIL = "thumbnail";

    private static final String LOW_RESOLUTION = "low_resolution";

    private static final String STD_RESOLUTION = "standard_resolution";

    private static final String URL = "url";

    public void parseAndUpdate(String response) {
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        if (jsonObject.has(PAGINATION)) {
            try {
                parsePaginationObject(jsonObject.getJSONObject(PAGINATION));
            } catch (JSONException e) {
                e.printStackTrace();
                mNextUrl = null;
            }
        }

        if (jsonObject.has(DATA)) {
            try {
                parseDataObject(jsonObject.getJSONArray(DATA));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void parseDataObject(JSONArray jsonArray) throws JSONException {
        if (jsonArray.length() > 0) {

            if (mTagImageDatas.size() > 0 &&
                    mTagImageDatas.get(mTagImageDatas.size() - 1).isPlaceHolderForNextItems) {
                mTagImageDatas.remove(mTagImageDatas.size() - 1);
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dataObject = jsonArray.getJSONObject(i);

                if (dataObject.has(IMAGES)) {
                    JSONObject imgObj = dataObject.getJSONObject(IMAGES);

                    String[] imgUrls = {"", "", ""};

                    TagImageData imageData = new TagImageData();

                    if (imgObj.has(THUMBNAIL)) {
                        JSONObject thumbObj = imgObj.getJSONObject(THUMBNAIL);
                        imgUrls[0] = thumbObj.getString(URL);
                    }

                    if (imgObj.has(LOW_RESOLUTION)) {
                        JSONObject thumbObj = imgObj.getJSONObject(LOW_RESOLUTION);
                        imgUrls[1] = thumbObj.getString(URL);
                    }

                    if (imgObj.has(STD_RESOLUTION)) {
                        JSONObject thumbObj = imgObj.getJSONObject(STD_RESOLUTION);
                        imgUrls[2] = thumbObj.getString(URL);
                    }

                    imageData.setImageUrls(imgUrls);

                    mTagImageDatas.add(imageData);
                }
            }

            TagImageData imageData = new TagImageData();
            imageData.isPlaceHolderForNextItems = true;

            mTagImageDatas.add(imageData);
        }
    }

    private void parsePaginationObject(JSONObject jsonObject) throws JSONException {
        mNextUrl = jsonObject.getString(NEXT_URL);
    }

    public void clear() {
        if (mTagImageDatas != null) {
            mTagImageDatas.clear();
        }
    }
}
