package com.dripop.sys.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.sys.dto.CustomerOrderDetailDto;
import com.dripop.sys.dto.InterDtoWXCustomer;

/**
 * Created by liyou on 2018/1/11.
 */
public interface CustomerService {

    Pagination<InterDtoWXCustomer> customerPage(InterDtoWXCustomer customer, Pageable pageable);

    Pagination<CustomerOrderDetailDto> detailPage(Long custId, Pageable pageable);
}
