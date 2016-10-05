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
public class MaterielForm {

	private static final String CHAMP_CATEGORIE = "categorie";
	private static final String CHAMP_DESIGNATION = "designation";
	private static final String CHAMP_VALACH = "valeurAch";
	private static final String CHAMP_DATEACH = "dateAch";
	private static final String CHAMP_TYPE = "type";
	private static final String CHAMP_MARQUE = "marque";
	private static final String CHAMP_FOURNISSEUR = "fournisseur";

	// Partie Instrument
	private static final String CHAMP_INST_ETAT = "etat";
	private static final String CHAMP_INST_NUMSERIE = "numSerie";
	private static final String CHAMP_INST_VALREA = "valRea";
	private static final String CHAMP_INST_DEPLACABLE = "deplacable";
	private static final String CHAMP_INST_OBSERVATION = "observation";
	private static final String CHAMP_INST_LOUABLE = "louable";

	// Partie Mobilier
	private static final String CHAMP_MOBI_PRIXU = "prixU";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	/* Variables récupérées BEGIN */
	// variables communes aux deux categories
	private int idCategorie;
	private int idDesignation; //
	private float valAch;
	private String dateAch;
	private String type;
	private int idMarque;

	// variables categorie Instrument
	private int idEtat;
	private String numserie;
	private float valRea;
	private String observation;
	private boolean deplacable;
	private int idFournisseur;
	private boolean louable;

	// variables categorie Mobilier
	private float prixU;

	/* Variables recupérées END */

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void testerMateriel(HttpServletRequest request) {

		/* Récupération des champs du formulaire */

		dateAch = getValeurChamp(request, CHAMP_DATEACH);
		observation = getValeurChamp(request, CHAMP_INST_OBSERVATION);
		String fourniss = getValeurChamp(request, CHAMP_FOURNISSEUR);
		type = getValeurChamp(request, CHAMP_TYPE);
		String deplac = getValeurChamp(request, CHAMP_INST_DEPLACABLE);
		String loua = getValeurChamp(request, CHAMP_INST_LOUABLE);
		numserie = getValeurChamp(request, CHAMP_INST_NUMSERIE);

		/* Validation du champ categorie. */
		try {
			idCategorie = Integer.parseInt(getValeurChamp(request,
					CHAMP_CATEGORIE));
		} catch (Exception e) {
			setErreur(CHAMP_CATEGORIE, e.getMessage());
		}

		/* Validation du champ fournisseur. */
		try {
			validationFournisseur(fourniss);
		} catch (Exception e) {
			setErreur(CHAMP_FOURNISSEUR, e.getMessage());
		}

		/* Validation de la designation. */
		try {
			idDesignation = Integer.parseInt(getValeurChamp(request,
					CHAMP_DESIGNATION));
			validationDesignation(idDesignation);
		} catch (Exception e) {
			setErreur(CHAMP_DESIGNATION, e.getMessage());
		}

		/* Validation de la valeur d'achat. */
		try {
			valAch = Float.parseFloat(getValeurChamp(request, CHAMP_VALACH));
			validationValeurAchat(valAch);
		} catch (Exception e) {
			setErreur(CHAMP_VALACH, e.getMessage());
		}

		/* Validation du champ deplacable. */
		try {
			validationDeplacable(deplac);
		} catch (Exception e) {
			setErreur(CHAMP_TYPE, e.getMessage());
		}

		/* Validation du champ louable. */
		try {
			validationLouable(loua);
		} catch (Exception e) {
			setErreur(CHAMP_TYPE, e.getMessage());
		}

		/* Validation du champ type. */
		try {
			validationType(type);
		} catch (Exception e) {
			setErreur(CHAMP_TYPE, e.getMessage());
		}

		/* Validation du champ etat */
		try {
			idEtat = Integer.parseInt(getValeurChamp(request, CHAMP_INST_ETAT));
			validationEtat(idEtat);
		} catch (Exception e) {
			setErreur(CHAMP_INST_ETAT, e.getMessage());
		}

		/* Validation du champ marque */
		try {
			idMarque = Integer.parseInt(getValeurChamp(request, CHAMP_MARQUE));
			validationMarque(idMarque);
		} catch (Exception e) {
			setErreur(CHAMP_MARQUE, e.getMessage());
		}
		/* Validation du champ valeur de reaprovisionnement */
		try {
			valRea = Float
					.parseFloat(getValeurChamp(request, CHAMP_INST_VALREA));
			validationValeurReapprovisionnement(valRea);
		} catch (Exception e) {
			setErreur(CHAMP_INST_VALREA, e.getMessage());
		}

		type = getValeurChamp(request, CHAMP_TYPE);
		idMarque = Integer.parseInt(getValeurChamp(request, CHAMP_MARQUE));

		/* Validation du champ type. */
		try {
			validationType(type);
		} catch (Exception e) {
			setErreur(CHAMP_TYPE, e.getMessage());
		}

		/* Validation du champ marque */
		try {
			validationMarque(idMarque);
		} catch (Exception e) {
			setErreur(CHAMP_MARQUE, e.getMessage());
		}

		/* Initialisation du résultat global de la validation. */
		if (erreurs.isEmpty()) {
			resultat = "Succès de l'ajout.";
			if (deplac.equals("oui")) {
				deplacable = true;
			} else {
				deplacable = false;
			}
			if (loua.equals("oui")) {
				louable = true;
			} else {
				louable = false;
			}
			idFournisseur = Integer.parseInt(fourniss);
		} else {
			resultat = "Échec de l'ajout.";
		}
	}

