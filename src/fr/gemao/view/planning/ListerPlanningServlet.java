package fr.gemao.view.planning;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
@WebServlet(Pattern.PLANNING_LISTER)
public class ListerPlanningServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Planning> plannings = PlanningCtrl.getAllEnCoursLight();

		if (plannings.isEmpty()) {
			ResultatServlet.redirectErreur(request, response, "Aucun planning enregistré",
					"Il n'y a pas de planning enregistré !", Pattern.PLANNING_CREER, "Retour à la création de planning",
					null, null, null);
			return;
		}

		if (modifCaracteristiquePlanning(request, response, plannings)) {
			return;
		}

		if (dupliquerPlanning(request, response, plannings)) {
			return;
		}

		if (deletePlanning(request, response, plannings)) {
			return;
		}

		if (quickAddCreneau(request, response, plannings)) {
			return;
		}

		if (archivePlanning(request, response, plannings)) {
			return;
		}

		request.setAttribute("plannings", plannings);

		this.getServletContext().getRequestDispatcher(JSPFile.PLANNING_LISTER).forward(request, response);

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
	 * @param response
	 * @param ctrl
	 * @param plannings
	 * @return
	 * @throws IOException
	 */
	private boolean quickAddCreneau(HttpServletRequest request, HttpServletResponse response, List<Planning> plannings)
			throws IOException {
		String quickAdd = request.getParameter("quickAdd");
		if (quickAdd != null) {
			Integer idPlanningToAdd = Integer.parseInt(quickAdd);
			if (idPlanningToAdd != null) {
				final Planning p = PlanningCtrl.getPlanningDansListe(idPlanningToAdd, plannings);
				if (p != null) {
					HttpSession session = request.getSession();
					session.setAttribute("planning", p);
					response.sendRedirect("/GEMAO" + Pattern.CRENEAU_CREER);
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * @param request
	 * @param ctrl
	 * @param plannings
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean deletePlanning(HttpServletRequest request, HttpServletResponse response, List<Planning> plannings)
			throws ServletException, IOException {
		Integer idPlanningDelete = ServletUtil.getParameterIntegerValue("idPlanningDelete", request);
		if (idPlanningDelete != null) {
			final Planning planning = PlanningCtrl.getPlanningDansListe(idPlanningDelete, plannings);
			if (planning != null) {
				PlanningCtrl.delete(planning);
				ResultatServlet.redirect(request, response, "Résultat", "Le planning a bien été supprimé.",
						Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null, null, null);
				return true;
			}
		}
		return false;
	}

	/**
	 * @param request
	 * @param ctrl
	 * @param plannings
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean dupliquerPlanning(HttpServletRequest request, HttpServletResponse response,
			List<Planning> plannings) throws ServletException, IOException {
		Integer idPlanningDuplique = ServletUtil.getParameterIntegerValue("idPlanningDuplique", request);
		if (idPlanningDuplique != null) {
			final Planning planning = PlanningCtrl.getPlanningDansListe(idPlanningDuplique, plannings);
			if (planning != null) {
				PlanningCtrl.dupliquer(planning);
				ResultatServlet.redirect(request, response, "Résultat", "Le planning a bien été dupliqué.",
						Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null, null, null);
				return true;
			}
		}
		return false;
	}

	/**
	 * @param request
	 * @param response
	 * @param ctrl
	 * @param plannings
	 * @throws IOException
	 */
	private boolean modifCaracteristiquePlanning(HttpServletRequest request, HttpServletResponse response,
			List<Planning> plannings) throws IOException {
		Integer idToModif = ServletUtil.getParameterIntegerValue("idPlanningModif", request);

		Planning toModif = null;
		if (idToModif != null) {

			toModif = PlanningCtrl.getPlanningDansListe(idToModif, plannings);

			if (toModif != null) {
				HttpSession session = request.getSession();
				session.setAttribute("planning", toModif);
				response.sendRedirect("/GEMAO" + Pattern.PLANNING_MODIFIER_CARACTERISTIQUES);
				return true;
			}
		}

		return false;
	}

	/**
	 * @param request
	 * @param response
	 * @param ctrl
	 * @param plannings
	 * @return
	 * @throws ServletException
	 */
	private boolean archivePlanning(HttpServletRequest request, HttpServletResponse response, List<Planning> plannings)
			throws IOException, ServletException {
		Integer idPlanningArchive = ServletUtil.getParameterIntegerValue("idPlanningArchive", request);

		Planning planningArchive = null;
		if (idPlanningArchive != null) {

			planningArchive = PlanningCtrl.getPlanningDansListe(idPlanningArchive, plannings);

			if (planningArchive != null) {
				PlanningCtrl.archiver(planningArchive);
				ResultatServlet.redirect(request, response, "Résultat", "Le planning a bien été archivé.",
						Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null, null, null);
				return true;
			}
		}
		return false;
	}
}
