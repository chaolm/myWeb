package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.goodspackage.dto.OpPackageSaleSearchDto;
import com.dripop.goodspackage.dto.OpPackageSaleSearchReq;
import com.dripop.goodspackage.service.PackageSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liyou on 2018/1/10.
 */
@Controller
@RequestMapping("packagesale")
public class PackageSaleController extends BaseController {

    @Autowired
    private PackageSaleService packageSaleService;

    /**
     * (套餐销售)op分页获取普通商品套餐配置列表
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("page")
    @ResponseBody
    public ResultInfo page(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        Integer pageNo = jsonObject.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        OpPackageSaleSearchReq reqDto = JsonUtil.fromJson(jsonObject, OpPackageSaleSearchReq.class);
        Pagination<OpPackageSaleSearchDto> page = packageSaleService.getPackageSaleListPage(reqDto, pageable);
        return returnSuccess(page);
    }

    /**
     * (套餐销售)op获取主商品套餐详情信息
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("info")
    @ResponseBody
    public ResultInfo getMainGoodsPackageSaleInfo(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        Long onlineId = jsonObject.getLong("onlineId");
        Integer type = jsonObject.getInteger("type");
        List<OpPackageSaleSearchDto> list = packageSaleService.getMainGoodsPackageSaleInfo(onlineId, type);
        return returnSuccess(list);
    }

    /**
     * (套餐销售)op保存主商品套餐销售配置
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("save")
    @ResponseBody
    public ResultInfo saveMainGoodsPackageSale(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        String mainOnlineIds = jsonObject.getString("mainOnlineIds");   //主商品上架ID,多个上架ID之间以英文逗号隔开
        String recomOnlineIds = jsonObject.getString("recomOnlineIds");   //主商品推荐套餐绑定的商品上架ID数据,多个上架ID之间以英文逗号隔开

        if(StringUtil.isBlank(mainOnlineIds, recomOnlineIds)){
            throw new ServiceException("必填项不可为空，请检查!");
        }
        packageSaleService.savePackage(mainOnlineIds, recomOnlineIds);
        return returnSuccess();
    }

    /**
     * 删除销售套装
     * @param request
     * @param jsonObject
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deletePackageSale(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        Long onlineId = jsonObject.getLong("onlineId");
        packageSaleService.deletePackageSale(onlineId);
        return returnSuccess();
    }
}
