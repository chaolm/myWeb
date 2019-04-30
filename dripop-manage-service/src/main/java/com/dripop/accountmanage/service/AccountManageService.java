package com.dripop.accountmanage.service;

import com.dripop.accountmanage.dto.*;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;

public interface AccountManageService {

    /**
     *商城对账单查询
     * @return
     */
    Pagination<GetShopCheckInfoDto> getShopCheckInfo(ShopCheckReq reqDto, Pageable page);

    /**
     * ERP对账单查询
     * @param reqDto
     * @param page
     * @return
     */
    Pagination<GetShopCheckInfoDto> getERPCheckInfo(ShopCheckReq reqDto, Pageable page);

    /**
     * ERP对账单明细查询
     */
    Pagination<GetERPDetailInfo>  getERPCheckDetail(ShopCheckReq req, Pageable pageable);

    /**
     * shop对账单明细查询
     */
    Pagination<GetShopdetailInfo> getShopCheckDetail(ShopCheckReq req, Pageable pageable);

    /**
     * 定时任务（每天0点对前一天数据进行查询并保存数据库中）
     */
    void execute();

    Pagination<GetShopDetailAfter> getShopCheckDetailafter(ShopCheckReq req, Pageable pageable);
    Pagination<GetshopInfoAfter> getShopCheckInfoAfter(ShopCheckReq req,Pageable pageable);
}
