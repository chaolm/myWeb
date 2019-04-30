package com.dripop.util;

import com.alibaba.fastjson.TypeReference;
import com.bean.ChannelType;
import com.bean.OrderType;
import com.dripop.core.util.*;
import com.dripop.dao.OrderDao;
import com.dripop.entity.TGoodsOnline;
import com.dripop.entity.TOrder;
import com.dripop.entity.TStaticData;
import com.dripop.service.CommonService;
import com.dripop.util.hbfq.GoodsStagingDto;
import com.dripop.util.hbfq.StagingDto;
import com.dripop.util.hbfq.StagingItemDto;
import com.dripop.util.hbfq.TokioPeriodRate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liyou on 2018/3/15.
 */
public class OrderUtil {

    public static String getOrderNo(OrderType orderType, ChannelType orderChannel) {
        String date = DateUtil.dateFormat(new Date(), "yyyyMMdd");
        String key = "order_no" + date;
        String orderNo = RedisUtil.INSTANCE.get(key);
        if(StringUtil.isBlank(orderNo)) {
            RedisUtil.INSTANCE.setnx(key, date+"000000", 24*60*60);
        }
        Long result = RedisUtil.INSTANCE.incr(key);
        String yy = result.toString().substring(6,8);
        String mm = result.toString().substring(4,6);
        String dd = result.toString().substring(2,4);
        String total = result.toString().substring(8);
        String env = SpringContextUtil.getPropertiesValue("env");
        if(env.equals("dev")) {
            env = "1";
        }else if(env.equals("test")) {
            env = "2";
        }else {
            env = "";
        }
        return env+orderType.getValue()+dd+mm+orderChannel.getValue()+yy+total;
    }

    /**
     * 根据上架商品获得相关分期信息
     * @param goodsOnline
     * @return
     */
    public static StagingDto getStagingInfo(TGoodsOnline goodsOnline) {
        StagingDto stagingDto = new StagingDto();
        Integer isUseStaging = goodsOnline.getIsUseStaging();
        Integer stagingNum = goodsOnline.getStagingNum();
        Integer salePrice =goodsOnline.getSalePrice();
        Integer presellMoney = goodsOnline.getPresellMoney();
        Integer deposit = goodsOnline.getDeposit();
        Integer discountAmount =  goodsOnline.getDepositDiscountAmount()==null?0: goodsOnline.getDepositDiscountAmount();
        //获得商品销售类型 1普通商品，2预售商品，3增值服务商品
        Integer goodsSellType = goodsOnline.getGoodsSellType();
        //金额（单位：分）
        if(2 == goodsSellType ){
            //需要支付定对定金花呗分期
            if ( presellMoney == null) {
                salePrice = deposit;
            }else {
                salePrice = presellMoney + deposit - (discountAmount==0?deposit:discountAmount);
            }
        }else  if (3 == goodsSellType || 4 == goodsSellType){
            if (presellMoney==null){
                stagingDto.setIsUseStaging(1);
                return stagingDto;
            }else {
                salePrice = presellMoney;
            }
        }
        if(null == isUseStaging || 1 == isUseStaging){
            stagingDto.setIsUseStaging(1);
            return stagingDto;
        }
        stagingDto.setIsUseStaging(isUseStaging);
        stagingDto.setStagingNum(stagingNum);
        List<StagingItemDto> itemList = getStagingCalculate(salePrice, stagingNum);
        stagingDto.setStagingItemList(itemList);
        if(stagingNum == null || stagingNum == 0){
            stagingDto.setStagingDesc("该商品支持花呗分期支付");
        }else {
            stagingDto.setStagingDesc("该商品支持花呗分期支付，最高" + stagingNum + "期免息");
        }
        return stagingDto;
    }

