package fr.gemao.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import fr.gemao.view.Pattern;

/**
 * Filtre qui redirige directement vers la servlet de déconnexion
 * lorsque celle-ci est appelée.
 * Le filtre est nécessaire pour que n'importe qui puisse se déconnecter
 * à n'importe quel instant.
 */
public class DeconnectionFilter implements Filter {
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/* Redirection vers la page de déconnexion */
        request.getRequestDispatcher( Pattern.DECONNEXION ).forward( request, response );
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
