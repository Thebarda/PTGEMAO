package fr.gemao.view.planning.salle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.planning.SalleCtrl;
import fr.gemao.entity.cours.Salle;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;
import fr.gemao.view.ServletUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.SALLE_MODIFIER)
public class ModifierSalleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (isModifAllowed(request)) {
			Salle salle = SalleCtrl.getSalle(ServletUtil.getParameterIntegerValue("idSalleUpdate", request));
			request.setAttribute("salle", salle);
			this.getServletContext().getRequestDispatcher(JSPFile.SALLE_MODIFIER).forward(request, response);
		} else {
			ResultatServlet.redirect(request, response, "Erreur",
					"Impossible de modifier la salle, une erreur est survenue", Pattern.SALLE_LISTER,
					"Retour à la liste des salles", null, null, null);
		}

	}

	private boolean isModifAllowed(HttpServletRequest request) {
		Integer idSalleUpdate = ServletUtil.getParameterIntegerValue("idSalleUpdate", request);
		if (idSalleUpdate != null) {
			final Salle salle = SalleCtrl.getSalle(idSalleUpdate);
			if (salle != null) {
				return true;
			}
		}
		return false;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!isModifAllowed(request)) {
			ResultatServlet.redirect(request, response, "Erreur",
					"Impossible de modifier la salle, une erreur est survenue", Pattern.SALLE_LISTER,
					"Retour à la liste des salles", null, null, null);
			return;
		}

		// Récupération des données
		String nomSalle = Form.getValeurChamp(request, "nomSalle");

		Integer idSalle = ServletUtil.getParameterIntegerValue("idSalleUpdate", request);
		if (idSalle == null) {
			ResultatServlet.redirect(request, response, "Erreur",
					"Impossible de modifier la salle, une erreur est survenue...", Pattern.SALLE_LISTER,
					"Retour à la liste des salles", null, null, null);
			return;
		}

		Salle salleBefore = SalleCtrl.getSalle(idSalle);

		Salle salle = new Salle(salleBefore.getIdSalle(), nomSalle);

		Salle retour = SalleCtrl.update(salle);

		if (retour == null) {
			ResultatServlet.redirect(request, response, "Résultat de la modification d'une salle",
					"La salle n'a pas été modifiée.", Pattern.SALLE_LISTER, "Retour à la liste des salles ?", null,
					null, null);
			return;
		}

		ResultatServlet.redirect(request, response, "Résultat de la modification d'une salle",
				"La salle a bien été modifiée.", Pattern.SALLE_LISTER, "Retour à la liste des salles", null, null,
				null);
	}
}
