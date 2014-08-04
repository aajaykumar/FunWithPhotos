package org.tringapps.funwithphotos.model;

/**
 * Created by tring-ajay on 7/30/14
 */
public class TagImageData {

    private String[] imageUrls;

    public boolean isPlaceHolderForNextItems;

    public String getImageUrl(TagResult.ImageSize imageSize) {
        if (imageSize == null) {
            return imageUrls[0];
        }

        switch (imageSize) {
            case THUMBNAIL:
                return imageUrls[0];

            case LOW_RES:
                return imageUrls[1];

            case STD_RES:
                return imageUrls[2];
        }

        return null;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }
}
