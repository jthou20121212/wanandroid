package com.jthou.wanandroid.util.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by user on 2018/5/21.
 */

public class GlideImageProvider implements ImageProvider {

    @Override
    public void pause(Context context) {
        Glide.with(context).pauseAllRequests();
    }

    @Override
    public void resume(Context context) {

    }

    @Override
    public void displayUri(Context context, Uri uri, ImageView imageView) {

    }

    @Override
    public void displayFile(Context context, String path, ImageView imageView) {

    }

    @Override
    public void displayAsset(Context context, String path, ImageView imageView) {

    }

    @Override
    public void displayHttp(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

    @Override
    public void displayResource(Context context, int resourceId, ImageView imageView) {

    }

    @Override
    public void clearMemoryCache() {

    }

}
