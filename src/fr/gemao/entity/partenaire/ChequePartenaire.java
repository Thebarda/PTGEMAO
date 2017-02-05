package fr.gemao.entity.partenaire;

public class ChequePartenaire {
	private int id;
	private long numero;
	private int montant;
	private String datePaiement;
	private String dateEncaissement;
	private String dateEncaissementEffective;
	private String partenaire;
	
	public ChequePartenaire(int id, long numero, int montant, String datePaiement, String dateEncaissement, String dateEncaissementEffective, String partenaire){
		this.id=id;
		this.numero=numero;
		this.montant=montant;
		this.dateEncaissement=dateEncaissement;
		this.datePaiement=datePaiement;
		this.dateEncaissementEffective=dateEncaissementEffective;
		this.partenaire=partenaire;
	}

	public String getPartenaire() {
		return partenaire;
	}

	public void setPartenaire(String partenaire) {
		this.partenaire = partenaire;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public String getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(String datePaiement) {
		this.datePaiement = datePaiement;
	}

	public String getDateEncaissement() {
		return dateEncaissement;
	}

	public void setDateEncaissement(String dateEncaissement) {
		this.dateEncaissement = dateEncaissement;
	}

	public String getDateEncaissementEffective() {
		return dateEncaissementEffective;
	}

	public void setDateEncaissementEffective(String dateEncaissementEffective) {
		this.dateEncaissementEffective = dateEncaissementEffective;
	}
}
