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

import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.Pattern;

/**
 * Filtre qui vérifie si l'utilisateur est connecté ou si
 * l'accès a été autorisé par le filtre {@link AllowAccessFilter}
 * Si l'accès est refusé, on renvoie vers la page de connexion
 */
public class CheckConnectedFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
		/* Récupération de la session depuis la requête */
        HttpSession session = ((HttpServletRequest) request).getSession();
        
        /**
         * Si l'objet utilisateur n'existe pas dans la session en cours, alors
         * l'utilisateur n'est pas connecté.
         */
        if ( session.getAttribute( ConnexionServlet.ATT_SESSION_USER ) == null && session.getAttribute(AllowAccessFilter.ATTR_ALLOW_ACCESS)==null ) {
            /* Redirection vers la page de connexion */
            //response.sendRedirect( request.getContextPath() + "/Connexion" );
            request.getRequestDispatcher( Pattern.CONNEXION ).forward( request, response );
        } else {
        	
            /* On continue le filtrage */
            chain.doFilter( request, response );
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
