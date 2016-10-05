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

import fr.gemao.entity.personnel.Personnel;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.Pattern;

/**
 * Filtre qui regarde si la personne connectée doit changer son mot
 * de passe (première connexion ou bien suite à une réinitialisation)
 * et qui renvoie vers la page de changement de mot de passe si c'est
 * le cas.
 */
public class FirstConnectionFilter implements Filter {
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        /* Récupération de la session depuis la requête */
        HttpSession session = ((HttpServletRequest) request).getSession();
        
        
        if (session.getAttribute(AllowAccessFilter.ATTR_ALLOW_ACCESS)==null && (session.getAttribute( ConnexionServlet.ATT_SESSION_USER ) == null || ((Personnel)session.getAttribute( ConnexionServlet.ATT_SESSION_USER )).isPremiereConnexion() ) ) {
            /* Redirection vers la page de changement */
            request.getRequestDispatcher( Pattern.CHANGER_MOT_DE_PASSE ).forward( request, response );
        } else {
        	if(session.getAttribute(AllowAccessFilter.ATTR_ALLOW_ACCESS)!=null)
        		session.setAttribute(AllowAccessFilter.ATTR_ALLOW_ACCESS, null);
            /* On continue le filtrage */
            chain.doFilter( request, response );
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
