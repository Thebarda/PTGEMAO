package fr.gemao.view.adherent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.planning.DisciplineCtrl;
import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.adherent.Famille;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.util.Civilite;
import fr.gemao.form.adherent.AdherentForm;
import fr.gemao.sql.CommuneDAO;
import fr.gemao.sql.DAOFactory;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.util.AutocompletionAdresse;
import fr.gemao.view.util.AutocompletionCommune;
import fr.gemao.view.util.AutocompletionFamille;

/**
 * Servlet implementation class AjoutAdherent
 */
@WebServlet(Pattern.ADHERENT_AJOUT)
public class AjoutAdherentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		/*
		 * for (String i :request.getParameterValues(getServletName())) {
		 * System.out.println(i); }
		 */
		session.setAttribute("listDiscipline", DisciplineCtrl.getAll());

		request = AutocompletionCommune.initRequestForAutoCompletionCommune(request);
		request = AutocompletionAdresse.initRequestForAutoCompletionAdresse(request);
		request = AutocompletionFamille.initRequestForAutocompletionFamille(request);

		if (request.getParameter("errDate") != null) {
			request.setAttribute("errDate", true);
		} else {
			session.setAttribute("ajout_adh_adherent", null);
			session.setAttribute("ajout_adh_commune", null);
			session.setAttribute("ajout_adh_adresse", null);
			session.setAttribute("ajout_adh_responsable", null);
		}

		this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_AJOUT_ADHERENT).forward(request, response);
		session.removeAttribute("errAdhVide");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AdherentForm adherentForm = new AdherentForm();
		HttpSession session = request.getSession();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		adherentForm.testerAdherent(request);

		/**
		 * Recuperation des données concernant l'adhérent
		 */
		String nom = adherentForm.getNom();
		String prenom = adherentForm.getPrenom();
		String famille = adherentForm.getFamille();
		String dateNaissance = adherentForm.getDateNaissance();
		String telFixe = adherentForm.getTelFixe();
		String telPortable = adherentForm.getTelPort();
		String email = adherentForm.getEmail();
		String droitImage = adherentForm.getDroitImage();
		String membreCA = adherentForm.getMembreCA();
		String dateInscription = adherentForm.getDateEntree();
		String civilite = request.getParameter("civilite");

		/**
		 * Création de l'adhérent
		 */
		Date dateNaiss = new Date();
		Date dateInscri = new Date();
		try {
			dateNaiss = formatter.parse(dateNaissance);
			dateInscri = formatter.parse(dateInscription);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Discipline> list = new ArrayList<>();
		Adherent adherent = new Adherent(null, null, null, nom, prenom, dateNaiss, telFixe, telPortable, email,
				Civilite.MONSIEUR, Boolean.parseBoolean(membreCA), null, null, Boolean.parseBoolean(droitImage),
				dateInscri, null, null, 0.0f, list, null, null, false, new Famille(null, famille));
		if (civilite.equals("F")) {
			adherent.setCivilite(Civilite.MADAME);
		}
		List<Discipline> listDiscipline = adherentForm.getDisciplines();
		adherent.setDisciplines(listDiscipline);

		/**
		 * Réupération des données de la commune
		 */
		String com = adherentForm.getNomCommune();
		Integer codePostal = adherentForm.getCodePostal();

		/**
		 * Création de la commune
		 */
		Commune commune = new Commune(null, codePostal, com, false);
		CommuneDAO communeDAO = DAOFactory.getInstance().getCommuneDAO();
		Commune c = communeDAO.existNomCodePostal(commune);
		if (c != null) {
			commune = c;
		}

		/**
		 * Réupération des données de l'adresse
		 */
		String numAdresse = adherentForm.getNumRue();
		String rueAdresse = adherentForm.getNomRue();
		String complAdresse = adherentForm.getInfoCompl();

		/**
		 * Création de l'adresse
		 */
		Adresse adresse = new Adresse(null, null, numAdresse, rueAdresse, complAdresse);

		session.setAttribute("ajout_adh_adherent", adherent);
		session.setAttribute("ajout_adh_commune", commune);
		session.setAttribute("ajout_adh_adresse", adresse);

		Calendar dateMineur = Calendar.getInstance();
		dateMineur.set(dateMineur.get(Calendar.YEAR) - 18, dateMineur.get(Calendar.MONTH),
				dateMineur.get(Calendar.DAY_OF_MONTH));

		if (adherentForm.getErreurs().isEmpty()) {

			if (dateNaiss.after(dateMineur.getTime())) {
				response.sendRedirect(request.getContextPath() + Pattern.ADHERENT_AJOUT_RESPONSABLE);
			} else {
				response.sendRedirect(request.getContextPath() + Pattern.ADHERENT_SAISIE_COTISATION);
			}
		} else {
			response.sendRedirect("/GEMAO" + Pattern.ADHERENT_AJOUT + "?errDate=1");
		}

	}

}
