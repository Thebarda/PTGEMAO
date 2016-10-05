package fr.gemao.entity.administration;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeDroit implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Map<Integer, TypeDroit> allDroits = new HashMap<>();
	private Integer idType;
	private String nomType;
	
	private TypeDroit(Integer idType, String nomType){
		this.idType = idType;
		this.nomType = nomType;
	}
	
	public static TypeDroit put(Integer idType, String nomType){
		TypeDroit type = new TypeDroit(idType, nomType);
		return TypeDroit.allDroits.put(idType, type);
	}
	
	public static Collection<TypeDroit> getAllTypeDroit(){
		return TypeDroit.allDroits.values();
	}
	
	public static TypeDroit getTypeDroit(Integer idType){
		return TypeDroit.allDroits.get(idType);
	}
	
	public static TypeDroit getTypeDroit(String nom){
		Collection<TypeDroit> list = TypeDroit.allDroits.values();
		for(TypeDroit t : list){
			if(t.getNomType().equals(nom)){
				return t;
			}
		}
		return null;
	}

	/**
	 * @return the idType
	 */
	public Integer getIdType() {
		return idType;
	}

	/**
	 * @param idType the idType to set
	 */
	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	/**
	 * @return the nomType
	 */
	public String getNomType() {
		return nomType;
	}

	/**
	 * @param nomType the nomType to set
	 */
	public void setNomType(String nomType) {
		this.nomType = nomType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TypeDroit [idType=" + idType + ", nomType=" + nomType + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((nomType == null) ? 0 : nomType.hashCode());
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
		TypeDroit other = (TypeDroit) obj;
		if (idType == null) {
			if (other.idType != null)
				return false;
		} else if (!idType.equals(other.idType))
			return false;
		if (nomType == null) {
			if (other.nomType != null)
				return false;
		} else if (!nomType.equals(other.nomType))
			return false;
		return true;
	}
	
	
}
