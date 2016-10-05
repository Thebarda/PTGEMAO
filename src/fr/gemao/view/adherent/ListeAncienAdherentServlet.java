package fr.gemao.view.adherent;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class ListeAdherentServlet
 */
@WebServlet(Pattern.ADHERENT_LISTER_ANCIEN)
public class ListeAncienAdherentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Adherent> adherents = AdherentCtrl.recupererAnciensAdherents();
		request.setAttribute("listeAdherents", adherents);
		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_LISTER_ANCIEN).forward(request, response);
	}
}
