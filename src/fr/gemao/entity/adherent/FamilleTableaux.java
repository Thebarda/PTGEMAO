package fr.gemao.entity.adherent;

public class FamilleTableaux {
	private Integer idFamille;
	private Integer annee;
	private String tfc;
	private String tr;
	
	public void setIdFamille(Integer idFamille) {
		this.idFamille = idFamille;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public void setTfc(String tfc) {
		this.tfc = tfc;
	}

	public void setTr(String tr) {
		this.tr = tr;
	}

	public FamilleTableaux(Integer id, Integer annee, String tfc, String tr){
		this.idFamille = id;
		this.annee = annee;
		this.tfc = tfc;
		this.tr = tr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + annee;
		result = prime * result + idFamille;
		result = prime * result + ((tfc == null) ? 0 : tfc.hashCode());
		result = prime * result + ((tr == null) ? 0 : tr.hashCode());
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
		FamilleTableaux other = (FamilleTableaux) obj;
		if (annee != other.annee)
			return false;
		if (idFamille != other.idFamille)
			return false;
		if (tfc == null) {
			if (other.tfc != null)
				return false;
		} else if (!tfc.equals(other.tfc))
			return false;
		if (tr == null) {
			if (other.tr != null)
				return false;
		} else if (!tr.equals(other.tr))
			return false;
		return true;
	}

	public Integer getIdFamille() {
		return idFamille;
	}

	public Integer getAnnee() {
		return annee;
	}

	public String getTfc() {
		return tfc;
	}

	public String getTr() {
		return tr;
	}
}
