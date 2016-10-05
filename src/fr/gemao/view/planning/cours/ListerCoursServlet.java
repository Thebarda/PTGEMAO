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
import fr.gemao.entity.cours.Cours;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;
import fr.gemao.view.ServletUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.COURS_LISTER)
public class ListerCoursServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Cours> listeCours = CoursCtrl.getAll();

		if (listeCours == null) {
			ResultatServlet.redirectErreur(request, response, "Erreur", "Aucun cours enregistré", Pattern.COURS_CREER,
					"Créer un cours ?", null, null, null);
			return;
		}

		if (deleteCours(request, response)) {
			return;
		}

		request.setAttribute("cours", listeCours);
		this.getServletContext().getRequestDispatcher(JSPFile.COURS_LISTER).forward(request, response);
	}

	/**
	 * @param request
	 * @param response
	 * @param ctrl
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean deleteCours(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer idCoursDelete = ServletUtil.getParameterIntegerValue("idCoursDelete", request);
		if (idCoursDelete != null) {
			final Cours cours = CoursCtrl.get(idCoursDelete);
			if (cours != null) {
				try {
					CoursCtrl.delete(cours);
					ResultatServlet.redirect(request, response, "Resultat", "Le cours a bien été supprimé",
							Pattern.COURS_LISTER, "Retour à la liste des cours", null, null, null);
					return true;
				} catch (DAOException e) {
					ResultatServlet.redirectErreur(request, response, "Erreur",
							"Impossible de supprimer le cours, il est déjà utilisé par d'autres ressources",
							Pattern.COURS_LISTER, "Retour à la liste des cours", null, null, null);
					return true;
				}
			}
		}
		return false;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
