/**
 * 
 */
package fr.gemao.entity.administration;

/**
 * Cette enumeration contient les profils immuable et non modifiable en base
 * (Prof et Admin)
 * 
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public enum ProfilEnum {

	PROFESSEUR("Professeur", 2), ADMIN("Admin", 1), MEMBRE_CA("Membre CA", 3);

	private String nom;
	private int id;

	/**
	 * 
	 */
	private ProfilEnum(String nom, int id) {
		this.nom = nom;
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public int getId() {
		return this.id;
	}
}
