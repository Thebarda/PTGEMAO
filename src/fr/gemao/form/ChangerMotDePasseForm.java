package fr.gemao.form;

import javax.servlet.http.HttpServletRequest;

import fr.gemao.form.util.Form;

/**
 * Classe récupérant les données du formulaire de changement de
 * mot de passe et vérifiant que les deux nouveaux sont identiques
 * @author Benoît
 *
 */
public class ChangerMotDePasseForm extends Form{

	public static final String CHAMP_ANCIEN_MDP = "ancien",
			CHAMP_NOUVEAU_MDP_1 = "nouveau1",
			CHAMP_NOUVEAU_MDP_2 = "nouveau2",
			ERREUR_ANCIEN_MDP = "ancien",
			ERREUR_NOUVEAUX_MDP = "nouveau";
	
	public static int TAILLE_MIN_MDP = 6;
	
	private String nouveauMotDePasse, ancienMotDePasse;
	
	public ChangerMotDePasseForm(HttpServletRequest request) {
		// Récupération des données du formulaire
		String ancien = getValeurChamp(request, CHAMP_ANCIEN_MDP);
		String nouveau1 = getValeurChamp(request, CHAMP_NOUVEAU_MDP_1);
		String nouveau2 = getValeurChamp(request, CHAMP_NOUVEAU_MDP_2);
		
		try {
			this.validationNouveauxMotDePasse(nouveau1, nouveau2);
		} catch(Exception e) {
			this.setErreur(ERREUR_NOUVEAUX_MDP, e.getMessage());
		}
		
		if(this.getErreurs().isEmpty()){
			nouveauMotDePasse = nouveau1;
			ancienMotDePasse = ancien;
		}
	}
	
	private void validationNouveauxMotDePasse(String motDePasse1, String motDePasse2) throws Exception {
		// Vérification que les deux mots de passe sont identiques
		if (!motDePasse1.equals(motDePasse2)) {
			throw new Exception("Les deux mots de passe sont différents.");
		}
		
		// Vérification de la longueur du nouveau mot de passe
		if(motDePasse1.length()<TAILLE_MIN_MDP){
			throw new Exception("Le nouveau mot de passe doit comporter au moins "+TAILLE_MIN_MDP+" caractères");
		}
	}

	public String getNouveauMotDePasse() {
		return nouveauMotDePasse;
	}

	public String getAncienMotDePasse() {
		return ancienMotDePasse;
	}
	
	
	
}
