package com.jthou.wanandroid.util.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by user on 2016/11/21.
 */
public class ImageLoader {

    private static ImageProvider sImageProvider;

    public static void pause(Context context) {
        sImageProvider.pause(context);
    }

    public static void resume(Context context) {
        sImageProvider.resume(context);
    }

    public static void setImageProvider(ImageProvider imageProvider) {
        sImageProvider = imageProvider;
    }

    public static void displayUri(Context context, Uri uri, ImageView imageView) {
        sImageProvider.displayUri(context, uri, imageView);
    }

    public static void displayFile(Context context, String path, ImageView imageView) {
        sImageProvider.displayFile(context, path, imageView);
    }

    public static void displayAsset(Context context, String path, ImageView imageView) {
        sImageProvider.displayAsset(context, path, imageView);
    }

    public static void displayImage(Context context, String url, ImageView imageView){
        sImageProvider.displayHttp(context, url, imageView);
    }

    public static void displayResource(Context context, int resourceId, ImageView imageView) {
        sImageProvider.displayResource(context, resourceId, imageView);
    }

    public static void clearMemoryCache(){
        sImageProvider.clearMemoryCache();
    }

}
