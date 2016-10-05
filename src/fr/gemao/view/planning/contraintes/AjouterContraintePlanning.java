package fr.gemao.view.planning.contraintes;

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
import fr.gemao.ctrl.planning.PlanningCtrl;
import fr.gemao.ctrl.planning.SalleCtrl;
import fr.gemao.entity.Jour;
import fr.gemao.entity.cours.Cours;
import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.planning.Contrainte;
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
@WebServlet(Pattern.CONTRAINTE_ADD_GLOBALE)
// TODO non testée - lien de test :
// http://localhost:8080/GEMAO/Planning/ContrainteGlobale
public class AjouterContraintePlanning extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer numPlanning = ServletUtil.getParameterIntegerValue("idPlanning", request);
		List<Salle> listeSalle = SalleCtrl.getAllSalles();
		List<Jour> listeJour = new JourCtrl().getAllJours();
		List<Cours> listeCours = CoursCtrl.getAll();
		List<Planning> listePlanning = PlanningCtrl.getAllEnCoursLight();

		request.setAttribute("NUM_PLANNING", numPlanning);
		request.setAttribute("LISTE_PLANNINGS", listePlanning);
		request.setAttribute("LISTE_JOUR", listeJour);
		request.setAttribute("LISTE_SALLE", listeSalle);
		request.setAttribute("LISTE_COURS", listeCours);

		this.getServletContext().getRequestDispatcher(JSPFile.CONTRAINTE_ADD_GLOBALE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Contrainte> contraintes = new ArrayList<>();

		Integer idPlanning = Integer.parseInt(Form.getValeurChamp(request, "idPlanning"));
		Planning retour = PlanningCtrl.getPlanning(idPlanning);

		String jsonContrainte = Form.getValeurChamp(request, "jsonContraintes");
		JSONArray jsonContraintes = new JSONArray(jsonContrainte);

		for (int i = 0; i < jsonContraintes.length(); i++) {
			contraintes.add(ajouterContrainte(jsonContraintes.getJSONObject(i), retour));
		}
		for (Contrainte contrainte : contraintes) {
			Contrainte ret = ContrainteCtrl.create(contrainte);
		}

		ResultatServlet.redirect(request, response, "Résultat de la création de contraintes",
				"Les contraintes ont bien été créés.", Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null,
				null, null);

	}

	/**
	 * Permet d'ajouter une contrainte GLOBALE
	 * 
	 * @param contrainte
	 * @param planning
	 * @return
	 */
	private Contrainte ajouterContrainte(JSONObject contrainte, Planning planning) {
		Salle salle = null;
		Jour jour = null;
		HeurePlanning heureDebut = null;
		HeurePlanning heureFin = null;
		String message = null;

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

		return new Contrainte(null, null, planning.getIdPlanning(), salle, jour, heureDebut, heureFin, message);
	}
}
