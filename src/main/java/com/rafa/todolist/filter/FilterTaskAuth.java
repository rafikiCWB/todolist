package com.rafa.todolist.filter;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FilterTaskAuth implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       //executar alguma ação
        System.out.println("Chegou no filtro");
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