    /**
     * 根据上架商品获得相关分期信息
     * @param goodsOnlineList
     * @return
     */
    public static StagingDto getStagingInfo(List<TGoodsOnline> goodsOnlineList) {
        StagingDto stagingDto = new StagingDto();
        Integer stagingNum = null;
        for(TGoodsOnline goodsOnline : goodsOnlineList){
            if(null == goodsOnline.getIsUseStaging() || 1 == goodsOnline.getIsUseStaging()){
                stagingDto.setIsUseStaging(1);
                return stagingDto;
            }
            //获得支持所有满足花呗分期期数的最高分期数
            if(null == goodsOnline.getStagingNum()){
                stagingNum = 0;
            }else if(stagingNum == null || 0 != stagingNum){
                if(stagingNum == null){
                    stagingNum = goodsOnline.getStagingNum();
                }
                if(stagingNum > goodsOnline.getStagingNum()){
                    stagingNum = goodsOnline.getStagingNum();
                }
            }
        }

        stagingDto.setIsUseStaging(0);
        stagingDto.setStagingNum(stagingNum);
        if(stagingNum == 0){
            stagingDto.setStagingDesc("该订单支持花呗分期支付");
        }else{
            stagingDto.setStagingDesc("该订单支持花呗分期支付，最高"+stagingNum+"期免息");
        }
       /* if(isUse){
            //多商品花呗分期计算
            getGoodsListUndertakePoundage(stagingDto, salePrice, stagingNum);
        }else{
            stagingDto.setIsUseStaging(1);
            return stagingDto;
        }*/
        return stagingDto;
    }

    /**
     * 根据上架商品获得相关分期信息
     * @return
     */
    public static StagingDto getStagingInfo(Long orderId) {
        StagingDto stagingDto = new StagingDto();
        OrderDao orderDao = SpringContextUtil.getContext().getBean(OrderDao.class);
        TOrder order = orderDao.get(orderId);
        Integer salePrice = order.getRealPay();
        if (order.getOrderType().equals(OrderType.PRESELL.getValue())&&order.getDepositPay()!=null){
                if (order.getDepositPayStatus()==0){
                    salePrice = order.getDepositPay();
                }else {
                    salePrice = order.getRealPay() - order.getDepositPay();
                }
        }
        Integer stagingNum = null;
        Integer stagingTip = 0;
        //根据orderId获得所有的detail
        String sql = "select tgo.is_use_staging, tgo.staging_num, tgo.staging_start_time, tgo.staging_end_time " +
                "from t_order_detail tod,t_goods_online tgo " +
                "where tod.online_id = tgo.online_id and tod.order_id = :orderId";
        List<GoodsStagingDto> orderDetailList = orderDao.findManyBySql(sql, GoodsStagingDto.class, orderId);

        boolean isUse = true;
        for (GoodsStagingDto goodsStagingDto : orderDetailList) {
            if (null == goodsStagingDto.getIsUseStaging()) {
                isUse = false;
                break;
            } else {
                if (1 == goodsStagingDto.getIsUseStaging()) {
                    isUse = false;
                    break;
                }
            }
            //获得支持所有满足换呗分期期数的最高分期数
            if (null == goodsStagingDto.getStagingNum()) {
                stagingNum = 0;
            } else if (stagingNum == null || 0 != stagingNum) {
                if (stagingNum == null) {
                    stagingNum = goodsStagingDto.getStagingNum();
                }
                if (stagingNum > goodsStagingDto.getStagingNum()) {
                    stagingNum = goodsStagingDto.getStagingNum();
                }
                if (stagingNum != goodsStagingDto.getStagingNum()) {
                    stagingTip = 1;
                }
            }
        }
        //支持花呗分期
        if (isUse) {
            stagingDto.setIsUseStaging(0);
            //支持多商品免息
            stagingDto.setStagingTip(stagingTip);
            //多商品花呗分期计算
            List<StagingItemDto> itemList = getStagingCalculate(salePrice, stagingNum);
            if(stagingNum == null || stagingNum == 0){
                stagingDto.setStagingDesc("该订单支持花呗分期支付");
            }else {
                stagingDto.setStagingDesc("该订单支持花呗分期支付，最高" + stagingNum + "期免息");
            }
            stagingDto.setStagingItemList(itemList);
        } else {
            stagingDto.setIsUseStaging(1);
            return stagingDto;
        }

        return stagingDto;
    }

