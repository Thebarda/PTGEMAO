package fr.gemao.entity.adherent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.entity.Personne;
import fr.gemao.entity.cours.Classe;
import fr.gemao.entity.cours.Cours;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.util.Civilite;

public class Adherent extends Personne implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MotifSortie motif;
	private Responsable responsable;
	private boolean droitImage;
	private Date dateEntree;
	private Date dateSortie;
	private Float qf;
	private float cotisation;
	private List<Discipline> disciplines;
	private List<Classe> classes;
	private List<Cours> listeCours;
	private boolean aPaye;
	private Famille famille;

	public Adherent() {
		super();
		disciplines = new ArrayList<>();
		classes = new ArrayList<>();
		listeCours = new ArrayList<Cours>();
	}

	/**
	 * Construit un Adh�rent
	 * 
	 * @param idPersonne
	 * @param idAdresse
	 * @param idCommuneNaiss
	 * @param nom
	 * @param prenom
	 * @param dateNaissance
	 * @param telFixe
	 * @param telPort
	 * @param email
	 * @param idMotif
	 * @param idResponsable
	 * @param droitImage
	 * @param dateEntree
	 * @param dateSortie
	 * @param qf
	 * @param cotisation
	 * @param disciplines
	 * @param classes
	 */
	public Adherent(Long idPersonne, Adresse adresse, Commune communeNaiss,
			String nom, String prenom, Date dateNaissance, String telFixe,
			String telPort, String email, Civilite civilite, boolean membreCA, MotifSortie motif,
			Responsable responsable, boolean droitImage, Date dateEntree,
			Date dateSortie, Float qf, float cotisation,
			List<Discipline> disciplines, List<Classe> classes,
			List<Cours> cours, boolean aPaye, Famille famille) {
		super(idPersonne, adresse, communeNaiss, nom, prenom,
				dateNaissance, telFixe, telPort, email, civilite, membreCA);
		this.motif = motif;
		this.responsable = responsable;
		this.droitImage = droitImage;
		this.dateEntree = dateEntree;
		this.dateSortie = dateSortie;
		this.qf = qf;
		this.cotisation = cotisation;
		if(disciplines == null){
			disciplines = new ArrayList<>();
		}
		this.disciplines = disciplines;
		this.classes = classes;
		this.listeCours = cours;
		this.aPaye = aPaye;
		this.famille = famille;
	}

	/**
	 * Construit un Adh�rent.
	 * 
	 * @param personne
	 * @param idMotif
	 * @param idResponsable
	 * @param droitImage
	 * @param dateEntree
	 * @param dateSortie
	 * @param qf
	 * @param classes
	 */
	public Adherent(Personne personne, MotifSortie motif,
			Responsable responsable, boolean droitImage, Date dateEntree,
			Date dateSortie, Float qf, float cotisation,
			List<Discipline> disciplines, List<Classe> classes,
			List<Cours> cours, boolean aPaye, Famille famille) {
		super(personne);
		this.motif = motif;
		this.responsable = responsable;
		this.droitImage = droitImage;
		this.dateEntree = dateEntree;
		this.dateSortie = dateSortie;
		this.qf = qf;
		this.cotisation = cotisation;
		if(disciplines == null){
			disciplines = new ArrayList<>();
		}
		this.disciplines = disciplines;
		this.classes = classes;
		this.listeCours = cours;
		this.aPaye = aPaye;
		this.famille = famille;
	}

	/**
	 * @return the idMotif
	 */
	public MotifSortie getMotif() {
		return motif;
	}

	/**
	 * @param idMotif
	 *            the idMotif to set
	 */
	public void setMotif(MotifSortie motif) {
		this.motif = motif;
	}

	public boolean isAPaye() {
		return aPaye;
	}

	public void setAPaye(boolean aPaye) {
		this.aPaye = aPaye;
	}

	/**
	 * @return the idResponsable
	 */
	public Responsable getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable
	 *            the responsable to set
	 */
	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	/**
	 * @return the droitImage
	 */
	public boolean isDroitImage() {
		return droitImage;
	}

	/**
	 * @param droitImage
	 *            the droitImage to set
	 */
	public void setDroitImage(boolean droitImage) {
		this.droitImage = droitImage;
	}

	/**
	 * @return the dateEntree
	 */
	public Date getDateEntree() {
		return dateEntree;
	}

	/**
	 * @param dateEntree
	 *            the dateEntree to set
	 */
	public void setDateEntree(Date dateEntree) {
		this.dateEntree = dateEntree;
	}

	/**
	 * @return the dateSortie
	 */
	public Date getDateSortie() {
		return dateSortie;
	}

	/**
	 * @param dateSortie
	 *            the dateSortie to set
	 */
	public void setDateSortie(Date dateSortie) {
		this.dateSortie = dateSortie;
	}

	/**
	 * @return the qf
	 */
	public Float getQf() {
		return qf;
	}

	/**
	 * @param qf
	 *            the qf to set
	 */
	public void setQf(float qf) {
		this.qf = qf;
	}

	public Float getCotisation() {
		return cotisation;
	}

	public void setCotisation(float cotisation) {
		this.cotisation = cotisation;
	}

	/**
	 * @return the disciplines
	 */
	public List<Discipline> getDisciplines() {
		return disciplines;
	}

	/**
	 * @param disciplines
	 *            the disciplines to set
	 */
	public void setDisciplines(List<Discipline> disciplines) {
		this.disciplines = disciplines;
	}
	
	/**
	 * Ajoute une discipline à l'adhérent.
	 * @param discipline
	 */
	public boolean addDiscipline(Discipline discipline){
		return disciplines.add(discipline);
	}
	
	/**
	 * Supprime une discipline à l'adhérent.
	 * @param discipline
	 * @return
	 */
	public boolean removeDiscipline(Discipline discipline){
		return disciplines.remove(discipline);
	}
	
	/**
	 * Ajout d'une classe suivie par l'adhérent
	 * @param classe
	 * @return
	 */
	public boolean addClasse(Classe classe){
		return classes.add(classe);
	}
	
	/**
	 * Supprime une classe 
	 * @param classe
	 * @return
	 */
	public boolean removeClasse(Classe classe){
		return disciplines.remove(classe);
	}
	
	public List<Classe> getClasses() {
		return classes;
	}

	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}

	public void setQf(Float qf) {
		this.qf = qf;
	}

	public boolean addAllDisciplines(List<Discipline> list){
		boolean rs = true;
		for (Discipline discipline : list) {
			rs = disciplines.add(discipline);
			if(!rs){
				rs = false;
			}
		}
		return rs;
	}

	public boolean ajouterCours(Cours cours){
		return listeCours.add(cours);
	}
	
	public boolean removeCours(Cours cours){
		return listeCours.remove(cours);
	}

	public List<Cours> getCours() {
		return listeCours;
	}

	public void setCours(List<Cours> cours) {
		this.listeCours = cours;
	}

	/**
	 * @return the famille
	 */
	public Famille getFamille() {
		return famille;
	}

	/**
	 * @param famille
	 *            the famille to set
	 */
	public void setFamille(Famille famille) {
		this.famille = famille;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Adherent [" + super.toString() + ", motif=" + motif
				+ ", responsable=" + responsable + ", droitImage=" + droitImage
				+ ", dateEntree=" + dateEntree + ", dateSortie=" + dateSortie
				+ ", qf=" + qf + ", cotisation=" + cotisation
				+ ", disciplines=" + disciplines + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((classes == null) ? 0 : classes.hashCode());
		result = prime * result + Float.floatToIntBits(cotisation);
		result = prime * result
				+ ((dateEntree == null) ? 0 : dateEntree.hashCode());
		result = prime * result
				+ ((dateSortie == null) ? 0 : dateSortie.hashCode());
		result = prime * result
				+ ((disciplines == null) ? 0 : disciplines.hashCode());
		result = prime * result + (droitImage ? 1231 : 1237);
		result = prime * result + ((famille == null) ? 0 : famille.hashCode());
		result = prime * result
				+ ((listeCours == null) ? 0 : listeCours.hashCode());
		result = prime * result + ((motif == null) ? 0 : motif.hashCode());
		result = prime * result + ((qf == null) ? 0 : qf.hashCode());
		result = prime * result
				+ ((responsable == null) ? 0 : responsable.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adherent other = (Adherent) obj;
		if (classes == null) {
			if (other.classes != null)
				return false;
		} else if (!classes.equals(other.classes))
			return false;
		if (Float.floatToIntBits(cotisation) != Float
				.floatToIntBits(other.cotisation))
			return false;
		if (dateEntree == null) {
			if (other.dateEntree != null)
				return false;
		} else if (!dateEntree.equals(other.dateEntree))
			return false;
		if (dateSortie == null) {
			if (other.dateSortie != null)
				return false;
		} else if (!dateSortie.equals(other.dateSortie))
			return false;
		if (disciplines == null) {
			if (other.disciplines != null)
				return false;
		} else if (!disciplines.equals(other.disciplines))
			return false;
		if (droitImage != other.droitImage)
			return false;
		if (famille == null) {
			if (other.famille != null)
				return false;
		} else if (!famille.equals(other.famille))
			return false;
		if (listeCours == null) {
			if (other.listeCours != null)
				return false;
		} else if (!listeCours.equals(other.listeCours))
			return false;
		if (motif == null) {
			if (other.motif != null)
				return false;
		} else if (!motif.equals(other.motif))
			return false;
		if (qf == null) {
			if (other.qf != null)
				return false;
		} else if (!qf.equals(other.qf))
			return false;
		if (responsable == null) {
			if (other.responsable != null)
				return false;
		} else if (!responsable.equals(other.responsable))
			return false;
		return true;
	}

	public boolean isMineur(){
		
		Calendar dateMineur = Calendar.getInstance();
		dateMineur.set(dateMineur.get(Calendar.YEAR) - 18,
				dateMineur.get(Calendar.MONTH),
				dateMineur.get(Calendar.DAY_OF_MONTH));

		if (this.getDateNaissance().after(dateMineur.getTime())) {
			return true;
		}
		return false;
	}

}
