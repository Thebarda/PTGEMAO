package fr.gemao.view.administration;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.administration.ModificationCtrl;
import fr.gemao.entity.administration.Modification;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class ListerModificationServlet
 */
@WebServlet(Pattern.ADMINISTRATION_LISTER_MODIFICATION)
public class ListerModificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static String ATTR_LISTE_MODIFS = "listeModifs";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Modification> listeModifs = ModificationCtrl.getAllModifications();

		request.setAttribute(ATTR_LISTE_MODIFS, listeModifs);

		request.getRequestDispatcher(JSPFile.ADMINISTRATION_LISTER_MODIFICATIONS).forward(request, response);
	}

}
