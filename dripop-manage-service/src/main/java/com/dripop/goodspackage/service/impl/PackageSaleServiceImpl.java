package com.dripop.goodspackage.service.impl;

import com.bean.IsUsed;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.PackageSaleDao;
import com.dripop.entity.TPackageSale;
import com.dripop.goodspackage.dto.OpPackageSaleSearchDto;
import com.dripop.goodspackage.dto.OpPackageSaleSearchReq;
import com.dripop.goodspackage.service.PackageSaleService;
import com.dripop.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shery on 2017/8/1.
 */
@Service
public class PackageSaleServiceImpl implements PackageSaleService {

    @Autowired
    private PackageSaleDao packageSaleDao;

    @Transactional
    public void savePackage(String mainOnlineIds, String recomOnlineIds) {
        String[] mainOnlineIdArr = mainOnlineIds.split(",");
        String[] recomOnlineIdArr = recomOnlineIds.split(",");
        TPackageSale packageSale = null;
        QLBuilder ql = null;
        Date date = new Date();
        Long userId = UserUtil.currentAdminUser().getId();
        for (String mainOnlineId : mainOnlineIdArr) {
            ql = new QLBuilder();
            ql.and(Filter.eq("mainOnlineId", Long.parseLong(mainOnlineId)));
            packageSaleDao.deleteByJpql(ql);
            for (String recomOnlineId : recomOnlineIdArr) {
                packageSale = new TPackageSale();
                packageSale.setMainOnlineId(Long.parseLong(mainOnlineId));
                packageSale.setBindOnlineId(Long.parseLong(recomOnlineId));
                packageSale.setCreateTime(date);
                packageSale.setCreateUserId(userId);
                packageSaleDao.insert(packageSale);
            }
        }
    }

    public Pagination<OpPackageSaleSearchDto> getPackageSaleListPage(OpPackageSaleSearchReq reqDto, Pageable pageable) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sb.append("select tgo.online_id, tgt.name typeName, tb.name brandName, tg.full_name FROM t_package_sale tps " +
                "left join t_goods_online tgo on tgo.online_id = tps.main_online_id " +
                "left join t_goods tg on tg.id = tgo.spu_id " +
                "left join t_goods_type tgt on tgt.id = tg.type_id " +
                "left join t_brand tb on tb.id = tg.brand_id " +
                "where tgo.is_used = :isUsed ");
        params.add(IsUsed.USED.getValue());
        if(reqDto.getTypeId() != null) {
            sb.append("and tg.type_id = :typeId ");
            params.add(reqDto.getTypeId());
        }
        if(reqDto.getBrandId() != null) {
            sb.append("and tg.brand_id = :brandId ");
            params.add(reqDto.getBrandId());
        }
        if(StringUtil.isNotBlank(reqDto.getFullName())) {
            sb.append("and tg.full_name like :fullName ");
            params.add("%"+reqDto.getFullName()+"%");
        }
        sb.append("group by tps.main_online_id order by tps.create_time desc");
        return packageSaleDao.findPageBySql(sb.toString(), pageable, OpPackageSaleSearchDto.class, params.toArray());
    }

    public List<OpPackageSaleSearchDto> getMainGoodsPackageSaleInfo(Long onlineId, Integer type) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sb.append("select tgo.online_id, tgt.name typeName, tb.name brandName, tg.full_name FROM t_package_sale tps " +
                "left join t_goods_online tgo on tgo.online_id = tps.bind_online_id " +
                "left join t_goods tg on tg.id = tgo.spu_id " +
                "left join t_goods_type tgt on tgt.id = tg.type_id " +
                "left join t_brand tb on tb.id = tg.brand_id " +
                "where tps.main_online_id = :onlineId ");
        params.add(onlineId);
        List<OpPackageSaleSearchDto> list = packageSaleDao.findManyBySql(sb.toString(), OpPackageSaleSearchDto.class, params.toArray());
        if(type != null && type == 1) {
            sb = new StringBuffer();
            params.clear();
            sb.append("select tgo.online_id, tgt.name typeName, tb.name brandName, tg.full_name FROM t_goods_online tgo " +
                    "left join t_goods tg on tg.id = tgo.spu_id " +
                    "left join t_goods_type tgt on tgt.id = tg.type_id " +
                    "left join t_brand tb on tb.id = tg.brand_id " +
                    "where tgo.online_id = :onlineId ");
            params.add(onlineId);
            OpPackageSaleSearchDto dto = packageSaleDao.findOneBySql(sb.toString(), OpPackageSaleSearchDto.class, params.toArray());
            list.add(0, dto);
        }
        return list;
    }

    @Transactional
    public void deletePackageSale(Long onlineId) {
        QLBuilder ql = new QLBuilder();
        ql.and(Filter.eq("mainOnlineId", onlineId));
        packageSaleDao.deleteByJpql(ql);
    }
}
