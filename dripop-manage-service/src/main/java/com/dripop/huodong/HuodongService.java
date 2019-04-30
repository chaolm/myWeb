package com.dripop.huodong;

/**
 * Created by liyou on 2018/4/27.
 */
public interface HuodongService {
    void yuyue(String phone, String address);

    void qiusai(String phone, String openId, String gj, String yj);

    void activeYear(String phone, String openId);

}
