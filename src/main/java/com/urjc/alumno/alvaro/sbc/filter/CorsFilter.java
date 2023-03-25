package com.urjc.alumno.alvaro.sbc.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component("CorsFilter")
@Slf4j
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletResponse res = (HttpServletResponse) servletResponse;
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "*");
        res.addHeader("Access-Control-Allow-Headers", "*");

        log.info("Received request -> {}", req.getRequestURI());

        if (Objects.equals(req.getMethod().toUpperCase(), HttpMethod.OPTIONS.name())) {

            log.info("PRELIGHT request successfully redirected");
            res.setStatus(HttpServletResponse.SC_OK);

        } else {

            filterChain.doFilter(req, res);

        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Cors filter created");
        Filter.super.init(filterConfig);
    }

}
