package fr.gemao.entity.partenaire;

public class Partenaire {
	private final Integer idPartenaire;
	private final String raisonSociale;
	private final String adresse;
	private final int annee;
	private final String taillePage;
	
	public Partenaire(Integer id, String raisonSociale, String adresse, int annee, String taillePage){
		this.idPartenaire=id;
		this.raisonSociale=raisonSociale;
		this.adresse=adresse;
		this.annee=annee;
		this.taillePage=taillePage;
	}
	
	public Partenaire(String raisonSociale, String adresse, int annee, String taillePage){
		this(null, raisonSociale, adresse, annee, taillePage);
	}
	
	public String getRaisonSociale() {
		return raisonSociale;
	}

	public String getAdresse() {
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
