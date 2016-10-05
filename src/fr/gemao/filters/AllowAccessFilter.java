package fr.gemao.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filtre rendant vide une variable de session afin d'autoriser l'accès à une page sans avoir besoin d'être
 * connecté. Si cette variable n'est pas vidée, le filtre {@link CheckConnectedFilter} restreint l'accès si
 * l'utilisateur ne s'est pas authentifié.
 */
public class AllowAccessFilter implements Filter {

	public static final String ATTR_ALLOW_ACCESS = "filtre_acces_page_autorise";
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		return;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
		
		/* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        session.setAttribute(ATTR_ALLOW_ACCESS, "");

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		return;
	}

}
