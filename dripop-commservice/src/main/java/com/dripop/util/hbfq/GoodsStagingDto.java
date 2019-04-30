package com.dripop.util.hbfq;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2018/3/21.
 */
public class GoodsStagingDto implements Serializable {

    private Integer isUseStaging;

    private Integer stagingNum;

    private Date stagingStartTime;

    private Date stagingEndTime;

    public Integer getIsUseStaging() {
        return isUseStaging;
    }

    public void setIsUseStaging(Integer isUseStaging) {
        this.isUseStaging = isUseStaging;
    }

    public Integer getStagingNum() {
        return stagingNum;
    }

    public void setStagingNum(Integer stagingNum) {
        this.stagingNum = stagingNum;
    }

    public Date getStagingStartTime() {
        return stagingStartTime;
    }

    public void setStagingStartTime(Date stagingStartTime) {
        this.stagingStartTime = stagingStartTime;
    }

    public Date getStagingEndTime() {
        return stagingEndTime;
    }

    public void setStagingEndTime(Date stagingEndTime) {
        this.stagingEndTime = stagingEndTime;
    }
}
