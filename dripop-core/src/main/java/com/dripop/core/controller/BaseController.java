package com.dripop.core.controller;

import com.dripop.core.bean.DateEditor;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.exception.ServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by liyou on 2017/9/5.
 */
public class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new DateEditor(true));
    }

    /**
     * 简单校验接口参数
     * @param result
     */
    public void valid(BindingResult result) {
        if(result.hasErrors()) {
            String message = "不能为null";
            if(message.equals(result.getFieldError().getDefaultMessage())) {
                throw new ServiceException("缺失必要参数");
            }
            throw new ServiceException(result.getFieldError().getDefaultMessage());
        }
    }

    public ResultInfo returnSuccess(Object data) {
        ResultInfo result = new ResultInfo();
        result.setStatus(0);
        result.setBody(data);
        return result;
    }

    public ResultInfo returnSuccess() {
        ResultInfo result = new ResultInfo();
        result.setStatus(0);
        return result;
    }

    public ModelAndView createView(String viewName) {
        ModelAndView view = new ModelAndView(viewName);
        return view;
    }

}
