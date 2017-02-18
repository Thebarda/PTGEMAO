package fr.gemao.entity.partenaire;

import fr.gemao.entity.Adresse;

public class Partenaire implements Comparable<Partenaire>{
	private final Integer idPartenaire;
	private final String raisonSociale;
	private final Adresse adresse;
	private final int annee;
	private final String taillePage;
	private Integer anneeDernierVersement;
	
	

	public Partenaire(Integer id, String raisonSociale, Adresse adresse, int annee, String taillePage, Integer anneeDernierVersement){
		this.idPartenaire=id;
		this.raisonSociale=raisonSociale;
		this.adresse=adresse;
		this.annee=annee;
		this.taillePage=taillePage;
		this.anneeDernierVersement=anneeDernierVersement;
	}
	
	public Partenaire(String raisonSociale, Adresse adresse, int annee, String taillePage, Integer anneeDernierVersement){
		this(null, raisonSociale, adresse, annee, taillePage, anneeDernierVersement);
	}
	
	public Partenaire(Integer idPartenaire, String raisonSociale, Adresse adresse, int annee, String taillePage){
		this(idPartenaire, raisonSociale, adresse, annee, taillePage, null);
	}
	
	public void setAnneeDernierVersement(Integer anneeDernierVersement) {
		this.anneeDernierVersement = anneeDernierVersement;
	}
	
	public Integer getAnneeDernierVersement() {
		return anneeDernierVersement;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public int getAnnee() {
		return annee;
	}

	public String getTaillePage() {
		return taillePage;
	}

	public int getIdPartenaire() {
		return idPartenaire;
	}

	@Override
	public int compareTo(Partenaire o) {
		return (this.getAnneeDernierVersement()).compareTo(o.getAnneeDernierVersement());
	}
}
