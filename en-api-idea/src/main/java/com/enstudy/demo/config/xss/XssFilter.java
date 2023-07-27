package com.enstudy.demo.config.xss;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
//因为请求中有富文本编辑器的数据，启用此过滤器会过滤到内容中的html标签
//所以这里未启动此过滤器
public class XssFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        XssHttpServletRequestWrapper wrapper=new XssHttpServletRequestWrapper(request);
        filterChain.doFilter(wrapper,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
