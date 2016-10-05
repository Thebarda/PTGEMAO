package fr.gemao.entity.planning;

import java.util.List;
import java.util.Objects;

import fr.gemao.entity.Jour;
import fr.gemao.entity.cours.Cours;
import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.util.HeurePlanning;

/**
 * Classe représentant un créneau, à placer sur le planning
 * 
 * @author G5
 *
 */
public class Creneau implements Cloneable {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Creneau [idCreneau=" + idCreneau + ",libelle=" + libelle
				+ ", heureDeb=" + heureDeb + ", duree=" + duree + ", idPlanning=" + idPlanning + ", salle=" + salle
				+ ", jour=" + jour + ", couleur=" + couleur + "]";
	}

	private Integer idCreneau;


	/**
	 * @return the idCreneau
	 */
	public Integer getIdCreneau() {
		return idCreneau;
	}
	public void setIdCreneau(Integer id) {
		idCreneau = id;
	}
	public void setLibelle(String lib) {
		libelle = lib;
	}
	private String libelle;
	private HeurePlanning heureDeb;
	private HeurePlanning duree;
	private Integer idPlanning;
	private Salle salle;
	private Jour jour;
	private String couleur;
	private Cours cours;
	private List<Contrainte> contraintes;

	public Creneau(Integer idCreneau, String libelle, HeurePlanning heureDeb, HeurePlanning duree, Integer idPlanning,
			Salle salle, Jour jour, String couleur, Cours cours, List<Contrainte> contraintes) {
		Objects.requireNonNull(libelle, "Le libelle ne peut être vide");
		// Objects.requireNonNull(salle, "La salle doit être renseignée");
		Objects.requireNonNull(duree, "La durée ne doit pas être nulle");
		// Objects.requireNonNull(jour, "Le jour ne doit pas etre nul");

		if (couleur == null) {
			couleur = "ffffff";
		}

		this.idCreneau = idCreneau;
		this.libelle = libelle;
		this.heureDeb = heureDeb;
		this.duree = duree;
		this.idPlanning = idPlanning;
		this.salle = salle;
		this.jour = jour;
		this.couleur = couleur;
		this.cours = cours;
		this.contraintes = contraintes;
	}

	public String getLibelle() {
		return libelle;
	}

	public HeurePlanning getHeureDeb() {
		return heureDeb;
	}

	public HeurePlanning getDuree() {
		return duree;
	}

	public Salle getSalle() {
		return salle;
	}

	public Jour getJour() {
		return this.jour;
	}

	public String getCouleur() {
		return this.couleur;
	}

	public Integer getIdPlanning() {
		return this.idPlanning;
	}

	public void setIdPlanning(Integer idPlanning) {
		this.idPlanning = idPlanning;
	}
	
	public List<Contrainte> getContraintes() {
		return this.contraintes;
	}

	/**
	 * Getteur permettant de récupérer l'heure de fin d'un créneau
	 * 
	 * @return HeurePlanning
	 */
	public HeurePlanning getHeureFin() {
		Objects.requireNonNull(this.heureDeb, "L'heure de début ne doit pas être nulle");
		Objects.requireNonNull(this.duree, "La durée ne doit pas être nulle");

		return HeurePlanning.getHeureAjoutDuree(this.heureDeb, this.duree);
	}

	/**
	 * Methode renvoyant le nombre de quart dh'eure que prendra un creneau La
	 * duree ne doit pas etre nulle
	 * 
	 * @return le nombre de quart d'heure
	 * 
	 */
	public int getNbQuartDHeure() {
		Objects.requireNonNull(this.duree, "La durée ne doit pas être nulle");
		int nb = this.duree.getHeure() * 4;
		nb += this.duree.getMinute() / 15;

		return nb;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Creneau other = (Creneau) obj;
		if (other.idCreneau == idCreneau)
			return false;
		return true;
	}

	@Override
	public Creneau clone() {
		return new Creneau(null, libelle, heureDeb, duree, idPlanning, salle, jour, couleur, cours, contraintes);
	}

	public Cours getCours() {
		return this.cours;
	}
	
	/*public Contrainte verifieContrainte() {
		Contrainte c = null;
		
		for (Contrainte contrainte : contraintes) {
			if (!contrainte.estRespecte()) {
				c = contrainte;
			}
		}
		return c;
	}*/
	
	public Contrainte verifieContrainte(Salle salle, Jour jour, HeurePlanning heureDebut, HeurePlanning heureFin) {
		Contrainte c = null;
		
		for (Contrainte contrainte : contraintes) {
			if (!contrainte.estRespecte(salle, jour, heureDebut, heureFin)) {
				c = contrainte;
			}
		}
		return c;
	}
	
	public boolean intersection(Creneau c) {
		
		if (this.getIdCreneau().equals(c.getIdCreneau()))
			return false;
		
		if (!this.getJour().equals(c.getJour()))
			return false;
		
		if (!this.getSalle().equals(c.getSalle()))
			return false;
		
		HeurePlanning thd = this.getHeureDeb();
		HeurePlanning thf = this.getHeureFin();
		HeurePlanning chd = c.getHeureDeb();
		HeurePlanning chf = c.getHeureFin();
		
		if (thf.isInferieur(chd))
			return false;
		if (thd.isSuperieur(chf))
			return false;
		
		if (thf.isInferieur(chf) && thf.isStrictSuperieur(chd))
			return true;
		if (thd.isSuperieur(chd) && thd.isStrictInferieur(chf))
			return true;
		
		return false;
	}
	
	public boolean intersection(List<Creneau> creneaux) {
		for (Creneau creneau : creneaux) {
			if (this.intersection(creneau)) {
				return true;
			}
		}
		
		return false;
	}
}
