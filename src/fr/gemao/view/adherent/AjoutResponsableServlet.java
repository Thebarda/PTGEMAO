package fr.gemao.view.adherent;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.entity.Commune;
import fr.gemao.entity.adherent.Responsable;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class AjoutResponsableServlet
 */
@WebServlet(Pattern.ADHERENT_AJOUT_RESPONSABLE)
public class AjoutResponsableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_AJOUT_RESPONSABLE)
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		Responsable responsable = new Responsable(null, nom, prenom, tel, email);

		session.setAttribute("ajout_adh_responsable", responsable);
		Commune commune = (Commune) session.getAttribute("ajout_adh_commune"); 

		if(commune.isAvantage()){
			response.sendRedirect(request.getContextPath() + Pattern.ADHERENT_CALCUL_QF);
		}else{
			response.sendRedirect(request.getContextPath() + Pattern.ADHERENT_SAISIE_COTISATION);
		}
	}

}
