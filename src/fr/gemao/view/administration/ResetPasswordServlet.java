package fr.gemao.view.administration;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.ChangerMotDePasseCtrl;
import fr.gemao.ctrl.personnel.PersonnelCtrl;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.util.Form;
import fr.gemao.util.Password;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class ResetPasswordServlet
 */
@WebServlet(Pattern.ADMINISTRATION_RESET_PASSWORD)
public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static String ATTR_LISTE_PERSONNEL = "listePersonnel", ATTR_PERSONNE = "personne", ATTR_ERREUR = "erreur",
			CHAMP_ID_PERSONNEL = "idPersonne", CHAMP_CACHE = "id", CHAMP_MOT_DE_PASSE = "password";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1er passage : on envoie juste la liste du personnel et on affiche la
		// vue

		// Récupérer la liste des membres du personnel et enlever celui connecté
		List<Personnel> listePersonnel = PersonnelCtrl.recupererTousPersonnels();
		HttpSession session = request.getSession();
		Personnel personneConnectee = (Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER);

		for (Personnel personnel : listePersonnel) {
			if (personnel.getLogin().equals(personneConnectee.getLogin())) {
				personneConnectee = personnel;
				break;
			}
		}
		listePersonnel.remove(personneConnectee);

		request.setAttribute(ATTR_LISTE_PERSONNEL, listePersonnel);
		request.getRequestDispatcher(JSPFile.ADMINISTRATION_RESET_PASSWORD).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ChangerMotDePasseCtrl changerMdpCtrl = new ChangerMotDePasseCtrl();

		// Récupérer la liste des membres du personnel et enlever celui connecté
		List<Personnel> listePersonnel = PersonnelCtrl.recupererTousPersonnels();
		HttpSession session = request.getSession();
		Personnel personneConnectee = (Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER);
		for (Personnel personnel : listePersonnel) {
			if (personnel.getLogin().equals(personneConnectee.getLogin())) {
				personneConnectee = personnel;
				break;
			}
		}
		listePersonnel.remove(personneConnectee);

		// Récupérer l'identifiant de la personne choisie
		long id = Long.parseLong(Form.getValeurChamp(request, CHAMP_ID_PERSONNEL));
		Personnel personne = PersonnelCtrl.recupererPersonnel(id);

		if (Form.getValeurChamp(request, CHAMP_CACHE) == null) {
			// Envoi déclenché par la liste déroulante
			request.setAttribute(ATTR_LISTE_PERSONNEL, listePersonnel);
			request.setAttribute(CHAMP_ID_PERSONNEL, Form.getValeurChamp(request, CHAMP_ID_PERSONNEL));
		} else {
			// Clic sur le bouton 'Valider'

			// Test du mot de passe de la personne effectuant la modification
			String password = Form.getValeurChamp(request, CHAMP_MOT_DE_PASSE);
			String login = ((Personnel) request.getSession().getAttribute(ConnexionServlet.ATT_SESSION_USER))
					.getLogin();

			if (!changerMdpCtrl.controlerMotDePasse(login, password)) {
				// Mot de passe incorrect
				request.setAttribute(ATTR_ERREUR, "Le mot de passe saisi est incorrect.");
				request.setAttribute(ATTR_LISTE_PERSONNEL, listePersonnel);
				request.setAttribute(CHAMP_ID_PERSONNEL, Form.getValeurChamp(request, CHAMP_ID_PERSONNEL));
			} else {
				// Modification du mot de passe

				personne.setPassword(Password.generatePassword());
				personne.setPremiereConnexion(true);
				String mdp = personne.getPassword();
				if (!changerMdpCtrl.changerMotDePasse(personne)) {
					request.setAttribute(ATTR_ERREUR, "Un problème est survenu lors du changement de mot de passe.");
					request.setAttribute(ATTR_LISTE_PERSONNEL, listePersonnel);
					request.setAttribute(CHAMP_ID_PERSONNEL, Form.getValeurChamp(request, CHAMP_ID_PERSONNEL));
				}
				// Mot de passe non crypté pour affichage
				personne.setPassword(mdp);
			}
		}

		request.setAttribute(ATTR_PERSONNE, personne);
		request.getRequestDispatcher(JSPFile.ADMINISTRATION_RESET_PASSWORD).forward(request, response);
	}

}
