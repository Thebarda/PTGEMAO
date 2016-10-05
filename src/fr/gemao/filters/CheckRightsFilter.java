package fr.gemao.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.gemao.entity.personnel.Personnel;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.JSPFile;

/**
 * Filtre qui teste que la personne connectée possède bien les droits
 * nécessaires pour accéder à la page demandée.
 * Fonctionnement :
 * 		- Si personne n'est connecté, on laisse le filtre 
 * 		{@link CheckConnectedFilter} s'en occuper
 * 		- Si la page n'appartient pas à un module, l'accès est accordé
 * 		à tous le monde (Accueil, changement de mot de passe, ...)
 * 		- Sinon, on regarde l'URL :
 * 			~ /GEMAO/module1/page correspond à une page du module1
 * 			nécessitant les droits de lecture sur ce module
 * 			~ /GEMAO/module1-w/page correspond à une page du module1
 * 			nécessitant les droits de lecture et d'écriture sur de module
 * Mieux vaut éviter les accents dans les URL pour des raisons d'encodage.
 */
public class CheckRightsFilter implements Filter {
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession();
		
		if(session.getAttribute(AllowAccessFilter.ATTR_ALLOW_ACCESS)!=null){
			// Accès autorisé (CSS, js par exemple)
			chain.doFilter(req, res);
			return;
		}
		
		Personnel personneConnectee = (Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER);
		
		if(personneConnectee==null){
			// Personne n'est connecté, le filtre CheckConnected s'en chargera
			chain.doFilter(req, res);
			return;
		}
		
		if(personneConnectee.getProfil() == null){
			// Aucun profil n'est associé à cet utilisateur => Erreur de droit
			request.getRequestDispatcher(JSPFile.ERREUR_DROIT).forward(request, res);
			return;
		}
		
		
		// Récupération de l'URI et décomposition
		String[] tab = request.getRequestURI().split("/");
		
		// Le premier élément de tab est le nom de l'application
		if(tab.length < 4){
			// Page hors d'un module
			// => pas besoin de droits
		} else {
			// Page dans un module
			String[] partiesModule = tab[2].split("-");
			String nomModule = partiesModule[0];
			
			// Problèmes d'accents et d'encodage des uri
			if(nomModule.equals("Adherent")){
				nomModule = "Adhérent";
			}
			if(nomModule.equals("Materiel")){
				nomModule = "Matériel";
			}
			
			if(partiesModule.length == 2){
				// Si le droit d'écriture doit être renseigné
				if(!personneConnectee.getProfil().recupererTypeDroit(nomModule).equals("Lecture/écriture")){
					// Refus de l'accès
					request.getRequestDispatcher(JSPFile.ERREUR_DROIT).forward(request, res);
					return;
				}
			} else {
				// Seul le droit de lecture est nécessaire
				if(personneConnectee.getProfil().recupererTypeDroit(nomModule).equals("Aucun")){
					// Refus de l'accès
					request.getRequestDispatcher(JSPFile.ERREUR_DROIT).forward(request, res);
					return;
				}
			}
		}

		// pass the request along the filter chain
		chain.doFilter(req, res);

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
