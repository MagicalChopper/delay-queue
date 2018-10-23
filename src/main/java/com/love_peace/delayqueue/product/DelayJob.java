package com.love_peace.delayqueue.product;

import java.util.Map;

/**
 * @Author: LiuHao
 * @Descirption:
 * @Date: 2018/10/19_16:04
 */
public class DelayJob {

    private Map jobParams;//job执行参数
    private Class aClass;//具体执行实例实现

    public DelayJob() {
    }

    public Map getJobParams() {
        return jobParams;
    }

    public void setJobParams(Map jobParams) {
        this.jobParams = jobParams;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

}
