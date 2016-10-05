package fr.gemao.view.adherent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.AdresseCtrl;
import fr.gemao.ctrl.CommuneCtrl;
import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.adherent.FamilleCtrl;
import fr.gemao.ctrl.adherent.ResponsableCtrl;
import fr.gemao.ctrl.administration.ModificationCtrl;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.adherent.Famille;
import fr.gemao.entity.administration.Modification;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class ValidationModifAdherentServlet
 */
@WebServlet(Pattern.ADHERENT_VALIDATION_MODIF)
public class ValidationModifAdherentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Adherent adherent = (Adherent) session.getAttribute("modif_adh_adherent");

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateNaissance = formatter.format(adherent.getDateNaissance());
		String dateInscription = formatter.format(adherent.getDateEntree());

		request.setAttribute("dateNaissance", dateNaissance);
		request.setAttribute("dateInscription", dateInscription);
		request.setAttribute("adherent", adherent);

		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_VALIDATION_MODIF).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Adherent adherent = (Adherent) session.getAttribute("modif_adh_adherent");

		request.setAttribute("adherent", adherent);

		Famille famille = FamilleCtrl.AjouterFamile(adherent.getFamille());
		adherent.setFamille(famille);
		CommuneCtrl.ajoutCommune(adherent.getAdresse().getCommune());
		AdresseCtrl.ajoutAdresse(adherent.getAdresse());

		if (AdherentCtrl.modifierAdherent(adherent)) {

			if (adherent.getResponsable() == null) {
				// Pas de responsable => modif ok
				// Archivage
				ModificationCtrl.ajouterModification(new Modification(0,
						(Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER), new Date(),
						"Modification adhérent : " + adherent.getNom() + " " + adherent.getPrenom()));

				// Redirection
				this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CONFIRMATION_MODIFICATION)
						.forward(request, response);
			} else {

				if (ResponsableCtrl.modifierResponsable(adherent.getResponsable())) {
					// modif ok
					// Archivage
					ModificationCtrl.ajouterModification(new Modification(0,
							(Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER), new Date(),
							"Modification adhérent : " + adherent.getNom() + " " + adherent.getPrenom()));

					// Redirection
					this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CONFIRMATION_MODIFICATION)
							.forward(request, response);
				} else {
					// erreur
					this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_ECHEC_MODIFICATION).forward(request,
							response);
				}
			}
		} else {
			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_ECHEC_MODIFICATION).forward(request,
					response);
		}

		session.setAttribute("modif_adh_adherent", null);

	}

}
