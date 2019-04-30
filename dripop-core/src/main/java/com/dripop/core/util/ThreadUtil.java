package com.dripop.core.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liyou on 2017/9/18.
 */
public enum ThreadUtil {

    INSTANCE;

    private ExecutorService executorService;

    ThreadUtil() {
        this.executorService = Executors.newFixedThreadPool(100);
    }

    public void excute(Runnable runnable) {
        executorService.execute(runnable);
    }

    public void excute(Callable callable, ThreadCallBack callBack) {
        callBack.success(executorService.submit(callable));
    }
}
