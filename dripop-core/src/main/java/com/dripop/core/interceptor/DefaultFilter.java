package com.dripop.core.interceptor;

import com.dripop.core.bean.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by liyou on 2017/10/14.
 */
public class DefaultFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(DefaultFilter.class);

    private static final String REQUEST_CONTEXT = "requestContext";

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest myRequestWrapper = null;
        if(servletRequest instanceof HttpServletRequest) {
            try {
                myRequestWrapper = new MyRequestWrapper((HttpServletRequest) servletRequest);
            }catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        }
        initContext(servletRequest);
        if(myRequestWrapper != null) {
            filterChain.doFilter(myRequestWrapper, servletResponse);
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {

    }

    private void initContext(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        Object obj = request.getSession().getAttribute(REQUEST_CONTEXT);
        if(obj == null) {
            RequestContext requestContext = new RequestContext();
            String basePath = request.getRequestURL().toString().replace(request.getServletPath(), "");
            requestContext.setBasePath(basePath);
            request.getSession().setAttribute(REQUEST_CONTEXT, requestContext);
        }
    }
}
