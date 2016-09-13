package ua.nure.efimov.summarytask4.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ua.nure.efimov.summarytask4.constants.ComonConstants;
import ua.nure.efimov.summarytask4.constants.PathConstants;
import ua.nure.efimov.summarytask4.utils.LogUtils;

/**
 * CharsetFilter.
 * 
 * Force the java webapp to handle all requests and responses as necessary
 * encoded.
 */
@WebFilter(urlPatterns = "/*")
public class CharsetFilter implements Filter {

	/**
	 * Encoding of application.
	 */
	private String encoding;

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(CharsetFilter.class);

	public void init(FilterConfig config) throws ServletException {
		if (encoding == null) {
			encoding = ComonConstants.ENCODING.getValue();
		}
		LOGGER.trace("Filter init.");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
			throws IOException, ServletException {

		/**
		 * Ingore all resources by path.
		 */
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String reqPath = httpRequest.getRequestURI();
		if (reqPath.startsWith(httpRequest.getContextPath() + PathConstants.RESOURCES_REL_PATH.getValue())) {
			LOGGER.trace("Ignore resources URL");
			next.doFilter(request, response); // Just continue chain.
		} else {

			/**
			 * Set specified character encoding
			 */
			if (request.getCharacterEncoding() == null) {
				request.setCharacterEncoding(encoding);
			}

			LogUtils.logDebug(LOGGER,
					"Encoding filter used to set: " + encoding + ", for: " + httpRequest.getRequestURI());
			next.doFilter(request, response);
		}
	}

	public void destroy() {
		LOGGER.trace("Filter destroyed.");
	}

}
