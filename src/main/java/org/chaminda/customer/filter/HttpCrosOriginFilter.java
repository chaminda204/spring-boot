package org.chaminda.customer.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class HttpCrosOriginFilter implements Filter {

	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain) throws IOException, ServletException {
		final HttpServletResponse servletResponse = (HttpServletResponse) response;
		servletResponse.setHeader("Access-Control-Allow-Origin", "*");
		servletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		servletResponse.setHeader("Access-Control-Max-Age", "3600");
		servletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) {

	}

	public void destroy() {}

}