/**
 * 
 */
package fr.gemao.view.planning.creneau;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.planning.CreneauCtrl;
import fr.gemao.entity.planning.Creneau;
import fr.gemao.entity.planning.Planning;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;
import fr.gemao.view.ServletUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.CRENEAUX_LISTER)
public class AfficherCreneauxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer numPlanning = ServletUtil.getParameterIntegerValue("idPlanning", request);

		List<Creneau> listeCreneaux = numPlanning != null ? CreneauCtrl.getCreneaux(numPlanning) : null;
		Planning p = numPlanning != null ? CreneauCtrl.getPlanning(numPlanning) : null;

		if (listeCreneaux == null) {
			ResultatServlet.redirectErreur(request, response, "Erreur", "Le numéro du planning doit être indiqué.",
					Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null, null, null);
			return;
		}

		if (listeCreneaux.isEmpty()) {
			ResultatServlet.redirectErreur(request, response, "Erreur", "Aucun créneau enregistré pour ce planing.",
					Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null, null, null);
			return;
		}

		request.setAttribute("creneaux", listeCreneaux);
		request.setAttribute("planning", p);
		this.getServletContext().getRequestDispatcher(JSPFile.CRENEAUX_LISTER).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
