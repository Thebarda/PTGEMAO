package fr.gemao.view.planning.salle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.planning.SalleCtrl;
import fr.gemao.entity.cours.Salle;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;

@WebServlet(Pattern.SALLE_CREER)
public class CreerSalleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(JSPFile.SALLE_CREER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nomSalle = Form.getValeurChamp(request, "nomSalle");
		Integer idSalle = null;

		Salle salle = new Salle(idSalle, nomSalle);

		Salle retour = SalleCtrl.create(salle);
		if (retour == null) {
			ResultatServlet.redirect(request, response, "Erreur", "La salle n'a pas été ajoutée.", Pattern.SALLE_CREER,
					"Réessayer ?", null, null, null);
			return;
		}

		ResultatServlet.redirect(request, response, "Résultat de la création d'une salle", "La salle a bien été créée.",
				Pattern.SALLE_CREER, "Retour à la création de salle", null, null, null);
	}
}
