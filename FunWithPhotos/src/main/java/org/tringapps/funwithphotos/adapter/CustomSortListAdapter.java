package org.tringapps.funwithphotos.adapter;

import com.squareup.picasso.Picasso;

import org.tringapps.funwithphotos.MyAppEnvironment;
import org.tringapps.funwithphotos.R;
import org.tringapps.funwithphotos.model.TagImageData;
import org.tringapps.funwithphotos.model.TagResult;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tring-ajay on 7/30/14
 *
 * <br />
 *
 * List Adapter that displays images in order BIG, SMALL, SMALL...
 */
public class CustomSortListAdapter extends BaseAdapter {

    private static final int VIEW_IMG = 0, VIEW_NEXT = 1, VIEW_COUNT = 2;

    private static final String BIG = "BIG";

    private static final String SMALL = "SMALL";

    @Override
    public int getCount() {
        return MyAppEnvironment.getTagResult().mTagImageDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return MyAppEnvironment.getTagResult().mTagImageDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        TagImageData imageData = MyAppEnvironment.getTagResult().mTagImageDatas.get(position);

        if (imageData != null) {
            return imageData.isPlaceHolderForNextItems ? VIEW_NEXT : VIEW_IMG;
        }

        return VIEW_IMG;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        int nextViewType = getItemViewType(i);

        if (view == null) {
            viewHolder = new ViewHolder();

            if (nextViewType == VIEW_NEXT) {
                view = LayoutInflater.from(MyAppEnvironment.getContext())
                        .inflate(R.layout.view_list_next_item, null);
            } else {
                view = LayoutInflater.from(MyAppEnvironment.getContext())
                        .inflate(R.layout.view_list_item, null);

                assert view != null;
                viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
                viewHolder.imgDescTextView = (TextView) view.findViewById(R.id.img_desc_text_view);
            }

            assert view != null;
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (nextViewType == VIEW_IMG) {
            TagImageData imageData = MyAppEnvironment.getTagResult().mTagImageDatas.get(i);
            if (i % 3 == 0) {
                Picasso.with(MyAppEnvironment.getContext())
                        .load(imageData.getImageUrl(TagResult.ImageSize.STD_RES))
                        .config(Bitmap.Config.RGB_565)
                        .error(R.drawable.ic_launcher)
                        .into(viewHolder.imageView);

                viewHolder.imgDescTextView.setText(BIG);
            } else {
                Picasso.with(MyAppEnvironment.getContext())
                        .load(imageData.getImageUrl(TagResult.ImageSize.LOW_RES))
                        .config(Bitmap.Config.RGB_565)
                        .error(R.drawable.ic_launcher)
                        .into(viewHolder.imageView);

                viewHolder.imgDescTextView.setText(SMALL);
            }
        }

        return view;
    }

    private class ViewHolder {

        TextView imgDescTextView;

        ImageView imageView;
    }
}
