package com.artemzin.android.images_please.model;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * @author Artem Zinnatullin [artem.zinnatullin@gmail.com]
 */
public class FileWalkerModel {

    /**
     * Listener for #scanRecursively method
     */
    public interface ScanRecursivelyListener {
        /**
         * This callback will be called when recursive scan detects new non-directory file
         * @param file that has been detected
         */
        void onFile(@NonNull File file);
    }

    public void scanRecursively(@NonNull File rootDir, @NonNull ScanRecursivelyListener listener) {
        File[] files = rootDir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                scanRecursively(file, listener);
            } else {
                listener.onFile(file);
            }
        }
    }
}
