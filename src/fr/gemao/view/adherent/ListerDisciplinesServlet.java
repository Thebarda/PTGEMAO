package fr.gemao.view.adherent;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.planning.DisciplineCtrl;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.cours.Matiere;
import fr.gemao.entity.cours.Niveau;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;
import fr.gemao.view.ServletUtil;

/**
 * Servlet implementation class ListerDisciplinesServlet
 */
@WebServlet(Pattern.ADHERENT_LISTE_DISCIPLINES)
public class ListerDisciplinesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (deletePlanning(request, response)) {
			return;
		}

		session.setAttribute("listDiscipline", DisciplineCtrl.getAll());

		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_LISTE_DISCIPLINES).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		// l'id correspond à l'id de la matière
		if (id == 0) { // nouvelle matière, on ajoute
			String matiereLibelle = request.getParameter("matiere");
			String niveauLibelle = request.getParameter("niveau");

			if (checkParameters(matiereLibelle, niveauLibelle, request, response))
				return;

			Matiere matiere = new Matiere(null, matiereLibelle);
			Niveau niveau = new Niveau(null, niveauLibelle);
			Discipline discipline = new Discipline(null, matiere, niveau, null);
			if (DisciplineCtrl.ajouterDiscipline(discipline)) {
				request.setAttribute("ajoutOK", true);
				session.setAttribute("listDiscipline", DisciplineCtrl.getAll());
			} else {
				request.setAttribute("ajoutKO", true);
			}
		} else { // on modifie une matière existante
			Discipline discipline = DisciplineCtrl.get(id);

			String matiereLibelle = request.getParameter("matiere");
			String niveauLibelle = request.getParameter("niveau");

			if (checkParameters(matiereLibelle, niveauLibelle, request, response))
				return;

			Matiere matiere = new Matiere(null, matiereLibelle);
			Niveau niveau = new Niveau(null, niveauLibelle);
			discipline.setMatiere(matiere);
			discipline.setNiveau(niveau);
			if (DisciplineCtrl.modifierDiscipline(discipline)) {
				request.setAttribute("modifOK", true);
				session.setAttribute("listDiscipline", DisciplineCtrl.getAll());
			} else {
				request.setAttribute("modifKO", true);
			}
		}

		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_LISTE_DISCIPLINES).forward(request, response); // on
																														// reaffiche
																														// la
																														// meme
																														// page
	}

	private boolean deletePlanning(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer idDisciplineDelete = ServletUtil.getParameterIntegerValue("delete", request);
		if (idDisciplineDelete != null) {
			final Discipline discipline = DisciplineCtrl.get(idDisciplineDelete);
			if (discipline != null) {
				try {
					DisciplineCtrl.delete(discipline);
				} catch (DAOException e) {
					ResultatServlet.redirectErreur(request, response, "La discipline n'a pas été supprimée", "Cette discipline est utilisée dans un cours. Veuillez d'abord supprimer le cours.",
							Pattern.ADHERENT_LISTE_DISCIPLINES, "Retour à la liste des discipline", null, null, null);
					return true;
				}
				ResultatServlet.redirect(request, response, "Résultat", "La discipline a bien été supprimée.",
						Pattern.ADHERENT_LISTE_DISCIPLINES, "Retour à la liste des discipline", null, null, null);
				return true;
			}
		}
		return false;
	}

	private boolean checkParameters(String matiere, String niveau, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if ((matiere == null && niveau == null) || (matiere.isEmpty() && niveau.isEmpty())) {
			ResultatServlet.redirectErreur(request, response, "Erreur",
					"Vous devez remplir au moins un des deux champs !", Pattern.ADHERENT_LISTE_DISCIPLINES,
					"Retour à la liste des discipline", null, null, null);
			return true;
		}
		return false;
	}

}
