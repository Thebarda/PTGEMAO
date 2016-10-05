package fr.gemao.view.materiel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.materiel.MaterielCtrl;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.MATERIEL_CONSULTER)
public class ConsulterMaterielServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Chargement de la page de modification. Le parametre idMateriel doit etre
	 * envoyé pour le doGet (l'id correspond a celui du materiel a modifier.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String param = request.getParameter("idMateriel");

		if (param != null) {
			int idParametre = Integer.parseInt(param);
			MaterielCtrl matctrl = new MaterielCtrl();
			Materiel mat = matctrl.recupererMateriel(idParametre);
			session.setAttribute("sessionObjectMateriel", mat);

			this.getServletContext().getRequestDispatcher(JSPFile.MATERIEL_CONSULTER)
					.forward(request, response);
		} else {
			this.getServletContext().getRequestDispatcher(Pattern.MATERIEL_LISTER)
					.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}
}