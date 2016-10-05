package fr.gemao.form.location;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import fr.gemao.form.util.Form;

public class LocationForm {
	
	private static final String CHAMP_CATEGORIE = "categorie";
	private static final String CHAMP_DESIGNATION = "designation";
	private static final String CHAMP_ADHERENT = "adherent";
	private static final String CHAMP_DATEDEBUT = "datedeb";
	private static final String CHAMP_DATEFIN = "datefin";
	
	private String categorie;
	private String designation;
	private String adherent;
	private String dateDebut, dateFin;
	
	private Map<String, String> erreurs = new HashMap<String, String>();


	public LocationForm(HttpServletRequest request) {
		categorie	= Form.getValeurChamp(request, CHAMP_CATEGORIE);
		designation	= Form.getValeurChamp(request, CHAMP_DESIGNATION);
		adherent	= Form.getValeurChamp(request, CHAMP_ADHERENT);
		dateDebut	= Form.getValeurChamp(request, CHAMP_DATEDEBUT);
		dateFin		= Form.getValeurChamp(request, CHAMP_DATEFIN);
		
		// TODO
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

	public void setDesignation(String designation) {
		this.designation = designation;
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

	public Map<String, String> getErreurs() {
		return erreurs;
	}

}
