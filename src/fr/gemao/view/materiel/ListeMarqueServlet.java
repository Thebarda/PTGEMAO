package fr.gemao.view.materiel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.materiel.MarqueCtrl;
import fr.gemao.entity.materiel.Marque;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.MATERIEL_LISTE_MARQUE)
public class ListeMarqueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("listMarque", MarqueCtrl.recupererToutesMarques());
		this.getServletContext()
				.getRequestDispatcher(JSPFile.MATERIEL_LISTE_MARQUE)
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
			Marque marque = new Marque();
			if (MarqueCtrl.ajouterMarque(lib)) {
				request.setAttribute("ajoutOK", true);
				session.setAttribute("listMarque",
						MarqueCtrl.recupererToutesMarques());
			} else {
				request.setAttribute("ajoutKO", true);
			}
		} else {
			try {
				int id1 = Integer.parseInt(request.getParameter("id"));
				Marque marque = MarqueCtrl.recupererMarque(id1);
				if (MarqueCtrl.supprimerMarque(marque.getNomMarque())) {
					request.setAttribute("modifOK", true);
					session.setAttribute("listMarque",
							MarqueCtrl.recupererToutesMarques());
				} else {
					request.setAttribute("modifKO", true);
				}
			} catch (Exception e) {
				request.setAttribute("modifKO", true);
				request.setAttribute("err", e.getMessage());
			}
		}

		this.getServletContext()
				.getRequestDispatcher(JSPFile.MATERIEL_LISTE_MARQUE)
				.forward(request, response);
	}

}
