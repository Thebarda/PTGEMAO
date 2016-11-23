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
		String valeur = Form.getValeurChamp(request, nomChamp);
		if (valeur == null) {
			return null;
		} else {
			return valeur;
		}
	}
	
	/*private void validationDateDebut(String dateDebut) throws Exception{
		if (dateDebut == null || dateDebut.equals("")){
			throw new Exception("Merci de choisir une date valide.");
		}else{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date dateLoc = dateFormat.parse(dateDebut);
			Date today = new Date();
			String tmp = dateLoc.toString();
			today = dateFormat.parse(tmp);
			if(today.after(dateLoc)){
				throw new Exception("Merci spécifier une date supérieure à aujourd'hui.");
			}
		}
	}*/
	
	/**
	 * Valide la caution
	 * @param caution
	 * @throws Exception
	 */
	private void validationCaution(String caution) throws Exception{
		int nb = Integer.parseInt(caution);
		if(nb <= 0){
			throw new Exception("Merci de préciser une caution supérieur à 0");
		}
	}
	
	/**
	 * Valide le montant
	 * @param montant
	 * @throws Exception
	 */
	private void validationMontant(String montant) throws Exception{
		int nb = Integer.parseInt(montant);
		if(nb <= 0){
			throw new Exception("Merci de préciser un montant suéprieur à 0");
		}
	}
	
	/**
	 * Teste le formulaire interne
	 * @param request
	 */
	public void testFormulaireInterne(HttpServletRequest request){
		dateDebut=this.getValeurChamp(request, CHAMP_DATEDEBUT);
		caution=this.getValeurChamp(request, CHAMP_CAUTION);
		montant=this.getValeurChamp(request, CHAMP_MONTANT);

		/*try{
			validationDateDebut(dateDebut);
		}catch(Exception e){
			setErreur(CHAMP_DATEDEBUT, e.getMessage());
		}*/
		try{
			validationCaution(caution);
		}catch(Exception e){
			setErreur(CHAMP_CAUTION, e.getMessage());
		}
	}
	
	/**
	 * Teste le formulaire externe
	 * @param request
	 */
	public void testFormulaireExterne(HttpServletRequest request){
		montant=this.getValeurChamp(request, CHAMP_MONTANT);

		/*try{
			validationDateDebut(dateDebut);
		}catch(Exception e){
			setErreur(CHAMP_DATEDEBUT, e.getMessage());
		}*/
		try{
			validationMontant(montant);
		}catch(Exception e){
			setErreur(CHAMP_MONTANT, e.getMessage());
		}
	}
	
	/**
	 * Defini la date d'échéance pour une location interne donc à l'année N+1. L'année bisextile est gérée
	 * @param dateDebut
	 * @return
	 */
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
		String fin="";
		if((date.getYear()%4==0)&&((date.getYear()%400==0)||(date.getYear()%10!=0))&&(date.getMonth()==2)){
			c.add(Calendar.YEAR, 1);
			int month = 1+c.getTime().getMonth();
			int year = 1900+c.getTime().getYear();
			if(date.getDate()==29){
				 fin = ""+01+"/"+month+"/"+year;
			}else{
				fin = ""+date.getDate()+"/"+month+"/"+year;
			}
			dateFin = fin;
			return fin;
		}else{
			c.add(Calendar.YEAR, 1);
			int month = 1+c.getTime().getMonth();
			int year = 1900+c.getTime().getYear();
			fin = ""+c.getTime().getDate()+"/"+month+"/"+year;
			dateFin = fin;
			return fin;
		}
	}
	
	/**
	 * Defini la date d'échéance pour une location externe. L'année bisextile est gérée
	 * @param duree
	 * @param dateDebut
	 * @return
	 */
	public String setDateFinFormByDuree(int duree, String dateDebut){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateDeb = null;
		try {
			dateDeb = dateFormat.parse(dateDebut);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String fin="";
		int sommeMois = duree + dateDeb.getMonth();
		if(sommeMois>12){
			if((dateDeb.getYear()%4==0)&&((dateDeb.getYear()%400==0)||(dateDeb.getYear()%10!=0))&&(sommeMois%12==2)){
				sommeMois = sommeMois%12;
				int year = 1990+dateDeb.getYear()+1;
				dateDeb.setMonth(sommeMois);
				int mois = sommeMois;
				if(dateDeb.getDate()==29){
					mois = mois+1;
					fin = ""+01+"/"+mois+"/"+year;
				}else{
					fin = ""+dateDeb.getDate()+"/"+mois+"/"+year;
				}
			}else{
				int mois = (sommeMois%12)+1;
				int year = dateDeb.getYear()+1900;
				fin = ""+dateDeb.getDate()+"/"+mois+"/"+year;
			}
		}else{
			if((dateDeb.getYear()%4==0)&&((dateDeb.getYear()%400==0)||(dateDeb.getYear()%10!=0))&&(sommeMois%12==2)){
				sommeMois = sommeMois%12;
				int year = 1990+dateDeb.getYear();
				dateDeb.setMonth(sommeMois);
				int mois = sommeMois;
				if(dateDeb.getDate()==29){
					mois = mois+1;
					fin = ""+01+"/"+mois+"/"+year;
				}else{
					fin = ""+dateDeb.getDate()+"/"+mois+"/"+year;
				}
			}else{
				int mois = (sommeMois%12)+1;
				int year = dateDeb.getYear()+1900;
				fin = ""+dateDeb.getDate()+"/"+mois+"/"+year;
			}
		}
		return fin;
	}
}
