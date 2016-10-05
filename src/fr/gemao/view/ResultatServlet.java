package fr.gemao.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe contenant uniquement le nom des variables utilisées dans la page
 * resultat.jsp. Les méthodes doGet et doPost ne sont pas implémentées car il
 * n'y a, a priori, aucune raison d'accéder directement à cette page.
 * 
 * @author Benoît
 *
 */
public class ResultatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String ATTR_TITRE_H1 = "titreH1", ATTR_RESULTAT = "resultat", ATTR_LIEN_BOUTON = "lienBouton",
			ATTR_NOM_BOUTON = "nomBouton", ATTR_LIEN_BONTON_2 = "lienBouton2", ATTR_NON_BOUTON_2 = "nomBouton2",
			ATTR_DOWNLOAD_BOUTON_2 = "downloadBouton2";

	public static void redirect(HttpServletRequest request, HttpServletResponse response, String titre, String message,
			String lienBouton, String nomBouton, String lienBouton2, String nomBouton2, String dlBouton)
					throws ServletException, IOException {

		request.setAttribute(ResultatServlet.ATTR_RESULTAT, message);
		request.setAttribute(ResultatServlet.ATTR_TITRE_H1, titre);
		request.setAttribute(ResultatServlet.ATTR_NOM_BOUTON, nomBouton);
		request.setAttribute(ResultatServlet.ATTR_LIEN_BOUTON, lienBouton);

		if (lienBouton2 != null && nomBouton2 != null) {
			request.setAttribute(ResultatServlet.ATTR_LIEN_BONTON_2, lienBouton2);
			request.setAttribute(ResultatServlet.ATTR_NON_BOUTON_2, nomBouton2);
		}

		if (dlBouton != null) {
			request.setAttribute(ResultatServlet.ATTR_DOWNLOAD_BOUTON_2, dlBouton);
		}
		request.getRequestDispatcher(JSPFile.RESULTAT).forward(request, response);
	}
	
	public static void redirectErreur(HttpServletRequest request, HttpServletResponse response, String titre, String message,
			String lienBouton, String nomBouton, String lienBouton2, String nomBouton2, String dlBouton)
					throws ServletException, IOException {

		request.setAttribute(ResultatServlet.ATTR_RESULTAT, message);
		request.setAttribute(ResultatServlet.ATTR_TITRE_H1, titre);
		request.setAttribute(ResultatServlet.ATTR_NOM_BOUTON, nomBouton);
		request.setAttribute(ResultatServlet.ATTR_LIEN_BOUTON, lienBouton);

		if (lienBouton2 != null && nomBouton2 != null) {
			request.setAttribute(ResultatServlet.ATTR_LIEN_BONTON_2, lienBouton2);
			request.setAttribute(ResultatServlet.ATTR_NON_BOUTON_2, nomBouton2);
		}

		if (dlBouton != null) {
			request.setAttribute(ResultatServlet.ATTR_DOWNLOAD_BOUTON_2, dlBouton);
		}
		request.getRequestDispatcher(JSPFile.ERREUR).forward(request, response);
	}
}
