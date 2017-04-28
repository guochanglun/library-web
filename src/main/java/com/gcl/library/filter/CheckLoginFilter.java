package com.gcl.library.filter;

import com.gcl.library.bean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gcl on 2016/12/16.
 */
@WebFilter
public class CheckLoginFilter implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getRequestURI();
        req.setCharacterEncoding("utf-8");
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            if (url.endsWith("js") || url.endsWith("css") || url.contains("fonts") || url.contains("user") ||
                    url.endsWith("/")) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect("/");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }
}
