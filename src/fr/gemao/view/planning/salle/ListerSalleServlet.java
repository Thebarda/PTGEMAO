package fr.gemao.view.planning.salle;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.planning.SalleCtrl;
import fr.gemao.entity.cours.Salle;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;
import fr.gemao.view.ServletUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.SALLE_LISTER)
public class ListerSalleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Salle> listeSalle = SalleCtrl.getAll();

		if (listeSalle.isEmpty()) {
			ResultatServlet.redirectErreur(request, response, "Aucune salle enregistréé",
					"Il n'y a pas de salle enregistrée !", Pattern.SALLE_CREER, "Retour à la création de salle", null,
					null, null);
			return;
		}

		if (deleteSalle(request, response)) {
			return;
		}

		request.setAttribute("salles", listeSalle);
		this.getServletContext().getRequestDispatcher(JSPFile.SALLE_LISTER).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean deleteSalle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer idSalleDelete = ServletUtil.getParameterIntegerValue("idSalleDelete", request);
		if (idSalleDelete != null) {
			final Salle salle = SalleCtrl.getSalle(idSalleDelete);
			if (salle != null) {
				try {
					SalleCtrl.delete(salle);
					ResultatServlet.redirect(request, response, "Resultat", "La salle a bien été supprimée.",
							Pattern.SALLE_LISTER, "Retour à la liste des salles", null, null, null);
					return true;
				} catch (DAOException e) {
					ResultatServlet.redirect(request, response, "Erreur",
							"Impossible de supprimer la salle, elle est utilisée par d'autres ressources...",
							Pattern.SALLE_LISTER, "Retour à la liste des salles", null, null, null);
					return true;
				}
			}
		}
		return false;
	}
}
