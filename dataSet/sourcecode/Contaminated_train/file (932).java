package com.sidian.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.util.NestedServletException;

import com.sidian.exception.ResponseException;

public class ControllerFilter extends AbstractController implements Filter {

	@Override
	public void destroy() {

	}

	private static Logger logger = LogManager.getLogger(ControllerFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {

			if (e instanceof NestedServletException) {
				Throwable t = e.getCause();

				if (t instanceof ResponseException) {
					responseServerError(t, (HttpServletRequest) request, (HttpServletResponse) response);
				} else {
					logger.error("Fatal error when user try to call API ", e);
					responseServerError(e, (HttpServletRequest) request, (HttpServletResponse) response);

				}
			} else {
				logger.error("Fatal error when user try to call API ", e);
				responseServerError(e, (HttpServletRequest) request, (HttpServletResponse) response);
			}

		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
