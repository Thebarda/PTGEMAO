package fr.gemao.view.planning.contraintes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.JourCtrl;
import fr.gemao.ctrl.planning.ContrainteCtrl;
import fr.gemao.ctrl.planning.CoursCtrl;
import fr.gemao.ctrl.planning.CreneauCtrl;
import fr.gemao.ctrl.planning.SalleCtrl;
import fr.gemao.entity.Jour;
import fr.gemao.entity.cours.Cours;
import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.planning.Contrainte;
import fr.gemao.entity.planning.Creneau;
import fr.gemao.entity.util.HeurePlanning;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;
import fr.gemao.view.ServletUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.CONTRAINTE_MODIFIER)
public class ModifierContrainteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Creneau creneau = CreneauCtrl.getCreneau(ServletUtil.getParameterIntegerValue("idCreneau", request));

		if (isModifAllowed(request)) {
			Contrainte contrainte = ContrainteCtrl.get(ServletUtil.getParameterIntegerValue("idContrainte", request));

			List<Salle> listeSalle = SalleCtrl.getAllSalles();
			List<Jour> listeJour = new JourCtrl().getAllJours();
			List<Cours> listeCours = CoursCtrl.getAll();

			request.setAttribute("contrainte", contrainte);
			request.setAttribute("creneau", creneau);
			request.setAttribute("LISTE_JOUR", listeJour);
			request.setAttribute("LISTE_SALLE", listeSalle);
			request.setAttribute("LISTE_COURS", listeCours);

			this.getServletContext().getRequestDispatcher(JSPFile.CONTRAINTE_MODIFIER).forward(request, response);
		} else {
			ResultatServlet.redirectErreur(request, response, "Erreur",
					"Impossible de modifier la restriction, une erreur est survenue", Pattern.CRENEAU_MODIFIER
							+ "?idPlanning=" + creneau.getIdPlanning() + "&idCreneau=" + creneau.getIdCreneau(),
					"Retour à la liste des plannings ?", null, null, null);
		}
	}

	/**
	 * @param request
	 * @param ctrl
	 */
	private boolean isModifAllowed(HttpServletRequest request) {
		Integer idContrainte = ServletUtil.getParameterIntegerValue("idContrainte", request);
		Integer idCreneau = ServletUtil.getParameterIntegerValue("idCreneau", request);
		if (idCreneau != null && idContrainte != null) {
			final Contrainte c = ContrainteCtrl.get(idContrainte);
			final Creneau creneau = CreneauCtrl.getCreneau(c.getIdCreneau());
			if (creneau != null && c != null) {
				return true;
			}
		}
		return false;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JourCtrl ctrlJour = new JourCtrl();
		Creneau creneau = CreneauCtrl.getCreneau(ServletUtil.getParameterIntegerValue("idCreneau", request));
		Contrainte contrainteBefore = ContrainteCtrl.get(ServletUtil.getParameterIntegerValue("idContrainte", request));

		if (!isModifAllowed(request)) {
			ResultatServlet.redirectErreur(request, response, "Erreur",
					"Impossible de modifier la restriction, une erreur est survenue", Pattern.CRENEAU_MODIFIER
							+ "?idPlanning=" + creneau.getIdPlanning() + "&idCreneau=" + creneau.getIdCreneau(),
					"Retour au créneau ?", null, null, null);
		}

		// Récupération des données
		String message = null;
		if (Form.getValeurChamp(request, "messageContrainte") != "") {
			message = Form.getValeurChamp(request, "messageContrainte");
		}
		
		Salle salle = null;
		if (Form.getValeurChamp(request, "salleContrainte") != "-1") {
			salle = CreneauCtrl.getSalle((Integer.parseInt(Form.getValeurChamp(request, "salleContrainte"))));
		}
		Jour jour = null;
		if (Form.getValeurChamp(request, "jourContrainte") != "9") {
			jour = ctrlJour.getJour(Integer.parseInt(Form.getValeurChamp(request, "jourContrainte")));
		}

		HeurePlanning heureDeb = null;
		if (Form.getValeurChamp(request, "heureDebContrainte") != null
				&& Form.getValeurChamp(request, "minuteDebContrainte") != null) {
			heureDeb = new HeurePlanning(Integer.parseInt(Form.getValeurChamp(request, "heureDebContrainte")),
					Integer.parseInt(Form.getValeurChamp(request, "minuteDebContrainte")));
		} else if (Form.getValeurChamp(request, "heureDebContrainte") != null
				&& Form.getValeurChamp(request, "minuteDebContrainte") == null) {
			heureDeb = new HeurePlanning(Integer.parseInt(Form.getValeurChamp(request, "heureDebContrainte")), 0);
		} else if (Form.getValeurChamp(request, "heureDebContrainte") == null
				&& Form.getValeurChamp(request, "minuteDebContrainte") != null) {
			heureDeb = new HeurePlanning(0, Integer.parseInt(Form.getValeurChamp(request, "minuteDebContrainte")));
		}

		HeurePlanning heureFin = null;
		if (Form.getValeurChamp(request, "heureFinContrainte") != null
				&& Form.getValeurChamp(request, "minuteFinContrainte") != null) {
			heureFin = new HeurePlanning(Integer.parseInt(Form.getValeurChamp(request, "heureFinContrainte")),
					Integer.parseInt(Form.getValeurChamp(request, "minuteFinContrainte")));
		} else if (Form.getValeurChamp(request, "heureFinContrainte") != null
				&& Form.getValeurChamp(request, "minuteFinContrainte") == null) {
			heureFin = new HeurePlanning(Integer.parseInt(Form.getValeurChamp(request, "heureFinContrainte")), 0);
		} else if (Form.getValeurChamp(request, "heureFinContrainte") == null
				&& Form.getValeurChamp(request, "minuteFinContrainte") != null) {
			heureFin = new HeurePlanning(0, Integer.parseInt(Form.getValeurChamp(request, "minuteFinContrainte")));
		}

		Contrainte contrainte = new Contrainte(contrainteBefore.getIdContrainte(), contrainteBefore.getIdCreneau(),
				contrainteBefore.getIdPlanning(), salle, jour, heureDeb, heureFin, message);

		try {
			Contrainte retour = ContrainteCtrl.update(contrainte);

			if (retour == null) {
				ResultatServlet.redirectErreur(request, response, "Résultat de la modification d'une restriction",
						"La restriction n'a pas été modifiée.", Pattern.CRENEAU_MODIFIER + "?idPlanning="
								+ creneau.getIdPlanning() + "&idCreneau=" + creneau.getIdCreneau(),
						"Retour au créneau ?", null, null, null);
				return;
			}

			ResultatServlet.redirect(request, response, "Résultat de la modification d'une restriction",
					"La restriction a bien été modifiée.", Pattern.CRENEAU_MODIFIER + "?idPlanning="
							+ creneau.getIdPlanning() + "&idCreneau=" + creneau.getIdCreneau(),
					"Retour au créneau", null, null, null);

		} catch (IllegalStateException ie) {
			ResultatServlet.redirectErreur(request, response,
					"Une erreur est survenue", ie.getMessage(), Pattern.CRENEAU_MODIFIER + "?idPlanning="
							+ creneau.getIdPlanning() + "&idCreneau=" + creneau.getIdCreneau(),
					"Retour au créneau ?", null, null, null);
			return;
		}
	}
}
