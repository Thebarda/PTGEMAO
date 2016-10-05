package fr.gemao.entity.administration;

import java.io.Serializable;

public class Droit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static Map<Integer, Collection<Droit>>allDroits = new HashMap<Integer, Collection<Droit>>();
	private TypeDroit type;
	private Module module;
	
	
	public Droit(TypeDroit type, Module module) {
		this.type = type;
		this.module = module;
	}


	/**
	 * @return the type
	 */
	public TypeDroit getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(TypeDroit type) {
		this.type = type;
	}


	/**
	 * @return the module
	 */
	public Module getModule() {
		return module;
	}


	/**
	 * @param module the module to set
	 */
	public void setModule(Module module) {
		this.module = module;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((module == null) ? 0 : module.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Droit other = (Droit) obj;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Droit [type=" + type + ", module=" + module + "]";
	}
	
	
	
}
