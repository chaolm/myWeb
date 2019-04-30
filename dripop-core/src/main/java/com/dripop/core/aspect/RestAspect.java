package com.dripop.core.aspect;

import com.dripop.core.constant.BaseConstant;
import com.dripop.core.exception.RestException;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.SessionUtil;
import com.dripop.core.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by liyou on 2017/9/15.
 */
@Aspect
@Component
public class RestAspect {

    private static final Logger logger = LoggerFactory.getLogger(RestAspect.class);

    private static ThreadLocal<String> localId = new ThreadLocal<String>();

    @Pointcut(value = "(@within(org.springframework.web.bind.annotation.RestController) || @annotation(org.springframework.web.bind.annotation.ResponseBody)) && !@within(org.springframework.web.bind.annotation.ControllerAdvice)")
    private void pointcut() {
    }

    /**
     * 前置通知：目标方法执行之前执行以下方法体的内容
     * @param jp
     */
    @Before(value = "pointcut()")
    public void beforeMethod(JoinPoint jp){
        String methodName = jp.getSignature().getName();
        localId.set(StringUtil.getRadomString(20, 2));
        logger.info("logId:{}, 【前置通知】the method : {}", localId.get(), methodName);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(SessionUtil.getRequest().getInputStream(), "utf-8"));
            String line = null;
            StringBuffer reqJson = new StringBuffer();
            while ((line = br.readLine()) != null) {
                reqJson.append(line).append("\n");
            }
            if(reqJson.length() > 0) {
                reqJson.delete(reqJson.length()-1, reqJson.length());
            }
            SessionUtil.setRequest(BaseConstant.REST_REQ_BODY_KEY, reqJson.toString());
            logger.info("logId:{}, method param : {}", localId.get(), reqJson.toString());
            br.close();
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            SessionUtil.setRequest(BaseConstant.REST_REQ_BODY_KEY, e.getMessage());
        }
    }

    /**
     * 返回通知：目标方法正常执行完毕时执行以下代码
     * @param jp
     * @param result
     */
    @AfterReturning(value = "pointcut()", returning="result")
    public void afterReturningMethod(JoinPoint jp, Object result){
        String methodName = jp.getSignature().getName();
        String respBody = JsonUtil.toJson(result);
        SessionUtil.setRequest(BaseConstant.REST_RESP_BODY_KEY, respBody);
        logger.info("logId:{}, 【返回通知】the method : {}, return with : {}", localId.get(), methodName, respBody);
    }

    /**
     * 异常通知：目标方法发生异常的时候执行以下代码
     */
    @AfterThrowing(value = "pointcut()", throwing="e")
    public void afterThorwingMethod(JoinPoint jp, Exception e){
        String methodName = jp.getSignature().getName();
        if(e instanceof ServiceException) {
            logger.info("logId:" + localId.get() +", 【异常通知】the method 【" + methodName + "】 occurs exception: " + e.getMessage());
            throw new RestException(((ServiceException) e).getCode(), ((ServiceException) e).getData(), e.getMessage());
        }
        logger.info("logId:" + localId.get() + ", 【异常通知】the method 【" + methodName + "】 occurs exception: " + e.getMessage());
        logger.error("logId:" + localId.get() + ", 【异常通知】the method 【" + methodName + "】 occurs exception: " + e.getMessage(), e);
        throw new RestException(9999, e.getMessage());
    }

}
