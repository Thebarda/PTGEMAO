package fr.gemao.form.materiel;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe pour la validation du formulaire d'ajout de materiel. Seules les
 * variables en rapport avec la categorie (instrument ou mobilier) sont
 * initialisees (faire la verification).
 * 
 * @author kayzen
 *
 */
public class ModifierMaterielFournitureForm {

	private static final String CHAMP_MOBI_QTE = "quantite";
	private static final String CHAMP_MOBI_OBS = "observation";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	/* Variables récupérées BEGIN */
	private String observation;

	/* Variables recupérées END */

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void testerMateriel(HttpServletRequest request) {

		/* Validation du champ quantité */
		try {
			validationQuantite(getValeurChamp(request, CHAMP_MOBI_QTE));
		} catch (Exception e) {
			setErreur(CHAMP_MOBI_QTE, e.getMessage());
		}

		/* Initialisation du résultat global de la validation. */
		if (erreurs.isEmpty()) {
			resultat = "Succès de l'ajout.";
			observation = getValeurChamp(request, CHAMP_MOBI_OBS);
			if(observation == null){
				observation = new String("");
			}
		} else {
			resultat = "Échec de l'ajout.";
		}
	}

	/**
	 * Retourne l'observation sur le materiel.
	 * 
	 * @return l'observation sur le materiel.
	 */
	public String getObservation() {
		return observation;
	}

	/**
	 * Valide la quantite saisie.
	 */
	private void validationQuantite(String qte) throws Exception {
		if (qte == null) {
			throw new Exception("Merci de saisir une quantite valide.");
		} else {
			if (qte.isEmpty()) {
				throw new Exception("Merci de saisir une quantite valide.");
			}
		}
	}

	/**
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	public void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	/**
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValeurChamp(HttpServletRequest request,
			String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}
}
