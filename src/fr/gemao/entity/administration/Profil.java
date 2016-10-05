package fr.gemao.entity.administration;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe représentant un profil, lié à un ou plusieurs membres
 * du personnel, permettant l'accès à certains modules
 * @author Benoît
 *
 */
public class Profil implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Map<Integer, Profil> allProfils = new HashMap<>();
	
	private Integer idProfil;
	private String nomProfil;
	private List<Droit> listDroits;
	
	public static void load(List<Profil> profils){
		if(!Profil.allProfils.isEmpty()){
			throw new UnsupportedOperationException("Les profils semblent être déjà chargés");
		}
		for(Profil p : profils){
			Profil.allProfils.put(p.idProfil, p);
		}
	}
	
	public static void put(Profil profil){
		if(profil == null){
			throw new NullPointerException("Le profil ne peut pas être null");
		}
		if(profil.getIdProfil() == null){
			throw new NullPointerException("L'identifiant du profil ne doit pas être null");
		}
		if(profil.getNomProfil() == null){
			throw new NullPointerException("Le nom du profil ne doit pas être null");
		}
		Profil.allProfils.put(profil.getIdProfil(), profil);
	}
	
	public static Collection<Profil> getAllProfils(){
		return Profil.allProfils.values();
	}
	
	public static Profil getProfil(Integer idProfil){
		return Profil.allProfils.get(idProfil);
	}
	
	
	
	public Profil() {

	}

	public Profil(Integer idProfil, String nomProfil, List<Droit> listDroit) {
		super();
		this.idProfil = idProfil;
		this.nomProfil = nomProfil;
		this.listDroits = listDroit;
	}

	public Integer getIdProfil() {
		return idProfil;
	}

	public void setIdProfil(Integer idProfil) {
		this.idProfil = idProfil;
	}

	public String getNomProfil() {
		return nomProfil;
	}

	public void setNomProfil(String nomProfil) {
		this.nomProfil = nomProfil;
	}

	public List<Droit> getListDroits() {
		return listDroits;
	}

	public void setListDroits(List<Droit> listDroits) {
		this.listDroits = listDroits;
	}
	
	public boolean removeDroit(Droit droit){
		return this.listDroits.remove(droit);
	}
	
	public boolean addDroit(Droit droit){
		return this.listDroits.add(droit);
	}
	
	public boolean addAlldroit(List<Droit> droits){
		return this.listDroits.addAll(droits);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idProfil;
		result = prime * result
				+ ((listDroits == null) ? 0 : listDroits.hashCode());
		result = prime * result
				+ ((nomProfil == null) ? 0 : nomProfil.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profil other = (Profil) obj;
		if (idProfil != other.idProfil)
			return false;
		if (listDroits == null) {
			if (other.listDroits != null)
				return false;
		} else if (!listDroits.equals(other.listDroits))
			return false;
		if (nomProfil == null) {
			if (other.nomProfil != null)
				return false;
		} else if (!nomProfil.equals(other.nomProfil))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Profil [idProfil=" + idProfil + ", nomProfil=" + nomProfil
				+ ", listDroits=" + listDroits + "]";
	}
	
	/**
	 * Méthode permettant de savoir quel droit est associé à un module
	 * dans ce profil
	 * @param nomModule : nom du module dont on veut récupérer le droit
	 * @return le nom du type de droit associé au module demandé
	 */
	public String recupererTypeDroit(String nomModule){
		String res = "Aucun";
		
		for(Droit d : listDroits){
			if(d.getModule().getNomModule().equals(nomModule)){
				res = d.getType().getNomType();
			}
		}
		
		return res;
	}
	
}
