package com.dripop.vipcard.wechat.dto;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class MemberCardDto implements Serializable {

    //商家自定义会员卡背景图
    private String background_pic_url;

    //会员卡特权说明,限制1024汉字
    private String  prerogative;

    //显示积分
    private Boolean supply_bonus=true;

    //是否支持储值
     private Boolean supply_balance=false;

     //入口名称
    //private String name;

    //入口右侧提示语，6个汉字内
    private String tips;

    //入口跳转链接
    private String url;

    private BaseInfoDto base_info;

    public BaseInfoDto getBase_info() {
        return base_info;
    }

    public void setBase_info(BaseInfoDto base_info) {
        this.base_info = base_info;
    }

    //设置为true时用户领取会员卡后系统自动将其激活，无需调用激活接口，详情见 自动激活 。
    private boolean auto_activate=true;

    public boolean isAuto_activate() {
        return auto_activate;
    }

    public void setAuto_activate(boolean auto_activate) {
        this.auto_activate = auto_activate;
    }


    public String getPrerogative() {
        return prerogative;
    }

    public void setPrerogative(String prerogative) {
        this.prerogative = prerogative;
    }

    public Boolean getSupply_bonus() {
        return supply_bonus;
    }

    public void setSupply_bonus(Boolean supply_bonus) {
        this.supply_bonus = supply_bonus;
    }

    public Boolean getSupply_balance() {
        return supply_balance;
    }

    public void setSupply_balance(Boolean supply_balance) {
        this.supply_balance = supply_balance;
    }

  /*  public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

  /*  public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }*/

  /* public String getUrl() {
        return url;
    }*/

  /*  public void setUrl(String url) {
        this.url = url;
    }

    public String getBackground_pic_url() {
        return background_pic_url;
    }
*/
    public void setBackground_pic_url(String background_pic_url) {
        this.background_pic_url = background_pic_url;
    }
}
