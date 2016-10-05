/**
 * 
 */
package fr.gemao.ctrl.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.gemao.entity.Jour;
import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.planning.Creneau;
import fr.gemao.entity.util.HeurePlanning;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.JourDAO;
import fr.gemao.sql.cours.SalleDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.planning.CreneauDAO;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class ModifierCreneauxCtrl {

	private ModifierCreneauxCtrl() {

	}

	public static boolean modifier(String json, Integer idPlanning) {

		final JSONArray creneaux = ModifierCreneauxCtrl.getCreneauxJSON(json);
		final List<Creneau> creneauxModifies = new ArrayList<>();
		final List<Creneau> creneauxCrees = new ArrayList<>();
		SalleDAO salleDAO = DAOFactory.getInstance().getSalleDAO();
		JourDAO jourDAO = DAOFactory.getInstance().getJourDAO();

		List<Jour> jours = jourDAO.getAll();
		List<Salle> salles = salleDAO.getAll();
		for (int i = 0; i < creneaux.length(); i++) {
			final JSONObject creneau = creneaux.getJSONObject(i);
			Objects.requireNonNull(creneau);
			if (creneauAEteCree(creneau)) {
				creneauxCrees.add(convertirCreneau(creneau, idPlanning, jours, salles));
			} else if (creneauAEteModifie(creneau)) {
				creneauxModifies.add(convertirCreneau(creneau, idPlanning, jours, salles));
			}
		}
		System.out.println(creneauxModifies);
		final CreneauDAO creneauDAO = DAOFactory.getInstance().getCreneauDAO();
		try {
			for (final Creneau c : creneauxModifies) {
				creneauDAO.update(c);
			}
			for (final Creneau c : creneauxCrees) {
				creneauDAO.create(c);
			}
		} catch (DAOException d) {
			d.printStackTrace();
			return false;
		}
		return true;
	}

	private static JSONArray getCreneauxJSON(String json) {
		Objects.requireNonNull(json);
		return new JSONArray(json);
	}

	private static boolean creneauAEteCree(JSONObject creneau) {
		boolean cree = creneau.getBoolean("cree");
		return cree;
	}

	private static boolean creneauAEteModifie(JSONObject creneau) {
		boolean cree = creneau.getBoolean("cree");
		boolean modifie = creneau.getBoolean("modifie");
		return modifie && !cree;
	}

	private static Creneau convertirCreneau(JSONObject creneau, Integer idPlanning, List<Jour> jours,
			List<Salle> salles) {
		Integer id = null;
		if (!creneau.isNull("id")) {
			id = creneau.getInt("id");
		}
		final String intitule = creneau.getString("intitule");
		final HeurePlanning duree = HeurePlanning.depuisNombreQuartDHeure(creneau.getInt("nbQuartDHeure"));

		HeurePlanning debut = null;
		if (!creneau.isNull("heureDeb")) {
			debut = HeurePlanning.depuisString(creneau.getString("heureDeb"));
		}

		Jour j = null;
		if (!creneau.isNull("jour")) {
			j = getJourCorrespondant(jours, creneau.getString("jour"));
		}
		Salle salle = null;
		if (!creneau.isNull("salle")) {
			salle = getSalleCorrespondante(salles, creneau.getString("salle"));
		}

		final String couleur = creneau.getString("backgroundColor");

		return new Creneau(id, intitule, debut, duree, idPlanning, salle, j, couleur, null, null);
		// TODO : convertir js en java pour cours et contraintes

	}

	private static Salle getSalleCorrespondante(List<Salle> salles, String nomSalle) {
		for (final Salle s : salles) {
			if (s.getNomSalle().equals(nomSalle)) {
				return s;
			}
		}
		return null;
	}

	private static Jour getJourCorrespondant(List<Jour> jours, String nomJour) {
		for (final Jour j : jours) {
			if (j.getNomJour().equals(nomJour)) {
				return j;
			}
		}
		return null;
	}

}
