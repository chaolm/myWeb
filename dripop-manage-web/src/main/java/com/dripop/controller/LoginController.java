package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.bean.TokenDto;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.entity.TSysOper;
import com.dripop.entity.TSysRole;
import com.dripop.goods.dto.OpGoodsDetailDto;
import com.dripop.sys.service.SysOperService;
import com.dripop.util.nelUtil;
import com.dripop.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liyou on 2018/1/11.
 */
@Controller
@RequestMapping
public class LoginController extends BaseController {

    @Autowired
    private SysOperService sysOperService;

    /**
     * 管理员登录
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("open/login")
    @ResponseBody
    public ResultInfo login(HttpServletRequest request, @RequestBody JSONObject reqJson){
        String phoneNo = reqJson.getString("phoneNo");
        String password = reqJson.getString("password");
        TSysOper sysOper = sysOperService.login(phoneNo, password);
        TSysRole role = sysOperService.getRoleByOperId(sysOper.getOperId());
        List<Long> menus = sysOperService.getUserRoleManageList(sysOper.getOperId());
        TokenDto tokenDto = UserUtil.refreshToken(sysOper, role.getRoleId());
        tokenDto.setMenus(menus);
        return returnSuccess(tokenDto);
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("logout")
    @ResponseBody
    public ResultInfo logout(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        UserUtil.removeAdminUser(UserUtil.currentAdminUser().getId());
        return returnSuccess();
    }
}
