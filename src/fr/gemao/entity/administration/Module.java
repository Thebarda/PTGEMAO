package fr.gemao.entity.administration;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe représentant un module développé pour cette application
 * @author Benoît
 *
 */
public class Module implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Map<Integer, Module> allModules = new HashMap<>();
	
	private Integer idModule;
	private String nomModule;
	
	public static Module put(Integer idModule, String nomModule){
		Module module = new Module(idModule, nomModule);
		return Module.allModules.put(idModule, module);
	}
	
	public static Collection<Module> getAllModules(){
		return Module.allModules.values();
	}
	
	public static Module getModule(Integer idModule){
		return Module.allModules.get(idModule);
	}
	
	public static Module getModule(String nom){
		Collection<Module> list = Module.allModules.values();
		for(Module m : list){
			if(m.getNomModule().equals(nom)){
				return m;
			}
		}
		return null;
	}
	
	private Module() {
		
	}
	
	private Module(Integer idModule, String nomModule) {
		this.idModule = idModule;
		this.nomModule = nomModule;
	}
	
	public Integer getIdModule() {
		return idModule;
	}
	
	public void setIdModule(Integer idModule) {
		this.idModule = idModule;
	}
	
	public String getNomModule() {
		return nomModule;
	}
	
	public void setNomModule(String nomModule) {
		this.nomModule = nomModule;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idModule == null) ? 0 : idModule.hashCode());
		result = prime * result
				+ ((nomModule == null) ? 0 : nomModule.hashCode());
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
		Module other = (Module) obj;
		if (idModule == null) {
			if (other.idModule != null)
				return false;
		} else if (!idModule.equals(other.idModule))
			return false;
		if (nomModule == null) {
			if (other.nomModule != null)
				return false;
		} else if (!nomModule.equals(other.nomModule))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Module [idModule=" + idModule + ", nomModule=" + nomModule
				+ "]";
	}
	
	
	
}
