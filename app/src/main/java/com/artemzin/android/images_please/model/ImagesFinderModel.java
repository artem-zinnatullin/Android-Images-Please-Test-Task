package com.artemzin.android.images_please.model;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Artem Zinnatullin [artem.zinnatullin@gmail.com]
 */
public class ImagesFinderModel {

    private static final Executor EXECUTOR = Executors.newCachedThreadPool();
    private static final String[] POSSIBLE_IMAGE_EXTENSIONS = {
            "jpg",
            "jpeg",
            "png"
    };

    private final FileWalkerModel mFileWalkerModel = new FileWalkerModel();

    public void asyncFindPossibleImageFiles(@NonNull final File rootDirForSearch, @NonNull TaskListener<List<File>> listener) {
        EXECUTOR.execute(new TaskRunnable<List<File>>(listener) {
            @Override protected List<File> doTask() {
                final List<File> possibleImageFiles = new ArrayList<File>();
                final FileWalkerModel.ScanRecursivelyListener scanRecursivelyListener = new FileWalkerModel.ScanRecursivelyListener() {
                    @Override public void onFile(@NonNull File file) {
                        if (isFilePossibleImage(file)) {
                            possibleImageFiles.add(file);
                        }
                    }
                };

                mFileWalkerModel.scanRecursively(rootDirForSearch, scanRecursivelyListener);

                return possibleImageFiles;
            }
        });
    }

    protected boolean isFilePossibleImage(@NonNull File file) {
        try {
            final String filePath = file.getPath().toLowerCase();

            for (String possibleImageExtension : POSSIBLE_IMAGE_EXTENSIONS) {
                if (filePath.endsWith(possibleImageExtension)) {
                    return true;
                }
            }

            return false;
        } catch (Exception ignored) {
            return false;
        }
    }
}
