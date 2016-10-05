package fr.gemao.entity.util;

public enum Civilite {

	MONSIEUR("Monsieur", "M.", "M"),
	MADAME("Madame", "Mme", "F");
	
	private final String name;
	private final String nameCourt;
	private final String sexe;
	
	private Civilite(String name, String nameCourt, String sexe) {
		this.name = name;
		this.nameCourt = nameCourt;
		this.sexe = sexe;
	}
	
	public String getName(){
		return name;
	}
	
	public String getNameCourt(){
		return nameCourt;
	}
	
	public String getSexe(){
		return sexe;
	}
	
	public String toString(){
		return name;
	}
}
