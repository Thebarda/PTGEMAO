/**
 * 
 */
package fr.gemao.view.planning.creneau;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.gemao.ctrl.JourCtrl;
import fr.gemao.ctrl.planning.ContrainteCtrl;
import fr.gemao.ctrl.planning.CoursCtrl;
import fr.gemao.ctrl.planning.CreneauCtrl;
import fr.gemao.entity.Jour;
import fr.gemao.entity.cours.Cours;
import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.planning.Contrainte;
import fr.gemao.entity.planning.Creneau;
import fr.gemao.entity.planning.Planning;
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
@WebServlet(Pattern.CRENEAU_MODIFIER)
public class ModifierCreneauServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("idPlanning") == null) {
			Creneau creneau = CreneauCtrl.getCreneau(ServletUtil.getParameterIntegerValue("idCreneau", request));

			if (deleteContrainte(request, response, creneau)) {
				return;
			}
		}

		Integer numPlanning = Integer.parseInt(request.getParameter("idPlanning"));

		request.setAttribute("id", numPlanning);

		if (isModifAllowed(request)) {
			Creneau creneau = CreneauCtrl.getCreneau(ServletUtil.getParameterIntegerValue("idCreneau", request));

			List<Salle> listeSalle = CreneauCtrl.getAllSalles();
			List<Jour> listeJour = new JourCtrl().getAllJours();
			List<Contrainte> listeContrainte = ContrainteCtrl.getAllByIdCreneau(creneau.getIdCreneau());
			List<Cours> listeCours = CoursCtrl.getAll();

			request.setAttribute("creneau", creneau);
			request.setAttribute("LISTE_JOUR", listeJour);
			request.setAttribute("LISTE_SALLE", listeSalle);
			request.setAttribute("contraintes", listeContrainte);
			request.setAttribute("LISTE_COURS", listeCours);

			this.getServletContext().getRequestDispatcher(JSPFile.CRENEAU_MODIFIER).forward(request, response);
		} else {
			ResultatServlet.redirectErreur(request, response, "Erreur",
					"Impossible de modifier le créneau, une erreur est survenue", Pattern.PLANNING_LISTER,
					"Retour à la liste des plannings ?", null, null, null);
		}
	}

	/**
	 * @param request
	 * @param ctrl
	 */
	private boolean isModifAllowed(HttpServletRequest request) {
		Integer idPlanningUpdate = ServletUtil.getParameterIntegerValue("idPlanning", request);
		Integer idCreneauUpdate = ServletUtil.getParameterIntegerValue("idCreneau", request);
		if (idPlanningUpdate != null && idCreneauUpdate != null) {
			final Creneau c = CreneauCtrl.getCreneau(idCreneauUpdate);
			final Planning planning = CreneauCtrl.getPlanning(c.getIdPlanning());
			if (planning != null && c != null) {
				return true;
			}

		}

		return false;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JourCtrl ctrlJour = new JourCtrl();

		if (!isModifAllowed(request)) {
			ResultatServlet.redirectErreur(request, response, "Erreur",
					"Impossible de modifier le créneau, une erreur est survenue", Pattern.PLANNING_LISTER,
					"Retour à la liste des plannings", null, null, null);
			return;
		}

		// Récupération des données
		String libelle = Form.getValeurChamp(request, "libelle");

		Salle salle = CreneauCtrl.getSalle((Integer.parseInt(Form.getValeurChamp(request, "salle"))));

		Jour jour = ctrlJour.getJour(Integer.parseInt(Form.getValeurChamp(request, "jour")));

		String couleur = Form.getValeurChamp(request, "couleur");
		couleur = couleur.substring(1, 7);
		HeurePlanning heureDeb = null;
		if (Form.getValeurChamp(request, "heureDeb") != null && Form.getValeurChamp(request, "minuteDeb") != null) {
			heureDeb = new HeurePlanning(Integer.parseInt(Form.getValeurChamp(request, "heureDeb")),
					Integer.parseInt(Form.getValeurChamp(request, "minuteDeb")));
		} else if (Form.getValeurChamp(request, "heureDeb") != null
				&& Form.getValeurChamp(request, "minuteDeb") == null) {
			heureDeb = new HeurePlanning(Integer.parseInt(Form.getValeurChamp(request, "heureDeb")), 0);
		} else if (Form.getValeurChamp(request, "heureDeb") == null
				&& Form.getValeurChamp(request, "minuteDeb") != null) {
			heureDeb = new HeurePlanning(0, Integer.parseInt(Form.getValeurChamp(request, "minuteDeb")));
		}
		HeurePlanning duree = new HeurePlanning(Integer.parseInt(Form.getValeurChamp(request, "heureDuree")),
				Integer.parseInt(Form.getValeurChamp(request, "minuteDuree")));

		Integer idCreneau = ServletUtil.getParameterIntegerValue("idCreneau", request);
		if (idCreneau == null) {
			ResultatServlet.redirectErreur(request, response, "Erreur",
					"Impossible de modifier le créneau, une erreur est survenue", Pattern.PLANNING_LISTER,
					"Retour à la liste des plannings", null, null, null);
			return;
		}

		// Gestion Cours
		Integer idCours = Integer.valueOf(Form.getValeurChamp(request, "idCours"));
		Cours c = null;
		c = CoursCtrl.get(idCours);
		/*
		 * Si c == null, cela veut dire que l'utilisateur à décidé ne rien
		 * mettre dans l'un des deux champs Discipline et /ou prof. Dans ce cas,
		 * on ira mettre null dans idCours (Creneau) afin de supprimer
		 * l'association entre les deux.
		 */

		Creneau creneauBefore = CreneauCtrl.getCreneau(idCreneau);

		Creneau creneau = new Creneau(creneauBefore.getIdCreneau(), libelle, heureDeb, duree,
				creneauBefore.getIdPlanning(), salle, jour, couleur, c, null);

		try {
			if (!creneau.intersection(CreneauCtrl.getCreneaux(creneau.getIdPlanning())))
			{
				Creneau retour = CreneauCtrl.update(creneau);

				if (retour == null) {
					ResultatServlet.redirectErreur(request, response, "Résultat de la modification d'un créneau",
							"Le créneau n'a pas été modifié.", Pattern.PLANNING_LISTER, "Retour à la liste des plannings ?",
							null, null, null);
					return;
				}

				ResultatServlet.redirect(request, response, "Résultat de la modification d'un créneau",
						"Le créneau a bien été modifié.", Pattern.CRENEAUX_LISTER + "?idPlanning=" + retour.getIdPlanning(),
						"Retour à la liste des créneaux", null, null, null);
			} else {
				ResultatServlet.redirectErreur(request, response, "Résultat de la modification d'un créneau",
						"Le créneau n'a aps pû être modifié car déborde sur un autre.", Pattern.CRENEAUX_LISTER + "?idPlanning=" + creneau.getIdPlanning(),
						"Retour à la liste des créneaux", null, null, null);
			}
		} catch (IllegalStateException ie) {
			ResultatServlet.redirectErreur(request, response, "Une erreur est survenue", ie.getMessage(),
					Pattern.CRENEAUX_LISTER + "?idPlanning=" + creneau.getIdPlanning(),
					"Retour à la liste des créneaux de ce planning ?", null, null, null);
			return;
		}

		List<Contrainte> contraintes = new ArrayList<>();

		String jsonContrainte = Form.getValeurChamp(request, "jsonContraintes");

		System.out.println(jsonContrainte);

		JSONArray jsonContraintes = new JSONArray(jsonContrainte);

		for (int i = 0; i < jsonContraintes.length(); i++) {
			contraintes.add(ajouterContrainte(jsonContraintes.getJSONObject(i), creneau));
		}
		for (Contrainte contrainte : contraintes) {
			Contrainte ret = ContrainteCtrl.create(contrainte);
		}
	}

	/**
	 * @param request
	 * @param ctrl
	 * @param plannings
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean deleteContrainte(HttpServletRequest request, HttpServletResponse response, Creneau creneau)
			throws ServletException, IOException {
		Integer idContrainteDelete = ServletUtil.getParameterIntegerValue("idContrainteDelete", request);
		if (idContrainteDelete != null) {
			final Contrainte contrainte = ContrainteCtrl.get(idContrainteDelete);
			if (contrainte != null) {
				ContrainteCtrl.delete(contrainte);
				ResultatServlet.redirect(request, response, "Résultat",
						"La contrainte a bien été supprimée.", Pattern.CRENEAU_MODIFIER + "?idPlanning="
								+ creneau.getIdPlanning() + "&idCreneau=" + creneau.getIdCreneau(),
						"Retour au créneau", null, null, null);
				return true;
			}
		}
		return false;
	}

	private Contrainte ajouterContrainte(JSONObject contrainte, Creneau creneau) {
		Salle salle = null;
		Jour jour = null;
		HeurePlanning heureDebut = null;
		HeurePlanning heureFin = null;
		String message = null;
		Boolean isGlobale = false;

		if (!contrainte.isNull("idSalle")) {
			if (contrainte.getInt("idSalle") != -1) {
				salle = new Salle(contrainte.getInt("idSalle"), null);
			}
		}

		if (!contrainte.isNull("idJour")) {
			if (contrainte.getInt("idJour") != 9) {
				jour = new Jour(contrainte.getInt("idJour"), null);
			}
		}

		if (!contrainte.isNull("heureDebContrainte") && !contrainte.isNull("minuteDebContrainte")) {
			if (!contrainte.getString("heureDebContrainte").isEmpty()
					&& !contrainte.getString("minuteDebContrainte").isEmpty()) {
				heureDebut = new HeurePlanning(contrainte.getInt("heureDebContrainte"),
						contrainte.getInt("minuteDebContrainte"));
			}
		}
		if (!contrainte.isNull("heureFinContrainte") && !contrainte.isNull("minuteFinContrainte")) {
			if (!contrainte.getString("heureFinContrainte").isEmpty()
					&& !contrainte.getString("minuteFinContrainte").isEmpty()) {
				heureFin = new HeurePlanning(contrainte.getInt("heureFinContrainte"),
						contrainte.getInt("minuteFinContrainte"));
			}
		}

		if (!contrainte.isNull("messageContrainte")) {
			if (!contrainte.getString("messageContrainte").isEmpty()) {
				message = contrainte.getString("messageContrainte");
			}
		}

		if (!contrainte.isNull("isGlobale")) {
			if (!contrainte.getBoolean("isGlobale")) {
				isGlobale = contrainte.getBoolean("isGlobale");
			}
		}

		// TODO: idCreneau ? Vraiment ? (;)
		// return new Contrainte(null, creneau.getIdCreneau(), salle, jour,
		// heureDebut, heureFin, message, isGlobale);
		return null; // TODO apres insert
	}
}
