package fr.gemao.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.ConnexionCtrl;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.ConnexionForm;


/**
 * Servlet implementation class Connexion
 */
@WebServlet(Pattern.CONNEXION)
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String ATT_USER = "personnel";
	public static final String ATT_FORM = "form";
	public static final String ATT_SESSION_USER = "sessionObjectPersonnel";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( JSPFile.CONNEXION ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Préparation de l'objet formulaire
        ConnexionForm form = new ConnexionForm();

        //Traitement de la requête et récupération du bean en résultant
        Personnel personnel = form.testerDonneesFormulaires( request );

        //Récupération de la session depuis la requête
        HttpSession session = request.getSession();


		//Si aucune erreur de validation n'a eu lieu, alors on teste le mot de passe
		if (form.getErreurs().isEmpty()) {
			ConnexionCtrl connexionCtrl = new ConnexionCtrl();
			try {
				personnel = connexionCtrl.controlerPersonnel(form.getLogin(), form.getMotDePasse());
			} catch (Exception e) {
				// Erreur lors de la connexion
				form.setErreur("Connexion", e.getMessage());
				personnel = new Personnel();
				personnel.setLogin(form.getLogin());
			}
		}
		
		if (form.getErreurs().isEmpty()) {
			// On place en session un objet contenant les informations sur la personne connectée
			session.setAttribute(ATT_SESSION_USER, personnel);
			
			// L'utilisateur voit la redirection
			response.sendRedirect(request.getContextPath() + Pattern.ACCUEIL);
		} else {
			// Retour à la page de connexion
			request.setAttribute(ATT_FORM, form);
			request.setAttribute(ATT_USER, personnel);
			this.getServletContext().getRequestDispatcher(JSPFile.CONNEXION)
					.forward(request, response);
		}

	}

}
