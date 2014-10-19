package com.artemzin.android.images_please.model;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * @author Artem Zinnatullin [artem.zinnatullin@gmail.com]
 */
public abstract class TaskRunnable<Result> implements Runnable {

    private static final Handler UI_THREAD_HANDLER = new Handler(Looper.getMainLooper());

    private final TaskListener<Result> mTaskListener;

    public TaskRunnable(@NonNull TaskListener<Result> taskListener) {
        mTaskListener = taskListener;
    }

    @Override public final void run() {
        UI_THREAD_HANDLER.post(new Runnable() {
            @Override public void run() {
                mTaskListener.onPreExecute();
            }
        });

        try {
            final Result result = doTask();

            UI_THREAD_HANDLER.post(new Runnable() {
                @Override public void run() {
                    mTaskListener.onDataProcessed(result);
                }
            });
        } catch (final Throwable throwable) {
            UI_THREAD_HANDLER.post(new Runnable() {
                @Override public void run() {
                    mTaskListener.onProblemOccurred(throwable);
                }
            });
        }
    }

    protected abstract Result doTask();
}
