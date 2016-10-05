package fr.gemao.form.contrat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe de validation du formulaire Contrat
 * @author Coco
 *
 */
public class ContratForm {
	
	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_PRENOM = "prenom";
	private static final String CHAMP_TYPECONTRAT = "typeContrat";
	private static final String CHAMP_DATEDEBUT = "dateDebut";
	private static final String CHAMP_DATEFIN = "dateFin";
	
	private String nom;
	private String prenom;
	private String typeContrat;
	private Date dateDebut;
	private Date dateFin;
	
	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public String getTypeContrat() {
		return typeContrat;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	
	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	/* Méthode utilitaire qui retourne null si un champ est vide, et son contenu sinon */
	private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}
	
	/* Ajoute un message correspondant au champ spécifié à la map des erreurs. */
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}
	
	/**
	 * Méthode permettant de valider le nom sur le contrat
	 * @param nom : le nom sur le contrat
	 * @throws Exception
	 */
	private void validationNom(String nom) throws Exception {
		if (nom == null || nom.equals("")) {
			throw new Exception("Merci de saisir un nom valide.");
		}
	}
	
	/**
	 * Méthode permettant de vérifier le prénom sur le contrat
	 * @param prenom : le prénom sur le contrat
	 * @throws Exception
	 */
	private void validationPrenom(String prenom) throws Exception {
		if (prenom == null || prenom.equals("")) {
			throw new Exception("Merci de saisir un prénom valide.");
		}
	}

	/**
	 * Méthode permettant de vérifier le type du contrat
	 * @param typeContrat : le type du contrat
	 * @throws Exception
	 */
	private void validationTypeContrat(String typeContrat) throws Exception {
		if (typeContrat == null || typeContrat.equals("")) {
			throw new Exception("Merci de saisir un type de contrat valide.");
		}
	}

	/**
	 * Méthode permettant de vérifier la date de début du contrat
	 * @param dateDebut : la date de début du contrat
	 * @throws Exception
	 */
	private void validationDateDebut(Date dateDebut) throws Exception {
		if (dateDebut.equals("")) {
			throw new Exception("Merci de saisir une date de début du contrat valide.");
		}
	}
	
	/**
	 * Méthode permettant de vérifier la date de fin du contrat
	 * @param dateFin : la date de fin du contrat
	 * @throws Exception
	 */
	private void validationDateFin(Date dateFin) throws Exception {
		if (dateFin.equals("")) {
			throw new Exception("Merci de saisir une date de fin du contrat valide.");
		}
	}
	
	/**
	 * Méthode permettant de tester un Contrat
	 * @param request
	 */
	public void testerContrat(HttpServletRequest request) {

		/* Récupération des champs du formulaire */
		nom = getValeurChamp(request, CHAMP_NOM);
		prenom = getValeurChamp(request, CHAMP_PRENOM);
		typeContrat = getValeurChamp(request, CHAMP_TYPECONTRAT);
		//dateDebut = getValeurChamp(request, CHAMP_DATEDEBUT);
		//dateFin = getValeurChamp(request, CHAMP_DATEFIN);

		/* Validation du nom sur le contrat */
		try {
			validationNom(nom);
		} catch (Exception e) {
			setErreur(CHAMP_NOM, e.getMessage());
		}

		/* Validation du prénom sur le contrat */
		try {
			validationPrenom(prenom);
		} catch (Exception e) {
			setErreur(CHAMP_PRENOM, e.getMessage());
		}

		/* Validation du type de contrat */
		try {
			validationTypeContrat(typeContrat);
		} catch (Exception e) {
			setErreur(CHAMP_TYPECONTRAT, e.getMessage());
		}

		/* Validation de la date de début du contrat */
		try {
			validationDateDebut(dateDebut);
		} catch (Exception e) {
			setErreur(CHAMP_DATEDEBUT, e.getMessage());
		}
		
		/* Validation de la date de fin du contrat */
		try {
			validationDateFin(dateFin);
		} catch (Exception e) {
			setErreur(CHAMP_DATEFIN, e.getMessage());
		}
	}
}