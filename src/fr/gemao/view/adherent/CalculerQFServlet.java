package fr.gemao.view.adherent;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class CalculerQFServlet
 */
@WebServlet(Pattern.ADHERENT_CALCUL_QF)
public class CalculerQFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CALCUL_QF).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Float quotient = AdherentCtrl.calculerQuotient(request);
		if (quotient != null) {

			HttpSession session = request.getSession();
			Adherent adherent = (Adherent) session.getAttribute("ajout_adh_adherent");
			adherent.setQf(quotient);
			session.setAttribute("ajout_adh_adherent", adherent);

			response.sendRedirect(request.getContextPath() + Pattern.ADHERENT_SAISIE_COTISATION);
		} else {
			request.setAttribute("erreurPers", true);
			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CALCUL_QF).forward(request, response);
		}
	}

}
