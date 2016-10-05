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

@WebServlet(Pattern.ADHERENT_APAYE)
public class APayeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CHAMP_IDADHERENT = "id";
	private static final String CHAMP_COTISATION = "cotisation";
	private static final String CHAMP_CONFIRAMTION = "confirmation";
	private static final String CHAMP_ADHERENT = "adherent";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Récupération de l'identifiant de l'adhérent
		String id = Form.getValeurChamp(req, CHAMP_IDADHERENT);
		if (id == null) {
			// Accès illégal à cette page
			req.getRequestDispatcher(Pattern.ACCUEIL).forward(req, resp);
		} else {
			// On place l'identifiant dans une variable de session
			Integer idAdh = Integer.parseInt(id);

			Adherent adherent = AdherentCtrl.recupererAdherent(idAdh);
			float cotisation = adherent.getCotisation();
			req.setAttribute(CHAMP_COTISATION, cotisation);

			// Confirmation
			req.setAttribute(CHAMP_CONFIRAMTION, false);

			// Ajout id Adherent en session
			HttpSession session = req.getSession();
			session.setAttribute(CHAMP_IDADHERENT, idAdh);

			// On envoie vers la page jsp
			req.getRequestDispatcher(JSPFile.ADHERENT_A_PAYE).forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Integer idAdh = (Integer) session.getAttribute(CHAMP_IDADHERENT);
		session.removeAttribute(CHAMP_IDADHERENT);

		Adherent adherent = AdherentCtrl.recupererAdherent(idAdh);

		adherent.setAPaye(true);

		AdherentCtrl.modifierAdherent(adherent);

		ModificationCtrl.ajouterModification(
				new Modification(0, (Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER), new Date(),
						"Confirmation paiement adhérent : " + adherent.getNom() + " " + adherent.getPrenom()));

		req.setAttribute(CHAMP_ADHERENT, adherent);
		req.setAttribute(CHAMP_CONFIRAMTION, true);

		// On envoie vers la page jsp
		req.getRequestDispatcher(JSPFile.ADHERENT_A_PAYE).forward(req, resp);
	}

}
