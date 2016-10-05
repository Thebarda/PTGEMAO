package fr.gemao.view.adherent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.gemao.ctrl.AdresseCtrl;
import fr.gemao.ctrl.CommuneCtrl;
import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.adherent.ResponsableCtrl;
import fr.gemao.ctrl.planning.DisciplineCtrl;
import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.adherent.Responsable;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.util.Civilite;
import fr.gemao.form.adherent.AdherentForm;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;

@WebServlet(Pattern.ADHERENT_REINSCRIRE)
public class ReinscrireAdherentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("modif_adh_adherent", null);

		if (request.getParameter("id") == null) {
			// Accès illégal à cette page
			request.getRequestDispatcher(Pattern.ACCUEIL).forward(request, response);
		} else {
			int id = Integer.parseInt(request.getParameter("id"));
			Adherent adherent = AdherentCtrl.recupererAdherent(id);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateNaissance = formatter.format(adherent.getDateNaissance());
			String dateInscription = formatter.format(adherent.getDateEntree());

			Calendar dateMineur = Calendar.getInstance();
			dateMineur.set(dateMineur.get(Calendar.YEAR) - 18, dateMineur.get(Calendar.MONTH),
					dateMineur.get(Calendar.DAY_OF_MONTH));

			// Si plus mineur
			if (!adherent.getDateNaissance().after(dateMineur.getTime())) {
				adherent.setResponsable(null);
			}

			session.setAttribute("modif_adh_adherent", adherent);
			request.setAttribute("adherent", adherent);
			request.setAttribute("dateNaissance", dateNaissance);
			request.setAttribute("dateInscription", dateInscription);
			session.setAttribute("listDiscipline", DisciplineCtrl.getAll());
			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_REINSCRIRE).forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Adherent adherent = (Adherent) session.getAttribute("modif_adh_adherent");

		AdherentForm adherentForm = new AdherentForm();

		adherentForm.testerAdherent(request);

		if (adherentForm.getErreurs().isEmpty()) {

			String nom = adherentForm.getNom();
			adherent.setNom(nom);
			String prenom = adherentForm.getPrenom();
			adherent.setPrenom(prenom);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateNaiss = null;
			Date dateEntree = null;
			try {
				dateNaiss = formatter.parse(adherentForm.getDateNaissance());
				dateEntree = formatter.parse(adherentForm.getDateEntree());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			adherent.setDateNaissance(dateNaiss);
			adherent.setDateEntree(dateEntree);

			String civilite = request.getParameter("civilite");
			if (civilite.equals("F")) {
				adherent.setCivilite(Civilite.MADAME);
			} else {
				adherent.setCivilite(Civilite.MONSIEUR);
			}

			String telFixe = request.getParameter("telFixe");
			adherent.setTelFixe(telFixe);
			String telPort = request.getParameter("telPort");
			adherent.setTelPort(telPort);
			String email = request.getParameter("email");
			adherent.setEmail(email);

			String nomCommune = request.getParameter("commune");
			Integer codePostal = Integer.parseInt(request.getParameter("codePostal"));
			Commune commune = new Commune(null, codePostal, nomCommune, false);

			CommuneCtrl.ajoutCommune(commune);

			Adresse adresse = new Adresse();
			String numRue = request.getParameter("num");
			adresse.setNumRue(numRue);
			String nomRue = request.getParameter("rue");
			adresse.setNomRue(nomRue);
			String infoCompl = request.getParameter("compl");
			adresse.setInfoCompl(infoCompl);
			adresse.setCommune(commune);
			AdresseCtrl.ajoutAdresse(adresse);

			if (adherent.getQf() != null && !adherent.getAdresse().equals(adresse))
				adherent.setQf(null);

			adherent.setAdresse(adresse);

			List<Discipline> listDiscipline = adherentForm.lireDisciplines(request);
			adherent.setDisciplines(listDiscipline);
			System.out.println(adherent.getDisciplines());

			String droitImage = request.getParameter("droitImage");
			adherent.setDroitImage(Boolean.parseBoolean(droitImage));

			String membreCA = request.getParameter("membreCA");
			adherent.setMembreCA(Boolean.parseBoolean(membreCA));

			if (adherent.getResponsable() != null) {
				Responsable responsable = adherent.getResponsable();
				System.out.println(responsable);
				String nomResp = request.getParameter("nomResp");
				responsable.setNom(nomResp);
				String prenomResp = request.getParameter("prenomResp");
				responsable.setPrenom(prenomResp);
				String telResp = request.getParameter("telResp");
				responsable.setTelephone(telResp);
				String emailResp = request.getParameter("emailResp");
				responsable.setEmail(emailResp);

				ResponsableCtrl.modifierResponsable(responsable);
			}

			session.setAttribute("ajout_adh_adherent", adherent);
			session.setAttribute("reinscription", true);
			if (adherent.getResponsable() != null && adresse.getCommune().isAvantage()) {
				response.sendRedirect(request.getContextPath() + Pattern.ADHERENT_CALCUL_QF);
			} else {
				response.sendRedirect(request.getContextPath() + Pattern.ADHERENT_SAISIE_COTISATION);
			}

		} else {
			System.out.println(adherentForm.getErreurs());
			System.out.println("Erreur !");

			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_LISTER).forward(request, response);
		}
	}
}
