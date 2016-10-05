package fr.gemao.view.adherent;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.ParametreCtrl;
import fr.gemao.entity.Parametre;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class SaisieCotisationServlet
 */
@WebServlet(Pattern.ADHERENT_SAISIE_COTISATION)
public class SaisieCotisationServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Adherent adherent = (Adherent) session
				.getAttribute("ajout_adh_adherent");
		
		ParametreCtrl parametreCtrl = new ParametreCtrl();
		Parametre param = parametreCtrl.getLast();
		request.setAttribute("params", param);		
		request.setAttribute("adherent", adherent);
		
		this.getServletContext()
				.getRequestDispatcher(JSPFile.ADHERENT_SAISIE_COTISATION)
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Adherent adherent = (Adherent) session
				.getAttribute("ajout_adh_adherent");
		adherent.setCotisation(Float.parseFloat(request.getParameter("cotisation")));
		session.setAttribute("ajout_adh_adherent", adherent);
		

		if (session.getAttribute("reinscription") == null) {
			response.sendRedirect(request.getContextPath()
				+ Pattern.ADHERENT_VALIDATION_AJOUT);
		} else { // Si on réinscrit
			session.setAttribute("reinscription", null);
			response.sendRedirect(request.getContextPath()
					+ Pattern.ADHERENT_VALIDATION_REINS);
		}
	}

}
