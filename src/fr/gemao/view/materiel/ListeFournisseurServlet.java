package fr.gemao.view.materiel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.materiel.FournisseurCtrl;
import fr.gemao.entity.materiel.Fournisseur;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.MATERIEL_LISTE_FOURNISSEUR)
public class ListeFournisseurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("listFour", FournisseurCtrl.getListeFournisseur());
		this.getServletContext()
				.getRequestDispatcher(JSPFile.MATERIEL_LISTE_FOURNISSEUR)
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
			String lib = new String(request.getParameter("lib"));
			Fournisseur four = new Fournisseur();
			if (FournisseurCtrl.ajoutFournisseur(lib)) {
				request.setAttribute("ajoutOK", true);
				session.setAttribute("listFour",
						FournisseurCtrl.getListeFournisseur());
			} else {
				request.setAttribute("ajoutKO", true);
			}
		} else {
			try {
				int id1 = Integer.parseInt(request.getParameter("id"));
				Fournisseur four = FournisseurCtrl.recupererFournisseur(id1);
				if (FournisseurCtrl.supprimerFournisseur(four
						.getNomFournisseur())) {
					request.setAttribute("modifOK", true);
					session.setAttribute("listFour",
							FournisseurCtrl.getListeFournisseur());
				} else {
					request.setAttribute("modifKO", true);
				}
			} catch (Exception e) {
				request.setAttribute("modifKO", true);
				request.setAttribute("err", e.getMessage());
			}
		}

		this.getServletContext()
				.getRequestDispatcher(JSPFile.MATERIEL_LISTE_FOURNISSEUR)
				.forward(request, response);
	}

}
