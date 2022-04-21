package com.example.grapgqldemo.controller.filters;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsOfFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ((HttpServletResponse)servletResponse).setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        ((HttpServletResponse)servletResponse).setHeader("Access-Control-Allow_Credentials", "true");
        ((HttpServletResponse)servletResponse).setHeader("Access-Control-Allow-Methods", "*");
        ((HttpServletResponse)servletResponse).setHeader("Access-Control-Max-Age", "3600");
        ((HttpServletResponse)servletResponse).setHeader("Access-Control-Allow-Headers", "*");
        ((HttpServletResponse)servletResponse).setHeader("Access-Control-Expose-Headers", "Authorisation");
        ((HttpServletResponse)servletResponse).setHeader("X-Requested-With","XMLHttpRequest");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}