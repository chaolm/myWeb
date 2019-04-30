package com.dripop.core.util;

import java.util.concurrent.Future;

/**
 * Created by liyou on 2017/9/18.
 */
public interface ThreadCallBack {
    public void success(Future<?> submit);
}
