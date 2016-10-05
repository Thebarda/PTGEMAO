/**
 * 
 */
package fr.gemao.view.planning;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.planning.PlanningCtrl;
import fr.gemao.entity.planning.Planning;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;
import fr.gemao.view.ServletUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.PLANNING_LISTER_ARCHIVE)
public class ListerPlanningArchiveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Planning> plannings = PlanningCtrl.getAllArchive();

		if (plannings.isEmpty()) {
			ResultatServlet.redirectErreur(request, response, "Aucun planning archivé",
					"Il n'y a pas de planning archivé !", Pattern.PLANNING_LISTER, "Retour à la liste des plannings",
					null, null, null);
			return;
		}

		if (desarchiver(request, response, plannings)) {
			return;
		}

		request.setAttribute("plannings", plannings);

		this.getServletContext().getRequestDispatcher(JSPFile.PLANNING_LISTER_ARCHIVE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @param request
	 * @param ctrl
	 * @param plannings
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean desarchiver(HttpServletRequest request, HttpServletResponse response, List<Planning> plannings)
			throws ServletException, IOException {

		Integer idPlanningDesarchive = ServletUtil.getParameterIntegerValue("idPlanningDesarchive", request);
		if (idPlanningDesarchive != null) {
			final Planning planning = PlanningCtrl.getPlanningDansListe(idPlanningDesarchive, plannings);
			if (planning != null) {
				PlanningCtrl.desarchiver(planning);
				ResultatServlet.redirect(request, response, "Résultat", "Le planning a bien été désarchivé.",
						Pattern.PLANNING_LISTER_ARCHIVE, "Retour à la liste des archives", null, null, null);
				return true;
			}
		}
		return false;
	}
}
