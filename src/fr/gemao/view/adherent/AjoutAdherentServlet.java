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
import fr.gemao.entity.adherent.FamilleTableaux;
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
		
		String tableauFicheComptable="<table><tr><td class='ignorer'></td><th class='numEleve'>Elève 1</th><th class='numEleve'>Elève 2</th><th class='numEleve'>Elève 3</th><th class='numEleve'>Elève 4</th><th class='numEleve'>Elève 5</th></tr><tr><th class='intitule'>Prénom + Nom</th><td contenteditable='true' class='nom' id='eleve_1'></td><td contenteditable='true' class='nom' id='eleve_2'></td><td contenteditable='true' class='nom' id='eleve_3'></td><td contenteditable='true' class='nom' id='eleve_4'></td><td contenteditable='true' class='nom' id='eleve_5'></td></tr><tr><th class='intitule'>QF (1, 2, 3)</th><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td><td contenteditable='true' id='qf'></td></tr><tr><th class='intitule'>FM / inst<br>Eveil / éveil inst</th><td contenteditable='true' id='lig_0_col_0'  class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_0_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>FM seule éveil</th><td contenteditable='true' id='lig_1_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_1_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>Instrument seul</th><td contenteditable='true' id='lig_2_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_2_col_4' class='sommeMensuelle'></td></tr><tr><th class='intitule'>Atelier</th><td contenteditable='true' id='lig_3_col_0' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_1' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_2' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_3' class='sommeMensuelle'></td><td contenteditable='true' id='lig_3_col_4' class='sommeMensuelle'></td><th class='intituleTotal'>Total mensuel</th></tr><tr><th class='intitule'>Total mensuel par élève</th><td class='sous_total' id='sommeMensuelle_0'></td><td class='sous_total' id='sommeMensuelle_1'></td><td class='sous_total' id='sommeMensuelle_2'></td><td class='sous_total' id='sommeMensuelle_3'></td><td class='sous_total' id='sommeMensuelle_4'></td><td class='total' id='totalMensuel'></td></tr><tr><th class='intitule'>Pratique collective</th><td contenteditable='true' id='lig_0_col_0' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_1' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_2' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_0_col_4' class='sommeAnnuelle'></td></tr><tr><th class='intitule'>Cotisation</th><td contenteditable='true' id='lig_1_col_0' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_1' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_2' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_1_col_4' class='sommeAnnuelle'></td></tr><tr><th class='intitule'>Membre admin</th><td contenteditable='true' id='lig_2_col_0' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_1' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_2' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_3' class='sommeAnnuelle'></td><td contenteditable='true' id='lig_2_col_4' class='sommeAnnuelle'></td><th class='intituleTotal'>Total annuel</th></tr><tr><th class='intitule'>Total Annuel par élève</th><td class='sous_total' id='sommeAnnuelle_0'></td><td class='sous_total' id='sommeAnnuelle_1'></td><td class='sous_total' id='sommeAnnuelle_2'></td><td class='sous_total' id='sommeAnnuelle_3'></td><td class='sous_total' id='sommeAnnuelle_4'></td><td class='total' id='totalAnnuel'></td></tr><tr><th class='intitule'>Part C.E.</th><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td><td contenteditable='true' class='ce'></td></tr><tr><td class='ignorer'></td></tr><tr><th class='intituleTotal'>Reste à la charge du client</th><td class='total' id='totalFinal'></td></tr></table>";
		String tableauRecapitulatif="<table id='tableau'><thead><tr><td class='ignorer'></td><td class='intitule'>1er Elève</td><td class='intitule'>2ème Elève</td><td class='intitule'>3ème Elève</td><td class='intitule'>4ème Elève</td><td class='intitule'>5ème Elève</td><td class='intitule'>TOTAL</td><td class='intitule'>N° de chèque</td><td class='intitule'>Montant chèque</td><td class='intitule'>Date encaissement</td><td class='intitule'>Observation</td></tr></thead><tbody><tr data='01'><td class='intitule'>SEPTEMBRE</td><td contenteditable='true' id='lig_01_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_01_col_1' class='tableauRecap'></td><td class='gris'></td><td class='gris'></td><td class='gris'></td><td id='total_01' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_01'></td><td><span id='date_01'>--/--/----</span><input type='hidden' id='datepicker_01'></td><td contenteditable='true'></td></tr><tr data='02'><td class='intitule'>OCTOBRE</td><td contenteditable='true' id='lig_02_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_02_col_4' class='tableauRecap'></td><td id='total_02' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_02'></td><td><span id='date_02'>--/--/----</span><input type='hidden' id='datepicker_02'></td><td contenteditable='true'></td></tr><tr data='03'><td class='intitule'>NOVEMBRE</td><td contenteditable='true' id='lig_03_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_03_col_4' class='tableauRecap'></td><td id='total_03' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_03'></td><td><span id='date_03'>--/--/----</span><input type='hidden' id='datepicker_03'></td><td contenteditable='true'></td></tr><tr data='04'><td class='intitule'>DECEMBRE</td><td contenteditable='true' id='lig_04_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_04_col_4' class='tableauRecap'></td><td id='total_04' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_04'></td><td><span id='date_04'>--/--/----</span><input type='hidden' id='datepicker_04'></td><td contenteditable='true'></td></tr><tr data='05'><td class='intitule'>JANVIER</td><td contenteditable='true' id='lig_05_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_05_col_4' class='tableauRecap'></td><td id='total_05' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_05'></td><td><span id='date_05'>--/--/----</span><input type='hidden' id='datepicker_05'></td><td contenteditable='true'></td></tr><tr data='06'><td class='intitule'>FEVRIER</td><td contenteditable='true' id='lig_06_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_06_col_4' class='tableauRecap'></td><td id='total_06' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_06'></td><td><span id='date_06'>--/--/----</span><input type='hidden' id='datepicker_06'></td><td contenteditable='true'></td></tr><tr data='07'><td class='intitule'>MARS</td><td contenteditable='true' id='lig_07_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_07_col_4' class='tableauRecap'></td><td id='total_07' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_07'></td><td><span id='date_07'>--/--/----</span><input type='hidden' id='datepicker_07'></td><td contenteditable='true'></td></tr><tr data='08'><td class='intitule'>AVRIL</td><td contenteditable='true' id='lig_08_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_08_col_4' class='tableauRecap'></td><td id='total_08' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_08'></td><td><span id='date_08'>--/--/----</span><input type='hidden' id='datepicker_08'></td><td contenteditable='true'></td></tr><tr data='09'><td class='intitule'>MAI</td><td contenteditable='true' id='lig_09_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_09_col_4' class='tableauRecap'></td><td id='total_09' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_09'></td><td><span id='date_09'>--/--/----</span><input type='hidden' id='datepicker_09'></td><td contenteditable='true'></td></tr><tr data='10'><td class='intitule'>JUIN</td><td contenteditable='true' id='lig_10_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_10_col_4' class='tableauRecap'></td><td id='total_10' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_10'></td><td><span id='date_10'>--/--/----</span><input type='hidden' id='datepicker_10'></td><td contenteditable='true'></td></tr><tr id='ajoutLigne_0'  data='11'><td class='intitule'>PART C.E.</td><td contenteditable='true' id='lig_11_col_0' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_1' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_2' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_3' class='tableauRecap'></td><td contenteditable='true' id='lig_11_col_4' class='tableauRecap'></td><td id='total_11' class='sous_total'></td><td contenteditable='true'></td><td contenteditable='true' class='sommePayee' id='paiement_11'></td><td><span id='date_11'>--/--/----</span><input type='hidden' id='datepicker_11'></td><td contenteditable='true'></td></tr></tbody><tfoot><tr><td class='ignorer' rowspan='1'><input type='button' id='ajoutTab' class='button2' value='Ajouter ligne'></button></td></tr><tr><td class='ignorer'></td></tr><tr><td class='intituleTotal'>Total restant à payer</td><td id='totalRestant'></td></tr></tfoot></table>";
		
		Adherent adherent = new Adherent(null, null, null, nom, prenom, dateNaiss, telFixe, telPortable, email,
				Civilite.MONSIEUR, Boolean.parseBoolean(membreCA), null, null, Boolean.parseBoolean(droitImage),
				dateInscri, null, null, 0.0f, list, null, null, false, new Famille(null, famille));
		Date date = new Date();
		FamilleTableaux famtab=null;
		if((date.getMonth()>=0)&&(date.getMonth()<=7)){
			famtab = new FamilleTableaux(null, (1900+date.getYear()-1), tableauFicheComptable, tableauRecapitulatif);
		}else{
			famtab = new FamilleTableaux(null, (1900+date.getYear()), tableauFicheComptable, tableauRecapitulatif);
		}
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
		session.setAttribute("familleTableaux", famtab);

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
