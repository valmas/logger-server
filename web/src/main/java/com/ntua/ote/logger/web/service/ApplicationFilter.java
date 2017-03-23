package com.ntua.ote.logger.web.service;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ntua.ote.logger.core.common.UserSessionBean;

/**
 * The Class that filters all the requests on VLS portal web
 */
@WebFilter(urlPatterns = { "/views/*", "/index.xhtml"})
public class ApplicationFilter implements Filter {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(ApplicationFilter.class);
	
	@Inject
	private UserSessionBean userSessionBean;

	/**
     * If in the user is already authenticated then the request is forwarded
     * else the request is dropped and the user is redirected in the login page
     * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (userSessionBean.isAuthenticated()) {
			chain.doFilter(request, response);
		} else {
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect(req.getContextPath() + "/login.xhtml");
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
