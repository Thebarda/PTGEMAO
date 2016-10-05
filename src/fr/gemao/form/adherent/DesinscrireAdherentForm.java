package fr.gemao.form.adherent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe pour la validation du formulaire de désinscription d'un adhérent
 * @author claire
 *
 */
public class DesinscrireAdherentForm {

	/**
	 * Champs du formulaire de désinscription d'un adhérent
	 */
	private static final String CHAMP_DATESORTIE = "dateSortie";
	private static final String CHAMP_MOTIFSORTIE = "motifSortie";
	
	
	/**
	 * Variables récupérées via le formulaire de désinscription d'un adhérent
	 */
	private String dateSortie;
	private String motifSortie;
	
	
	//Gestion des erreurs
	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	//Format des dates
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public String getResultat(){
		return this.resultat;
	}
	
	public Map<String, String> getErreurs(){
		return this.erreurs;
	}
	
	public String getDateSortie(){
		return this.dateSortie;
	}
	
	public String getMotifSortie(){
		return this.motifSortie;
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
	public void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}
	
	/**
	 * Méthode permettant de valider la date de sortie de l'adhérent
	 * @param dateSortie
	 * @throws Exception
	 */
	private void validationDateSortie(String dateSortie) throws Exception {
		Date dateSort = new Date();
		try {
			dateSort = dateFormat.parse(dateSortie);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (dateSort == null || dateSort.equals("") || dateSort.before(new Date())) {
			throw new Exception("Merci de saisir une date de sortie valide.");
		}
	}
	
	/**
	 * Méthode permettant de valider le motif de sortie de l'adhérent
	 * @param motifSortie
	 * @throws Exception
	 */
	//A modifier !
	private void validationMotifSortie(String motifSortie) throws Exception {
		if (motifSortie.isEmpty() || motifSortie == null) {
			throw new Exception("Merci de saisir un motif de sortie.");
		}
	}
	
	public void testerAdherent(HttpServletRequest request) {

		//Récupération des champs du formulaire
		dateSortie = getValeurChamp(request, CHAMP_DATESORTIE);
		motifSortie = getValeurChamp(request, CHAMP_MOTIFSORTIE);

		//Validation du champ date de sortie
		try {
			validationDateSortie(dateSortie);
		} catch (Exception e) {
			setErreur(CHAMP_DATESORTIE, e.getMessage());
		}
		
		//Validation du champ motif de sortie
		try {
			validationMotifSortie(motifSortie);
		} catch (Exception e) {
			setErreur(CHAMP_MOTIFSORTIE, e.getMessage());
		}
	}
}
