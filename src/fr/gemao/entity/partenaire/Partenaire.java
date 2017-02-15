package fr.gemao.entity.partenaire;

import fr.gemao.entity.Adresse;

public class Partenaire {
	private final Integer idPartenaire;
	private final String raisonSociale;
	private final Adresse adresse;
	private final int annee;
	private final String taillePage;
	
	public Partenaire(Integer id, String raisonSociale, Adresse adresse, int annee, String taillePage){
		this.idPartenaire=id;
		this.raisonSociale=raisonSociale;
		this.adresse=adresse;
		this.annee=annee;
		this.taillePage=taillePage;
	}
	
	public Partenaire(String raisonSociale, Adresse adresse, int annee, String taillePage){
		this(null, raisonSociale, adresse, annee, taillePage);
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
}
