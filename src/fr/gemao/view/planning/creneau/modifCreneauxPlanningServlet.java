package fr.gemao.view.planning.creneau;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import fr.gemao.ctrl.planning.ModifierCreneauxCtrl;
import fr.gemao.ctrl.planning.PlanningCtrl;
import fr.gemao.ctrl.planning.SalleCtrl;
import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.planning.Planning;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class modifCreneauxPlanningServlet
 */
@WebServlet(Pattern.PLANNING_MODIFIERCRENEAUX)
public class modifCreneauxPlanningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public modifCreneauxPlanningServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final List<Salle> salles = SalleCtrl.getAll();
		Integer numPlanning = Integer.parseInt(request.getParameter("idPlanning"));

		Planning p = numPlanning != null ? PlanningCtrl.getPlanning(numPlanning) : null;

		request.setAttribute("planning", p);
		request.setAttribute("enModif", true);
		request.setAttribute("salles", salles);
		this.getServletContext().getRequestDispatcher(JSPFile.PLANNING_MODIFIERCRENEAUX).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = request.getParameter("creneaux");
		System.out.println(s);
		Integer idPlanning = Integer.parseInt(request.getParameter("idPlanning"));
		boolean estModifie = ModifierCreneauxCtrl.modifier(s, idPlanning);

		JSONObject jobj = new JSONObject();
		if (estModifie) {
			jobj.put("state", "succes");
			response.setStatus(200);
		} else {
			jobj.put("state", "echec");
			response.setStatus(500);
		}
		response.getWriter().write(jobj.toString());
	}

}
