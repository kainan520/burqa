package com.mvvm.lux.burqa.model.event;

import com.mvvm.lux.framework.base.BaseEvent;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/17 19:55
 * @Version
 */
public class ProgressEvent extends BaseEvent {

    public int mProgress;

    public ProgressEvent(int progress) {
        mProgress = progress;
    }
}
