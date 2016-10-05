package fr.gemao.view.planning.contraintes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.planning.ContrainteCtrl;
import fr.gemao.ctrl.planning.PlanningCtrl;
import fr.gemao.entity.planning.Contrainte;
import fr.gemao.entity.planning.Planning;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;
import fr.gemao.view.ServletUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.CONTRAINTE_LISTE_GLOBALE)
public class ListerContrainteGlobaleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer numPlanning = ServletUtil.getParameterIntegerValue("idPlanning", request);

		Planning p = numPlanning != null ? PlanningCtrl.getPlanning(numPlanning) : null;

		if (p == null) {
			ResultatServlet.redirect(request, response, "Erreur", "Le numéro du planning doit être indiqué.",
					Pattern.PLANNING_LISTER, "Retour à la liste des plannings", null, null, null);
			return;
		}
		List<Contrainte> contraintes = ContrainteCtrl.getAllByIdPlanning(numPlanning);

		request.setAttribute("planning", p);
		request.setAttribute("contraintes", contraintes);

		this.getServletContext().getRequestDispatcher(JSPFile.CONTRAINTE_LISTER_GLOBALE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
