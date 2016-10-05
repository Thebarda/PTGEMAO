package fr.gemao.fileparser;

import java.util.Map;

/**
 * Classe abstraite représentant le modèle à suivre
 * pour les classes qui en hériteront et qui serviront
 * à écrire ou lire des données dans un fichier.
 */
public abstract class Parser {
	protected String filename;
	
	/**
	 * Constructeur
	 * @param filename
	 * 		Nom du fichier à ouvrir pour lire
	 * 		ou écrire
	 */
	public Parser(String filename){
		this.filename = filename;
	}
	
	/**
	 * Lecture un fichier et renvoi des informations
	 * qu'il contient sous forme de {@link Map}
	 */
	public abstract Map<String, Object> read();
	
	/**
	 * Ecriture dans un fichier à partir d'informations
	 * sous forme de {@link Map}
	 */
	public abstract boolean write(Map<String, Object> data);

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
