package fr.gemao.form.location;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import fr.gemao.form.util.Form;

public class LocationForm {
	
	private static final String CHAMP_CATEGORIE = "categorie";
	private static final String CHAMP_DESIGNATION = "nomDesignation";
	private static final String CHAMP_ADHERENT = "adherent";
	private static final String CHAMP_DATEDEBUT = "debutLocation";
	private static final String CHAMP_CAUTION = "caution";
	private static final String CHAMP_MONTANT = "montant";
	private static final String CHAMP_DATEFIN = "finLocation";
	
	private String categorie;
	private String designation;
	private String adherent;
	private String dateDebut;
	private String dateFin;
	private String caution;
	private String montant;
	private String resultat;
	
	private Map<String, String> erreurs = new HashMap<String, String>();


	public LocationForm(HttpServletRequest request) {
		categorie	= Form.getValeurChamp(request, CHAMP_CATEGORIE);
		designation	= Form.getValeurChamp(request, CHAMP_DESIGNATION);
		adherent	= Form.getValeurChamp(request, CHAMP_ADHERENT);
		dateDebut	= Form.getValeurChamp(request, CHAMP_DATEDEBUT);
		dateFin		= Form.getValeurChamp(request, CHAMP_DATEFIN);
		caution		= Form.getValeurChamp(request, CHAMP_CAUTION);
		montant 	= Form.getValeurChamp(request, CHAMP_MONTANT);
		resultat = "";
		// TODO
	}
	
	public LocationForm(){
		
	}
	
	/**
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String materiel) {
		this.designation = materiel;
	}

	public String getAdherent() {
		return adherent;
	}

	public void setAdherent(String adherent) {
		this.adherent = adherent;
	}

	public String getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	
	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	public void setResultat(String resultat){
		this.resultat = resultat;
	}
	
	public String getResultat(){
		return this.resultat;
	}
	
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}
	
	private void validationDateDebut(String dateDebut) throws Exception{
		if (dateDebut == null || dateDebut.equals("")){
			throw new Exception("Merci de choisir une date valide.");
		}else{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date dateLoc = dateFormat.parse(dateDebut);
			Date today = new Date();
			String tmp = dateLoc.toString();
			today = dateFormat.parse(tmp);
			if(dateLoc.before(today)){
				throw new Exception("Merci spécifier une date supérieure à aujourd'hui.");
			}
		}
	}
	
	private void validationCaution(String caution) throws Exception{
		int nb = Integer.parseInt(caution);
		if(nb <= 0){
			throw new Exception("Merci de préciser une caution supérieur à 0");
		}
	}
	
	private void validationMontant(String montant) throws Exception{
		int nb = Integer.parseInt(montant);
		if(nb <= 0){
			throw new Exception("Merci de préciser un montant suéprieur à 0");
		}
	}

	public void testFormulaire(HttpServletRequest request){
		categorie = LocationForm.getValeurChamp(request, CHAMP_CATEGORIE);
		designation=this.getValeurChamp(request, CHAMP_DESIGNATION);
		adherent=this.getValeurChamp(request, CHAMP_ADHERENT);
		dateDebut=this.getValeurChamp(request, CHAMP_DATEDEBUT);
		dateFin=this.getValeurChamp(request, CHAMP_DATEFIN);
		caution=this.getValeurChamp(request, CHAMP_CAUTION);
		montant=this.getValeurChamp(request, CHAMP_MONTANT);

		try{
			validationDateDebut(dateDebut);
		}catch(Exception e){
			setErreur(CHAMP_DATEDEBUT, e.getMessage());
		}
		try{
			validationCaution(caution);
		}catch(Exception e){
			setErreur(CHAMP_CAUTION, e.getMessage());
		}
		try{
			validationMontant(montant);
		}catch(Exception e){
			setErreur(CHAMP_MONTANT, e.getMessage());
		}
		
		if(erreurs.isEmpty()){
			this.setResultat("Location ajouté avec succés");
		}else{
			this.setResultat("Location contenant des erreurs");
		}
	}
	
	public String setDateFinForm(String dateDebut) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date=null;
		try {
			date = dateFormat.parse(dateDebut);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, 1);
		int month = 1+c.getTime().getMonth();
		int year = 1900+c.getTime().getYear();
		String fin = ""+c.getTime().getDate()+"/"+month+"/"+year;
		dateFin = fin;
		return fin;
	}
}
