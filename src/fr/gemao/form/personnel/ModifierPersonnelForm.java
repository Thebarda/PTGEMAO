package fr.gemao.form.personnel;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe de validation du formulaire du personnel
 * @author Coco
 *
 */
public class ModifierPersonnelForm {
	
	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();
	
	public String getResultat() {
		return resultat;
	}
	
	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	/**
	 * Méthode permettant de tester un personnel
	 * @param request : le request
	 */
	public void testerPersonnel(HttpServletRequest request) {
		
	}	
}