package com.artemzin.android.images_please;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author Artem Zinnatullin [artem.zinnatullin@gmail.com]
 */
public class ImagesPleaseApp extends Application {

    @Override public void onCreate() {
        super.onCreate();
        Loggi.setEnabled(BuildConfig.DEBUG);
        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 2)
                .build();

        ImageLoader.getInstance().init(configuration);
    }
}
