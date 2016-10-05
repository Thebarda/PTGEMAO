package fr.gemao.view.administration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.administration.ModificationCtrl;
import fr.gemao.ctrl.administration.ProfilsCtrl;
import fr.gemao.entity.administration.Droit;
import fr.gemao.entity.administration.Modification;
import fr.gemao.entity.administration.Module;
import fr.gemao.entity.administration.Profil;
import fr.gemao.entity.administration.TypeDroit;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.util.Form;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class AjouterProfilServlet
 */
@WebServlet(Pattern.ADMINISTRATION_AJOUT_PROFIL)
public class AjouterProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ATTR_LISTE_MODULE = "listeModules";
	private static final String ATTR_LISTE_TYPE_DROIT = "listeTypes";
	private static final String ATTR_ERREUR = "erreur";
	private static final String ATTR_RESULTAT = "resultat";
	private static final String ATTR_NOM_BOUTON_PAGE_RESULTAT = "nomBouton";
	private static final String ATTR_LIEN_BOUTON_PAGE_RESULTAT = "lienBouton";
	private static final String ATTR_TITRE_H1 = "titreH1";
	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_DROIT_MODULE = "module";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Passage en paramètre des listes de modules et de types de droit
		request.setAttribute(ATTR_LISTE_MODULE, Module.getAllModules());
		request.setAttribute(ATTR_LISTE_TYPE_DROIT, TypeDroit.getAllTypeDroit());

		// Redirection vers le formulaire
		request.getRequestDispatcher(JSPFile.ADMINISTRATION_AJOUT_PROFIL).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération des données issues du formulaire
		String nom = Form.getValeurChamp(request, CHAMP_NOM);

		if (nom == null) {
			// Retour au formulaire
			request.setAttribute(ATTR_ERREUR, "Le nom du profil n'est pas valide.");
			request.getRequestDispatcher(JSPFile.ADMINISTRATION_AJOUT_PROFIL).forward(request, response);
			return;
		}

		// Récupération des listes de modules et de types de droits
		Collection<Module> listeModules = Module.getAllModules();

		// Liste des droits liés au profil créé
		List<Droit> listeDroits = new ArrayList<>();

		// Ajout des droits à partir des données du formulaire
		String valeur;
		for (Module m : listeModules) {
			// On récupère la valeur correspondant au module m
			valeur = Form.getValeurChamp(request, CHAMP_DROIT_MODULE + m.getIdModule());

			// Si un droit a été positionné pour ce module, on l'ajoute à la
			// liste
			if (valeur != null) {
				listeDroits.add(new Droit(TypeDroit.getTypeDroit(Integer.parseInt(valeur)), m));
			}

		}

		// Création du profil
		ProfilsCtrl ctrl = new ProfilsCtrl();
		Profil profil = ctrl.creerProfil(nom, listeDroits);

		if (profil == null) {
			// Retour au formulaire
			request.setAttribute(ATTR_LISTE_MODULE, Module.getAllModules());
			request.setAttribute(ATTR_LISTE_TYPE_DROIT, TypeDroit.getAllTypeDroit());
			request.setAttribute(ATTR_ERREUR, "Ce nom du profil existe déjà.");
			request.getRequestDispatcher(JSPFile.ADMINISTRATION_AJOUT_PROFIL).forward(request, response);
			return;
		}

		// Renvoi vers une page de résultat
		request.setAttribute(ATTR_RESULTAT, "Le profil \"" + profil.getNomProfil() + "\" a bien été ajouté.");
		request.setAttribute(ATTR_LIEN_BOUTON_PAGE_RESULTAT, Pattern.ADMINISTRATION_LISTER_PROFIL);
		request.setAttribute(ATTR_NOM_BOUTON_PAGE_RESULTAT, "Retour à la liste");
		request.setAttribute(ATTR_TITRE_H1, "Résultat de la création d'un profil");
		// Archivage de la modification
		HttpSession session = request.getSession();
		ModificationCtrl.ajouterModification(
				new Modification(0, (Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER), new Date(),
						"Création profil : " + profil.getNomProfil()));
		request.getRequestDispatcher(JSPFile.RESULTAT).forward(request, response);
	}

}
