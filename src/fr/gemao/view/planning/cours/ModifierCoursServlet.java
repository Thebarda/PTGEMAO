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
import fr.gemao.view.ServletUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.COURS_MODIFIER)
public class ModifierCoursServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer idCoursUpdate = ServletUtil.getParameterIntegerValue("idCoursUpdate", request);

		try {
			final Cours cours = CoursCtrl.get(idCoursUpdate);

			if (cours == null) {
				// Pas de cours renseigné, on redirige vers la liste des
				// plannings.
				ResultatServlet.redirectErreur(request, response, "Erreur lors de la modification",
						"Le cours n'existe pas.", Pattern.COURS_LISTER, "Retour à la liste des cours ?", null, null,
						null);
			}

			request.setAttribute("cours", cours);
		} catch (NullPointerException e) {
			ResultatServlet.redirectErreur(request, response, "Erreur", "Aucun cours selectionné !",
					Pattern.COURS_LISTER, "Retour à la liste des cours", null, null, null);
			return;
		}

		List<Discipline> listeDiscipline = DisciplineCtrl.getAll();
		List<Prof> listeProf = ProfCtrl.getAll();

		request.setAttribute("LISTE_PROF", listeProf);
		request.setAttribute("LISTE_DISCIPLINE", listeDiscipline);

		this.getServletContext().getRequestDispatcher(JSPFile.COURS_MODIFIER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération du Prof et de la Discipline
		Integer idDiscipline = Integer.valueOf(Form.getValeurChamp(request, "idDiscipline"));
		Integer idProf = Integer.valueOf(Form.getValeurChamp(request, "idProf"));

		Integer idCours = Integer.valueOf(Form.getValeurChamp(request, "idCours"));

		Cours c = null;

		try {
			c = CoursCtrl.getByDisciplineAndProf(idDiscipline, idProf);

			if (c != null) {
				// Un cours similaire existe déjà, inutile de dupliquer des
				// données.
				ResultatServlet.redirectErreur(request, response, "Erreur",
						"Il n'est pas nécessaire de modifier ce cours comme ceci, puisqu'il en existe déjà un avec ce professeur et cette discipline !",
						Pattern.COURS_LISTER, "Retour à la liste des cours ?", null, null, null);
				return;
			}

			if (c == null && idProf != -1 && idDiscipline != -1) {
				// le cours n'existe pas déjà, on le crée, en s'assurant que
				// idProf et idDiscipline ne sont pas set à aucun, auquel cas,
				// on ne s'occupe pas de ce cours.
				c = new Cours(idCours, DisciplineCtrl.get(idDiscipline), null, ProfCtrl.get(idProf));
				// System.out.println(c.getIdCours());
				c = CoursCtrl.update(c);
			}
		} catch (IllegalStateException ie) {
			ResultatServlet.redirectErreur(request, response, "Une erreur est survenue", ie.getMessage(),
					Pattern.COURS_LISTER, "Retour à la liste des cours ?", null, null, null);
			return;
		}

		if (c == null) {
			// Erreur durant la requête SQL.
			ResultatServlet.redirectErreur(request, response, "Une erreur est survenue",
					"Le cours n'a pas pu être mis à jour.", Pattern.COURS_LISTER, "Retour à la liste des cours ?", null,
					null, null);
			return;

		}

		ResultatServlet.redirect(request, response, "Succès !",
				"Le cours a bien été modifié. Le nouveau cours est désormais \"" + c.getIntituleCours() + "\"",
				Pattern.COURS_LISTER, "Retour à la liste des cours", null, null, null);

	}
}
