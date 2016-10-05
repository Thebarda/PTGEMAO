package fr.gemao.view.planning;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.gemao.ctrl.planning.PlanningCtrl;
import fr.gemao.entity.planning.Planning;
import fr.gemao.form.util.Form;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.ResultatServlet;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
@WebServlet(Pattern.PLANNING_CREER)
public class CreerPlanningServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher(JSPFile.PLANNING_CREER).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String nomPlanning = Form.getValeurChamp(request, "nomPlanning");
		if (nomPlanning == null) {
			ResultatServlet.redirect(request, response, "Erreur", "Le nom du planning doit être rempli !",
					Pattern.PLANNING_CREER, "Réessayer ?", null, null, null);
			return;
		}

		Date dateD = null;
		Date dateF = null;

		try {
			dateF = (Date) sdf.parse(request.getParameter("dateF"));
			dateD = (Date) sdf.parse(request.getParameter("dateD"));
		} catch (ParseException e) {
			ResultatServlet.redirect(request, response, "Résultat de la création d'un planning",
					"Le format de date n'est pas valide.", Pattern.PLANNING_CREER, "Réessayer ?", null, null, null);
			return;
		}

		java.sql.Date dataD = new java.sql.Date(dateD.getTime());
		java.sql.Date dataF = new java.sql.Date(dateF.getTime());

		Calendar calendar = GregorianCalendar.getInstance(Locale.FRANCE);
		calendar.set(dateD.getYear(), dateD.getMonth(), dateD.getDay());

		if (!dateD.before(dataF)) {
			ResultatServlet.redirect(request, response, "Résultat de la création d'un planning",
					"La date de début de " + nomPlanning + " doit être supérieure à la date de fin !",
					Pattern.PLANNING_CREER, "Réessayer ?", null, null, null);
			return;

		}

		Planning planning = new Planning(null, nomPlanning, null, dataD, dataF, Calendar.WEEK_OF_YEAR);
		// TODO gérer problème semaine avec certains décalage.

		Planning retour = PlanningCtrl.create(planning);

		if (retour == null) {
			ResultatServlet.redirect(request, response, "Résultat de la création d'un planning",
					"Le planning \"" + planning.getNomPlanning() + "\" n'a pas été ajouté.", Pattern.PLANNING_CREER,
					"Réessayer ?", null, null, null);
			return;
		}

		ResultatServlet.redirect(request, response, "Résultat de la création d'un planning",
				"Le planning \"" + retour.getNomPlanning() + "\" a bien été ajouté.", Pattern.PLANNING_LISTER,
				"Retour à la liste des plannings", null, null, null);

	}

}
