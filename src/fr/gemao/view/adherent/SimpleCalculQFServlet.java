package fr.gemao.view.adherent;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.ParametreCtrl;
import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.entity.Parametre;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

/**
 * Servlet implementation class SimpleCalculQFServlet
 */
@WebServlet(Pattern.ADHERENT_SIMPLE_CALCUL_QF)
public class SimpleCalculQFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CALCUL_QF).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Float simpleQuotient = AdherentCtrl.calculerQuotient(request);
		if (simpleQuotient != null) {
			ParametreCtrl parametreCtrl = new ParametreCtrl();
			Parametre parametre = parametreCtrl.getLast();
			String QF = "";
			if (parametre.getQf_min() > simpleQuotient)
				QF = "Quotient 3";
			else if (parametre.getQf_max() > simpleQuotient)
				QF = "Quotient 2";
			else
				QF = "Quotient 1";

			DecimalFormat df = new DecimalFormat("########.00");
			String str = df.format(simpleQuotient);
			simpleQuotient = Float.parseFloat(str.replace(',', '.'));

			request.setAttribute("QF", QF);
			request.setAttribute("simpleQuotient", simpleQuotient);

			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CALCUL_QF).forward(request, response);
		} else {
			request.setAttribute("erreurPers", true);
			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_CALCUL_QF).forward(request, response);
		}
	}

}
