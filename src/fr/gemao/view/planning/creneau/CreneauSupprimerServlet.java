/**
 * 
 */
package fr.gemao.view.planning.creneau;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.planning.CreneauCtrl;
import fr.gemao.entity.planning.Creneau;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;
import fr.gemao.view.ServletUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.CRENEAU_SUPPRIMER)
public class CreneauSupprimerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer idCreneau = ServletUtil.getParameterIntegerValue("idCreneau", request);

		if (idCreneau == null) {
			ResultatServlet.redirect(request, response, "Erreur",
					"Impossible de supprimer le créneau, une erreur est survenue", Pattern.PLANNING_LISTER,
					"Retour à la liste des planningS", null, null, null);
			return;
		}

		Creneau c = CreneauCtrl.getCreneau(idCreneau);
		CreneauCtrl.deleteCreneau(c);

		ResultatServlet.redirect(request, response, "Résultat", "Le créneau a été supprimé.",
				Pattern.CRENEAUX_LISTER + "?idPlanning=" + c.getIdPlanning(), "Retour à la liste des créneaux", null,
				null, null);
		return;

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
