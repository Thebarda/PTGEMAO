package fr.gemao.view.personnel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.AdresseCtrl;
import fr.gemao.ctrl.CommuneCtrl;
import fr.gemao.ctrl.personnel.PersonnelCtrl;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;

/**
 * Servlet implementation class ValidationAjoutPersonnelServlet
 */
@WebServlet(Pattern.PERSONNEL_VALIDATION_MODIF)
public class ValidationModifPersonnelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("nom") == null) {
			this.getServletContext().getRequestDispatcher(JSPFile.ERREUR_404).forward(request, response);
		} else {
			HttpSession session = request.getSession();
			Personnel pers = (Personnel) session.getAttribute("modif_personnel");

			request.setAttribute("personnel", pers);

			this.getServletContext().getRequestDispatcher(JSPFile.PERSONNEL_VALIDATION_MODIF).forward(request,
					response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* Positionnement des attributs relatifs à la commune */
		HttpSession session = request.getSession();
		Personnel pers = (Personnel) session.getAttribute("modif_personnel");

		CommuneCtrl.ajoutCommune(pers.getAdresse().getCommune());
		AdresseCtrl.ajoutAdresse(pers.getAdresse());

		PersonnelCtrl.modifierPersonnel(pers);
		session.setAttribute("modif_personnel", null);

		request.setAttribute(ResultatServlet.ATTR_TITRE_H1, "Confirmation");
		request.setAttribute(ResultatServlet.ATTR_NOM_BOUTON, "Retour");
		request.setAttribute(ResultatServlet.ATTR_LIEN_BOUTON, Pattern.PERSONNEL_LISTER);
		request.setAttribute(ResultatServlet.ATTR_RESULTAT,
				"Le personnel " + pers.getNom() + " " + pers.getPrenom() + " a été modifié.");

		this.getServletContext().getRequestDispatcher(JSPFile.PERSONNEL_AJOUT3).forward(request, response);
	}
}