	private void validationFournisseur(String fourniss) throws Exception {
		if (fourniss == null) {
			throw new Exception("Merci de choisir un fournisseur valide.");
		} else {
			if (fourniss.equals("")) {
				throw new Exception("Merci de choisir un fournisseur valide.");
			}
		}
	}

	/**
	 * Retourne la categorie du materiel.
	 * 
	 * @return la categorie du materiel.
	 */
	public int getCategorie() {
		return idCategorie;
	}

	/**
	 * Retourne la designation du materiel.
	 * 
	 * @return la designation du materiel.
	 */
	public int getDesignation() {
		return idDesignation;
	}

	/**
	 * Retourne la valeur d'achat du materiel.
	 * 
	 * @return la valeur d'achat du materiel.
	 */
	public float getValAch() {
		return valAch;
	}

	/**
	 * Retourne la date d'achat du materiel.
	 * 
	 * @return la date d'achat du materiel.
	 */
	public String getDateAch() {
		return dateAch;
	}

	/**
	 * Retourne l'id du type.
	 * 
	 * @return l'id du type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Retourne l'etat de l'instrument.
	 * 
	 * @return l'etat de l'instrument.
	 */
	public int getEtat() {
		return idEtat;
	}

	/**
	 * Retourne la marque du materiel.
	 * 
	 * @return la marque du materiel.
	 */
	public int getMarque() {
		return idMarque;
	}

	/**
	 * Retourne le numero de serie.
	 * 
	 * @return le numero de serie.
	 */
	public String getNumserie() {
		return numserie;
	}

	/**
	 * Retourne la valeur de réapprovisionnement de l'instrument.
	 * 
	 * @return la valeur de réapprovisionnement de l'instrument.
	 */
	public float getValRea() {
		return valRea;
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
	 * Retourne le prix unitaire du mobilier.
	 * 
	 * @return le prix unitaire du mobilier.
	 */
	public float getPrixU() {
		return prixU;
	}

	/**
	 * Retourne la déplacabilité(?) d'un instrument.
	 * 
	 * @return vrai si l'instrument est deplacable, faux sinon.
	 */
	public boolean getDeplacable() {
		return deplacable;
	}

	/**
	 * Retourne la louabilité(?) d'un instrument.
	 * 
	 * @return vrai si l'instrument est louable, faux sinon.
	 */
	public boolean getLouable() {
		return louable;
	}

	/**
	 * Retourne le fournisseur d'un materiel.
	 * 
	 * @return l'id du fournisseur du materiel
	 */
	public int getFournisseur() {
		return idFournisseur;
	}

	/**
	 * Valide la deplacabilité(?) saisie.
	 */
	private void validationDeplacable(String deplac) throws Exception {
		if (deplac == null) {
			throw new Exception(
					"Merci de choisir une valeur valide pour deplacable.");
		} else {
			if (!deplac.equals("oui") && !deplac.equals("non")) {
				throw new Exception("Merci de choisir une valeur valide pour deplacable.");
			}
		}
	}

	/**
	 * Valide la deplacabilité(?) saisie.
	 */
	private void validationLouable(String loua) throws Exception {
		if (loua == null) {
			throw new Exception(
					"Merci de choisir une valeur valide pour louable.");
		} else {
			if (!loua.equals("oui") && !loua.equals("non")) {
				throw new Exception("Merci de choisir une valeur valide pour louable.");
			}
		}
	}

	/**
	 * Valide la valeur de reapprovisionnement saisie.
	 */
	private void validationValeurReapprovisionnement(float valRea2)
			throws Exception {
		if (valRea2 < 0) {
			throw new Exception(
					"Merci de saisir une valeur de reapprovisionnement valide");
		}
	}

	/**
	 * Valide la valeur d'achat saisie.
	 */
	private void validationValeurAchat(float valAch2) throws Exception {
		if (valAch2 < 0) {
			throw new Exception("Merci de saisir une valeur d'achat valide");
		}
	}

	/**
	 * Valide la designation saisie.
	 */
	private void validationDesignation(int idDesignation2) throws Exception {
		if (idDesignation2 <= 0) {
			throw new Exception("Merci de saisir une designation valide.");
		}
	}

	/**
	 * Valide le prix unitaire saisi.
	 */
	private void validationPrixUnitaire(float prixU) throws Exception {
		if (prixU < 0) {
			throw new Exception("Merci de saisir un prix unitaire valide.");
		}
	}

	/**
	 * Valide le type choisi.
	 */
	private void validationType(String type2) throws Exception {
		if (type2 == null) {
			throw new Exception("Merci de saisir un type valide.");
		} else {
			if (type2.equals("")) {
				throw new Exception("Merci de saisir un type valide.");
			}
		}
	}

	/**
	 * Valide l'etat saisi.
	 */
	private void validationEtat(int idEtat2) throws Exception {
		if (idEtat2 <= 0) {
			throw new Exception("Merci de saisir un etat valide.");
		}
	}

	/**
	 * Valide la marque saisie.
	 */
	private void validationMarque(int idMarque2) throws Exception {
		if (idMarque2 <= 0) {
			throw new Exception("Merci de saisir une marque valide.");
		}
	}

	/**
	 * Valide le numero de serie saisi.
	 */
	private void validationNumserie(String numserie2) throws Exception {
		if (numserie2 == null) {
			throw new Exception("Merci de saisir un numero de serie valide.");
		} else {
			if (numserie2.equals("")) {
				throw new Exception(
						"Merci de saisir un numero de serie valide.");
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