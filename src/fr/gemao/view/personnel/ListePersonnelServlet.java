package fr.gemao.view.personnel;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.personnel.PersonnelCtrl;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class ListePersonnelServlet
 */
@WebServlet(Pattern.PERSONNEL_LISTER)
public class ListePersonnelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Personnel> personnels = PersonnelCtrl.recupererTousPersonnels();
		request.setAttribute("listePersonnels", personnels);
		request.setAttribute("lien", JSPFile.PERSONNEL_CONSULTER);
		this.getServletContext().getRequestDispatcher(JSPFile.PERSONNEL_LISTER).forward(request, response);
	}
}