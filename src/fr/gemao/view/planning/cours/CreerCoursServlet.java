/**
 * 
 */
package fr.gemao.view.planning.cours;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.planning.CoursCtrl;
import fr.gemao.ctrl.planning.DisciplineCtrl;
import fr.gemao.ctrl.planning.ProfCtrl;
import fr.gemao.entity.cours.Cours;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.cours.Prof;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.COURS_CREER)
public class CreerCoursServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Discipline> listeDiscipline = DisciplineCtrl.getAll();
		List<Prof> listeProf = ProfCtrl.getAll();

		request.setAttribute("LISTE_PROF", listeProf);
		request.setAttribute("LISTE_DISCIPLINE", listeDiscipline);

		this.getServletContext().getRequestDispatcher(JSPFile.COURS_CREER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération du Prof et de la Discipline
		Integer idDiscipline = Integer.valueOf(Form.getValeurChamp(request, "idDiscipline"));
		Integer idProf = Integer.valueOf(Form.getValeurChamp(request, "idProf"));

		Cours c = null;

		try {
			c = CoursCtrl.getByDisciplineAndProf(idDiscipline, idProf);

			if (c != null) {
				ResultatServlet.redirectErreur(request, response, "Erreur", "Ce cours existe déjà !",
						Pattern.COURS_LISTER, "Retour à la liste des cours ?", null, null, null);
				return;
			}

			if (c == null && idProf != -1 && idDiscipline != -1) {
				// le cours n'existe pas déjà, on le crée, en s'assurant que
				// idProf et idDiscipline ne sont pas set à aucun, auquel cas,
				// on ne s'occupe pas de ce cours.
				c = new Cours(null, DisciplineCtrl.get(idDiscipline), null, ProfCtrl.get(idProf));
				// System.out.println(c.getIdCours());
				c = CoursCtrl.create(c);
			}
		} catch (IllegalStateException ie) {
			ResultatServlet.redirectErreur(request, response, "Une erreur est survenue", ie.getMessage(),
					Pattern.ACCUEIL, "Retour à l'accueil ?", null, null, null);
			return;
		}

		ResultatServlet.redirect(request, response, "Succès !",
				"Le cours " + c.getIntituleCours() + " a bien été ajouté.", Pattern.COURS_LISTER,
				"Retour à la liste des cours ?", null, null, null);

	}
}
