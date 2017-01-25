package fr.gemao.entity.materiel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import fr.gemao.entity.Personne;


/**
 * The Class Location.
 */
public class Location implements Serializable, Comparable<Location>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The id */
	private int id;
	
	/** The personne. */
	private Personne personne;
	
	/** The materiel. */
	private Materiel materiel;
	
	/** The etat debut. */
	private Etat etatDebut;
	
	/** The etat fin. */
	private Etat etatFin;
	
	/** The date emprunt. */
	private String dateEmprunt;
	
	/** The date retour. */
	private String dateRetour;
	
	/** The duree. */
	private String dateEcheance;
	
	/** The montant. */
	private float montant;
	
	private int caution;
	
	/** The reparation. */
	private Reparation reparation;

	/** The nom du contrat. */
	private String nomContrat;
	
	private String type;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Location [ id="+id+", personne=" + personne + ", materiel=" + materiel
				+ ", etatDebut=" + etatDebut + ", etatFin=" + etatFin
				+ ", dateEmprunt=" + dateEmprunt + ", dateRetour=" + dateRetour
				+ ", dateEcheance=" + dateEcheance + ", montant=" + montant + "]";
	}

	public Location() {
	}
	
	/**
	 * Instantiates a new location.
	 *
	 * @param personne the personne
	 * @param materiel the materiel
	 * @param etatDebut the etat debut
	 * @param etatFin the etat fin
	 * @param dateEmprunt the date emprunt
	 * @param dateRetour the date retour
	 * @param dateEcheance the date echeance
	 * @param montant the montant
	 * @param reparation the reparation
	 */
	public Location(int id, Personne personne, Materiel materiel, Etat etatDebut,
			Etat etatFin, String dateEmprunt, String dateRetour, String dateEcheance, int caution,
			float montant, Reparation reparation, String nomContrat, String type) {
		this.id = id;
		this.personne = personne;
		this.materiel = materiel;
		this.etatDebut = etatDebut;
		this.etatFin = etatFin;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
		this.dateEcheance = dateEcheance;
		this.montant = montant;
		this.caution = caution;
		this.reparation = reparation;
		this.nomContrat = nomContrat;
		this.type=type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCaution(int caution) {
		this.caution = caution;
	}

	/**
	 * Gets the montant.
	 *
	 * @return the montant
	 */
	public float getMontant() {
		return montant;
	}

	/**
	 * Sets the montant.
	 *
	 * @param montant the new montant
	 */
	public void setMontant(float montant) {
		this.montant = montant;
	}

	/**
	 * Gets the personne.
	 *
	 * @return the personne
	 */
	public Personne getPersonne() {
		return personne;
	}

	/**
	 * Gets the materiel.
	 *
	 * @return the materiel
	 */
	public Materiel getMateriel() {
		return materiel;
	}

	/**
	 * Gets the etat debut.
	 *
	 * @return the etat debut
	 */
	public Etat getEtatDebut() {
		return etatDebut;
	}

	/**
	 * Gets the etat fin.
	 *
	 * @return the etat fin
	 */
	public Etat getEtatFin() {
		return etatFin;
	}

	/**
	 * Gets the date emprunt.
	 *
	 * @return the date emprunt
	 */
	public String getDateEmprunt() {
		return dateEmprunt;
	}

	/**
	 * Gets the date retour.
	 *
	 * @return the date retour
	 */
	public String getDateRetour() {
		return dateRetour;
	}

	

	public Reparation getReparation() {
		return reparation;
	}

	public void setReparation(Reparation reparation) {
		this.reparation = reparation;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public void setEtatDebut(Etat etatDebut) {
		this.etatDebut = etatDebut;
	}

	public void setEtatFin(Etat etatFin) {
		this.etatFin = etatFin;
	}

	public void setDateEmprunt(String dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	public void setDateRetour(String dateRetour) {
		this.dateRetour = dateRetour;
	}

	public String getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(String dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public int getCaution(){
		return this.caution;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}

	public String getNomContrat(){
		return this.nomContrat;
	}
	
	public void setNomContrat(String nomContrat){
		this.nomContrat = nomContrat;
	}
	
	@Override
	public int compareTo(Location loc) {
		DateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		int res = 0;
		try {
			Date thisEmprunt = sdf.parse(this.getDateEmprunt());
			Date emprunt = sdf.parse(loc.getDateEmprunt());
			res = thisEmprunt.compareTo(emprunt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
}
