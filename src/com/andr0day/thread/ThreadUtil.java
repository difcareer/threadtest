package com.andr0day.thread;

import android.os.Looper;

public class ThreadUtil {

    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
