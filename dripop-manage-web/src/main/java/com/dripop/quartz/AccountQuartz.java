package com.dripop.quartz;

import com.dripop.accountmanage.service.AccountManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by clm
 */
@Component
public class AccountQuartz {
    private static final Logger log = LoggerFactory.getLogger(AccountQuartz.class);

    @Autowired
    private AccountManageService accountManageService;

    /**
     * 每天0点对前一天数据进行查询并保存数据库中
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void account(){
        log.info("========account start==============");
        accountManageService.execute();
        log.info("========account end==============");

    }
}
