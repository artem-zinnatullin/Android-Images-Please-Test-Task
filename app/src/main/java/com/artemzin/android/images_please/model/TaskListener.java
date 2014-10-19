package com.artemzin.android.images_please.model;

/**
 * Listener for the tasks
 *
 * <p>NOTICE: if listener's impl throw exception in one of its methods, it won't fail the task execution!</p>
 *
 * @param <Result> type of the task work result
 */
public abstract class TaskListener<Result> {
    /**
     * Called in UI thread right before executing the task
     */
    public void onPreExecute() { }

    /**
     * Called in UI thread if exception had occurred while executing the task
     *
     * @param t throwable
     */
    public abstract void onProblemOccurred(Throwable t);

    /**
     * Called in UI thread when the task finished data processing
     *
     * @param result of the processing
     */
    public abstract void onDataProcessed(Result result);

    /**
     * Called in UI thread after all task callbacks
     */
    public void afterAllCallbacks() { }
}
