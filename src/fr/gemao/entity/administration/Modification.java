package fr.gemao.entity.administration;

import java.io.Serializable;
import java.util.Date;

import fr.gemao.entity.personnel.Personnel;

/**
 * Classe représentant une modification apportée à la base
 * à partir de l'appplication.
 * @author Benoît
 *
 */
public class Modification implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idModif;
	private Personnel personne;
	private Date dateModif;
	private String libelle;
	
	
	public Modification() {
		super();
	}
	
	public Modification(int idModif, Personnel personne, Date dateModif,
			String libelle) {
		super();
		this.idModif = idModif;
		this.personne = personne;
		this.dateModif = dateModif;
		this.libelle = libelle;
	}

	public int getIdModif() {
		return idModif;
	}

	public void setIdModif(int idModif) {
		this.idModif = idModif;
	}

	public Personnel getPersonne() {
		return personne;
	}

	public void setPersonne(Personnel personne) {
		this.personne = personne;
	}

	public Date getDateModif() {
		return dateModif;
	}

	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	
	
}
