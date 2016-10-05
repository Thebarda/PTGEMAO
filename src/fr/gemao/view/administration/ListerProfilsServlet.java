package fr.gemao.view.administration;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.administration.ProfilsCtrl;
import fr.gemao.entity.administration.Profil;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.ADMINISTRATION_LISTER_PROFIL)
public class ListerProfilsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ATTR_LISTE_PROFILS = "listeProfils";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProfilsCtrl ctrl = new ProfilsCtrl();
		List<Profil> listeProfils = ctrl.getAllProfils();
		
		// Renvoi vers la liste des profils
		request.setAttribute(ATTR_LISTE_PROFILS, listeProfils);
		request.getRequestDispatcher(JSPFile.ADMINISTRATION_LISTER_PROFILS).forward(request, response);
	}
}
