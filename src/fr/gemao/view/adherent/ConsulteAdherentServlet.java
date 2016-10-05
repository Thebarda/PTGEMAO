package fr.gemao.view.adherent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.ParametreCtrl;
import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.entity.Parametre;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class ConsulteAdherentServlet
 */
@WebServlet(Pattern.ADHERENT_CONSULTER)
public class ConsulteAdherentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public final String PARAM_DATE_NAISSANCE = "dateNaissance";
	public final String PARAM_DATE_INSCRIPTION = "dateInscription";
	public final String PARAM_ADHERENT = "adherent";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("id") == null) {
			request.setAttribute("lien", "/adherent/ConsulteAdherent");
			List<Adherent> adherents = AdherentCtrl.recupererTousAdherents();
			ParametreCtrl parametreCtrl = new ParametreCtrl();
			Parametre param = parametreCtrl.getLast();
			request.setAttribute("params", param);
			request.setAttribute("listeAdherents", adherents);
			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_LISTER).forward(request, response);
		} else {

			int id = Integer.parseInt(request.getParameter("id"));
			Adherent adherent = AdherentCtrl.recupererAdherent(id);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateNaissance = formatter.format(adherent.getDateNaissance());
			String dateInscription = formatter.format(adherent.getDateEntree());

			request.setAttribute("adherent", adherent);
			request.setAttribute("dateNaissance", dateNaissance);
			request.setAttribute("dateInscription", dateInscription);
			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CONSULTER).forward(request, response);
		}
	}

}
