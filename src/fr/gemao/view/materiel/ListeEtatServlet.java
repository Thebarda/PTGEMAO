package fr.gemao.view.materiel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.materiel.EtatCtrl;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.MATERIEL_LISTE_ETAT)
public class ListeEtatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("listEtat", EtatCtrl.getListeEtat());
		this.getServletContext()
				.getRequestDispatcher(JSPFile.MATERIEL_LISTE_ETAT)
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();

		if (id == 0) {
			String lib = new String(request.getParameter("libelle"));
			Etat etat = new Etat();
			if (EtatCtrl.ajoutEtat(lib)) {
				request.setAttribute("ajoutOK", true);
				session.setAttribute("listEtat", EtatCtrl.getListeEtat());
			} else {
				request.setAttribute("ajoutKO", true);
			}
		} else {
			try {
				int id1 = Integer.parseInt(request.getParameter("id"));
				Etat etat = EtatCtrl.recupererEtat(id1);
				if (EtatCtrl.supprimerEtat(etat.getLibelleEtat())) {
					request.setAttribute("modifOK", true);
					session.setAttribute("listEtat", EtatCtrl.getListeEtat());
				} else {
					request.setAttribute("modifKO", true);
				}
			} catch (Exception e) {
				request.setAttribute("modifKO", true);
				request.setAttribute("err", e.getMessage());
			}
		}

		this.getServletContext()
				.getRequestDispatcher(JSPFile.MATERIEL_LISTE_ETAT)
				.forward(request, response);
	}

}
