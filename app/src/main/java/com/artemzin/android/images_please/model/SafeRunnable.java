package com.artemzin.android.images_please.model;

import com.artemzin.android.images_please.Loggi;

/**
 * @author Artem Zinnatullin [artem.zinnatullin@gmail.com]
 */
public abstract class SafeRunnable implements Runnable {
    @Override public final void run() {
        try {
            safeRun();
        } catch (Throwable t) {
            // stupid IDEA bug with casting to object for Object's methods calls
            Loggi.e(((Object) this).getClass().getSimpleName(), "safeRun()", t);
        }
    }

    protected abstract void safeRun();
}