    /**
     * 根据上架预售商品获得尾款 单位：分
     * @param goodsOnline
     * @return
     */
    public static Integer getBalancePayMent(TGoodsOnline goodsOnline) {
        Integer balancePayment = null;
        if(null != goodsOnline.getPresellMoney()){
            if(null != goodsOnline.getDepositDiscountAmount()){
                balancePayment = goodsOnline.getPresellMoney() - goodsOnline.getDepositDiscountAmount();
            }else{
                balancePayment = goodsOnline.getPresellMoney() - goodsOnline.getDeposit();
            }
        }
        return  balancePayment;
    }

//    /**
//     * 用户承担手续费
//     * @param stagingDto
//     * @param salePrice
//     * @throws Exception
//     */
//    private static void getUserUndertakePoundage(StagingDto stagingDto, Integer salePrice, boolean isGood) throws Exception {
//        String sql = "select value from t_static_data where type='TOKIO-PERIOD-RATE' and is_used=1";
//        GoodsOnlineServiceNew interService = SpringContextUtil.getBean("goodsOnlineServiceNew");
//        List<Object> rsData = interService.findBySql(sql);
//        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
//        List<StagingItemDto> stagingItemList = new ArrayList<StagingItemDto>();
//        String jsonStr = "";
//        if(rsData!=null && rsData.size()>0){
//            jsonStr = rsData.get(0).toString();
//            Map<String, Object> map = new HashMap<String, Object>();
//            map = JsonUtil.fromJson(jsonStr,map.getClass());
//            datas = (List)(map.get("content"));
//        }
//        for(Map<String,Object> dataMap : datas){
//            StagingItemDto stagingItemDto = new StagingItemDto();
//            // 分期期数
//            Integer period = CommonUtil.getIntValue(dataMap.get("period"));
//            // 用户承担该分期期数的利率
//            getStagingCalculateForUser(salePrice, stagingItemList, dataMap, stagingItemDto, period);
//        }
//        stagingDto.setStagingItemList(stagingItemList);
//        if(isGood){
//            stagingDto.setStagingDesc("该商品支持花呗分期支付");
//        }else{
//            stagingDto.setStagingDesc("该订单支持花呗分期支付");
//        }
//    }

//    /**
//     * 对于商家承担手续费计算
//     * @param salePrice
//     * @param stagingItemList
//     * @param dataMap
//     * @param stagingItemDto
//     * @param period
//     * @param sellerPeriod
//     */
//    private static void getStagingCalculateForSeller(Integer salePrice, List<StagingItemDto> stagingItemList, Map<String, Object> dataMap, StagingItemDto stagingItemDto, Integer period, Integer sellerPeriod) {
//        // 该分期期数的利率
//        String sellerRate = dataMap.get("rate").toString();
//
//        Double sellerStagingRate = Double.parseDouble(sellerRate.substring(0,sellerRate.length()-1));
//        //小数点后保留6位有效数
//        BigDecimal doubleRate = new BigDecimal(sellerStagingRate).divide(new BigDecimal("100"), 6,BigDecimal.ROUND_HALF_UP);
//        BigDecimal totalFeeInDecimal = BigDecimal.valueOf(salePrice).multiply(doubleRate);
//        //对费用进行取整--总手续费用
//        Long terminallyPay = totalFeeInDecimal.setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue();
//        //计算每期手续费
//        BigDecimal eachFee = BigDecimal.valueOf(terminallyPay).divide(new BigDecimal(period), BigDecimal.ROUND_DOWN);
//        //每期应还本金
//        BigDecimal eachPrin = BigDecimal.valueOf(salePrice).divide(new BigDecimal(period), BigDecimal.ROUND_DOWN);
//        //每期应还款金额 = 每期手续费 + 每期应还本金
//        BigDecimal prinAndFee = eachFee.add(eachPrin);
//        stagingItemDto.setIsStaging(0);
//        stagingItemDto.setStagingText("(免手续费)");
//        stagingItemDto.setItemStagingNum(sellerPeriod);
//        stagingItemDto.setTerminallyPay(String.format("%.2f",Integer.parseInt(eachPrin.toString()) * 1.0 / 100));
//        //手续费由商家承担，设置为0
//        stagingItemDto.setPoundageCost(String.format("%.2f",Integer.parseInt(eachFee.toString()) * 1.0 / 100));
//        //总手续费 单位：分
//        stagingItemDto.setPoundageCostTotal(terminallyPay.intValue());
//        //总金额
//        Integer salePriceTotal = salePrice + terminallyPay.intValue();
//        stagingItemDto.setTerminallyPayTotal(salePriceTotal);
//        stagingItemList.add(stagingItemDto);
//    }

//    /**
//     * 多商品花呗分期计算
//     * @param stagingDto
//     * @param salePrice
//     * @param stagingNum
//     */
//    private static void getGoodsListUndertakePoundage(StagingDto stagingDto, Integer salePrice, Integer stagingNum) {
//        stagingDto.setIsUseStaging(0);
//        if(0 ==stagingNum){
//            stagingDto.setStagingNum(null);
//            try{
//                // 用户承担手续费
//                getUserUndertakePoundage(stagingDto, salePrice, false);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        }else{
//            stagingDto.setStagingNum(stagingNum);
//            try{
//                // 根据stagingNum的值来区分是用户承担还是商家承担，小于等于stagingNum的商家承担，其他的用户承担
//                getUserSellerUndertakePoundage(stagingDto, salePrice, stagingNum, false);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    /**
//     * 用户商家承担手续费计算
//     * @param stagingDto
//     * @param salePrice
//     * @param stagingNum
//     * @throws Exception
//     */
//    private static void getUserSellerUndertakePoundage(StagingDto stagingDto, Integer salePrice, Integer stagingNum, boolean isGood ) throws Exception {
//        String sql = "select value from t_static_data where type='TOKIO-PERIOD-RATE' and is_used=1";
//        CommonService commonService = SpringContextUtil.getContext().getBean(CommonService.class);
//        TStaticData staticData = commonService.findDataByCode("TOKIO-PERIOD-RATE").get(0);
//        List<TokioPeriodRate> list = JsonUtil.fromJson(staticData.getVal(), new TypeReference<List<TokioPeriodRate>>() {});
////        List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
//        List<StagingItemDto> stagingItemList = new ArrayList<StagingItemDto>();
//        String jsonStr = "";
////        if(rsData!=null && rsData.size()>0){
////            jsonStr = rsData.get(0).toString();
////            Map<String, Object> map = new HashMap<String, Object>();
////            map = JsonUtil.fromJson(jsonStr,map.getClass());
////            datas = (List)(map.get("content"));
////        }
//        for(TokioPeriodRate tokio : list){
//            StagingItemDto stagingItemDto = new StagingItemDto();
//            // 分期期数
//            Integer period = tokio.getPeriod();
//
//            if(period > stagingNum){//用户承担
//                // 用户承担该分期期数的利率
//                getStagingCalculateForUser(salePrice, stagingItemList, tokio, stagingItemDto, period);
//            }else{//商家承担
//                getStagingCalculateForSeller(salePrice, stagingItemList, tokio, stagingItemDto, period, sellerPeriod);
//                sql = "select value from t_static_data where type='TOKIO-PERIOD-RATE-SELLER-TWELVE' and is_used=1";
//                staticData = commonService.findDataByCode("TOKIO-PERIOD-RATE-SELLER").get(0);
//                List<Object> sellerData = interService.findBySql(sql);
//                List<Map<String,Object>> sellerDatas = new ArrayList<Map<String,Object>>();
//                String sellerJsonStr = "";
//                if(sellerData!=null && sellerData.size()>0){
//                    sellerJsonStr = sellerData.get(0).toString();
//                    Map<String, Object> map = new HashMap<String, Object>();
//                    map = JsonUtil.fromJson(sellerJsonStr,map.getClass());
//                    sellerDatas = (List)(map.get("content"));
//                }
//                for(Map<String, Object> sellerMap : sellerDatas){
//                    // 分期期数
//                    Integer sellerPeriod = CommonUtil.getIntValue(sellerMap.get("period"));
//                    if(period == sellerPeriod && sellerPeriod <= stagingNum){
//                        // 对于商家承担手续费
//                        getStagingCalculateForSeller(salePrice, stagingItemList, sellerMap, stagingItemDto, period, sellerPeriod);
//                    }
//                }
//            }
//        }
//        stagingDto.setStagingItemList(stagingItemList);
//        if(isGood){
//            stagingDto.setStagingDesc("该商品支持花呗分期支付，最高"+stagingNum+"期免息");
//        }else{
//            stagingDto.setStagingDesc("该订单支持花呗分期支付，最高"+stagingNum+"期免息");
//        }
//
//    }

