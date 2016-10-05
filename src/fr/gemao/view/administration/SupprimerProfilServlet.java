package fr.gemao.view.administration;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.administration.ModificationCtrl;
import fr.gemao.ctrl.administration.ProfilsCtrl;
import fr.gemao.entity.administration.Modification;
import fr.gemao.entity.administration.Profil;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.util.Form;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class SupprimerProfilServlet
 */
@WebServlet(Pattern.ADMINISTRATION_SUPPRIMER_PROFIL)
public class SupprimerProfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String PARAM_IDENTIFIANT = "id";
	public static String ATTR_RESULTAT = "resultat", ATTR_LIEN_BOUTON = "lienBouton", ATTR_NOM_BOUTON = "nomBouton",
			ATTR_TITRE_H1 = "titreH1";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération des données du profil dont l'identifiant est passé en
		// paramètre
		String paramId = Form.getValeurChamp(request, PARAM_IDENTIFIANT);

		// Accès illégal à la page (pas de paramètre id passé en get)
		if (paramId == null) {
			request.getRequestDispatcher(Pattern.ADMINISTRATION_LISTER_PROFIL).forward(request, response);
			return;
		}

		ProfilsCtrl profilctrl = new ProfilsCtrl();
		request.setAttribute(ATTR_TITRE_H1, "Résultat de la suppression");
		request.setAttribute(ATTR_LIEN_BOUTON, Pattern.ADMINISTRATION_LISTER_PROFIL);
		request.setAttribute(ATTR_NOM_BOUTON, "Retour");
		Profil profil = profilctrl.getProfil(Integer.parseInt(paramId));
		if (profilctrl.deleteProfil(Integer.parseInt(paramId))) {
			request.setAttribute(ATTR_RESULTAT, "La suppression a bien été effectuée.");
			// Archivage de la modification
			HttpSession session = request.getSession();
			ModificationCtrl.ajouterModification(
					new Modification(0, (Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER), new Date(),
							"Suppression profil : " + profil.getNomProfil()));
			request.getRequestDispatcher(JSPFile.RESULTAT).forward(request, response);
		} else {
			request.setAttribute(ATTR_RESULTAT, "Le profil est utilisé, vous ne pouvez pas le supprimer.");
			request.getRequestDispatcher(JSPFile.ERREUR).forward(request, response);
		}
	}

}
