package fr.gemao.view.adherent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import fr.gemao.ctrl.ParametreCtrl;
import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.adherent.ResponsableCtrl;
import fr.gemao.ctrl.planning.DisciplineCtrl;
import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.entity.Parametre;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.adherent.Famille;
import fr.gemao.entity.adherent.Responsable;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.util.Civilite;
import fr.gemao.form.adherent.AdherentForm;
import fr.gemao.sql.DAOFactory;
import fr.gemao.view.JSPFile;
import fr.gemao.view.Pattern;
import fr.gemao.view.util.AutocompletionAdresse;
import fr.gemao.view.util.AutocompletionCommune;
import fr.gemao.view.util.AutocompletionFamille;

/**
 * Servlet implementation class ModifAdherentServlet
 */
@WebServlet(Pattern.ADHERENT_MODIFIER)
public class ModifAdherentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public final String PARAM_DATE_NAISSANCE = "dateNaissance";
	public final String PARAM_ADHERENT = "adherent";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.setAttribute("modif_adh_adherent", null);

		if (request.getParameter("id") == null) {
			request.setAttribute("lien", Pattern.ADHERENT_MODIFIER);
			List<Adherent> adherents = AdherentCtrl.recupererTousAdherents();
			ParametreCtrl parametreCtrl = new ParametreCtrl();
			Parametre param = parametreCtrl.getLast();
			request.setAttribute("params", param);
			request.setAttribute("listeAdherents", adherents);
			this.getServletContext().getRequestDispatcher(Pattern.ADHERENT_LISTER).forward(request, response);
		} else {
			int id = Integer.parseInt(request.getParameter("id"));
			if (request.getParameter("errDate") != null) {
				request.setAttribute("errDate", true);
			}
			Adherent adherent = AdherentCtrl.recupererAdherent(id);

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dateNaissance = formatter.format(adherent.getDateNaissance());
			String dateInscription = formatter.format(adherent.getDateEntree());

			request = AutocompletionCommune.initRequestForAutoCompletionCommune(request);
			request = AutocompletionAdresse.initRequestForAutoCompletionAdresse(request);
			request = AutocompletionFamille.initRequestForAutocompletionFamille(request);

			session.setAttribute("modif_adh_adherent", adherent);
			request.setAttribute("adherent", adherent);
			request.setAttribute("dateNaissance", dateNaissance);
			request.setAttribute("dateInscription", dateInscription);
			session.setAttribute("listDiscipline", DisciplineCtrl.getAll());
			this.getServletContext().getRequestDispatcher(JSPFile.ADHERENT_MODIFIER_ADHERENT).forward(request,
					response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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
			String famille = adherentForm.getFamille();
			adherent.setFamille(new Famille(null, famille, null, null));

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

			DAOFactory factory = DAOFactory.getInstance();
			commune = factory.getCommuneDAO().existNomCodePostal(commune);

			if (adherent.getQf() != null)
				if (!commune.isAvantage() || !adherent.isMineur())
					adherent.setQf(null);

			String cotisation = request.getParameter("cotisation");
			adherent.setCotisation(Float.parseFloat(cotisation));

			adherent.setAdresse(adresse);

			List<Discipline> listDiscipline = adherentForm.lireDisciplines(request);
			adherent.setDisciplines(listDiscipline);

			String droitImage = request.getParameter("droitImage");
			adherent.setDroitImage(Boolean.parseBoolean(droitImage));

			String membreCA = request.getParameter("membreCA");
			adherent.setMembreCA(Boolean.parseBoolean(membreCA));

			if (!adherent.isMineur())
				adherent.setResponsable(null);

			if (adherent.getResponsable() != null) {
				Responsable responsable = adherent.getResponsable();
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

			session.setAttribute("modif_adh_adherent", adherent);
			response.sendRedirect(request.getContextPath() + Pattern.ADHERENT_VALIDATION_MODIF);
		} else {
			System.out.println(adherentForm.getErreurs());
			System.out.println("Erreur !");

			response.sendRedirect("/GEMAO" + Pattern.ADHERENT_MODIFIER + "?errDate=1&id=" + adherent.getIdPersonne());
		}
	}
}
