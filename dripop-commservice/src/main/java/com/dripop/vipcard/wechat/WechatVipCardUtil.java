package com.dripop.vipcard.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dripop.core.util.HttpUtil;
import com.dripop.core.util.JsonUtil;
import com.dripop.vipcard.wechat.dto.*;
import com.dripop.weixin.WeixinUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class WechatVipCardUtil {



    /*创建会员卡*/
    public static void createVipCard(CardDto card) {
        JSONObject json=new JSONObject();

        json.put("card",JSON.toJSON(card));
        System.out.println(JSON.toJSONString(json));
        String result = HttpUtil.post("https://api.weixin.qq.com/card/create?access_token="+WeixinUtil.getTokenForCard(), JSON.toJSONString(json));

        System.out.println("-------------" + result + "-------------");


    }


    //更改会员卡信息接口
    public static void updateVipCard(String card_id,CardDto card) {
        JSONObject json=new JSONObject();
        json.put("card_id",card_id);
        String baseInfoJson =JsonUtil.toFullJson(card);
      //  card.setBase_info(baseInfoJson);
        String cardStr = JsonUtil.toFullJson(card);
        json.put("member_card",cardStr);

        String result = HttpUtil.post("https://api.weixin.qq.com/card/update?access_token="+WeixinUtil.getTokenForCard(), json.toJSONString());


        System.out.println("-------------" + result + "-------------");

    }




    /*会员卡审核结果*/
    public static String getAuditingResult() {


        return null;
    }

    //创建二维码
    public static String createVipCardQrcode(ActionInfoSingDto actionInfoDto) {
        JSONObject qrcodeJson = new JSONObject();
        qrcodeJson.put("action_name", "QR_CARD");
       // qrcodeJson.put("expire_seconds", 1800);   不填默认是 365天有效
        qrcodeJson.put("action_info", JsonUtil.toJson(actionInfoDto.getCard()));

        String result = HttpUtil.post("https://api.weixin.qq.com/card/qrcode/create?access_token="+WeixinUtil.getTokenForCard(), qrcodeJson.toJSONString());

        System.out.println("-------------" + result + "-------------");
        return result;
    }


    //上传图片
    public static String uploadVipCardImg(MultipartFile file)  {
        String result=null;
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("img", file.getName(), RequestBody.create(MediaType.parse("image/png"), file.getBytes()));
            RequestBody requestBody = builder.build();
             result = HttpUtil.post("https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="+WeixinUtil.getTokenForCard(), requestBody);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("---------"+result+"-----------");
        return result;
    }

    private static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    //领取卡券记录信息返回
    public static String receiveCardRecord(){
        String xml=
                "<xml>"
                +"<ToUserName> <![CDATA[gh_fc0a06a20993]]> </ToUserName>"
                +"<FromUserName> <![CDATA[oZI8Fj040-be6rlDohc6gkoPOQTQ]]> </FromUserName>"
                +"<CreateTime>1472551036</CreateTime>"
                +"<MsgType> <![CDATA[event]]> </MsgType>"
                +"<Event> <![CDATA[user_get_card]]> </Event>"
                +"<CardId> <![CDATA[pZI8Fjwsy5fVPRBeD78J4RmqVvBc]]> </CardId>"
                +"<IsGiveByFriend>0</IsGiveByFriend>"
                +"<UserCardCode> <![CDATA[226009850808]]> </UserCardCode>"
                +"<FriendUserName> <![CDATA[]]> </FriendUserName>"
                +"<OuterId>0</OuterId>"
                +"<OldUserCardCode> <![CDATA[]]> </OldUserCardCode>"
                +"<OuterStr> <![CDATA[12b]]> </OuterStr>"
                +"<IsRestoreMemberCard>0</IsRestoreMemberCard>"
                +"<IsRecommendByFriend>0</IsRecommendByFriend>"
                +"</xml>";

        String result = HttpUtil.post("",xml,HttpUtil.XML);
        return result;
    }




 //拉取会员信息 积分查询
 public static String getVipUserMessage(String code, String cardId) {
     JSONObject userJson=new JSONObject();
     userJson.put("card_id",cardId);
     userJson.put("code",code);
     String result = HttpUtil.post("https://api.weixin.qq.com/card/membercard/userinfo/get?access_token=" + WeixinUtil.getTokenForCard(), userJson.toJSONString());
     System.out.println("---------"+result+"---------");
     return result;
 }

 public static void  deleteVipCard(String card_id){
        JSONObject json=new JSONObject();
        json.put("card_id",card_id);
        String result = HttpUtil.post("https://api.weixin.qq.com/card/delete?access_token="+WeixinUtil.getTokenForCard(),json.toJSONString());
        System.out.println("-----------"+result+"------------");
    }






}
