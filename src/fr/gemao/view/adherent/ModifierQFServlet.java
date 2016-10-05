package fr.gemao.view.adherent;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.ParametreCtrl;
import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.entity.Parametre;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class ModifierQFServlet
 */
@WebServlet(Pattern.ADHERENT_MODIFER_QF)
public class ModifierQFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("id") == null) {
			request.setAttribute("lien", "/adherent/ConsulteAdherent");
			List<Adherent> adherents = AdherentCtrl.recupererTousAdherents();
			ParametreCtrl parametreCtrl = new ParametreCtrl();
			Parametre param = parametreCtrl.getLast();
			request.setAttribute("params", param);
			request.setAttribute("listeAdherents", adherents);
			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_LISTER).forward(request, response);
		} else {

			int id = Integer.parseInt(request.getParameter("id"));
			Adherent adherent = AdherentCtrl.recupererAdherent(id);

			HttpSession session = request.getSession();

			session.setAttribute("modif_QF_adherent", adherent);

			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CALCUL_QF).forward(request, response);
		}
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

			Adherent adherent = (Adherent) session.getAttribute("modif_QF_adherent");
			adherent.setQf(quotient);

			if (AdherentCtrl.modifierAdherent(adherent))
				request.setAttribute("modifOK", true);
			else
				request.setAttribute("modifOK", false);

			request.setAttribute("adherent", adherent);
			session.setAttribute("modif_QF_adherent", null);
			ParametreCtrl parametreCtrl = new ParametreCtrl();
			Parametre param = parametreCtrl.getLast();
			request.setAttribute("params", param);

			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CONFIRMATION_MODIFICATION_QF)
					.forward(request, response);
		} else {
			request.setAttribute("erreurPers", true);
			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CALCUL_QF).forward(request, response);
		}
	}

}