    /**
     * 承担的每期分期费用计算
     * @param salePrice
     * @param stagingNum
     * @return
     */
    private static List<StagingItemDto> getStagingCalculate(Integer salePrice, Integer stagingNum) {
        if(stagingNum == null) {
            stagingNum = 0;
        }
        String sql = "select value from t_static_data where type='TOKIO-PERIOD-RATE' and is_used=1";
        CommonService commonService = SpringContextUtil.getContext().getBean(CommonService.class);
        TStaticData staticData = commonService.findDataByCode("TOKIO-PERIOD-RATE").get(0);
        List<TokioPeriodRate> list = JsonUtil.fromJson(staticData.getVal(), new TypeReference<List<TokioPeriodRate>>() {});

        List<StagingItemDto> stagingList = new ArrayList<>();
        StagingItemDto stagingItemDto = null;
        for (TokioPeriodRate tokio : list) {
            stagingItemDto = new StagingItemDto();
            stagingItemDto.setItemStagingNum(tokio.getPeriod());
            String rate = null;
            if(stagingNum < tokio.getPeriod()) {
                rate = tokio.getRate();
                stagingItemDto.setIsStaging(1);
                stagingItemDto.setStagingText("(含手续费)");
            }else {
                stagingItemDto.setIsStaging(0);
                stagingItemDto.setStagingText("(免手续费)");
                rate = tokio.getSellerRate();
            }

            Double stagingRate = Double.parseDouble(rate.substring(0,rate.length()-1));
            //小数点后保留6位有效数
            BigDecimal doubleRate = new BigDecimal(stagingRate).divide(new BigDecimal("100"), 6,BigDecimal.ROUND_HALF_UP);
            BigDecimal totalFeeInDecimal = BigDecimal.valueOf(salePrice).multiply(doubleRate);
            //对费用进行取整--总手续费用
            Long terminallyPay = totalFeeInDecimal.setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue();
            //计算L每期手续费
            BigDecimal eachFee = BigDecimal.valueOf(terminallyPay).divide(new BigDecimal(tokio.getPeriod()), BigDecimal.ROUND_DOWN);
            //每期应还本金
            BigDecimal eachPrin = BigDecimal.valueOf(salePrice).divide(new BigDecimal(tokio.getPeriod()), BigDecimal.ROUND_DOWN);
            //每期应还款金额 = 每期手续费 + 每期应还本金
            //BigDecimal prinAndFee = eachFee.add(eachPrin);
            //支付总金额 = 商品金额+服务费  每期应还金额 = 支付总金额/分期期数
            BigDecimal prinAndFee = (BigDecimal.valueOf(salePrice).add(BigDecimal.valueOf(terminallyPay))).divide(new BigDecimal(tokio.getPeriod()), BigDecimal.ROUND_DOWN);

            stagingItemDto.setTerminallyPay(String.format("%.2f",Integer.parseInt(prinAndFee.toString()) * 1.0 / 100));
            //手续费由用户承担，设置为0
            stagingItemDto.setPoundageCost(String.format("%.2f",Integer.parseInt(eachFee.toString()) * 1.0 / 100));
            //总手续费 单位：分
            stagingItemDto.setPoundageCostTotal(terminallyPay.intValue());
            //总金额
            Integer salePriceTotal = salePrice + terminallyPay.intValue();
            stagingItemDto.setTerminallyPayTotal(salePriceTotal);
            stagingList.add(stagingItemDto);
        }

        return stagingList;
    }

    /**
     * 订单跟踪内容生成
     * @param caseStr
     * @param message
     * @return
     */
    public static String getOrderTrackMessage(int caseStr,String message) {

        switch (caseStr) {
            case 1 :
                return "您提交了订单，等待系统确认";
            case 2 :
                return "您的订单已经进入" + message + "准备出库";
            case 3 :
                return "您的订单拦截成功，正在进行退款申请，请耐心等待";
            case 4 :
                return "您取消了退款申请，订单恢复正常";
            case 5 :
                return "您进行了收货地址的修改，将为您送至: "+message;
            case 6 :
                return "您进行了自提门店的修改，正在为您切换";
            case 7 :
                return "您的订单已经进入"+message+"，等待您的取货";
            case 8 :
                return "您的订单已经进入"+message+"准备出库（预售商品数量有限，我们将尽快为您发货）";
        }
        return null;
    }
}
