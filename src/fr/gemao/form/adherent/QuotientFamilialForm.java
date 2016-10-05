package fr.gemao.form.adherent;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class QuotientFamilialForm {

	/**
	 * Champs du formulaire
	 */
	private static final String CHAMP_NBPERSONNES = "nbPersonnes";
	private static final String CHAMP_NBENFANTS = "nbPersonnes";
	private static final String CHAMP_REVENUS = "revenusAnnuels";
	
	/**
	 * Variables récupérées via le formulaire
	 */
	private Integer nbPersonnes;
	private Integer nbEnfants;
	private Float revenusAnnuels;
	
	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	public String getResultat(){
		return this.resultat;
	}
	
	public Map<String, String> getErreurs(){
		return this.erreurs;
	}
	
	public Integer getNbPersonnes(){
		return this.nbPersonnes;
	}
	
	public Integer getNbEnfants(){
		return this.nbEnfants;
	}
	
	public Float getRevenusAnnuels(){
		return this.revenusAnnuels;
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
	 * Méthode permettant de valider le nombre de personnes
	 * @param nbPersonnes
	 * @throws Exception
	 */
	private void validationNbPersonnes(Integer nbPersonnes) throws Exception {
		if (nbPersonnes == null || nbPersonnes.equals("") || nbPersonnes <= 0) {
			throw new Exception("Merci de saisir un nombre de personnes valide.");
		}
	}
	
	/**
	 * Méthode permettant de valider le nombre d'enfants
	 * @param nbEnfants
	 * @throws Exception
	 */
	private void validationNbEnfants(Integer nbEnfants) throws Exception {
		if (nbEnfants == null || nbEnfants.equals("") || nbEnfants < 0 || nbEnfants >= nbPersonnes) {
			throw new Exception("Merci de saisir un nombre d'enfants valide.");
		}
	}
	
	/**
	 * Méthode permettant de valider les revenus annuels
	 * @param revenusAnnuels
	 * @throws Exception
	 */
	private void validationRevenusAnnuels(Float revenusAnnuels) throws Exception {
		if (revenusAnnuels == null || revenusAnnuels.equals("") || revenusAnnuels < 0.0) {
			throw new Exception("Merci de saisir des revenus annuels valides.");
		}
	}

	public void testerAdherent(HttpServletRequest request) {

		//Récupération des champs du formulaire
		nbPersonnes = Integer.parseInt(getValeurChamp(request, CHAMP_NBPERSONNES));
		nbEnfants = Integer.parseInt(getValeurChamp(request, CHAMP_NBENFANTS));
		revenusAnnuels = Float.parseFloat(getValeurChamp(request, CHAMP_REVENUS));

		//Validation du champ nombre de personnes
		try {
			validationNbPersonnes(nbPersonnes);
		} catch (Exception e) {
			setErreur(CHAMP_NBPERSONNES, e.getMessage());
		}
		
		//Validation du champ nombre d'enfants
		try {
			validationNbEnfants(nbEnfants);
		} catch (Exception e) {
			setErreur(CHAMP_NBENFANTS, e.getMessage());
		}
		
		//Validation du champ revenus annuels
		try {
			validationRevenusAnnuels(revenusAnnuels);
		} catch (Exception e) {
			setErreur(CHAMP_REVENUS, e.getMessage());
		}
	}
}
