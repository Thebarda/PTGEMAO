package fr.gemao.view.adherent;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.administration.ModificationCtrl;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.administration.Modification;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.util.Form;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;

@WebServlet(Pattern.ADHERENT_REINIT_APAYE)
public class ReinitAPayeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CHAMP_IDADHERENT = "id";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = Form.getValeurChamp(request, CHAMP_IDADHERENT);
		if (id == null) {
			// Accès illégal à cette page
			request.getRequestDispatcher(Pattern.ACCUEIL).forward(request, response);
		} else {
			// On place l'identifiant dans une variable de session
			Integer idAdh = Integer.parseInt(id);

			Adherent adherent = AdherentCtrl.recupererAdherent(idAdh);

			adherent.setAPaye(false);
			AdherentCtrl.modifierAdherent(adherent);

			ModificationCtrl.ajouterModification(new Modification(0,
					(Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER), new Date(),
					"Annuler confirmation paiement adhérent : " + adherent.getNom() + " " + adherent.getPrenom()));

			request.setAttribute(ResultatServlet.ATTR_LIEN_BOUTON, Pattern.ADHERENT_LISTER);
			request.setAttribute(ResultatServlet.ATTR_NOM_BOUTON, "Retour à la liste");
			request.setAttribute(ResultatServlet.ATTR_TITRE_H1, "Confirmation");
			request.setAttribute(ResultatServlet.ATTR_RESULTAT,
					"La confirmation de paiement de l'adhérent a été annulée.");

			// On envoie vers la page jsp
			request.getRequestDispatcher(JSPFile.RESULTAT).forward(request, response);
		}
	}

}
