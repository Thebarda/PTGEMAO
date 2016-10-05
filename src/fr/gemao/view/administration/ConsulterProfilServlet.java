package fr.gemao.view.administration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.administration.ProfilsCtrl;
import fr.gemao.entity.administration.Module;
import fr.gemao.entity.administration.Profil;
import fr.gemao.entity.administration.TypeDroit;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class ConsulterProfilServlet
 */
@WebServlet(Pattern.ADMINISTRATION_CONSULTER_PROFIL)
public class ConsulterProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String PARAM_IDENTIFIANT = "id";
	public static final String ATTR_PROFIL = "profil";
	public static final String ATTR_LISTE_MODULE = "listeModules";
	public static final String ATTR_LISTE_TYPE_DROIT = "listeTypeDroit";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") == null) {
			// Arrivée invalide vers cette page, on renvoie vers la liste
			request.getRequestDispatcher(Pattern.ADMINISTRATION_LISTER_PROFIL).forward(request, response);
			return;
		}
		
		// Récupération des données du profil dont l'identifiant est passé en paramètre
		int id = Integer.parseInt(Form.getValeurChamp(request, PARAM_IDENTIFIANT));
		
		Profil p = new ProfilsCtrl().getProfil(id);
				
		// Passage des données à la page jsp
		request.setAttribute(ATTR_PROFIL, p);
		request.setAttribute(ATTR_LISTE_MODULE, Module.getAllModules());
		request.setAttribute(ATTR_LISTE_TYPE_DROIT, TypeDroit.getAllTypeDroit());
		request.getRequestDispatcher(JSPFile.ADMINISTRATION_CONSULTER_PROFIL).forward(request, response);
	}
}
