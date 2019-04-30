package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.ExcelUtil;
import com.dripop.core.util.JsonUtil;
import com.dripop.entity.TCard;
import com.dripop.entity.TCustomer;
import com.dripop.promotion.dto.*;
import com.dripop.promotion.service.CardService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liyou on 2018/2/5.
 */
@Controller
@RequestMapping("card")
public class CardController extends BaseController {

    @Autowired
    private CardService cardService;

    /**
     * 卡券列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("page")
    @ResponseBody
    public ResultInfo cardPage(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        CardPageReq reqDto = JsonUtil.fromJson(reqJson, CardPageReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        Pagination<CardPageDto> page = cardService.cardPage(reqDto, pageable);
        return returnSuccess(page);
    }

    /**
     * 卡券状态设置
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("setting")
    @ResponseBody
    public ResultInfo setting(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        Integer status = reqJson.getInteger("status");
        cardService.setting(id, status);
        return returnSuccess();
    }

    /**
     * 卡券删除
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        cardService.delete(id);
        return returnSuccess();
    }

    @PostMapping("savaOrUpdateCard")
    @ResponseBody
    public ResultInfo savaOrUpdateCard(HttpServletRequest request, String dataJson, MultipartFile file) throws Exception{
        JSONObject reqJson = JSONObject.parseObject(dataJson);
        Integer minUsePrice = reqJson.getJSONObject("card").getBigDecimal("minUsePrice").multiply(new BigDecimal("100")).intValue();
        CardDetailDto dto = JsonUtil.fromJson(reqJson, CardDetailDto.class);
        dto.getCard().setMinUsePrice(minUsePrice);

        if(file!=null){
            dto.getCustomerIds().addAll(importUserList(file));
        }

        cardService.savaOrUpdateCard(dto);
        return returnSuccess();
    }

    public List<Long> importUserList(MultipartFile file) throws Exception {
        List<Object[]> userExcelList=ExcelUtil.readExcel(file.getInputStream(), 1);

        List<String> userPhoneList=new ArrayList<>();
        for (Object[] obj: userExcelList){
            if(obj[0] == null) {
                continue;
            }
            userPhoneList.add(obj[0].toString());
        }

       return cardService.getCustIdList(userPhoneList);
    }


    @PostMapping("selectCustomer")
    @ResponseBody
    public ResultInfo selectCustomer(HttpServletRequest request, @RequestBody JSONObject reqJson){
        SelectCustomerDto dto=JsonUtil.fromJson(reqJson,SelectCustomerDto.class);
        List<TCustomer> list= cardService.selectCustomer(dto);
        return returnSuccess(list);
    }

    @PostMapping("findCardDetail")
    @ResponseBody
    public ResultInfo findCardDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        TCard card= JsonUtil.fromJson(reqJson, TCard.class);
        CardDetailDto dto = cardService.findCardDetail(card.getId());
        return returnSuccess(dto);
    }
    @PostMapping("exportfindCardDetail")
    public void exportfindCardDetail(HttpServletRequest request, String exportJson, HttpServletResponse response) throws IOException {
        TCard card= JsonUtil.fromJson(exportJson, TCard.class);
        CardDetailDto page = cardService.findCardDetail(card.getId());
        String[] header = {"用户账号","用户昵称","注册时间","领取时间","领取次数","使用数"};
        List<Object[]> objList = new ArrayList<Object[]>();
        List<Object> objArr = null;
        for (UserClaimRecordDto dto : page.getUserClaimRecordDtoList()) {
            objArr = new LinkedList<Object>();
            objArr.add(dto.getPhoneNo());
            objArr.add(dto.getName());
            objArr.add(dto.getRegisterTime());
            objArr.add(dto.getReceiveTime());
            objArr.add(dto.getReceiveNum());
            objArr.add(dto.getUseNum());
            objList.add(objArr.toArray());
        }
        HSSFWorkbook book = ExcelUtil.buildExcel(header, objList);
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
        String fileName = new String("优惠券领取信息.xls".getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        book.write(ouputStream);
        ouputStream.flush();
        if (ouputStream != null) {
            ouputStream.close();
        }
    }

    @GetMapping("exportUserRecord")
    public void exportUserRecord(HttpServletResponse response, Long cardId) throws Exception {
//        TCustomerCard customerCard = JsonUtil.fromJson(reqJson, TCustomerCard.class);
        List<UserClaimRecordDto> ucrList = cardService.getUCRList(cardId);

        String [] headers={"用户账号","用户昵称","注册时间","领取时间","领取次数","使用数"};
        List<Object[]> list=new ArrayList<Object[]>();

        for (UserClaimRecordDto ucr:ucrList) {
            List<Object> row=new ArrayList<Object>();
            row.add(ucr.getName());
            row.add(ucr.getPhoneNo());
            row.add(ucr.getRegisterTime());
            row.add(ucr.getReceiveTime());
            row.add(ucr.getReceiveNum());
            row.add(ucr.getUseNum());

            list.add(row.toArray());
        }

        HSSFWorkbook book = ExcelUtil.buildExcel(headers,list);

        response.reset();
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
        String fileName = new String("优惠券领取信息.xls".getBytes("gbk"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        book.write(ouputStream);
        ouputStream.flush();
        if (ouputStream != null) {
            ouputStream.close();
        }


    }




}
