package fr.gemao.form.adherent;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ResponsableForm {
	
	/**
	 * Champs du formulaire
	 */
	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_PRENOM = "prenom";
	private static final String CHAMP_TELEPHONE = "telephone";
	private static final String CHAMP_EMAIL = "email";
	
	/**
	 * Variables récupérées via le formulaire
	 */
	private String nom;
	private String prenom;
	private String telephone;
	private String email;
	
	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	public String getResultat(){
		return this.resultat;
	}
	
	public Map<String, String> getErreurs(){
		return this.erreurs;
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public String getPrenom(){
		return this.prenom;
	}
	
	public String getTelephone(){
		return this.telephone;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	/*
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
	
	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}
	
	/**
	 * Méthode permettant de valider le nom du responsable
	 * @param nom
	 * @throws Exception
	 */
	private void validationNom(String nom) throws Exception {
		if (nom == null || nom.equals("")) {
			throw new Exception("Merci de saisir un nom valide.");
		}
	}
	
	/**
	 * Méthode permettant de valider le prénom du responsable
	 * @param prenom
	 * @throws Exception
	 */
	private void validationPrenom(String prenom) throws Exception {
		if (prenom == null || prenom.equals("")) {
			throw new Exception("Merci de saisir un nom valide.");
		}
	}
	
	/**
	 * Méthode permettant de valider le numéro de téléphone du responsable
	 * @param telephone
	 * @throws Exception
	 */
	private void validationTelephone(String telephone) throws Exception {
		if (telephone == null || telephone.equals("")) {
			throw new Exception("Merci de saisir un numéro de téléphone valide.");
		}
	}
	
	/**
	 * Méthode permettant de valider l'adresse email du responsable
	 * @param email
	 * @throws Exception
	 */
	private void validationEmail(String email) throws Exception {
		if (email == null || email.equals("")) {
			throw new Exception("Merci de saisir une adresse email valide.");
		}
	}

	public void testerAdherent(HttpServletRequest request) {

		//Récupération des champs du formulaire
		nom = getValeurChamp(request, CHAMP_NOM);
		prenom = getValeurChamp(request, CHAMP_PRENOM);
		telephone = getValeurChamp(request, CHAMP_TELEPHONE);
		email = getValeurChamp(request, CHAMP_EMAIL);

		//Validation du champ nom
		try {
			validationNom(nom);
		} catch (Exception e) {
			setErreur(CHAMP_NOM, e.getMessage());
		}
		
		//Validation du champ prénom
		try {
			validationPrenom(prenom);
		} catch (Exception e) {
			setErreur(CHAMP_PRENOM, e.getMessage());
		}
		
		//Validation du champ téléphone
		try {
			validationTelephone(telephone);
		} catch (Exception e) {
			setErreur(CHAMP_TELEPHONE, e.getMessage());
		}
		
		//Validation du champ email
		try {
			validationEmail(email);
		} catch (Exception e) {
			setErreur(CHAMP_EMAIL, e.getMessage());
		}
	}
}
