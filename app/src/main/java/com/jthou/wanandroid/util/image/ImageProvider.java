package com.jthou.wanandroid.util.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

public interface ImageProvider {

    void pause(Context context);

    void resume(Context context);

    void displayUri(Context context, Uri uri, ImageView imageView);

    void displayFile(Context context, String path, ImageView imageView);

    void displayAsset(Context context, String path, ImageView imageView);

    void displayHttp(Context context, String path, ImageView imageView);

    void displayResource(Context context, int resourceId, ImageView imageView);

    void clearMemoryCache();

}
