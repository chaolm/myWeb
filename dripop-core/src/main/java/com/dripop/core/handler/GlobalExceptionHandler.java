package com.dripop.core.handler;

import com.dripop.core.bean.ResultInfo;
import com.dripop.core.constant.BaseConstant;
import com.dripop.core.exception.RestException;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.SessionUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RestException.class)
    @ResponseBody
    public ResultInfo handleException(RestException e) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMessage((e.getCode() != null && e.getCode() == 9999) ? "系统服务繁忙，请稍后再试" : e.getMessage());
        resultInfo.setStatus((e.getCode() == null || e.getCode() == 9999) ? 1 : e.getCode());
        resultInfo.setBody(e.getData());
        return resultInfo;
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleException(Exception e) {
//        logger.error(e.getMessage(), e);
//        ModelAndView view = new ModelAndView("error");
//        return view;
//    }
}