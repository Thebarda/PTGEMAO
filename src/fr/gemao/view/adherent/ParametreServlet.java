package fr.gemao.view.adherent;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.ParametreCtrl;
import fr.gemao.ctrl.administration.ModificationCtrl;
import fr.gemao.entity.Parametre;
import fr.gemao.entity.administration.Modification;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.form.ParametreForm;
import fr.gemao.view.ConnexionServlet;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class {@link ParametreServlet}
 */
@WebServlet(Pattern.ADHERENT_PARAMETRE)
public class ParametreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ParametreCtrl parametreCtrl = new ParametreCtrl();
		Parametre parametre = parametreCtrl.getLast();
		if (parametre != null) {
			request.setAttribute("alloc2", parametreCtrl.conversionDeSqlVersAffichage(parametre.getAlloc2()));
			request.setAttribute("alloc3", parametreCtrl.conversionDeSqlVersAffichage(parametre.getAlloc3()));
			request.setAttribute("alloc4", parametreCtrl.conversionDeSqlVersAffichage(parametre.getAlloc4()));
			request.setAttribute("alloc5", parametreCtrl.conversionDeSqlVersAffichage(parametre.getAlloc5()));
			request.setAttribute("qf_max", parametreCtrl.conversionDeSqlVersAffichage(parametre.getQf_max()));
			request.setAttribute("qf_min", parametreCtrl.conversionDeSqlVersAffichage(parametre.getQf_min()));
		}
		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_PARAMETRE).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ParametreForm form = new ParametreForm();
		ParametreCtrl parametreCtrl = new ParametreCtrl();
		HttpSession session = request.getSession();

		Parametre parametre = form.ajoutParametre(request);
		if (form.getErreurs().isEmpty()) {
			try {
				parametreCtrl.controlerParametre(parametre);
				// Archivage
				ModificationCtrl.ajouterModification(
						new Modification(0, (Personnel) session.getAttribute(ConnexionServlet.ATT_SESSION_USER),
								new Date(), "Modification paramètres adhérent"));
				form.setResultat("Les paramètres sont modifiés");
			} catch (Exception e) {
				form.setErreur("Parametre", e.getMessage());
				System.out.println(form.getErreurs());
			}
		} else {
			System.out.println("Erreur");
		}
		request.setAttribute("form", form);
		request.setAttribute("alloc2", parametreCtrl.conversionDeSqlVersAffichage(parametre.getAlloc2()));
		request.setAttribute("alloc3", parametreCtrl.conversionDeSqlVersAffichage(parametre.getAlloc3()));
		request.setAttribute("alloc4", parametreCtrl.conversionDeSqlVersAffichage(parametre.getAlloc4()));
		request.setAttribute("alloc5", parametreCtrl.conversionDeSqlVersAffichage(parametre.getAlloc5()));
		request.setAttribute("qf_max", parametreCtrl.conversionDeSqlVersAffichage(parametre.getQf_max()));
		request.setAttribute("qf_min", parametreCtrl.conversionDeSqlVersAffichage(parametre.getQf_min()));
		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_PARAMETRE).forward(request, response);
	}

}
